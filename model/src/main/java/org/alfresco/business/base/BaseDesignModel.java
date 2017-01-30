package org.alfresco.business.base;


import org.alfresco.business.api.DesignModel;

import java.util.UUID;

/**
 * Created by msalatino on 27/01/2017.
 */
public abstract class BaseDesignModel implements DesignModel {
    private String id;
    private String name;
    private String content;

    public BaseDesignModel(String name, String content) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.content = content;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String content() {
        return content;
    }

    @Override
    public String id() {
        return id;
    }
}
