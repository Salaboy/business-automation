package org.alfresco.decision.tree.service;

import org.alfresco.business.api.Event;
import org.alfresco.business.api.EventListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by msalatino on 06/02/2017.
 */
@Component
public class RabbitMQEventListener implements EventListener {

    private RabbitTemplate rabbitTemplate;

    public RabbitMQEventListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void onEvent(Event event) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "decision-tree-queue"), "on Event: " + event.toString());
    }
}
