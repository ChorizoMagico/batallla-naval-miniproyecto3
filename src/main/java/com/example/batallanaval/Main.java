package com.example.batallanaval;

import com.example.batallanaval.view.StartStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        StartStage.getInstance(primaryStage);
    }
}
