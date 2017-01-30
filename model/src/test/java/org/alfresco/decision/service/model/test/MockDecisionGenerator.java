package org.alfresco.decision.service.model.test;


import org.alfresco.business.api.DesignModel;
import org.alfresco.business.api.EventListener;
import org.alfresco.business.api.ExecutableModel;
import org.alfresco.decision.service.model.DecisionDesignModel;
import org.alfresco.decision.service.model.DecisionExecutableModel;
import org.alfresco.decision.service.model.DecisionGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by msalatino on 27/01/2017.
 */
public class MockDecisionGenerator implements DecisionGenerator {

    private Set<EventListener> eventListeners = new HashSet<>();

    @Override
    public DecisionExecutableModel generate(DecisionDesignModel model) {
        return new MockDecisionExecutableModel(model);
    }

    @Override
    public ExecutableModel generate(DesignModel model) {
        return generate((DecisionDesignModel)model);
    }

    @Override
    public void addEventListener(EventListener listener) {
        eventListeners.add(listener);
    }

    @Override
    public Set<EventListener> eventListeners() {
        return eventListeners;
    }
}
