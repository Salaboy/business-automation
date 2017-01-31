package org.alfresco.decision.tree.model.api;

import java.util.List;


public interface TreeInstance {
    void eval( Object instance, List handlers );
}

