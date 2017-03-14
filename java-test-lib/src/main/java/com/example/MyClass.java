package com.example;

import java.util.regex.Pattern;

public class MyClass {

    public static final Pattern PHONE
            = Pattern.compile(                      // sdd = space, dot, or dash
            "(\\+[0-9]+[\\- \\.]*)?"        // +<digits><sdd>*
                    + "(\\([0-9]+\\)[\\- \\.]*)?"   // (<digits>)<sdd>*
                    + "([0-9][0-9\\- \\.]+[0-9])"); // <digit><digit|sdd>+<digit>

    public static final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    /*
     * global-phone-number = ["+"] 1*( DIGIT / written-sep )
     * written-sep         = ("-"/".")
     */
    private static final Pattern GLOBAL_PHONE_NUMBER_PATTERN =
            Pattern.compile("[\\+]?[0-9.-]+");

    public static void main(String[] args) {
        testPhone();
    }

    private static void testPhone() {
        String phone = "+8613600314080";
        System.out.println(phone);
        System.out.println(PHONE.matcher(phone).matches());
        System.out.println(GLOBAL_PHONE_NUMBER_PATTERN.matcher(phone).matches());

        phone = "13600314080";
        System.out.println(phone);
        System.out.println(PHONE.matcher(phone).matches());
        System.out.println(GLOBAL_PHONE_NUMBER_PATTERN.matcher(phone).matches());

        phone = "136003140801360031408013600314080";
        System.out.println(phone);
        System.out.println(PHONE.matcher(phone).matches());
        System.out.println(GLOBAL_PHONE_NUMBER_PATTERN.matcher(phone).matches());

        phone = "136003140.801360031408013600314080";
        System.out.println(phone);
        System.out.println(PHONE.matcher(phone).matches());
        System.out.println(GLOBAL_PHONE_NUMBER_PATTERN.matcher(phone).matches());

        phone = "136003140.801360031-408013600314080";
        System.out.println(phone);
        System.out.println(PHONE.matcher(phone).matches());
        System.out.println(GLOBAL_PHONE_NUMBER_PATTERN.matcher(phone).matches());

        phone = "136003140.801360031-4080136003+14080";
        System.out.println(phone);
        System.out.println(PHONE.matcher(phone).matches());
        System.out.println(GLOBAL_PHONE_NUMBER_PATTERN.matcher(phone).matches());

        phone = "(1360031)40.801360031-408013600314080";
        System.out.println(phone);
        System.out.println(PHONE.matcher(phone).matches());
        System.out.println(GLOBAL_PHONE_NUMBER_PATTERN.matcher(phone).matches());

        phone = "";
        Pattern pattern = Pattern.compile("^(\\+86)?[1][3|4|5|7|8][0-9]{9}$");
        System.out.println(phone);
        System.out.println(pattern.matcher(phone).matches());
        System.out.println(phone.matches("^(\\+86)?[1][3|4|5|7|8][0-9]{9}$"));
    }

}
