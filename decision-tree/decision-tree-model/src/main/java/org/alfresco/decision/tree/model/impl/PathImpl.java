
package org.alfresco.decision.tree.model.impl;


import org.alfresco.decision.tree.model.api.Node;
import org.alfresco.decision.tree.model.api.Path;

public class PathImpl implements Path {

    private Operator operator;
    private String condition;
    private Node nodeTo;

    public PathImpl() {
    }

    public PathImpl( String condition ) {
        this.condition = condition;
    }

    public PathImpl( Operator operator, String condition ) {
        this.condition = condition;
        this.operator = operator;
    }

    public PathImpl( Operator operator, String condition, Node nodeTo ) {
        this.condition = condition;
        this.operator = operator;
        this.nodeTo = nodeTo;
    }

    @Override
    public String getCondition() {
        return condition;
    }

    @Override
    public void setCondition( String condition ) {
        this.condition = condition;
    }

    @Override
    public Node getNodeTo() {
        return nodeTo;
    }

    @Override
    public void setNodeTo( Node node ) {
        this.nodeTo = node;
    }

    @Override
    public Operator getOperator() {
        return operator;
    }

    @Override
    public void setOperator( Operator operator ) {
        this.operator = operator;
    }

}
