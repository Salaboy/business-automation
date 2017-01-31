package org.alfresco.decision.tree.infra.test;


import org.alfresco.decision.tree.model.api.Handler;

public class Person {

    private String city;
    private Integer age;
    private Boolean married;

    public Person() {
    }

    public Person( String city, Integer age, Boolean married ) {
        this.city = city;
        this.age = age;
        this.married = married;
    }

    public String getCity() {
        return city;
    }

    public void setCity( String city ) {
        this.city = city;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge( Integer age ) {
        this.age = age;
    }

    public Boolean getMarried() {
        return married;
    }

    public void setMarried( Boolean married ) {
        this.married = married;
    }

    public void eval(  Object instance, java.util.List handlers ) {
        if ( ((org.alfresco.decision.tree.infra.test.Person)instance).getAge( ).intValue() < 30 ) {
            if ( ((org.alfresco.decision.tree.infra.test.Person)instance).getCity( ).equals("Mendoza") ) {
                for( Object o : handlers ){
                    ((org.alfresco.decision.tree.model.api.Handler)o).notifyDecisionMade("Doesn't Apply");
                }
            } else if ( ((org.alfresco.decision.tree.infra.test.Person)instance).getCity( ).equals("London") ) {
                if ( ((org.alfresco.decision.tree.infra.test.Person)instance).getMarried( ).booleanValue() ==false ) {
                    for( Object o : handlers ){
                        ((org.alfresco.decision.tree.model.api.Handler)o).notifyDecisionMade("Send Ad 1");
                    }
                } else if ( ((org.alfresco.decision.tree.infra.test.Person)instance).getMarried( ).booleanValue() ==true ) {
                    for( Object o : handlers ){
                        ((org.alfresco.decision.tree.model.api.Handler)o).notifyDecisionMade("Send Ad 2");
                    }
                }
            }
        } else if ( ((org.alfresco.decision.tree.infra.test.Person)instance).getAge( ).intValue() > 30 ) {
            for( Object o : handlers ){
                ((org.alfresco.decision.tree.model.api.Handler)o).notifyDecisionMade("Too Old");
            }
        }
    }


}
