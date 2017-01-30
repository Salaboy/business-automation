

package org.alfresco.decision.tree.model.impl;



import org.alfresco.decision.tree.model.api.ConditionalNode;
import org.alfresco.decision.tree.model.api.Path;

import java.util.Collection;
import java.util.HashSet;

public class ConditionalNodeImpl implements ConditionalNode {

    private String name;
    private String id;
    private Collection<Path> paths;
    

    public ConditionalNodeImpl() {

    }

    public ConditionalNodeImpl( String id,  String name ) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Collection<Path> getPaths() {
        return paths;
    }

    @Override
    public void setPaths( Collection<Path> paths ) {
        this.paths = paths;
    }

    @Override
    public void addPath( Path p ) {
        if ( this.paths == null ) {
            paths = new HashSet<>();
        }
        this.paths.add( p );
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
