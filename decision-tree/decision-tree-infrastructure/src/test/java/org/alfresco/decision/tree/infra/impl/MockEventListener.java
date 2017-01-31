package org.alfresco.decision.tree.infra.impl;

import org.alfresco.business.api.Event;
import org.alfresco.business.api.EventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msalatino on 31/01/2017.
 */
public class MockEventListener implements EventListener{
    private List<Event> events = new ArrayList<>();


    @Override
    public void onEvent(Event event) {
        events.add(event);
        System.out.println("Event recieved: " + event);
    }

    public List<Event> events(){
        return events;
    }
}
