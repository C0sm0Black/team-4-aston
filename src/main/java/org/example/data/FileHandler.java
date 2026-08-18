package org.example.data;

import org.example.collection.CustomLinkedList;
import org.example.entity.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class FileHandler {

    /**
     * Сохраняет список пользователей в файл в режиме добавления (APPEND).
     * Если файл или директория не существуют, они будут созданы автоматически.
     *
     * @param users    Коллекция пользователей для сохранения
     * @param filename Путь к файлу (например "user.txt")
     * @throws IOException Произошла ошибка при записи на диск
     */
    public static void saveToFile(CustomLinkedList<User> users, String filename) throws IOException {

        Path path = Path.of(filename);

        if (path.getParent() != null && !Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }

        List<String> linesToWrite = users.stream()
                .map(user -> String.join(";", user.getName(), user.getPassword(), user.getEmail()))
                .collect(Collectors.toList());

        Files.write(
                path,
                linesToWrite,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );

    }

}