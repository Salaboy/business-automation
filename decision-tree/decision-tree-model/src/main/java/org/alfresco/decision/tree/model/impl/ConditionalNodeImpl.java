

package org.alfresco.decision.tree.model.impl;



import org.alfresco.decision.tree.model.api.ConditionalNode;
import org.alfresco.decision.tree.model.api.Path;

import java.util.Collection;
import java.util.HashSet;

public class ConditionalNodeImpl implements ConditionalNode {

    private String name;
    private String id;
    private Collection<Path> paths;
    

    public ConditionalNodeImpl() {}

    public ConditionalNodeImpl( String id,  String name ) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Collection<Path> paths() {
        return paths;
    }


    @Override
    public void addPath( Path p ) {
        if ( this.paths == null ) {
            paths = new HashSet<>();
        }
        this.paths.add( p );
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String id() {
        return id;
    }



}
