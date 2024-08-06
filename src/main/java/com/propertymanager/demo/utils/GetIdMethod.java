/*package com.propertymanager.demo.utils;

import java.lang.reflect.Method;

public class GetIdMethod {

    public <T> String getId(Class<T> response) {
        try {
            Method getIdMethod = response.getClass().getMethod("getId");
            return getIdMethod.invoke(response).toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get ID", e);
        }
    }
}*/
