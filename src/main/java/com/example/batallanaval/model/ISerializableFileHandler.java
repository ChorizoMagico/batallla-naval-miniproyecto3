package com.example.batallanaval.model;
/**
 * Interface which Handles object serialization and deserialization using Java's native serialization mechanism.
 *
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */
public interface ISerializableFileHandler {

    /**
     * Serializes a given object and saves it to the specified file.
     *
     * @param fileName the name of the file where the object will be saved
     * @param element the object to be serialized
     */
    void serialize(String fileName, Object element);

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
    Object deserialize(String fileName);
}
