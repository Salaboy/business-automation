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

//    public void eval( Person instance, java.util.Map<String, Handler> handlers ) {
//        if ( instance.getAge() < 30 ) {
//            if ( instance.getCity().equals( "London" ) ) {
//                if ( instance.getMarried() == false ) {
//                    handlers.get( "Send Ad 1" ).execute();
//                }
//                if ( instance.getMarried() == true ) {
//                    handlers.get( "Send Ad 2" ).execute();
//                }
//            }
//            if ( instance.getCity().equals( "Mendoza" ) ) {
//                handlers.get( "Doesn't Apply" ).execute();
//            }
//        }
//        if ( instance.getAge() > 30 ) {
//            handlers.get( "Too Old" ).execute();
//        }
//    }

}
