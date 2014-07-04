/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.utils;

import java.util.Iterator;
import java.util.Objects;
import javax.swing.ListModel;

/**
 *
 * @author SPerkins
 */
public class StringUtils {
    private static final String emptyString = "";
    private static final String space = " ";

    private StringUtils() {
    }
    
    public static String emptyString() {
        return emptyString;
    }
    
    public static String space() {
        return space;
    }

    public static String join(String delimiter, Iterable<String> elements) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(elements);
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = elements.iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
            if (iterator.hasNext()) {
                builder.append(delimiter);
            }
        }

        return builder.toString();
    }

    public static <T> String join(String delimiter, ListModel<T> elements) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < elements.getSize(); i++) {
            builder.append(elements.getElementAt(i));
            
            if (i != (elements.getSize() - 1)) {
                builder.append(delimiter);
            }
        }        
        
        return builder.toString();
    }
}
