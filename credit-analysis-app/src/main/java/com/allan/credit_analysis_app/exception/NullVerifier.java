package com.allan.credit_analysis_app.exception;

import java.lang.reflect.Field;

public class NullVerifier {

    /**
     * Verifies that all specified fields in the object are non-null.
     *
     * @param object      the object to verify
     * @param fieldNames  the names of the fields to check for null
     * @throws IllegalArgumentException if any field is null, indicating which field and in which class
     */
    public static void verifyFieldsNotNull(Object object, String... fieldNames) {
        if (object == null) {
            throw new IllegalArgumentException("Target object is null");
        }

        Class<?> clazz = object.getClass();

        for (String fieldName : fieldNames) {
            try {
                Field field = getFieldFromClassHierarchy(clazz, fieldName);
                field.setAccessible(true);

                Object value = field.get(object);

                if (value == null) {
                    throw new IllegalArgumentException(String.format(
                            "Field '%s' is null in class '%s'", fieldName, clazz.getSimpleName()));
                }

            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException(String.format(
                        "Field '%s' not found in class '%s'", fieldName, clazz.getSimpleName()), e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Unable to access field '" + fieldName + "'", e);
            }
        }
    }

    private static Field getFieldFromClassHierarchy(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Class<?> current = clazz;
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }
}