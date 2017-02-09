package org.alfresco.decision.tree.service;

/**
 * Created by msalatino on 05/02/2017.
 */
import static org.alfresco.decision.tree.model.api.Path.Operator.EQUALS;
import static org.alfresco.decision.tree.model.api.Path.Operator.GREATER_THAN;
import static org.alfresco.decision.tree.model.api.Path.Operator.LESS_THAN;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.alfresco.business.api.DesignModel;
import org.alfresco.business.api.ExecutableModel;
import org.alfresco.decision.tree.infra.impl.DecisionTreeExecutableModel;
import org.alfresco.decision.tree.infra.impl.DecisionTreeGenerator;
import org.alfresco.decision.tree.infra.impl.PojoGenerator;
import org.alfresco.decision.tree.model.api.ConditionalNode;
import org.alfresco.decision.tree.model.api.Tree;
import org.alfresco.decision.tree.model.api.fluent.TreeFluent;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DecisionController {

    private static final String TEMPLATE = "Hello, %s!";

    private RabbitMQHandler rabbitMQHandler;
    private RabbitMQEventListener eventListener;

    private Map<String, DesignModel> designModels = new HashMap<>();
    private Map<String, ExecutableModel> executableModels = new HashMap<>();

    public DecisionController(RabbitMQEventListener eventListener, RabbitMQHandler rabbitMQHandler) {
        this.rabbitMQHandler = rabbitMQHandler;
        this.eventListener = eventListener;
    }


    @RequestMapping(method = RequestMethod.POST, path = "/generate")
    public HttpEntity<DecisionResult> generate(
            @RequestBody(required = true) String content){
        DecisionResult greeting = new DecisionResult(String.format(TEMPLATE, content));
        greeting.add(linkTo(methodOn(DecisionController.class).generate(content)).withSelfRel());



        return new ResponseEntity<DecisionResult>(greeting, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST, path = "/decide")
    public HttpEntity<DecisionResult> decide(
            @RequestBody(required = true) String content) throws ClassNotFoundException, NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, IOException {

        DecisionResult greeting = new DecisionResult(String.format(TEMPLATE, content));
        greeting.add(linkTo(methodOn(DecisionController.class).decide(content)).withSelfRel());

        Gson gson = new GsonBuilder().create();

        Class generated = PojoGenerator.generate(content);

        DesignModel designModel = new TreeFluent().newTree("my person tree", generated)
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
        Tree t = (Tree) designModel;

        DecisionTreeGenerator dtg = new DecisionTreeGenerator();

        dtg.addEventListener(eventListener);
        ExecutableModel executableModel = dtg.generate(designModel);

        ((DecisionTreeExecutableModel)executableModel).addHandler(rabbitMQHandler);

        executableModel.addEventListener(eventListener);

        Object o = gson.fromJson(content, generated);

        System.out.println("Object generated: " + o);

        executableModel.execute(o);



        return new ResponseEntity<DecisionResult>(greeting, HttpStatus.OK);
    }
}
