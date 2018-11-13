/**
 * Created by almi1016 on 24-Aug-18.
 */
//package releng.vcs.svn-tagging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class SVNTagging {

    public static final String INPUT_FILE = "input_branches.list";
    public static String branch = "branchName";
    public static String branchURL = "https://svn.Domain.Com/RepoName/branchName";

    public static final String OUTPUT_FILE = "result.txt";
    public static final String CONFIG_FILE = "config.xml";
   // public Configuration cfg;                           // from CONFIG_FILE

    public static ArrayList<String> auxArrList = new ArrayList<String>();
    public static ArrayList<String> tagList = new ArrayList<String>();

    // input [branches] array
    // list of Brances and theirs lastRevisions
    public static Properties brList = new Properties();

    public static void main(String[] args) {

        File inputFile = new File(INPUT_FILE);
        File configFile = new File(CONFIG_FILE);

        // filling brList
        IOData.readFile(inputFile, auxArrList);

        SVNHandler.setSVNKitParams(args[0], args[1]);
/*
        Configuration cfg = JaxbHandler.unmarshallingXmlToObject(configFile);
        cfg.obsAsString();
*/

        for (String s : auxArrList) {
            long lastRev = -1;
            System.out.println("\n  added: " + s);

            // search for lastRevision for each branch @branchURL in repo
            lastRev = SVNHandler.getLastRevision(s);

            if (lastRev > 0) {
                brList.setProperty(s, String.valueOf(lastRev));

                // prepare full tag name
                String fullTagName = SVNHandler.makeTagName(s, lastRev);

//                SVNHandler.createTag(s, lastRev, fullTagName);

                tagList.add(fullTagName);
            } else {
                brList.setProperty(s, "Undefined");                         // lastRevision was Undefined
            }
        }
        auxArrList = null;

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("auxResult.txt");
            brList.store(fos, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // write created tags down to result-file
        IOData.writeListToFile(tagList, OUTPUT_FILE);
    }
}
