package org.alfresco.decision.tree.infra.impl;

import org.alfresco.decision.tree.model.api.Handler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by msalatino on 03/02/2017.
 */

@Component
public class RabbitMQHandler implements Handler{
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void notifyDecisionMade(String nodeName) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "decision-tree-queue"), "Hello from RabbitMQ!");
    }
}
