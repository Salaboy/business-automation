package org.alfresco.business.api.tests.mocks;

import org.alfresco.business.api.Event;
import org.alfresco.business.api.events.AfterGenerationEvent;
import org.alfresco.business.api.events.BeforeGenerationEvent;
import org.alfresco.business.base.listeners.BaseGeneratorEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msalatino on 30/01/2017.
 */
public class MockGeneratorEventListener extends BaseGeneratorEventListener {

    private List<Event> events = new ArrayList<>();

    @Override
    public void onEvent(Event event) {
        events.add(event);
    }

    @Override
    public void beforeGeneration(BeforeGenerationEvent bge) {
        events.add(bge);
    }

    @Override
    public void afterGeneration(AfterGenerationEvent age) {
        events.add(age);
    }

    public List<Event> events(){
        return events;
    }
}
