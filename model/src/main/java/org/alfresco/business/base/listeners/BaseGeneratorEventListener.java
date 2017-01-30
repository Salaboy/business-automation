package org.alfresco.business.base.listeners;


import org.alfresco.business.api.EventListener;
import org.alfresco.business.api.events.AfterGenerationEvent;
import org.alfresco.business.api.events.BeforeGenerationEvent;

/**
 * Created by msalatino on 30/01/2017.
 */
public abstract class BaseGeneratorEventListener implements EventListener{
    public abstract void beforeGeneration(BeforeGenerationEvent bge);
    public abstract void afterGeneration(AfterGenerationEvent age);
}
