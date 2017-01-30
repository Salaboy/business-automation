

package org.alfresco.decision.tree.model.api;

public interface RootNode extends Node {

    Path path();

    void addOnlyPath(Path p);
}
