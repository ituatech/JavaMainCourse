package com.it_uatech.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by tully.
 */
@Target({ElementType.PACKAGE})
public @interface PackageOwner {
    String owner() default "";
}
