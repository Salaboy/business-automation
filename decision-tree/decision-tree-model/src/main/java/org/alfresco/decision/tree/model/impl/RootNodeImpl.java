

package org.alfresco.decision.tree.model.impl;


import org.alfresco.decision.tree.model.api.Path;
import org.alfresco.decision.tree.model.api.RootNode;

public class RootNodeImpl implements RootNode {

    private String id;
    private String name;
    private Path onlyPath;


    public RootNodeImpl() {
    }

    public RootNodeImpl(String id, String name) {
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
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void addOnlyPath(Path p) {
        this.onlyPath = p;
    }

    @Override
    public Path getOnlyPath() {
        return onlyPath;
    }
}
