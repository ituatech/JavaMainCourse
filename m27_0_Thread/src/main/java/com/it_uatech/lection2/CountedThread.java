package com.it_uatech.lection2;

/**
 * Example of unsafe code.
 * <p>
 * 1. static
 * 2. volatile
 * 3. synchronized
 * 4. synchronized(object)
 * 5. synchronized(class)
 */

public final class CountedThread extends BaseThread {
    /*private int i;

    protected void doSomething() {
        super.doSomething();
        i++;
    }*/

    private static int i;

    protected void doSomething() {
        synchronized (this.getClass()) {
            super.doSomething();
            i++;
        }
    }

    int getI() {
        return i;
    }
}
