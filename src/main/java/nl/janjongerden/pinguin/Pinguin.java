package nl.janjongerden.pinguin;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Pinguin extends Application {

    private static final String MP3_PLAYER = "/usr/bin/mpg321";
    private Process process;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Pinguin");
        stage.getIcons().add(new Image(Pinguin.class.getResourceAsStream("/pinguin.jpg")));

        BorderPane componentLayout = new BorderPane();
        componentLayout.setPadding(new Insets(20, 0, 20, 20));

        VBox list = new VBox(20);

        addButton("Pinguin Radio", "http://pr320.pinguinradio.com:80/", list);
        addButton("Pinguin on the Rocks", "http://po192.pinguinradio.com:80/", list);
        addButton("Pinguin Classics", "http://pc192.pinguinradio.com:80/", list);
        addStopButton(list);

        componentLayout.setTop(list);

        Scene appScene = new Scene(componentLayout, 500, 500);

        stage.setScene(appScene);
        stage.show();
    }

    private void addButton(String name, String url, VBox list) {
        Button button = new Button(name);
        button.setMinWidth(300);
        button.setOnAction(event -> playStream(url));
        list.getChildren().add(button);
    }

    private void addStopButton(VBox list) {
        Button button = new Button("Stop");
        button.setMinWidth(300);
        button.setOnAction(event -> killProcess());
        list.getChildren().add(button);
    }

    private void playStream(String url) {
        Runtime rt = Runtime.getRuntime();
        try {
            killProcess();
            process = rt.exec(MP3_PLAYER + " " + url);
        } catch (IOException e) {
            System.err.println("Error streaming: " + e.getMessage());
        }
    }

    private void killProcess() {
        if (process != null) {
            process.destroyForcibly();
        }
    }

    @Override
    public void stop() {
        killProcess();
    }
}
