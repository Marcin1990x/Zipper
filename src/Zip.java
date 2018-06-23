import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javafx.scene.control.Label;

/**
 * Functions for zip and unzip files
 */
public class Zip
{

    private static final int BUFFER = 1024; // size of byte array

    /**
     * Function for files zip
     * @param filesPath files paths for zip file
     * @param zipName .zip file name
     * @param statement information after zip function execution
     */
    public static void zipFiles(String[] filesPath, String zipName, Label statement)
    {
        byte[] tempData = new byte[BUFFER];

        String newZipPath = Zip.inputFilesPath(filesPath); // creating default zip file directory from first chosen file directory

        try
        {
            //zip file stream with default directory
            ZipOutputStream zipOutput = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(newZipPath + zipName), BUFFER));
            zipOutput.setLevel(9); // zip compression level

            for (int i = 0; i < filesPath.length; i++)
            {
                File file = new File(filesPath[i]);

                BufferedInputStream filesInput = new BufferedInputStream(new FileInputStream(file.getPath()), BUFFER);

                zipOutput.putNextEntry(new ZipEntry(file.getName()));

                int counter; // to store read bytes quantity, -1 if it is finished

                while ((counter = filesInput.read(tempData, 0, BUFFER)) != -1) // checking of filesInpun end of stream
                    zipOutput.write(tempData, 0, counter); // zip file writing

                zipOutput.closeEntry();
                filesInput.close();
            }

            zipOutput.close();
            statement.setText("Files correctly zipped!"); // informatio label

        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Function for zip file unzip
     * @param zipFilePath file path for unzip
     * @param catalogName .zip file name
     * @param statement information after unzip function execution
     */
    public static void unzipFiles(String zipFilePath, String catalogName, Label statement)
    {
        // default directory for unzipped files catalog, same as zip file directory
        File catalog = new File(Zip.inputFilePath(zipFilePath) + File.separator + catalogName);

        if (!catalog.exists()) // creation of directory
            catalog.mkdir();

        ZipEntry entry = null; // ZipEntry object to manage .getNextEntry function

        byte[] tempData = new byte[BUFFER];

        try
        {
            ZipInputStream zipInput = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFilePath), BUFFER));

            while ((entry = zipInput.getNextEntry()) != null) // checking of zip file end
            {
                BufferedOutputStream outputFiles = new BufferedOutputStream
                        (new FileOutputStream(catalog + File.separator + entry.getName()), BUFFER);

                int counter;

                while ((counter = zipInput.read(tempData, 0, BUFFER)) != -1)
                    outputFiles.write(tempData, 0, counter);

                outputFiles.close();
                zipInput.closeEntry();
            }

            zipInput.close();
            statement.setText("File correctly unzipped!"); // information after zip file unzip

        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Modification of absolute file path
     * @param filesPath
     * @return returns file path without its name
     */
    public static String inputFilesPath(String[] filesPath)
    {
        return filesPath[0].substring(0, filesPath[0].lastIndexOf('\\') + 1);
    }

    /**
     * Modification of absolute file path
     * @param filePath
     * @return returns file path without its name
     */
    public static String inputFilePath(String filePath)
    {
        return filePath.substring(0, filePath.lastIndexOf('\\') + 1);
    }
}
