package org.alfresco.decision.tree.infra.test;

import org.alfresco.decision.tree.model.api.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msalatino on 06/02/2017.
 */
public class MockHandler implements Handler {
    private List<String> notifications = new ArrayList<>();

    @Override
    public void notifyConditionReached(String nodeName) {
        notifications.add("Condition Reached: " + nodeName);
    }

    @Override
    public void notifyPathTaken(String pathName) {
        notifications.add("Path Taken: " + pathName);
    }

    @Override
    public void notifyDecisionMade(String nodeName) {
        notifications.add("Decision Made: " + nodeName);
    }

    public List<String> getNotifications(){
        return notifications;
    }
}
