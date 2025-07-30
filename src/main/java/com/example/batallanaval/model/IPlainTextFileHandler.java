package com.example.batallanaval.model;

/**
 * Interface that defines basic operations for handling plain text files.
 * Provides methods to write content to a file and read its contents line by line.
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */
public interface IPlainTextFileHandler {

    /**
     * Writes the specified content to a plain text file.
     * If the file already exists, it may be overwritten or modified depending on the implementation.
     *
     * @param fileName The name or path of the file to write to.
     * @param content  The text content to be written into the file.
     */
    void writeToFile(String fileName, String content);


    /**
     * Reads the contents of a plain text file.
     *
     * @param fileName The name or path of the file to read from.
     * @return An array of strings, where each element represents one line from the file.
     */
    String[] readFromFile(String fileName);

}
