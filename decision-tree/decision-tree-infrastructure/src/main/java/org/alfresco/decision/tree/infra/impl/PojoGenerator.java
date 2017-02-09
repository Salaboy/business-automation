package org.alfresco.decision.tree.infra.impl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.apache.commons.io.FileUtils;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

/**
 * Created by salaboy on 21/11/2016.
 */
public class PojoGenerator {


    public static Class generate(String json) throws IOException, CannotCompileException {

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

        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new NoopAnnotator(), new SchemaStore()), new SchemaGenerator());
        JType jType = mapper.generate(codeModel, "ClassName", "org.alfresco.generated", json);


        File output = new File("output");
        output.mkdir();
        codeModel.build(output);


        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        int result = compiler.run(null, System.out, System.err, output.getAbsolutePath()+"/org/alfresco/generated/ClassName.java" );

        System.out.println("Compile result code = " + result);

        ClassPool pool = ClassPool.getDefault();
        File file = new File(output.getAbsolutePath()+"/org/alfresco/generated/ClassName.class");

        CtClass cc = pool.makeClass(FileUtils.openInputStream(file));

        System.out.println(cc);

        Class aClass = cc.toClass();

        return aClass;

    }

    public static Class generate(String className, Map<String, Class<?>>  properties) throws NotFoundException,
            CannotCompileException {

        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass(className);

        // add this to define a super class to extend
        // cc.setSuperclass(resolveCtClass(MySuperClass.class));

        // add this to define an interface to implement
        cc.addInterface(resolveCtClass(Serializable.class));

        for (Entry<String, Class<?>> entry : properties.entrySet()) {

            cc.addField(new CtField(resolveCtClass(entry.getValue()), entry.getKey(), cc));

             // add getter
            cc.addMethod(generateGetter(cc, entry.getKey(), entry.getValue()));

            // add setter
            cc.addMethod(generateSetter(cc, entry.getKey(), entry.getValue()));
        }

        return cc.toClass();
    }

    private static CtMethod generateGetter(CtClass declaringClass, String fieldName, Class fieldClass)
            throws CannotCompileException {

        String getterName = "get" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);

        StringBuffer sb = new StringBuffer();
        sb.append("public ").append(fieldClass.getName()).append(" ")
                .append(getterName).append("(){").append("return this.")
                .append(fieldName).append(";").append("}");
        return CtMethod.make(sb.toString(), declaringClass);
    }

    private static CtMethod generateSetter(CtClass declaringClass, String fieldName, Class fieldClass)
            throws CannotCompileException {

        String setterName = "set" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);

        StringBuffer sb = new StringBuffer();
        sb.append("public void ").append(setterName).append("(")
                .append(fieldClass.getName()).append(" ").append(fieldName)
                .append(")").append("{").append("this.").append(fieldName)
                .append("=").append(fieldName).append(";").append("}");
        return CtMethod.make(sb.toString(), declaringClass);
    }

    private static CtClass resolveCtClass(Class clazz) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        return pool.get(clazz.getName());
    }
}
