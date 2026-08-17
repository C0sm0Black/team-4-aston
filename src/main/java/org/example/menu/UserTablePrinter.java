package org.example.menu;

import org.example.collection.CustomLinkedList;
import org.example.entity.User;

public class UserTablePrinter {

    private static int calculateNameWidth(CustomLinkedList<User> users) {
        return users.stream()
                .map(User::getName)
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    private static int calculateEmailWidth(CustomLinkedList<User> users) {
        return users.stream()
                .map(User::getEmail)
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    private static void printSeparator(int nameWidth, int emailWidth) {

        System.out.printf(
                "+-----+-%s-+--------+-%s-+%n",
                "-".repeat(nameWidth),
                "-".repeat(emailWidth)
        );

    }

    public static void printUsers(CustomLinkedList<User> users) {

        int nameWidth = Math.max("Имя".length(), calculateNameWidth(users));
        int emailWidth = Math.max("Почта".length(), calculateEmailWidth(users));
        int index = 1;
        int passwordWidth = 8;

        for (User user : users) {

            printSeparator(nameWidth, emailWidth);

            System.out.printf(
                    "| %-3d | %-" + nameWidth + "s | %-" + passwordWidth + "s | %-" + emailWidth + "s |%n",
                    index,
                    user.getName(),
                    "*".repeat(8),
                    user.getEmail()
            );

            index++;

        }

        printSeparator(nameWidth, emailWidth);

    }

}