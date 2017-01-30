/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    public TreeFluent newTree(String id, String name, String content, Class clazz) {
        tree = new TreeImpl(id, name, content, clazz);
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
