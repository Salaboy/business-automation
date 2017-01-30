
package org.alfresco.decision.tree.model.api;

import org.alfresco.business.base.BaseDesignModel;

public abstract class Tree extends BaseDesignModel {

    public Tree(String id, String name, String content) {
        super(id, name, content);
    }

    public abstract Class getClazz();

    public abstract void setClazz(Class clazz);

    public abstract Node getRootNode();

    public abstract void setRootNode(Node root);

}
