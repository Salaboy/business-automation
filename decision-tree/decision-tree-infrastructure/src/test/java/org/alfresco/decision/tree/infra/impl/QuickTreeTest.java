package org.alfresco.decision.tree.infra.impl;

import org.alfresco.business.api.DesignModel;
import org.alfresco.business.api.Event;
import org.alfresco.business.api.EventListener;
import org.alfresco.business.api.ExecutableModel;
import org.alfresco.business.api.events.AfterExecutionEvent;
import org.alfresco.business.api.events.AfterGenerationEvent;
import org.alfresco.business.api.events.BeforeExecutionEvent;
import org.alfresco.business.api.events.BeforeGenerationEvent;
import org.alfresco.business.base.BaseExecutableModel;
import org.alfresco.business.base.BaseGenerator;
import org.alfresco.decision.tree.infra.test.Person;
import org.alfresco.decision.tree.model.api.ConditionalNode;
import org.alfresco.decision.tree.model.api.Tree;
import org.alfresco.decision.tree.model.api.fluent.TreeFluent;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.alfresco.decision.tree.model.api.Path.Operator.EQUALS;
import static org.alfresco.decision.tree.model.api.Path.Operator.GREATER_THAN;
import static org.alfresco.decision.tree.model.api.Path.Operator.LESS_THAN;
import static org.junit.Assert.*;

/**
 * Created by msalatino on 30/01/2017.
 */
public class QuickTreeTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

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