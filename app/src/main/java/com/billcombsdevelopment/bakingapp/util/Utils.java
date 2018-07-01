/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.util;

public class Utils {

    /**
     * Changes the first char of the String to upper case
     *
     * @param string - String to change
     * @return new String object with the first char upper case
     */
    public static String toUpperCase(String string) {
        if (!string.isEmpty()) {
            string = string.substring(0, 1).toUpperCase()
                    + string.substring(1);
        }

        return string;
    }
}
