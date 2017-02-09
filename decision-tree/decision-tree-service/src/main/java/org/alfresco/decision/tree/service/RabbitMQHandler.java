package org.alfresco.decision.tree.service;

import org.alfresco.decision.tree.model.api.Handler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by msalatino on 06/02/2017.
 */
@Component
public class RabbitMQHandler implements Handler{

    private RabbitTemplate rabbitTemplate;

    public RabbitMQHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void notifyConditionReached(String nodeName) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "decision-tree-queue"), "Condition Reached: "+ nodeName);
    }

    @Override
    public void notifyPathTaken(String pathName) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "decision-tree-queue"), "Path Taken:" +pathName);
    }

    @Override
    public void notifyDecisionMade(String nodeName) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "decision-tree-queue"), "Decision Made: "+nodeName);
    }
}
