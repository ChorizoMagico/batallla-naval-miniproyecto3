package com.example.batallanaval.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartStage {

    protected static StartStage instance;

    public static StartStage getInstance(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartStage.class.getResource("/com/example/batallanaval/FXML/start-view.fxml"));

        if(instance == null){
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Guerra de peruanos");
            stage.setResizable(false);
            stage.show();

            instance = new StartStage();
        }
        return instance;
    }
}
