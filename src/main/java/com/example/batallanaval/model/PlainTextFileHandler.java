package com.example.batallanaval.model;

import java.io.*;

/**
 * Class who manage the plain text serialization
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */

public class PlainTextFileHandler implements IPlainTextFileHandler {

    /**
     * Writes the specified content in a plain text file.
     *
     * @param fileName Name of the file in which the content is being written.
     * @param content Content to write in the file.
     */
    @Override
    public void writeToFile(String fileName, String content) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    /**
     * Reads the content in a file and returns it as an array of strings
     * Each line is a separate entry, divided by commas
     *
     * @param fileName Name of the file which is being read.
     * @return Array of strings with the read data, separated by commas.
     */
    @Override
    public String[] readFromFile(String fileName) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line.trim()).append(",");
            }
        } catch (IOException e) {
            System.err.println("Error al deserializar el archivo: " + fileName);
            e.printStackTrace();
            return new String[0];
        }
        return content.toString().split(",");
    };
}
