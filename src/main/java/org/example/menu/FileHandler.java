package org.example.menu;

import org.example.User;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileHandler {
    public static String toCSVLine(User user){
        return  String.join(";",
                user.getName(),
                user.getPassword(),
                user.getEmail());
    }

    public static void saveToFile(List<User> users, String filename) throws IOException {
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


        File file = new File(filename);
        file.createNewFile();
        PrintWriter pw = new PrintWriter(file);

        for(User user : users){
            pw.println(toCSVLine(user));
        }

        pw.close();
    }
}