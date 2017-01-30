

package org.alfresco.decision.tree.model.api;

public interface Path {

    public enum Operator {
        EQUALS, GREATER_THAN, LESS_THAN
    };

    String getCondition();

    void setCondition(String condition);

    Operator getOperator();

    void setOperator(Operator operator);

    Node getNodeTo();

    void setNodeTo(Node node);
}
