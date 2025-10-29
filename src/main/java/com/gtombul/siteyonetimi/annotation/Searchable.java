package com.gtombul.siteyonetimi.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Searchable {
    /**
     * Varsayılan olarak LIKE (case-insensitive). İsterseniz ileride tür bazlı genişletebilirsiniz.
     */
}
