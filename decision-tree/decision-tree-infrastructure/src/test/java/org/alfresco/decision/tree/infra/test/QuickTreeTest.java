package org.alfresco.decision.tree.infra.test;

import org.alfresco.business.api.DesignModel;
import org.alfresco.business.api.ExecutableModel;
import org.alfresco.decision.tree.infra.impl.DecisionTreeGenerator;
import org.alfresco.decision.tree.model.api.ConditionalNode;
import org.alfresco.decision.tree.model.api.Tree;
import org.alfresco.decision.tree.model.api.fluent.TreeFluent;
import org.junit.Assert;
import org.junit.Test;

import static org.alfresco.decision.tree.model.api.Path.Operator.EQUALS;
import static org.alfresco.decision.tree.model.api.Path.Operator.GREATER_THAN;
import static org.alfresco.decision.tree.model.api.Path.Operator.LESS_THAN;
import static org.junit.Assert.*;

/**
 * This test creates a simple decision tree, then uses the Business Automation Model
 *  to create an executable model and test the execution
 */
public class QuickTreeTest {

    @Test
    public void simpleDecisionGeneratorTest() {
        DesignModel designModel = new TreeFluent().newTree("my person tree", Person.class)
                .condition("age")
                .path(LESS_THAN, "30")
                .condition("city")
                .path(EQUALS, "Mendoza").end("Doesn't Apply")
                .path(EQUALS, "London")
                .condition("married")
                .path(EQUALS, "true").end("Send Ad 2")
                .path(EQUALS, "false").end("Send Ad 1")
                .endCondition()
                .endCondition()
                .path(GREATER_THAN, "30").end("Too Old")
                .endCondition()
                .build();
        Tree t = (Tree) designModel;
        assertEquals("age", t.rootNode().path().nodeTo().name());
        assertEquals(2, ((ConditionalNode) t.rootNode().path().nodeTo()).paths().size());

        DecisionTreeGenerator dtg = new DecisionTreeGenerator();

        MockEventListener mockEventListener = new MockEventListener();

        dtg.addEventListener(mockEventListener);
        ExecutableModel executableModel = dtg.generate(designModel);

        Assert.assertEquals(2, mockEventListener.events().size());
        
        executableModel.addEventListener(mockEventListener);

        executableModel.execute(new Person("Mendoza", 33, true ));
       
        Assert.assertEquals(4, mockEventListener.events().size());


    }

}