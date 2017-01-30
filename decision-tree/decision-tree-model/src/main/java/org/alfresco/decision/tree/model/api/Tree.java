
package org.alfresco.decision.tree.model.api;

import org.alfresco.business.base.BaseDesignModel;

public abstract class Tree extends BaseDesignModel {

    public Tree( String name, String content) {
        super( name, content);
    }

    public abstract Class clazz();

    public abstract RootNode rootNode();


}
