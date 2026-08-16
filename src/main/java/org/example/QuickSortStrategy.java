package org.example;

import java.util.Comparator;
import java.util.List;

public class QuickSortStrategy implements SortStrategy {
    private final SortField sortField;

    public QuickSortStrategy(SortField sortField) {
        this.sortField = sortField;
    }

    @Override
    public void sort(List<User> users) {
        quickSort(users, 0, users.size() - 1, sortField.getComparator());
    }

    private void quickSort(List<User> users, int low, int high, Comparator<User> comparator) {
        if (low < high) {
            int pivotIndex = partition(users, low, high, comparator);
            quickSort(users, low, pivotIndex - 1, comparator);
            quickSort(users, pivotIndex + 1, high, comparator);
        }
    }

    private int partition(List<User> users, int low, int high, Comparator<User> comparator) {
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
        return "QuickSort (" + sortField + ")";
    }
}