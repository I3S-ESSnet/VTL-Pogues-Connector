package fr.insee.vtl.connectors;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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

		StructureBuilder builder = StaticDataset.create();

		try {
			// Read the Simpsons questionnaire as a DOM
			File simpsonsXML = new File("src/main/resources/simpsons.xml");
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder;

			documentBuilder = builderFactory.newDocumentBuilder();
			Document simpsonsDocument = documentBuilder.parse(simpsonsXML);
			NodeList variables = simpsonsDocument.getElementsByTagName("Variable");
			for (int index = 0; index < variables.getLength(); index++) {
				Element variableElement = (Element) variables.item(index);
				Element datatypeElement = (Element) variableElement.getElementsByTagName("Datatype").item(0);
				Element nameElement = (Element) variableElement.getElementsByTagName("Name").item(0);
				String variableName = nameElement.getTextContent();
				String typeName = datatypeElement.getAttribute("typeName");

				Class<?> typeClass = getTypeClass(typeName);
				if (typeClass == null) System.out.println("Unknown type: " + typeName);
				else {
					builder.addComponent(variableName, Role.IDENTIFIER, typeClass); // Doit exister et être unique
				}
				System.out.println("Added to connector:\nvariable name: " + variableName);
				System.out.println("variable type: " + typeName);
			}
		} catch (Exception e) {
			throw new ConnectorException(e);
		}



//		builder.addComponent("ID", Role.IDENTIFIER, String.class); // Doit exister et être unique
//		builder.addComponent("NAME", Role.ATTRIBUTE, String.class); // Nom de la variable, role identifiant également
//		builder.addComponent("AGE", Role.ATTRIBUTE, Long.class);

		return builder
//				.addPoints("ID", "Hadrien", 32L)
				.build();
	}

	@Override
	public Dataset putDataset(String identifier, Dataset dataset) throws ConnectorException {
		// TODO Auto-generated method stub
		return null;
	}

	private Class<?> getTypeClass(String type) {

		if ("BOOLEAN".equals(type)) return Boolean.class;
		if ("DATE".equals(type)) return String.class; // TODO: find more precise type
		if ("NUMERIC".equals(type)) return Long.class;
		if ("TEXT".equals(type)) return String.class;

		return null;

	}
}
