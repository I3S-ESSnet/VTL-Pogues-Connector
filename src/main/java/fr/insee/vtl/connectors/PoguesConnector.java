package fr.insee.vtl.connectors;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import no.ssb.vtl.connectors.Connector;
import no.ssb.vtl.connectors.ConnectorException;
import no.ssb.vtl.model.Component.Role;
import no.ssb.vtl.model.Dataset;
import no.ssb.vtl.model.StaticDataset;
import no.ssb.vtl.model.StaticDataset.StructureBuilder;

public class PoguesConnector implements Connector {

	public static String DATASET_ID = "POGUES-TEST";

	@Override
	public boolean canHandle(String identifier) {
		return DATASET_ID.equals(identifier);
	}

	@Override
	public Dataset getDataset(String identifier) throws ConnectorException {

		// Read the Simpsons questionnaire as a DOM
		File fXmlFile = new File("src/main/resources/simpsons.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		} catch (Exception e) {
			throw new ConnectorException(e);
		}
		
		
		StructureBuilder builder = StaticDataset.create();

		builder.addComponent("ID", Role.IDENTIFIER, String.class); // Doit exister et être unique
		builder.addComponent("NAME", Role.ATTRIBUTE, String.class); // Nom de la variable, role identifiant également
		builder.addComponent("AGE", Role.ATTRIBUTE, Integer.class);

		return builder.build();
	}

	@Override
	public Dataset putDataset(String identifier, Dataset dataset) throws ConnectorException {
		// TODO Auto-generated method stub
		return null;
	}

}
