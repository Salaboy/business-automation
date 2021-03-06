
package org.alfresco.decision.tree.infra.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.parse.Parser;
import javassist.CannotCompileException;

import org.alfresco.decision.tree.infra.impl.QuickTree;
import org.alfresco.decision.tree.model.api.*;
import org.alfresco.decision.tree.model.api.fluent.TreeFluent;
import org.alfresco.decision.tree.model.impl.*;
import org.alfresco.decision.tree.model.impl.handlers.PrintoutHandler;
import org.junit.Test;

import static org.alfresco.decision.tree.model.api.Path.Operator.EQUALS;
import static org.alfresco.decision.tree.model.api.Path.Operator.GREATER_THAN;
import static org.alfresco.decision.tree.model.api.Path.Operator.LESS_THAN;
import static org.junit.Assert.assertNotNull;


/**
 * @author salaboy
 */
public class TreeModelParsingTest {


    @Test
    public void hello() throws CannotCompileException, InstantiationException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {



        ConditionalNode ageNode = new ConditionalNodeImpl("n0", "age");

        Tree t = new TreeImpl("my person tree", Person.class, ageNode);

        Path lt30Path = new PathImpl(LESS_THAN, "30");
        ConditionalNode cityNode = new ConditionalNodeImpl("n1", "city");
        lt30Path.setNodeTo(cityNode);
        Node endNodeDoesntApply = new EndNodeImpl("end0", "Doesn't Apply");

        Path mendozaPath = new PathImpl(EQUALS, "Mendoza");
        mendozaPath.setNodeTo(endNodeDoesntApply);
        Path londonPath = new PathImpl(EQUALS, "London");

        ConditionalNode marriedNode = new ConditionalNodeImpl("n2", "married");
        londonPath.setNodeTo(marriedNode);
        cityNode.addPath(mendozaPath);
        cityNode.addPath(londonPath);
        ageNode.addPath(lt30Path);
        Path gt30Path = new PathImpl(GREATER_THAN, "30");
        ageNode.addPath(gt30Path);
        Node endNodeTooOld = new EndNodeImpl("end1", "Too Old");
        gt30Path.setNodeTo(endNodeTooOld);
        Path marriedPath = new PathImpl(EQUALS, "true");
        Path notMarriedPath = new PathImpl(EQUALS, "false");
        Node endNodeSendAd2 = new EndNodeImpl("end2", "Send Ad 2");
        marriedPath.setNodeTo(endNodeSendAd2);
        Node endNodeSendAd1 = new EndNodeImpl("end3", "Send Ad 1");

        notMarriedPath.setNodeTo(endNodeSendAd1);
        marriedNode.addPath(marriedPath);
        marriedNode.addPath(notMarriedPath);


        String generated = QuickTree.generateCode(t);

        System.out.println("Generated Code: \n " + generated);

        TreeInstance treeInstance = QuickTree.createTreeInstance(generated);

//        Map<String, Handler> handlers = new HashMap<>();
//        handlers.put("Send Ad 1", new PrintoutHandler("Sending Ad 1..."));
//        handlers.put("Send Ad 2", new PrintoutHandler("Sending Ad 2..."));
//        handlers.put("Too Old", new PrintoutHandler("Too Old for me..."));
//        handlers.put("Doesn't Apply", new PrintoutHandler("City Doesn't apply..."));
        List<Handler> handlers = new ArrayList<>();
        handlers.add(new PrintoutHandler());

        treeInstance.eval(new Person("London", 17, true), handlers);

        treeInstance.eval(new Person("London", 31, true), handlers);

        String json = generateJson(t);

        System.out.println("JSON: \n" + json);

        String grapviz = generateGraphviz(t);

        System.out.println("Graphviz: \n" + grapviz);

    }

