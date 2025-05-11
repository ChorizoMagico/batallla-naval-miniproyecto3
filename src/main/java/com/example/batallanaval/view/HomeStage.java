package com.example.batallanaval.view;

import java.io.IOException;

public class HomeStage {

    protected static HomeStage instance;

    public static HomeStage getInstance() throws IOException {
        if(instance == null){
            instance = new HomeStage();
        }
        return instance;
    }
}
