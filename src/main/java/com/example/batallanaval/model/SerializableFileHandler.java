package com.example.batallanaval.model;

import java.io.*;

public class SerializableFileHandler implements ISerializableFileHandler {

    @Override
    public void serialize(String fileName, Object element){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))){
            oos.writeObject(element);
        }catch(IOException e){
            System.err.println("Error al deserializar el archivo: " + fileName);
            e.printStackTrace();
        }
    };

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
