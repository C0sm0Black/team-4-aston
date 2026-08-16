package org.example;

import java.util.List;

public class SortContext {
    private SortStrategy strategy;

    public void setStrategy(SortStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Sort strategy cannot be null");
        }
        this.strategy = strategy;
    }

    public void executeSort(List<User> users) {
        if (strategy == null) {
            throw new IllegalStateException("Sort strategy is not set");
        }
        strategy.sort(users);
    }

    public SortStrategy getStrategy() {
        return strategy;
    }
}