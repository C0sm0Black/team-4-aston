package org.example;

import java.util.Comparator;
import java.util.List;

public class InsertionSortStrategy implements SortStrategy {
    private final SortField sortField;

    public InsertionSortStrategy(SortField sortField) {
        this.sortField = sortField;
    }

    @Override
    public void sort(List<User> users) {
        Comparator<User> comparator = sortField.getComparator();
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
        return "InsertionSort (" + sortField + ")";
    }
}