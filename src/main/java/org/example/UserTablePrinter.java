package org.example;

import java.util.ArrayList;
import java.util.List;

public class UserTablePrinter {
    private static int calculateNameWidth(List<User> users){
        return users.stream()
                .map(User::getName)
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    private static int calculateEmailWidth(List<User> users){
        return users.stream()
                .map(User::getEmail)
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    private static void printSeparator(
            int nameWidth,
            int emailWidth
    ) {
        System.out.printf(
                "+-----+-%s-+--------+-%s-+%n",
                "-".repeat(nameWidth),
                "-".repeat(emailWidth)
        );
    }

    public static void printUsers(List<User> users){
        int nameWidth = Math.max("Имя".length(), calculateNameWidth(users));
        int emailWidth = Math.max("Почта".length(), calculateEmailWidth(users));
        int index = 0;

        for(User i:users){
            printSeparator(nameWidth, emailWidth);
            System.out.printf(
                    "| %-3d | %-" + nameWidth + "s | %-" +
                            passwordWidth + "s | %-" + emailWidth + "s |%n",
                    index + 1,
                    users.getName(),
                    maskPassword(users.getPassword()),
                    users.getEmail()
            );
        }
    }
}
