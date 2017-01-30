

package org.alfresco.decision.tree.model.api;

public interface RootNode extends Node {
    void addOnlyPath(Path p);

    Path getOnlyPath();
}
