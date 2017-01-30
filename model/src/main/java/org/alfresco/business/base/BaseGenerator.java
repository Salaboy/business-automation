package org.alfresco.business.base;

import org.alfresco.business.api.DesignModel;
import org.alfresco.business.api.EventListener;
import org.alfresco.business.api.ExecutableModel;
import org.alfresco.business.api.Generator;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by msalatino on 30/01/2017.
 */
public abstract class BaseGenerator implements Generator {
    private Set<EventListener> listeners = new HashSet<>();

    @Override
    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }

    @Override
    public Set<EventListener> eventListeners() {
        return listeners;
    }
}
