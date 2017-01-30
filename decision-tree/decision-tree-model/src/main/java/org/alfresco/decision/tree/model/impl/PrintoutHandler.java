

package org.alfresco.decision.tree.model.impl;


import org.alfresco.decision.tree.model.api.Handler;

public class PrintoutHandler implements Handler {

    private String message;

    public PrintoutHandler( String message ) {
        this.message = message;
    }

    @Override
    public void execute() {
        System.out.println( "Message: " + message );
    }

}
