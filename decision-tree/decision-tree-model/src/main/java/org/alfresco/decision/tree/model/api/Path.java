

package org.alfresco.decision.tree.model.api;

public interface Path {

    enum Operator {
        EQUALS, GREATER_THAN, LESS_THAN, FORWARD
    };

    String condition();

    Operator operator();

    Node nodeTo();

    void setNodeTo(Node node);

}
