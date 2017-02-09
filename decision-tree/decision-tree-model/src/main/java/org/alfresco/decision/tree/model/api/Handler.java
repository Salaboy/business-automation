

package org.alfresco.decision.tree.model.api;

public interface Handler {
    void notifyConditionReached(String nodeName);
    void notifyPathTaken(String pathName);
    void notifyDecisionMade(String nodeName);
}
