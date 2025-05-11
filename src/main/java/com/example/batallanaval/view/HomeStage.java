package com.example.batallanaval.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeStage {

    protected static HomeStage instance;

    public static HomeStage getInstance(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeStage.class.getResource("/com/example/batallanaval/FXML/start-view.fxml"));

        if(instance == null){
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Guerra de peruanos");
            stage.setResizable(false);
            stage.show();

            instance = new HomeStage();
        }
        return instance;
    }
}
