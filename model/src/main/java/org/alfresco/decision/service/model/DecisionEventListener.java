package org.alfresco.decision.service.model;


import org.alfresco.business.api.EventListener;

/**
 * Created by msalatino on 27/01/2017.
 */
public interface DecisionEventListener extends EventListener {
    void onDecisionMade(DecisionDesignModel decisionModel);
}
