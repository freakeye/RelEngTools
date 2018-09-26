// https://stackoverflow.com/questions/7704827/java-reading-xml-file
//
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.File;

@XmlRootElement
@XmlType(name = "project")
public class Configuration {

    public String projectPrefix;
    public String dateFormat;
    public String branchDirectory;
    public String tagDirectory;
    public String tagPrefix;
    public String tagPostfix;
    public String commitMessage;

    @XmlAttribute
    public void setProjectPrefix(String projectPrefix) {
        this.projectPrefix = projectPrefix;
    }

    @XmlElement
    public void setBranchDirectory(String branchDirectory) {
        this.branchDirectory = branchDirectory;
    }

    @XmlElement
    public void setTagDirectory(String tagDirectory) {
        this.tagDirectory = tagDirectory;
    }

    @XmlAttribute
    public void setTagPrefix(String tagPrefix) {
        this.tagPrefix = tagPrefix;
    }

    @XmlAttribute
    public void setTagPostfix(String tagPostfix) {
        this.tagPostfix = tagPostfix;
    }

    @XmlElement
    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    @XmlElement
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }


/*
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @XmlElement
    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    @XmlAttribute
    public void setId(int id) {
        this.id = id;
    }

    public static void xmlToOblect() {

        try {

            File file = new File(SVNTagging.OUTPUT_FILE);
            JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Configuration customer = (Configuration) jaxbUnmarshaller.unmarshal(file);
            System.out.println(customer);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    */
}

