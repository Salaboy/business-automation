package org.alfresco.decision.service.model.test;


import org.alfresco.business.api.DesignModel;
import org.alfresco.business.api.EventListener;
import org.alfresco.decision.service.model.DecisionDesignModel;
import org.alfresco.decision.service.model.DecisionEventListener;
import org.alfresco.decision.service.model.DecisionExecutableModel;

/**
 * Created by msalatino on 27/01/2017.
 */
public class MockDecisionExecutableModel extends DecisionExecutableModel {

    public MockDecisionExecutableModel(DesignModel designModel) {
        super(designModel);
    }

    @Override
    public void execute(Object input) {
        for(EventListener l : eventListeners()){
            if(l instanceof DecisionEventListener){
                ((DecisionEventListener)l).onDecisionMade((DecisionDesignModel) designModel());
            }

        }
    }
}
