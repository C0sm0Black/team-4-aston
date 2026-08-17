package org.example.sort;

import org.example.collection.CustomLinkedList;
import org.example.entity.User;

import java.util.Comparator;

public class InsertionSortStrategy implements SortStrategy {

    @Override
    public void sort(CustomLinkedList<User> users, Comparator<User> comparator) {

        for (int i = 1; i < users.size(); i++) {

            User key = users.get(i);
            int j = i - 1;

            while (j >= 0 && comparator.compare(users.get(j), key) > 0) {

                users.set(j + 1, users.get(j));
                j--;

            }

            users.set(j + 1, key);

        }

    }

    @Override
    public String getStrategyName() {
        return "InsertionSort";
    }

}