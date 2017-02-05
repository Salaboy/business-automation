package org.alfresco.decision.tree.service;

/**
 * Created by msalatino on 05/02/2017.
 */
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class DecisionController {

    private static final String TEMPLATE = "Hello, %s!";
    private RabbitTemplate rabbitTemplate;
    public DecisionController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @RequestMapping("/decision")
    public HttpEntity<DecisionResult> greeting(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

        DecisionResult greeting = new DecisionResult(String.format(TEMPLATE, name));
        greeting.add(linkTo(methodOn(DecisionController.class).greeting(name)).withSelfRel());

        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "decision-tree-queue"), "Hello from RabbitMQ!");

        return new ResponseEntity<DecisionResult>(greeting, HttpStatus.OK);
    }
}
