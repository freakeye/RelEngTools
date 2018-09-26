//package releng.vcs.svn-tagging;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by almi1016 on 24-Aug-18.
 */
public class IOData {

    //
    //
    public static void readFile(File file, ArrayList<String> arr) {

        final String UTF8_BOM = "\uFEFF";
        String line;

        try {
            BufferedReader inputBuffer = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), "UTF8"));
            try {

                // read 1st line and remove BOM
                //
                line = inputBuffer.readLine();
                if (line.startsWith(UTF8_BOM)) {      // npE - if file is empty
                    line = line.substring(1);
                }
                addToArray(line, arr);

                // read rest lines
                //
                while ((line = inputBuffer.readLine()) != null) {
                    addToArray(line, arr);
                }
            } /*catch (NullPointerException npExc) {
                npExc.printStackTrace();
                System.out.println("input file is empty!");
            } */ finally {
                inputBuffer.close();
            }
        } catch (IOException ioExc) {
            ioExc.printStackTrace();
            throw new RuntimeException(ioExc);
        } catch (NullPointerException npExc) {
            npExc.printStackTrace();
            System.out.println("input file is empty!");
        } finally {
            //inputBuffer.close();
        }
    }


    //
    // write list of strings @inpList to file @fileName
    //
    public static void writeListToFile(List<String> inpList, String fileName) {

        try {
            // Определяем файл
            File file = new File(fileName);

            // проверка наличия файла, если файл не существует то создаем его
            //
            if (!file.exists()) {
                file.createNewFile();
System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n File has been created!");
            } else {
                // сущ-ий файл используется как список помов для загрузки
            }

            BufferedWriter bufWr = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "UTF8"));

            // Запись строк из переданного списка в файл
            //
            for (String str : inpList) {
                bufWr.write(str + '\n');
                bufWr.flush();
            }
        } catch (UnsupportedEncodingException | FileNotFoundException exc) {
            exc.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void addToArray(String str, ArrayList<String> arr) {
        str = str.trim();
        if ( str.length() > 0 ) {
            arr.add(str);
        }
    }
}