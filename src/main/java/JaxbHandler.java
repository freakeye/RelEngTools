/*
https://javadevblog.com/primer-raboty-s-jaxb-sohranyaem-java-ob-ekt-v-xml-vosstanavlivaem-ob-ekt-iz-xml.html
 */

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class JaxbHandler {

    private static Configuration result = null;

    // заполнение объекта из XML файла
    public static Configuration unmarshallingXmlToObject(File file) {

        try {
            // создание объекта JAXBContext - точки входа для JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);
            Unmarshaller unmXmlToObj = jaxbContext.createUnmarshaller();

            result = (Configuration) unmXmlToObj.unmarshal(file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }
}