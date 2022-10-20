package com.util;

import java.lang.annotation.*;
import java.util.Map;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.LOCAL_VARIABLE, ElementType.FIELD, ElementType.METHOD})
public @interface insertCategory {
    String categoryname = null;
    Map<Class<? extends Annotation>, Annotation> map = null;
    boolean checkCategory() default false;
}
