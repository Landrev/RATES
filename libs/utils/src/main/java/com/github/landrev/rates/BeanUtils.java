package com.github.landrev.rates;

import static java.lang.Character.toLowerCase;

public final class BeanUtils {

    private BeanUtils() { }

    public static String getDefaultBeanName(Class<?> beanClass) {
        String beanName = beanClass.getSimpleName();
        return toLowerCase(beanName.charAt(0)) + beanName.substring(1);
    }
}
