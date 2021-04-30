package com.sunit.zingo.Utils;

import java.util.regex.Pattern;

public class AppUtil {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 number
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    ".{6,}" +               //at least 6 characters
                    "$");

    private static final String HEADER = "43564354346767";
    private static final String PLAYERID = "28bc3041-8515-4f64-aa1a-6ba30de8ed41";
    private static final int VENDORID = 2;
    private static final int Page = 1;
    private static final int limit = 20;

    public static int getPage() {
        return Page;
    }

    public static int getLimit() {
        return limit;
    }

    public static Pattern getPasswordPattern() {
        return PASSWORD_PATTERN;
    }

    public static String getHEADER() {
        return HEADER;
    }

    public static String getPLAYERID() {
        return PLAYERID;
    }

    public static int getVENDORID() {
        return VENDORID;
    }
}
