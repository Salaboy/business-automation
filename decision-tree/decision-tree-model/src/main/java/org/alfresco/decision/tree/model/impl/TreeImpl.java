
package org.alfresco.decision.tree.model.impl;


import org.alfresco.decision.tree.model.api.Node;
import org.alfresco.decision.tree.model.api.Path;
import org.alfresco.decision.tree.model.api.RootNode;
import org.alfresco.decision.tree.model.api.Tree;

public class TreeImpl extends Tree {
    
    private transient Class clazz;
    private RootNode rootNode;

    public TreeImpl(String name, Class clazz, Node initialNode) {
        super( name, "");
        this.clazz = clazz;
        rootNode = new RootNodeImpl("root" , name);
        PathImpl path = new PathImpl(Path.Operator.FORWARD, "from type");
        path.setNodeTo(initialNode);
        rootNode.addOnlyPath(path);
    }

    @Override
    public Class clazz() {
        return clazz;
    }
    @Override
    public RootNode rootNode() {
        return rootNode;
    }


}
