package org.alfresco.decision.tree.infra.impl;

import javassist.CannotCompileException;
import org.alfresco.business.api.DesignModel;
import org.alfresco.business.api.EventListener;
import org.alfresco.business.api.ExecutableModel;
import org.alfresco.business.api.events.AfterExecutionEvent;
import org.alfresco.business.api.events.AfterGenerationEvent;
import org.alfresco.business.api.events.BeforeExecutionEvent;
import org.alfresco.business.api.events.BeforeGenerationEvent;
import org.alfresco.business.base.BaseExecutableModel;
import org.alfresco.business.base.BaseGenerator;
import org.alfresco.decision.tree.model.api.Tree;
import org.alfresco.decision.tree.model.api.TreeInstance;

/**
 * Created by msalatino on 30/01/2017.
 */
public class DecisionTreeGenerator extends BaseGenerator {
    @Override
    public ExecutableModel generate(DesignModel model) {
        TreeInstance treeInstance = null;
        DecisionTreeExecutableModel decisionTreeExecutableModel = null;
        for (EventListener l : eventListeners()) {
            l.onEvent(new BeforeGenerationEvent());
        }
        try {
            String generatedCode = QuickTree.generateCode((Tree) model);
            System.out.println("Generated Code: " + generatedCode);
            treeInstance = QuickTree.createTreeInstance(generatedCode);
            decisionTreeExecutableModel = new DecisionTreeExecutableModel(model, treeInstance);
            for (EventListener l : eventListeners()) {
                l.onEvent(new AfterGenerationEvent());
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
        return decisionTreeExecutableModel;
    }
}
