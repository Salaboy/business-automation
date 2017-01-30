package org.alfresco.decision.tree.infra.impl;

import javassist.*;
import org.alfresco.decision.tree.model.api.*;

import java.util.Collection;
import java.util.UUID;
import static org.alfresco.decision.tree.model.api.Path.Operator.*;

/**
 * Created by salaboy on 21/11/2016.
 */
public class QuickTree {

    public static TreeInstance createTreeInstance(String generated) throws RuntimeException, IllegalAccessException, InstantiationException, CannotCompileException, CannotCompileException {
        ClassPool pool = ClassPool.getDefault();
        CtClass evalClass = pool.makeClass("TreeInstanceImpl" + UUID.randomUUID().toString());
        evalClass.addInterface(pool.makeClass("org.alfresco.decision.tree.model.api.TreeInstance"));
        CtMethod method = CtNewMethod.make(generated, evalClass);
        evalClass.addMethod(method);
        Class clazz = evalClass.toClass();
        TreeInstance treeInstance = (TreeInstance) clazz.newInstance();
        return treeInstance;
    }

    public static String generateCode(Tree t) throws NoSuchFieldException {
        StringBuilder sb = new StringBuilder();
        Node rootNode = t.rootNode();
        sb.append("public void eval( ").append(" Object instance")
                .append(", java.util.Map handlers) { \n");
        go(t.clazz(), rootNode, sb, 0);
        sb.append("}\n");
        return sb.toString();
    }

    private static void go(Class type, Node node, StringBuilder sb, int level) throws NoSuchFieldException {
        StringBuilder tabs = new StringBuilder();
        for (int i = 0; i < level; i++) {
            tabs.append("\t");
        }
        if (node instanceof RootNode) {
            Path onlyPath = ((RootNode) node).path();
            go(type, onlyPath.nodeTo(), sb, level + 1);
        }
        if (node instanceof ConditionalNode) {
            int i = 0;
            Collection<Path> paths = ((ConditionalNode) node).paths();
            for (Path path : paths) {
                if (i == 0) {
                    sb.append(tabs.toString());
                }
                sb.append("if ( ").append("((").append(type.getCanonicalName()).append(")instance).").append("get")
                        .append(node.name().substring(0, 1).toUpperCase()).append(node.name().substring(1)).append("( )");
                //Figure out operator based on type:
                Class<?> fieldType = type.getDeclaredField(node.name()).getType();
                String operatorString = resolveOperatorBasedOnType(path, fieldType, false);

                sb.append(operatorString);

                sb.append(" ) { \n");
                go(type, path.nodeTo(), sb, level + 1);
                if ((paths.size() == 1 && i == 0) || paths.size() == i + 1) {
                    sb.append(tabs.toString()).append(" }\n");
                } else {
                    sb.append(tabs.toString()).append(" } else ");
                }
                i++;
            }
        } else if (node instanceof EndNode) {
            sb.append(tabs.toString()).append("((org.alfresco.decision.tree.model.api.Handler)").append("handlers.get( \"").append(node.name()).append("\" )).execute();\n");

        }

    }

    public static String resolveOperatorBasedOnType(Path path, Class<?> fieldType, boolean friendly) {
        StringBuilder operatorSb = new StringBuilder();
        switch (path.operator()) {
            case GREATER_THAN:
                if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
                    operatorSb.append(".intValue() > ").append(path.condition());

                }
                break;
            case LESS_THAN:
                if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
                    operatorSb.append(".intValue() < ").append(path.condition());
                }
                break;
            case EQUALS:
                if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
                    if (!friendly) {
                        operatorSb.append(".booleanValue() ==").append(path.condition());
                    } else {
                        operatorSb.append(path.condition());
                    }
                } else if (fieldType.equals(String.class)) {
                    if (!friendly) {
                        operatorSb.append(".equals(\"").append(path.condition()).append("\")");
                    } else {
                        operatorSb.append(path.condition());
                    }
                }
                break;
        }
        return operatorSb.toString();
    }
}
