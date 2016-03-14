package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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



	public static Properties getProperties() {
		return properties;
	}

}
