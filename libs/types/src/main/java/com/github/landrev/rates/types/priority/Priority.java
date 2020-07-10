package com.github.landrev.rates.types.priority;

public interface Priority extends Comparable<Priority> {

    int getValue();

    static Priority create(int priority) {
        return new SimplePriority(priority);
    }

    static Priority createHighestPrecedence() {
        return new SimplePriority(Integer.MAX_VALUE);
    }

    static Priority createLowestPrecedence() {
        return new SimplePriority(Integer.MIN_VALUE);
    }
}
