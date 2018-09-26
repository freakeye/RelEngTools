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

//import releng.vcs.svn-tagging.SVNHandler;


public class SVNTagging {


    public static final String INPUT_FILE = "input_branches.list";
    public static String branch = "branchName";
    public static String branchURL = "https://svn.Domain.Com/RepoName/branchName";


    public static final String OUTPUT_FILE = "result.txt";
    public static final String CONFIG_FILE = "config.xml";

    public static ArrayList<String> auxArrList = new ArrayList<String>();
    public static ArrayList<String> tagList = new ArrayList<String>();

    // input [branches] array
    // list of Brances and theirs lastRevisions
    public static Properties brList = new Properties();

    public static void main(String[] args) {

        File inputFile = new File(INPUT_FILE);

        // filling brList
        IOData.readFile(inputFile, auxArrList);

        for (String s : auxArrList) {
//            String s = auxArrList.get(i);
            long lastRev = -1;
//System.out.println("  added: " + auxArrList.get(i));
System.out.println("\n  added: " + s);

            // найти в SVN lastRevision для каждой ветки
            // args[0] - is SVN_LOGIN
            // args[1] - is SVN_PASWD
            SVNHandler.setSVNKitParams(args[0], args[1]);
            lastRev = SVNHandler.getLastRevision(s);

            if (lastRev > 0) {
                brList.setProperty(s, String.valueOf(lastRev));
                String tagName = SVNHandler.makeTagName(s, lastRev);
                SVNHandler.createTag(s, lastRev, tagName);

                tagList.add(tagName);
            } else {
                brList.setProperty(s, "Undefined");                         // lastRevision was Undefined
            }
        }
        auxArrList = null;

//System.out.println("brList:\n" + brList.stringPropertyNames());

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
