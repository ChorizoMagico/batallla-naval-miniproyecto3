package com.example.batallanaval.model;

public interface IPlainTextFileHandler {
    void writeToFile(String fileName, String content);
    String[] readFromFile(String fileName);

}
