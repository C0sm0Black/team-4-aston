//package org.example.sort;
//
//import org.example.collection.CustomLinkedList;
//import org.example.entity.User;
//;import java.util.Comparator;
//
//public class SortContext {
//
//    private SortStrategy strategy;
//
//    public void setStrategy(SortStrategy strategy) {
//
//        if (strategy == null) {
//            throw new IllegalArgumentException("Sort strategy cannot be null");
//        }
//
//        this.strategy = strategy;
//
//    }
//
//    public void executeSort(CustomLinkedList<User> users) {
//
//        if (strategy == null) {
//            throw new IllegalStateException("Sort strategy is not set");
//        }
//
//        strategy.sort(users, Comparator<U);
//
//    }
//
//    public SortStrategy getStrategy() {
//        return strategy;
//    }
//
//}