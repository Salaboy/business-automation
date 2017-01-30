package org.alfresco.business.api.tests.mocks;

import org.alfresco.business.api.Event;
import org.alfresco.business.api.events.AfterExecutionEvent;
import org.alfresco.business.api.events.BeforeExecutionEvent;
import org.alfresco.business.base.listeners.BaseExecutionEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msalatino on 30/01/2017.
 */
public class MockExecutionEventListener extends BaseExecutionEventListener{

    private List<Event> events = new ArrayList<>();

    @Override
    public void onEvent(Event event) {
        events.add(event);
    }

    @Override
    public void beforeExecution(BeforeExecutionEvent bee) {
        events.add(bee);
    }

    @Override
    public void afterExecution(AfterExecutionEvent aee) {
        events.add(aee);
    }

    public List<Event> events(){
        return events;
    }

}
