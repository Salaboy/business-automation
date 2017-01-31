package org.alfresco.decision.tree.model.test;

import org.alfresco.business.api.DesignModel;
import org.alfresco.business.api.EventListener;
import org.alfresco.business.api.ExecutableModel;
import org.alfresco.business.api.events.AfterExecutionEvent;
import org.alfresco.business.api.events.AfterGenerationEvent;
import org.alfresco.business.api.events.BeforeExecutionEvent;
import org.alfresco.business.api.events.BeforeGenerationEvent;
import org.alfresco.business.base.BaseDesignModel;
import org.alfresco.business.base.BaseExecutableModel;
import org.alfresco.business.base.BaseGenerator;
import org.alfresco.decision.tree.model.api.ConditionalNode;
import org.alfresco.decision.tree.model.api.Node;
import org.alfresco.decision.tree.model.api.Path;
import org.alfresco.decision.tree.model.api.Tree;
import org.alfresco.decision.tree.model.api.fluent.TreeFluent;
import org.alfresco.decision.tree.model.impl.ConditionalNodeImpl;
import org.alfresco.decision.tree.model.impl.EndNodeImpl;
import org.alfresco.decision.tree.model.impl.PathImpl;
import org.alfresco.decision.tree.model.impl.TreeImpl;
import org.junit.Assert;
import org.junit.Test;

import static org.alfresco.decision.tree.model.api.Path.Operator.EQUALS;
import static org.alfresco.decision.tree.model.api.Path.Operator.GREATER_THAN;
import static org.alfresco.decision.tree.model.api.Path.Operator.LESS_THAN;
import static org.junit.Assert.*;

/**
 * Created by msalatino on 30/01/2017.
 */
public class DecisionTreeAPITest {

    @Test
    public void simpleDecisionTreeAPITest() {

        ConditionalNode ageNode = new ConditionalNodeImpl("n0", "age");

        Tree t = new TreeImpl("my person tree", Person.class, ageNode);

        Path lt30Path = new PathImpl(LESS_THAN, "30");
        ConditionalNode cityNode = new ConditionalNodeImpl("n1", "city");
        lt30Path.setNodeTo(cityNode);
        Node endNodeDoesntApply = new EndNodeImpl("end0", "Doesn't Apply");

        Path mendozaPath = new PathImpl(EQUALS, "Mendoza");
        mendozaPath.setNodeTo(endNodeDoesntApply);
        Path londonPath = new PathImpl(EQUALS, "London");

        ConditionalNode marriedNode = new ConditionalNodeImpl("n2", "married");
        londonPath.setNodeTo(marriedNode);
        cityNode.addPath(mendozaPath);
        cityNode.addPath(londonPath);
        ageNode.addPath(lt30Path);
        Path gt30Path = new PathImpl(GREATER_THAN, "30");
        ageNode.addPath(gt30Path);
        Node endNodeTooOld = new EndNodeImpl("end1", "Too Old");
        gt30Path.setNodeTo(endNodeTooOld);
        Path marriedPath = new PathImpl(EQUALS, "true");
        Path notMarriedPath = new PathImpl(EQUALS, "false");
        Node endNodeSendAd2 = new EndNodeImpl("end2", "Send Ad 2");
        marriedPath.setNodeTo(endNodeSendAd2);
        Node endNodeSendAd1 = new EndNodeImpl("end3", "Send Ad 1");

        notMarriedPath.setNodeTo(endNodeSendAd1);
        marriedNode.addPath(marriedPath);
        marriedNode.addPath(notMarriedPath);


        assertEquals(ageNode, t.rootNode().path().nodeTo());
        assertEquals(2, ((ConditionalNode) t.rootNode().path().nodeTo()).paths().size());


    }

    @Test
    public void simpleDecisionTreeFluentTest() {
        Tree t = new TreeFluent().newTree("my person tree", Person.class)
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

        assertEquals("age", t.rootNode().path().nodeTo().name());
        assertEquals(2, ((ConditionalNode) t.rootNode().path().nodeTo()).paths().size());

    }


}