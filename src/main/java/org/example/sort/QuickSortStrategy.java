package org.example.sort;

import org.example.collection.CustomLinkedList;
import org.example.entity.User;

import java.util.Comparator;

public class QuickSortStrategy implements SortStrategy {


    @Override
    public void sort(CustomLinkedList<User> users, Comparator<User> comparator) {
        quickSort(users, 0, users.size() - 1, comparator);
    }

    private void quickSort(CustomLinkedList<User> users, int low, int high, Comparator<User> comparator) {

        if (low < high) {

            int pivotIndex = partition(users, low, high, comparator);
            quickSort(users, low, pivotIndex - 1, comparator);
            quickSort(users, pivotIndex + 1, high, comparator);

        }

    }

    private int partition(CustomLinkedList<User> users, int low, int high, Comparator<User> comparator) {

        User pivot = users.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {

            if (comparator.compare(users.get(j), pivot) <= 0) {

                i++;
                User temp = users.get(i);
                users.set(i, users.get(j));
                users.set(j, temp);

            }

        }

        User temp = users.get(i + 1);
        users.set(i + 1, users.get(high));
        users.set(high, temp);
        return i + 1;

    }

    @Override
    public String getStrategyName() {
        return "QuickSort";
    }

}