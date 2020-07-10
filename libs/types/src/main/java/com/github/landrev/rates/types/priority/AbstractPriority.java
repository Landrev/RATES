package com.github.landrev.rates.types.priority;

import static java.lang.Integer.compare;

public abstract class AbstractPriority implements Priority {

    @Override
    public int compareTo(final Priority priority) {
        return compare(getValue(), priority.getValue());
    }
}
