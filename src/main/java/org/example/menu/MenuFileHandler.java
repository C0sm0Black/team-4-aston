package org.example.menu;

import org.example.data.FileHandler;
import org.example.collection.CustomLinkedList;
import org.example.entity.User;

import java.io.IOException;

public class MenuFileHandler {

    public static void saveToFile(CustomLinkedList<User> users, String filename) throws IOException {

        if (filename == null){
            throw new IllegalArgumentException("Имя файла не может быть пустым!");
        }

        if (users == null) {
            throw new IllegalArgumentException(
                    "Список пользователей не может быть null!"
            );
        }

        if (users.isEmpty()) {
            throw new IllegalArgumentException(
                    "Список пользователей пуст!"
            );
        }

        FileHandler.saveToFile(users, filename);

    }

}