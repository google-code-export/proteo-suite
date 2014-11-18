/*
 * ProteoSuite is releases under the Apache 2 license.
 * This means that you are free to modify, use and distribute the software in all legislations provided you give credit to our project.
 */
package org.proteosuite.model;

/**
 *
 * @author SPerkins
 */
public class IntWrapper {

    private int integer;

    public IntWrapper(int integer) {
        this.integer = integer;
    }

    public void set(int integer) {
        this.integer = integer;
    }

    public int get() {
        return this.integer;
    }
}
