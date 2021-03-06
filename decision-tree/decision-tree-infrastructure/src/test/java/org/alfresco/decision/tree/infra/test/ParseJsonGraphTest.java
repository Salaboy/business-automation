

package org.alfresco.decision.tree.infra.test;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;


import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;
import javassist.*;

import org.alfresco.decision.tree.infra.impl.PojoGenerator;
import org.alfresco.decision.tree.infra.impl.QuickTree;
import org.alfresco.decision.tree.model.api.*;
import org.alfresco.decision.tree.model.impl.*;
import org.alfresco.decision.tree.model.impl.handlers.PrintoutHandler;
import org.apache.commons.io.FileUtils;


import org.apache.commons.io.filefilter.IOFileFilter;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import static org.junit.Assert.assertTrue;


/**
 * @author salaboy
 */
public class ParseJsonGraphTest {


    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    @Test
    public void createJsonPojoAndGSonParsingTest() throws IOException, NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException {

       JCodeModel codeModel = new JCodeModel();

        GenerationConfig config = new DefaultGenerationConfig() {

            @Override
            public String getTargetPackage() {
                return "org.alfresco.generated";
            }

            @Override
            public boolean isIncludeConstructors() {
                return true;
            }

            @Override
            public boolean isIncludeAccessors() {
                return true;
            }

            @Override
            public boolean isUseJodaDates() {
                return true;
            }



            @Override
            public SourceType getSourceType() {
                return SourceType.JSON;
            }

            @Override
            public boolean isIncludeHashcodeAndEquals() {
                return false;
            }

            @Override
            public boolean isIncludeAdditionalProperties() {
                return false;
            }

            @Override
            public boolean isIncludeToString() {
                return false;
            }
        };

        String json = " {\n" +
                "        \"age\": 25,\n" +
                "        \"married\": \"false\",\n" +
                "        \"city\" : \"London\"\n" +
                "    }";
        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new NoopAnnotator(), new SchemaStore()), new SchemaGenerator());
        JType jType = mapper.generate(codeModel, "ClassName", "org.alfresco.generated", json);


        File dir = tempFolder.newFolder("output");
        codeModel.build(dir);

        String[] exts = {"java"};
        Collection<File> files = FileUtils.listFiles(dir, exts , true);
        for(File f : files){
            // Read it from temp file
            final String s = FileUtils.readFileToString(f);
            System.out.println(s);
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            int result = compiler.run(null, null, null, f.getAbsolutePath());

            System.out.println("Compile result code = " + result);

            ClassPool pool = ClassPool.getDefault();
            File file = new File(f.getAbsolutePath().replace("java", "class"));
            assertTrue(file.exists());

            CtClass cc = pool.makeClass(FileUtils.openInputStream(file));

            System.out.println(cc);

            Class aClass = cc.toClass();

            System.out.println(aClass);
            Gson gson = new GsonBuilder().create();
            Object o1 = gson.fromJson(json, aClass);






        }







    }

    @Test
    @Ignore
    public void hello() throws FileNotFoundException, ClassNotFoundException, NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Gson gson = new GsonBuilder().create();

        InputStream modelStream = ParseJsonGraphTest.class.getResourceAsStream("/model.json");
        JsonElement jsonElement = gson.fromJson(new InputStreamReader(modelStream), JsonElement.class);
        JsonObject jsonObj = jsonElement.getAsJsonObject();
        Map<String, Class<?>> fields = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonObj.entrySet()) {
            if (!entry.getKey().equals("type")) {
                fields.put(entry.getKey(), Class.forName(entry.getValue().getAsString()));
            }
        }


        Class type = PojoGenerator.generate("org.alfresco.decision.service.generated."+jsonObj.get("type").getAsString(), fields);

        InputStream graphStream = ParseJsonGraphTest.class.getResourceAsStream("/graph.json");


        jsonElement = gson.fromJson(new InputStreamReader(graphStream), JsonElement.class);
        jsonObj = jsonElement.getAsJsonObject();
        JsonArray nodesArray = jsonObj.getAsJsonArray("nodes");

        Tree t = null;
        Iterator<JsonElement> iteratorNodes = nodesArray.iterator();
        Map<String, Node> nodes = new HashMap<>();
        String clazzType = "";
        while (iteratorNodes.hasNext()) {
            JsonElement next = iteratorNodes.next();
            String id = next.getAsJsonObject().get("data").getAsJsonObject().get("id").getAsString();
            String name = next.getAsJsonObject().get("data").getAsJsonObject().get("name").getAsString();

            JsonElement typeElement = next.getAsJsonObject().get("data").getAsJsonObject().get("type");

            String nodeType = "";
            if (typeElement != null) {
                nodeType = typeElement.getAsString();
            }
            System.out.println("node id = " + id + " - name = " + name + " - type = " + nodeType);
            Node node = null;


            if(nodeType.equals("condition")) {
                node = new ConditionalNodeImpl(id, name);
                if(t == null){
                    t = new TreeImpl("my person tree",  type, node);
                    nodes.put("root", t.rootNode());
                }
            } else if(nodeType.equals("terminal")){
                node = new EndNodeImpl(id, name);
            }

            nodes.put(id, node);
        }

        JsonArray edgesArray = jsonObj.getAsJsonArray("edges");
        Iterator<JsonElement> iteratorEdges = edgesArray.iterator();
        Map<String, Map<String, String>> edges = new HashMap<>();
        while (iteratorEdges.hasNext()) {
            JsonElement next = iteratorEdges.next();
            String source = next.getAsJsonObject().get("data").getAsJsonObject().get("source").getAsString();
            String target = next.getAsJsonObject().get("data").getAsJsonObject().get("target").getAsString();
            JsonElement valueElement = next.getAsJsonObject().get("data").getAsJsonObject().get("value");
            String value = "";
            if (valueElement != null) {
                value = valueElement.getAsString();
            }
            String operator = "";

            JsonElement operatorElement = next.getAsJsonObject().get("data").getAsJsonObject().get("operator");
            if (operatorElement != null) {
                operator = operatorElement.getAsString();
            }

            System.out.println("edge source = " + source + " - target = " + target + " - operator = " + operator + " - value = " + value);
            if(! (nodes.get(source) instanceof RootNode)) {
                Path path = new PathImpl(Path.Operator.valueOf(operator), value);
                ((ConditionalNode) nodes.get(source)).addPath(path);
                path.setNodeTo(nodes.get(target));
            }

        }

        Object instance = type.newInstance();
        type.getMethod("setAge", Integer.class).invoke(instance, 20);
        type.getMethod("setMarried", Boolean.class).invoke(instance, true);
        type.getMethod("setCity", String.class).invoke(instance, "London");

        System.out.println("Clazz: " + type);
        System.out.println("Object: " + instance);
        for (final Method method : type.getDeclaredMethods()) {
            System.out.println(method);
        }


        String generated = QuickTree.generateCode(t);
        System.out.println(generated);

        List<Handler> handlers = new ArrayList<>();
        handlers.add(new PrintoutHandler());

        TreeInstance treeInstance = QuickTree.createTreeInstance(generated);
        treeInstance.eval(instance, handlers);
    }


}
