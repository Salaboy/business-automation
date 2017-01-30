package org.alfresco.business.api;

import java.util.Set;

/**
 * Created by msalatino on 27/01/2017.
 */
public interface ExecutableModel extends Model  {

    DesignModel designModel();

    void execute(Object input);

    void addEventListener(EventListener listener);

    Set<EventListener> eventListeners();
}
