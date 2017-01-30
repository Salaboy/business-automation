
package org.alfresco.decision.tree.model.impl;


import org.alfresco.decision.tree.model.api.Node;
import org.alfresco.decision.tree.model.api.Tree;

public class TreeImpl extends Tree {
    
    private transient Class clazz;
    private Node rootNode;

    public TreeImpl(String id, String name, String content, Class clazz) {
        super(id, name, content);
        this.clazz = clazz;
    }

    @Override
    public Class getClazz() {
        return clazz;
    }

    @Override
    public void setClazz( Class clazz ) {
        this.clazz = clazz;
    }

    @Override
    public Node getRootNode() {
        return rootNode;
    }

    @Override
    public void setRootNode( Node rootNode ) {
        this.rootNode = rootNode;
    }

}
