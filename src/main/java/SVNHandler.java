//package releng.vcs.svn-tagging;

/**
 * Created by freakeye on 24-Aug-18.
 *
 * link
 * http://subversion.1072662.n5.nabble.com/getting-a-directory-s-latest-revision-number-td1734.html
 */


import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.*;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.tmatesoft.svn.core.wc2.*;


import java.text.SimpleDateFormat;
import java.util.Date;

import static org.tmatesoft.svn.core.SVNErrorMessage.create;

public class SVNHandler {


/* TODO:
    Класс не должен знать о параметрах из конфига!
    Конфиги читаются спец. классом, кот. вызывается из main-класса SVNTagging

 */

    // auth parameters
    private static String SVN_LOGIN = "";
    private static String SVN_PASWD = "";
    private static ISVNAuthenticationManager authManager = null;

    public static String repoPath = "";
    public static String deliverable = "";

    public static final String PROJECT_NAME = "228_10.2.3.TFNECU.";
    //    public static final String PROJECT_NAME = "000_REINT.";
    public static final String COMMIT_MESSAGE = "[RE] tag created, due to REPROJ-29623";
    // tag params
    public static final String DIR_FOR_TAGS = "release-tags";
//    public static final String DIR_FOR_TAGS = "tags";
    public static final String BRANCHES_WORD = "branches/";
    public static final String DATE_FORMAT = "yyyy.MM.dd";


    public static final String RIGHT_SLASH = "/";
    // delimeter
    public static final String _ = "_";



    public static void setSVNKitParams(String login, String paswd) {
//        SVN_LOGIN = login;
//        SVN_PASWD = paswd;

        /*
         * For using over http:// and https://
         */
        DAVRepositoryFactory.setup();
        /*
         * For using over svn:// and svn+xxx://
         */
        SVNRepositoryFactoryImpl.setup();

        authManager = SVNWCUtil.createDefaultAuthenticationManager(login, paswd);
    }

    // if branchURL doesn't exist - is throught svnExc
    public static long getLastRevision(String branchURL) {

        long result = -1;

        try {
//            setupLibrary();
//            ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(login, paswd);
            SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded(branchURL));
            repository.setAuthenticationManager(authManager);
            SVNDirEntry entry = repository.info(".", -1);
            result = entry.getRevision();
//System.out.println("Latest Rev: " + result);

        } catch (SVNException svnExc) {
            svnExc.printStackTrace();
            System.exit(1);
        }

        return result;
    }

    // TODO: Test - is there @DIR_FOR_TAGS in @repoPath - ?


    //
    //
    //
    public static boolean createTag(String branchUrl, long revision,String tagDestinationPath) {
        boolean result = false;

//System.out.println("repoPath: " + repoPath + " | deliverable: " + deliverable + " | -m: " + COMMIT_MESSAGE);
//System.out.println("\ntagName : " + tagName);
System.out.println("\ntagDestinationPath : " + tagDestinationPath);

        // TODO: Test - is there tag on @revision - ?
        // if

            try {
//                final SvnList list = new SvnOperationFactory().createList();
                // https://stackoverflow.com/questions/12782036/java-svnkit-tagging-if-tag-not-exists
                SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
                svnOperationFactory.setAuthenticationManager(authManager);
                SvnRemoteCopy tagOperation = svnOperationFactory.createRemoteCopy();

                // Setup source - sourceObject
                SVNURL sourceURL = SVNURL.parseURIEncoded(branchUrl);
                SVNRevision sourceRev = SVNRevision.create(revision);
                SvnCopySource sourceObject = SvnCopySource.create(SvnTarget.fromURL(sourceURL), sourceRev);
                tagOperation.addCopySource(sourceObject);

                // setup destination
                SVNURL destinationURL = SVNURL.parseURIEncoded(tagDestinationPath);
                tagOperation.setSingleTarget(SvnTarget.fromURL(destinationURL));
System.out.println("destinationURL is: " + destinationURL);

                // Make the operation fail when destination exists
                tagOperation.setFailWhenDstExists(true);
                tagOperation.setCommitMessage(COMMIT_MESSAGE);
                tagOperation.run();

            } catch (SVNException svnExc) {
                /*
                if ( svnExc.getErrorMessage() = SVNErrorCode.ENTRY_EXISTS ) {
                    System.out.println("Entry exists");
                }*/
                svnExc.printStackTrace();
                System.exit(1);
            }
//        }

        return result;
    }

    // prepare tag name
    // short tag name: tag_2018.08.16_228_10.2.3.TFNECU.SSP.Release1.Drop1.Fix12_rev7974
    // full: https://svncn.netcracker.com/TFNECU.TOMS.SSP/release-tags/tag_2018.11.09_228_10.2.3.TFNECU.SSP.Release1.CD9_rev8736
    //
    static String makeTagName(String branchUrl, long revision) {
        String result = "",
                date = new SimpleDateFormat(DATE_FORMAT).format(new Date());

        int lastIndx = branchUrl.lastIndexOf(BRANCHES_WORD);
        if ( lastIndx > 0 ) {
            deliverable = branchUrl.substring(lastIndx + BRANCHES_WORD.length(), branchUrl.length());
            if ( deliverable.contains(RIGHT_SLASH) ) {
                deliverable = deliverable.replace(RIGHT_SLASH, _);
            }

            // short tag name
            result = "tag" + _ + date + _ + PROJECT_NAME + deliverable + _ + "rev" + revision;

            // full tag name
            // URL-address of repo
            repoPath = branchUrl.substring(0, branchUrl.indexOf(BRANCHES_WORD));
            // url address of tag - is tag destination path
            result = repoPath + DIR_FOR_TAGS + "/" + result;
        }
System.out.println("\ntagDestinationPath : " + result);
        return result;
    }

}
