import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Main extends Application {

    public static void main(String[] args)
    {
        Application.launch();
    }

    private String[] filesPaths;
    private String filePathUnZip;

    /**
     * Function with GUI declarations and buttons actions
     */
    @Override
    public void start(Stage primaryStage) {

        VBox mainVBox = new VBox(); // main Vbox
        mainVBox.setPadding(new Insets(10, 10, 10, 10));
        StackPane titleBox = new StackPane(); // stackPane for title label
        titleBox.setMinSize(400, 50);
        HBox centerHBox = new HBox(); // Hbox for left and right Vboxes
        centerHBox.setMinSize(400, 200);
        VBox leftVBox = new VBox(); // VBox left for zip
        leftVBox.setMinSize(190, 200);
        VBox rightVBox = new VBox(); // Vbox right for unzip
        rightVBox.setMinSize(190, 200);
        StackPane alarmBox = new StackPane(); // stackPane for error label and statement label

        // Title labels
        Label title = new Label("ZIPPER");
        Label titleZip = new Label("ZIP");
        Label titleUnZip = new Label("UNZIP");
        // Error and result labels
        Label error = new Label();
        error.setTextFill(Color.RED);
        Label statement = new Label();
        statement.setTextFill(Color.GREEN);

        // Controls for zip
        Button browseButtonZip = new Button("BROWSE");
        TextArea filesListArea = new TextArea();
        filesListArea.setEditable(false);
        TextField zipNameField = new TextField(null);
        Button zipButton = new Button("ZIP!");

        // Controls for unzip
        Button browseButtonUnZip = new Button("BROWSE");
        TextField fileAreaUnZip = new TextField();
        fileAreaUnZip.setEditable(false);
        TextField unZipNameField = new TextField(null);
        Button unZipButton = new Button("UNZIP!");

        // Childrens
        titleBox.getChildren().add(title);
        alarmBox.getChildren().addAll(error, statement);
        mainVBox.getChildren().addAll(titleBox, centerHBox, alarmBox);
        centerHBox.getChildren().addAll(leftVBox, rightVBox);

        leftVBox.getChildren().addAll(titleZip, browseButtonZip, filesListArea, zipNameField, zipButton);
        rightVBox.getChildren().addAll(titleUnZip, browseButtonUnZip, fileAreaUnZip, unZipNameField, unZipButton);

        // Scene, stage
        Scene scene = new Scene(mainVBox, 440, 440);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Buttons actions: Browse for zip
        browseButtonZip.setOnAction(e ->
        {
            error.setText(""); // information labels clear
            statement.setText("");
            filesPaths = Buttons.browseButtonAction(filesListArea, zipNameField);
        });

        // Buttons actions: zip button
        zipButton.setOnAction((e ->
        {
            error.setText(""); // information labels clear
            statement.setText("");
            if (Buttons.errorZip(filesPaths, error, zipNameField.getText())) // zip button conditions
                Zip.zipFiles(filesPaths, zipNameField.getText() + ".zip", statement);
        }));

        // Buttons actions: Browse for unzip
        browseButtonUnZip.setOnAction(e ->
        {
            statement.setText(""); // information labels clear
            error.setText("");
            filePathUnZip = Buttons.browseButtonActionUnzip(fileAreaUnZip, unZipNameField);
        });

        // Buttons actions: unzip button
        unZipButton.setOnAction(e ->
        {
            error.setText(""); // information labels clear
            statement.setText("");
            if (Buttons.errorUnZip(filePathUnZip, error, unZipNameField.getText())) // unzip button conditions
                Zip.unzipFiles(filePathUnZip, unZipNameField.getText(), statement);
        });
    }
}
