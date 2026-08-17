package org.example;

import java.util.List;

public interface SortStrategy {
    void sort(List<User> users);
    String getStrategyName();
}