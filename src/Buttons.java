import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.List;

/**
 * Class Buttons with browse buttons functions and checking conditions for zip and unzip buttons
 */
public class Buttons
{
    /**
     * Function for file chooser, returning paths of chosen files and displaying chosen files for user
     * @param textarea displaying chosen files
     * @param zipNameField zip name text field
     * @return returns chosen files paths
     */
    public static String[] browseButtonAction(TextArea textarea, TextField zipNameField)
    {
        FileChooser fileC = new FileChooser();
        fileC.setInitialDirectory(new File(System.getProperty("user.dir"))); // default directory
        List<File> selectedFiles = fileC.showOpenMultipleDialog(null);

        if (selectedFiles != null)
        {
            zipNameField.setText(Buttons.removeExtension(selectedFiles.get(0).getName())); // default zip name from first chosen file
            // without it's extension

            String[] filesPaths = new String[selectedFiles.size()];
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < selectedFiles.size(); i++)
            {
                filesPaths[i] = selectedFiles.get(i).getAbsolutePath();
                sb.append(selectedFiles.get(i).getName());
                sb.append("\n");
            }
            textarea.setText(sb.toString()); // display selected files - using string builder
            return filesPaths;
        }
        return null;
    }

    public static String browseButtonActionUnzip(TextField textField, TextField unZipNameField)
    {
        FileChooser fileC = new FileChooser();
        fileC.setInitialDirectory(new File(System.getProperty("user.dir")));
        // extension filter for .zip files only
        fileC.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("ZIP files", "*.ZIP"));
        File selectedFile = fileC.showOpenDialog(null);

        if (selectedFile != null)
        {
            unZipNameField.setText(Buttons.removeExtension(selectedFile.getName())); // default unzip catalog named same as zip file
            textField.setText(selectedFile.getName());
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    /**
     * function for removing extension from file name test.txt -> test
     * @param string file name
     * @return returns file name without its extension
     */
    public static String removeExtension(String string)
    {
        return string.substring(0, string.lastIndexOf("."));
    }

    /**
     * Function for checking for errors and displaying - zip
     * @param filePath chosen files absolute paths
     * @param error error label for error displaying
     * @param zipName zip name text field
     * @return returns false is there is some error, true if it is ok
     */
    public static boolean errorZip(String[] filePath, Label error, String zipName) {
        if (filePath == null) // if there are no selected files
        {
            error.setText("Please select files to zip");
            return false;
        }
        if (zipName.trim().isEmpty()) // if zip name contains only blank spaces
        {
            error.setText("Please put zip file name");
            return false;
        }

        char[] arr = zipName.toCharArray(); // for checking if zip name string contains any of forbidden in windows file name characters

        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] == '#' || arr[i] == '%' || arr[i] == '&' || arr[i] == '*' || arr[i] == ':' || arr[i] == '?' || arr[i] == '\\' || arr[i] == '/' || arr[i] == '|')
            {
                error.setText("Zip file name can not contain special characters # % & * : ? \\ / |");
                return false;
            }
        }
        return true;
    }
    /**
     * Function for checking for errors and displaying - zip
     * @param filePath chosen file absolute path
     * @param error error label for error displaying
     * @param unZipName unzip catalog text field
     * @return returns false is there is some error, true if it is ok
     */
    public static boolean errorUnZip(String filePath, Label error, String unZipName)
    {
        if (filePath == null)
        {
            error.setText("Please select file to unzip");
            return false;
        }
        if (unZipName.trim().isEmpty())
        {
            error.setText("Please put unzipped catalog name");
            return false;
        }

        char[] arr = unZipName.toCharArray();

        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] == '#' || arr[i] == '%' || arr[i] == '&' || arr[i] == '*' || arr[i] == ':' || arr[i] == '?' || arr[i] == '\\' || arr[i] == '/' || arr[i] == '|')
            {
                error.setText("Unzipped file catalog name can not contain special characters # % & * : ? \\ / |");
                return false;
            }
        }
        return true;
    }
}
