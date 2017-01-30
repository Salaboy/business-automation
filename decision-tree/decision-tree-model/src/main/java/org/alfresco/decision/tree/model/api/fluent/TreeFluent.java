
package org.alfresco.decision.tree.model.api.fluent;



import org.alfresco.decision.tree.model.api.ConditionalNode;
import org.alfresco.decision.tree.model.api.Node;
import org.alfresco.decision.tree.model.api.Path;
import org.alfresco.decision.tree.model.api.Tree;
import org.alfresco.decision.tree.model.impl.ConditionalNodeImpl;
import org.alfresco.decision.tree.model.impl.EndNodeImpl;
import org.alfresco.decision.tree.model.impl.PathImpl;
import org.alfresco.decision.tree.model.impl.TreeImpl;

import java.util.ArrayDeque;
import java.util.Deque;

public class TreeFluent {

    private Tree tree;
    private Node rootNode;
    private Path currentPath;
    private final Deque<Node> nodeStack = new ArrayDeque<>();
    private static int nodeIdGenerator = 0;

    public TreeFluent newTree(String name,  Class clazz) {
        tree = new TreeImpl("", name, "", clazz);
        return this;
    }

    public TreeFluent condition(String name) {
        //push stack
        ConditionalNodeImpl node = new ConditionalNodeImpl("n" + nodeIdGenerator++, name);
        if (rootNode == null) {
            tree.setRootNode(node);
            rootNode = node;
        } else {
            currentPath.setNodeTo(node);
        }
        nodeStack.push(node);
        return this;

    }

    public TreeFluent end(String name) {
        currentPath.setNodeTo(new EndNodeImpl("n" + nodeIdGenerator++, name));
        return this;
    }

    public TreeFluent path(Path.Operator operator, String condition) {
        currentPath = new PathImpl(operator, condition);
        if (nodeStack.peek() instanceof ConditionalNode) {
            ((ConditionalNode) nodeStack.peek()).addPath(currentPath);
        }
        return this;
    }

    public TreeFluent endCondition() {
        nodeStack.pop();
        return this;
    }

    public Tree build() {
        if (!nodeStack.isEmpty()) {
            throw new IllegalStateException("Make sure that you closed all the conditions");
        }
        return tree;
    }
}
