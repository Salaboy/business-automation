

package org.alfresco.decision.tree.model.impl.handlers;


import org.alfresco.decision.tree.model.api.Handler;

public class PrintoutHandler implements Handler {

    @Override
    public void notifyDecisionMade(String node) {
        System.out.println( "Decision Made on Node: " + node );
    }

}
