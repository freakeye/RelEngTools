// https://stackoverflow.com/questions/7704827/java-reading-xml-file
//
//package releng.vcs.svn-tagging;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;


@XmlRootElement(name = "configuration")
//@XmlType(name = "configuration")
public class Configuration {

    @XmlAttribute(name="projPrefix")
    public String projectPrefix;

    @XmlElement(name="dateFormat")
    public String dateFormat;

    @XmlElement(name="branchDirectory")
    public String branchDirectory;

    @XmlElement(name="tagDirectory")
    public String tagDirectory;

    @XmlAttribute(name="prefix")
    public String tagPrefix;

    @XmlAttribute(name="postfix")
    public String tagPostfix;

    @XmlElement(name="commitMessage")
    public String commitMessage;

    public Configuration() { }
    public Configuration(String projectPrefix, String dateFormat,
                         String branchDirectory, String tagDirectory,
                         String prefix, String postfix, String commitMessage) {

        this.projectPrefix = projectPrefix;
        this.dateFormat = dateFormat;
        this.branchDirectory = branchDirectory;
        this.tagDirectory = tagDirectory;
        this.tagPrefix = prefix;
        this.tagPostfix = postfix;
        this.commitMessage = commitMessage;
    }

    public void obsAsString() {
        System.out.println("{ Configuration\n" +
                "projectPrefix= " + projectPrefix + "\n" +
                "dateFormat= " + dateFormat + "\n" +
                "branchDirectory= " + branchDirectory + "\n" +
                "tagDirectory= " + tagDirectory + "\n" +
                "tagPrefix= " + tagPrefix + "\n" +
                "tagPostfix= " + tagPostfix + "\n" +
                "commitMessage= " + commitMessage + "}\n");
    }


}

