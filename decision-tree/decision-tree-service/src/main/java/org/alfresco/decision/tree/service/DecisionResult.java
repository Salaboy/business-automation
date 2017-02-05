package org.alfresco.decision.tree.service;

/**
 * Created by msalatino on 05/02/2017.
 */
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DecisionResult extends ResourceSupport {

    private final String content;

    @JsonCreator
    public DecisionResult(@JsonProperty("content") String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
