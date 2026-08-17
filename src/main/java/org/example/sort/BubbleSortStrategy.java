package org.example.sort;

import org.example.collection.CustomLinkedList;
import org.example.entity.User;

import java.util.Comparator;

public class BubbleSortStrategy implements SortStrategy {

    @Override
    public void sort(CustomLinkedList<User> users, Comparator<User> comparator) {

        int n = users.size();

        for (int i = 0; i < n - 1; i++) {

            boolean swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {

                if (comparator.compare(users.get(j), users.get(j + 1)) > 0) {

                    User temp = users.get(j);
                    users.set(j, users.get(j + 1));
                    users.set(j + 1, temp);
                    swapped = true;

                }

            }

            if (!swapped) {
                break;
            }

        }

    }

    @Override
    public String getStrategyName() {
        return "BubbleSort";
    }

}