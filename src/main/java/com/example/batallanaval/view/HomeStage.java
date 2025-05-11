package com.example.batallanaval.view;

import com.example.batallanaval.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeStage extends Stage {

    protected static HomeStage instance;

    protected Scene scene;

    protected FXMLLoader fxmlLoader;

    public HomeStage() throws IOException {
        fxmlLoader = new FXMLLoader(
                Main.class.getResource("home-view.fxml")
        );
        Parent root = fxmlLoader.load();
        scene = new Scene(root);
        initStage();
    }

    public void initStage() {
        setTitle("Gran Guerra Peruana");
        setResizable(false);
        getIcons().add(
                new Image(String.valueOf(getClass().getResource("/com/example/batallanaval/favicon.png"))));
        setScene(scene);
        show();
    }

    public static HomeStage getInstance() throws IOException {
        if(instance == null){
            instance = new HomeStage();
        }
        return instance;
    }
}
