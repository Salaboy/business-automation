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

package org.alfresco.decision.tree.model.impl;


import org.alfresco.decision.tree.model.api.Node;
import org.alfresco.decision.tree.model.api.Tree;

public class TreeImpl extends Tree {
    
    private transient Class clazz;
    private Node rootNode;

    public TreeImpl(String id, String name, String content, Class clazz) {
        super(id, name, content);
        this.clazz = clazz;
    }

    @Override
    public Class getClazz() {
        return clazz;
    }

    @Override
    public void setClazz( Class clazz ) {
        this.clazz = clazz;
    }

    @Override
    public Node getRootNode() {
        return rootNode;
    }

    @Override
    public void setRootNode( Node rootNode ) {
        this.rootNode = rootNode;
    }

}
