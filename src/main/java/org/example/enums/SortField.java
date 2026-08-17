package org.example.enums;

import org.example.entity.User;

import java.util.Comparator;

public enum SortField {

    NAME(Comparator.comparing(User::getName)),
    PASSWORD(Comparator.comparingInt((User u) -> u.getPassword().length())
            .thenComparing(User::getPassword)),
    EMAIL(Comparator.comparing(User::getEmail));

    private final Comparator<User> comparator;

    SortField(Comparator<User> comparator) {
        this.comparator = comparator;
    }

    public Comparator<User> getComparator() {
        return comparator;
    }
}