    @Test
    public void fluentAPITest() throws RuntimeException, IllegalAccessException, InstantiationException, CannotCompileException, NoSuchFieldException {
        Tree t = new TreeFluent().newTree( "my person tree", Person.class)
                .condition("age")
                .path(LESS_THAN, "30")
                .condition("city")
                .path(EQUALS, "Mendoza").end("Doesn't Apply")
                .path(EQUALS, "London")
                .condition("married")
                .path(EQUALS, "true").end("Send Ad 2")
                .path(EQUALS, "false").end("Send Ad 1")
                .endCondition()
                .endCondition()
                .path(GREATER_THAN, "30").end("Too Old")
                .endCondition()
                .build();
        String generated = QuickTree.generateCode(t);

        System.out.println("Generated Code: \n " + generated);

        TreeInstance treeInstance = QuickTree.createTreeInstance(generated);


        List<Handler> handlers = new ArrayList<>();
        handlers.add(new PrintoutHandler());
        treeInstance.eval(new Person("London", 17, true), handlers);

    }

    private String generateJson(Tree t) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(t);

    }





    /*
    *        digraph G {
    *           rankdir=LR;
    *           "Person" -> "Age?"
    *           "Age?" -> "City?"  [label = ">30"]
    *           "Age?" -> "Too Old"  [label = "<30", style = "bold"]
    *           "City?" -> "London?"
    *           "City?" -> "Mendoza?"
    *           "Mendoza?" -> "Don't apply" [style = "bold"]
    *           "London?" -> "Married?"
    *           "Married?" -> "Send Ad 1"  [label = "false", style = "bold"]
    *           "Married?" -> "Send Ad 2"  [label = "true", style = "bold"]
    *}
     */
    private String generateGraphviz(Tree t) throws NoSuchFieldException {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n \t rankdir=LR; \n");
        Node node = t.rootNode();
        sb.append("\t \"").append(t.clazz().getName()).append("\" -> \"").append(node.name()).append("?\" \n");

        evalNode(t.clazz(), node, sb);

        sb.append("}");
        return sb.toString();
    }

    private void evalNode(Class type, Node node, StringBuilder sb) throws NoSuchFieldException {
        if (node instanceof ConditionalNode) {
            for (Path p : ((ConditionalNode) node).paths()) {
                sb.append("\t \"").append(node.name()).append("?\" -> \"").append(p.nodeTo().name())
                        .append("?\" [label = \"");
                Class<?> fieldType = type.getDeclaredField(node.name()).getType();
                String operatorString = QuickTree.resolveOperatorBasedOnType(p, fieldType, true);
                sb.append(operatorString);
                sb.append("\"] \n");
                evalNode(type, p.nodeTo(), sb);

            }
        } else if (node instanceof EndNode) {
            // do some styling here.. 
        }
    }

    @Test
    public void graphvizParserTest() throws IOException {
        String model =  "digraph G { rankdir=LR;" +
                "\"Person\" -> \"Age?\" " +
                "\"Age?\" -> \"City?\"  [label = \">30\"]" +
                "\"Age?\" -> \"Too Old\"  [label = \"<30\", style = \"bold\"]" +
                "\"City?\" -> \"London?\"" +
                "\"City?\" -> \"Mendoza?\"" +
                "\"Mendoza?\" -> \"Don't apply\" [style = \"bold\"]" +
                "\"London?\" -> \"Married?\"" +
                "\"Married?\" -> \"Send Ad 1\"  [label = \"false\", style = \"bold\"]" +
                "\"Married?\" -> \"Send Ad 2\"  [label = \"true\", style = \"bold\"]" +
                "}";
        MutableGraph graphvizModel = Parser.read(model);

        assertNotNull(graphvizModel);
        System.out.println("- Graph label: " + graphvizModel.isDirected());
        System.out.println("- Graph is directed: " + graphvizModel.isDirected());
        System.out.println("- Graph is cluster: " + graphvizModel.isCluster());

        for(MutableNode n : graphvizModel.allNodes()){
            System.out.println(">>> Start Node: " + n.label());
            for(Link l: n.links()) {
                System.out.println("Link -> to: " + l.to().toString());
                Map<String, Object> stringObjectHashMap = new HashMap<>();
                Map<String, Object> stringObjectMap = l.attrs().applyTo(stringObjectHashMap);
                System.out.println("Link ->  attrs: " +stringObjectMap.get("label"));
            }
            System.out.println(">>> End Node: " + n.label());

        }



    }

}
