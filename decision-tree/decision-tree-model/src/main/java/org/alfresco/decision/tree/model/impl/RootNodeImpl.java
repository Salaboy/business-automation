

package org.alfresco.decision.tree.model.impl;


import org.alfresco.decision.tree.model.api.Path;
import org.alfresco.decision.tree.model.api.RootNode;

public class RootNodeImpl implements RootNode {

    private String id;
    private String name;
    private Path path;


    public RootNodeImpl() {
    }

    public RootNodeImpl(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public Path path() {
        return path;
    }

    @Override
    public void addOnlyPath(Path p) {
        if(path != null){
            throw new IllegalStateException("Only one path can be added to a Root Node");
        }else{
            path = p;
        }
    }
}
