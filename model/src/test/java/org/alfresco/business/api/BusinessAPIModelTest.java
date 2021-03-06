package org.alfresco.business.api;

import org.alfresco.business.api.events.AfterExecutionEvent;
import org.alfresco.business.api.events.AfterGenerationEvent;
import org.alfresco.business.api.events.BeforeExecutionEvent;
import org.alfresco.business.api.events.BeforeGenerationEvent;
import org.alfresco.business.api.tests.mocks.MockExecutionEventListener;
import org.alfresco.business.api.tests.mocks.MockGeneratorEventListener;
import org.alfresco.business.base.BaseDesignModel;
import org.alfresco.business.base.BaseExecutableModel;
import org.alfresco.business.base.BaseGenerator;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Created by msalatino on 30/01/2017.
 */
public class BusinessAPIModelTest {

    @Test
    public void testAPIModelFromConsumer(){
        DesignModel designModel = new BaseDesignModel("model id", "my first model"){};

        // Generators are going to be provided
        BaseGenerator generator = new BaseGenerator() {
            @Override
            public ExecutableModel generate(DesignModel model) {
                for(EventListener l : eventListeners()){
                    l.onEvent(new BeforeGenerationEvent());
                }

                ExecutableModel executableModel = new BaseExecutableModel(designModel){
                    @Override
                    public void execute(Object input) {
                        for(EventListener l : eventListeners()){
                            l.onEvent(new BeforeExecutionEvent());
                        }
                        // Actually do something here ...
                        for(EventListener l : eventListeners()){
                            l.onEvent(new AfterExecutionEvent());
                        }
                    }
                };
                for(EventListener l : eventListeners()){
                    l.onEvent(new AfterGenerationEvent());
                }

                return executableModel;
            }
        };

        MockGeneratorEventListener mockGeneratorEventListener = new MockGeneratorEventListener();
        generator.addEventListener(mockGeneratorEventListener);

        // This is the actual executableModel that can be used in runtime
        ExecutableModel executableModel = generator.generate(designModel);

        Assert.assertEquals(2, mockGeneratorEventListener.events().size());

        MockExecutionEventListener mockExecutionEventListener = new MockExecutionEventListener();
        executableModel.addEventListener(mockExecutionEventListener);

        executableModel.execute(new Object());

        Assert.assertEquals(2, mockExecutionEventListener.events().size());


    }
}