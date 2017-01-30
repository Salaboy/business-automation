

package org.alfresco.decision.tree.model.impl;

import org.alfresco.decision.tree.model.api.EndNode;

public class EndNodeImpl implements EndNode {

    private String id;
    private String name;


    public EndNodeImpl() {
    }

    public EndNodeImpl( String id, String name ) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setName( String name ) {
        this.name = name;
    }

}
