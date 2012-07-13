package kw.model;

/**
 * Author: parroit
 * Created: 22/06/12 16.56
 */
public @interface described {
    String fieldName() default "";
    Class<?> fieldType() default Integer.class;

}
