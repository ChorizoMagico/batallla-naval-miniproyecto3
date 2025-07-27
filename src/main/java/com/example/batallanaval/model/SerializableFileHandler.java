package com.example.batallanaval.model;

import java.io.*;

/**
 * Handles object serialization and deserialization using Java's native serialization mechanism.
 *
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */

public class SerializableFileHandler implements ISerializableFileHandler {


    /**
     * Serializes a given object and saves it to the specified file.
     *
     * @param fileName the name of the file where the object will be saved
     * @param element the object to be serialized
     */
    @Override
    public void serialize(String fileName, Object element){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))){
            oos.writeObject(element);
        }catch(IOException e){
            System.err.println("Error al deserializar el archivo: " + fileName);
            e.printStackTrace();
        }
    };

    /**
     * Deserializes and returns an object from the specified file.
     * <p>
     * If the class version is incompatible or an I/O error occurs,
     * the file may be deleted and {@code null} is returned.
     * </p>
     *
     * @param fileName the name of the file from which the object will be read
     * @return the deserialized object, or {@code null} if an error occurs
     */
    @Override
    public Object deserialize(String fileName) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))){
            return ois.readObject();

        } catch (InvalidClassException e) {
            System.err.println("Versión incompatible del archivo serializado: " + e.getMessage());
            new File(fileName).delete();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al deserializar el archivo: " + fileName);
            e.printStackTrace();
        }

        return null;
    }
}
