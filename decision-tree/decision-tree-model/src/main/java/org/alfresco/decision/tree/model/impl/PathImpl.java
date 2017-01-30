
package org.alfresco.decision.tree.model.impl;


import org.alfresco.decision.tree.model.api.Node;
import org.alfresco.decision.tree.model.api.Path;

public class PathImpl implements Path {

    private Operator operator;
    private String condition;
    private Node nodeTo;

    protected PathImpl() {
    }

    public PathImpl( Operator operator, String condition ) {
        this.condition = condition;
        this.operator = operator;
    }

    @Override
    public String condition() {
        return condition;
    }

    @Override
    public Node nodeTo() {
        return nodeTo;
    }

    @Override
    public Operator operator() {
        return operator;
    }

    @Override
    public void setNodeTo(Node node) {
        this.nodeTo = node;
    }
}
