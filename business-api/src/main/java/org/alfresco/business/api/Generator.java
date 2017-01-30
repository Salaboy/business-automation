package org.alfresco.business.api;

import java.util.Set;

/**
 * Created by msalatino on 27/01/2017.
 */
public interface Generator {

    void addEventListener(EventListener listener);

    Set<EventListener> eventListeners();

    ExecutableModel generate(DesignModel model);


}
