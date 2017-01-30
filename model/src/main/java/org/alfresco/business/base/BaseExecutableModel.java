package org.alfresco.business.base;



import org.alfresco.business.api.DesignModel;
import org.alfresco.business.api.EventListener;
import org.alfresco.business.api.ExecutableModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by msalatino on 27/01/2017.
 */
public abstract class BaseExecutableModel implements ExecutableModel {
    private Set<EventListener> listeners = new HashSet<>();
    private DesignModel designModel;

    public BaseExecutableModel(DesignModel designModel) {
        this.designModel = designModel;
    }

    @Override
    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }

    @Override
    public Set<EventListener> eventListeners() {
        return listeners;
    }

    @Override
    public DesignModel designModel() {
        return designModel;
    }
}
