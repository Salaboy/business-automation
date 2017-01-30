

package org.alfresco.decision.tree.model.api;

import java.util.Collection;

public interface ConditionalNode extends Node {


    Collection<Path> paths();

    void addPath(Path p);
}
