package org.example;

import java.util.Comparator;
import java.util.List;

public class BubbleSortStrategy implements SortStrategy {
    private final SortField sortField;

    public BubbleSortStrategy(SortField sortField) {
        this.sortField = sortField;
    }

    @Override
    public void sort(List<User> users) {
        Comparator<User> comparator = sortField.getComparator();
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
        return "BubbleSort (" + sortField + ")";
    }
}