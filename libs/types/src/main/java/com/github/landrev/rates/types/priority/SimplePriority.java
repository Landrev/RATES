package com.github.landrev.rates.types.priority;

public class SimplePriority extends AbstractPriority {

    private final int value;

    public SimplePriority(final int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
