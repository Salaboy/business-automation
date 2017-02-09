package org.alfresco.decision.tree.infra.impl;

import org.alfresco.business.api.DesignModel;
import org.alfresco.business.api.EventListener;
import org.alfresco.business.api.ExecutableModel;
import org.alfresco.business.api.events.AfterExecutionEvent;
import org.alfresco.business.api.events.BeforeExecutionEvent;
import org.alfresco.business.api.events.BeforeGenerationEvent;
import org.alfresco.business.base.BaseExecutableModel;
import org.alfresco.decision.tree.model.api.Handler;
import org.alfresco.decision.tree.model.api.TreeInstance;
import org.alfresco.decision.tree.model.impl.handlers.PrintoutHandler;

import java.util.*;

/**
 * Created by msalatino on 30/01/2017.
 */
public class DecisionTreeExecutableModel extends BaseExecutableModel {

    private Set<EventListener> listeners = new HashSet<>();
    private TreeInstance treeInstance;
    private List<Handler> handlers = new ArrayList<>();

    public DecisionTreeExecutableModel(DesignModel designModel, TreeInstance treeInstance) {
        super(designModel);
        this.treeInstance = treeInstance;
    }

    @Override
    public DesignModel designModel() {
        return super.designModel();
    }

    @Override
    public void execute(Object input) {
        for (EventListener l : eventListeners()) {
            l.onEvent(new BeforeExecutionEvent());
        }
        if(handlers == null || handlers.isEmpty()) {
            handlers.add(new PrintoutHandler());
        }
        treeInstance.eval(input, handlers);

        for (EventListener l : eventListeners()) {
            l.onEvent(new AfterExecutionEvent());
        }
    }

    public void setHandlers(List<Handler> handlers){
        this.handlers = handlers;
    }

    public void addHandler(Handler handler){
        if(handlers == null){
            handlers = new ArrayList<>();
        }
        handlers.add(handler);
    }

    @Override
    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }

    @Override
    public Set<EventListener> eventListeners() {
        return listeners;
    }
}
