package org.alfresco.decision.service.model.test;


import org.alfresco.business.api.Event;
import org.alfresco.decision.service.model.DecisionDesignModel;
import org.alfresco.decision.service.model.DecisionEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msalatino on 27/01/2017.
 */
public class MockDecisionEventListener implements DecisionEventListener {
    private List<String> events = new ArrayList<String>();

    @Override
    public void onDecisionMade(DecisionDesignModel decisionModel) {
        events.add(decisionModel.toString());
    }

    @Override
    public void onEvent(Event event) {
        onDecisionMade((DecisionDesignModel) event);
    }

    public List<String> getEvents() {
        return events;
    }
}
