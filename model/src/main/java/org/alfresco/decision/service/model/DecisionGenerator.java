package org.alfresco.decision.service.model;


import org.alfresco.business.api.Generator;

/**
 * Created by msalatino on 27/01/2017.
 */
public interface DecisionGenerator extends Generator {

    DecisionExecutableModel generate(DecisionDesignModel model);
}
