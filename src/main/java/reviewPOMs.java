/**
 * Created by freakeye on 15-Jan-18.
 */

import java.io.*;
import java.util.*;

public class reviewPOMs {

    public static final String LEFT_SLASH = "\\";
    public static final String SCOPE = "pom.xml";

    // input [job = branch] file
    // This file must have format <job>:<branch>
    public static final String INRUT_FILE = "input.txt";

    // input [job = branch] array
    public static Properties inputJobBranches = new Properties();

    // job name
    public static String job = "JobName";
    // url of branch of job, should be equal RepoName or DevLine
    public static String url = "https://svn.Domain.Com/RepoName/branch";

    // list of all pom.xml in repository
    //
    public static String pomFileName = job + ".list";

    // file contains POMs with left slashes
    //
    public static String outFileName = job + ".out.txt";

    // authentication parameters for SVN-repos
    //
    public static String SVN_NAME = "";
    public static String SVN_PSWD = "";

    // auxulary array
    //
    public static List<String> pomList = new ArrayList<>();

    // list of POMs with left slashes
    //
    public static List<String> badPoms = new ArrayList<>();


    /*


     */
    public static void main(String[] args) {

        SVN_NAME = args[0];
        SVN_PSWD = args[1];

        File inputFile = new File(INRUT_FILE);

        // filling inputJobBranches
        readFile(inputFile);
        inputJobBranches.list(new PrintStream(System.out));

        //
        // https://www.mkyong.com/java/java-properties-file-examples/
        //
        Enumeration<?> enumerator = inputJobBranches.propertyNames();
        while (enumerator.hasMoreElements()) {

            // initializing variables
            job = (String) enumerator.nextElement();
            url = inputJobBranches.getProperty(job);
            pomFileName = job + ".list";
            outFileName = job + ".out.txt";

            // clearing list before next iteration
            pomList.clear();

System.out.println("Job: " + job + "\tURL: " + url + "\nPOM file is: " + pomFileName);

            // reading all entries in current repository
            // and filling pomList
            //
            DisplayRepositoryTree.list(url);
            System.out.println("~~~~~~~~~~~~~~~~~~~~\n\npomList.size = " + pomList.size());
            writeListToFile(pomList, pomFileName);

            // reading pomList
            //
            for (String str : pomList) {
                String tmpString = DisplayFile.main(str);
                System.out.println(tmpString);

                // filtration
                //
                if (tmpString.contains(LEFT_SLASH)) {
                    badPoms.add(str);
                }
            }

            // writing to outFileName
            //
            writeListToFile(badPoms, outFileName);

        }
    }


    //
    // write list of strings @inpList to file @fileName
    //
    public static void writeListToFile(List<String> inpList, String fileName) {

        try {
            // file for writing
            File file = new File(fileName);

            /*
             * checking for a file presence
             * if file does not exist it is created
             * TODO: else - exist file is used a download list
             */
            if (!file.exists()) {
                file.createNewFile();
System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n File has been created!");
            } else {
                //

            }

            BufferedWriter bufWr = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "UTF8"));

            // Writing strings from list
            //
            for (String str : inpList) {
                bufWr.write(str);
                bufWr.flush();
            }
        } catch (UnsupportedEncodingException | FileNotFoundException exc) {
            exc.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //
    // TODO: if file is empty
    //
    public static void readFile(File file) {

//        String line;
        try {

            try {
                /*
                 * file should be witout BOM
                 * TODO: UTF-8 encoding does not recognize initial BOM
                 * http://bugs.java.com/bugdatabase/view_bug.do?bug_id=4508058
                 * http://rsdn.org/forum/java/3941503.all
                 *
                 * if file has BOM, it is neccessary read 1st string
                 * and remove 2 (for Java) or 3 (for MsWin) bytes of BOM from there
                 */
                FileInputStream inpFileS = new FileInputStream(file);

                inputJobBranches.load(inpFileS);

            } catch (IOException ioExc) {
                 throw new RuntimeException(ioExc);
            }

        } finally {
        }
    }
}
