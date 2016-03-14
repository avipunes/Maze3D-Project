package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import Presenter.Properties;

public class JaxbUtil {

	private static Properties properties = null;

	public static Properties readXML() {
		try {
			File file = new File(System.getProperty("user.dir") + "//" + "properties.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Properties.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			properties = (Properties) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return properties;
	}

	public static void writeXML() {
		List<String> algoList = new ArrayList<>();
		algoList.add("BFS");
		algoList.add("Astar Man");
		algoList.add("Astar Air");
		
		Properties properties = new Properties();
		properties.setColumn(33);
		properties.setLevel(3);
		properties.setLine(30);
		properties.setThreadPoolNumber(1);
		properties.setSolvesAlgo(algoList);
		
		try {
			File file = new File(System.getProperty("user.dir") + "//" + "properties.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Properties.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(properties, file);
			jaxbMarshaller.marshal(properties, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	public static Properties getProperties() {
		return properties;
	}

}
