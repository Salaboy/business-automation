

package org.alfresco.decision.tree.model.api;


import java.util.Map;

public interface TreeInstance {

    void eval( Object instance, Map handlers );
}

