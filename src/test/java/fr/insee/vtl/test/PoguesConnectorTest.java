package fr.insee.vtl.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.insee.vtl.connectors.PoguesConnector;
import no.ssb.vtl.connectors.ConnectorException;
import no.ssb.vtl.model.Dataset;

class PoguesConnectorTest {

	@Test
	void testGetDataset() throws ConnectorException {

		PoguesConnector poguesConnector = new PoguesConnector();

		assertNull(poguesConnector.getDataset("WRONG_ID"));
		Dataset poguesDataset = poguesConnector.getDataset(PoguesConnector.DATASET_ID);
		assertNotNull(poguesDataset);
		assertTrue(poguesDataset.getDataStructure().containsKey("ID"));

		
	}
}
