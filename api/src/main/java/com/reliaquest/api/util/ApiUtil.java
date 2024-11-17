package com.reliaquest.api.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

/**
 * Utility class for holding common utility methods.
 */
public class ApiUtil {

    /**
     * Masks the string by replacing all but the last 3 characters with asterisks.
     * @param str String to mask
     * @return Masked string
     */
    public static String mask(String str) {
        String trimmedName;
        if (null != str && Strings.isNotBlank(trimmedName = str.trim()) && trimmedName.length() > 3) {
            return StringUtils.overlay(trimmedName, "****", 0, trimmedName.length() - 3);
        } else {
            return str;
        }
    }
}
