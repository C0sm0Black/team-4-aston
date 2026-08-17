package org.example.sort;

import org.example.collection.CustomLinkedList;
import org.example.entity.User;

import java.util.Comparator;

public interface SortStrategy {

    void sort(CustomLinkedList<User> users, Comparator<User> comparator);
    String getStrategyName();

}