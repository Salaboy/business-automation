package org.alfresco.decision.service.model.test;


import org.alfresco.business.api.DesignModel;
import org.alfresco.business.api.EventListener;
import org.alfresco.business.api.ExecutableModel;
import org.alfresco.decision.service.model.DecisionDesignModel;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ModelTests {

	@Test
	public void simpleModelTest() {
		DesignModel designModel = new DecisionDesignModel("AAA",
						"my first decision", "content for the model");

		ExecutableModel executableModel = new MockDecisionGenerator().generate(designModel);
		EventListener listener = new MockDecisionEventListener();

		executableModel.addEventListener(listener);
		Object input = new Object();

		executableModel.execute(input);

		List<String> events = ((MockDecisionEventListener) listener).getEvents();


		assertEquals(1, events.size());


	}

}
