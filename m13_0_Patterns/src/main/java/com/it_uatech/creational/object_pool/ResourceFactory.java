package com.it_uatech.creational.object_pool;

/**
 * Created by tully.
 */
@FunctionalInterface
public interface ResourceFactory {
    Resource get();
}
