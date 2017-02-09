

package org.alfresco.decision.tree.model.impl.handlers;


import org.alfresco.decision.tree.model.api.Handler;

public class PrintoutHandler implements Handler {

    @Override
    public void notifyConditionReached(String nodeName) {
        System.out.println("\t >> Condition Reached: " + nodeName);
    }

    @Override
    public void notifyPathTaken(String pathName) {
        System.out.println("\t>> Path Taken: " + pathName);
    }

    @Override
    public void notifyDecisionMade(String node) {
        System.out.println( "\t >> Decision Made on Node: " + node );
    }



}
