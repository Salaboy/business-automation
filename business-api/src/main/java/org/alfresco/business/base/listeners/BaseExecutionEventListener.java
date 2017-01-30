package org.alfresco.business.base.listeners;

import org.alfresco.business.api.EventListener;
import org.alfresco.business.api.events.AfterExecutionEvent;
import org.alfresco.business.api.events.BeforeExecutionEvent;

/**
 * Created by msalatino on 30/01/2017.
 */
public abstract class BaseExecutionEventListener implements EventListener{
    public abstract void beforeExecution(BeforeExecutionEvent bee);
    public abstract void afterExecution(AfterExecutionEvent aee);
}
