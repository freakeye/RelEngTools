/**
 * Created by almi1016 on 15-Jan-18.
 */


import org.tmatesoft.svn.core.io.SVNRepository;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class reviewPOMs {

    public static final String LEFT_SLASH = "\\";
    public static final String SCOPE = "pom.xml";

    public static String url = "https://svncn.netcracker.com/videotron.cube/branches/iter5_stab";

    // список всех pom-файлов в репозитории
    //
    public static String pomFileName = "iter5_stab.list";

    // список pom.xml содержащих LEFT_SLASHes
    //
    public static String outFileName = "iter5_stab.out";

    // auth parameters
    //
    public static String SVN_NAME = "";
    public static String SVN_PSWD = "";

    // auxulary array
    //
    public static List<String> pomList = new ArrayList<>();


    public static void main(String[] args) {

        SVN_NAME = args[0];
        SVN_PSWD = args[1];


        DisplayRepositoryTree.list(url);
        System.out.println("~~~~~~~~~~~~~~~~~~~~\n\npomList.size = " + pomList.size());
        writeFile(pomList, pomFileName);

        DisplayFile.main(url);
        //
        //filtrationList(outFileName);

        // запись в outFileName
        //
        //writeFile(pomList, outFileName);

    }

    // метод записи массива строк @inpList в файл @fileName
    //
    public static void writeFile(List<String> inpList, String fileName)
    {
        // Определяем файл
        File file = new File(fileName);

        try {
            // проверка наличия файла, если файл не существует то создаем его
            //
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n File has been created!");
            } else {
                // сущ-ий файл используется как список помов для загрузки

            }

            // PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                // Запись строк из переданного списка в файл
                //
                for (String str: inpList) {
                    out.println(str);
                }
            } finally {
                // После чего мы должны закрыть файл, иначе файл не запишется
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
