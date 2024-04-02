package org.example.subscriptions;

public class ConstantsSubscriptions {
    public static final int NUM_SUBSCRIPTIONS = 100;
    static final int COMPANY_PERCENTAGE = 70;
    static final int DATE_PERCENTAGE = 20;
    static final int VALUE_PERCENTAGE = 80;
    static final int DROP_PERCENTAGE = 10;
    static final int VARIATION_PERCENTAGE = 20;
    static final int EQUAL_MIN_PERCENTAGE = 30;


    static final String[] COMPANY_NAMES = {"Google", "Microsoft", "Apple", "Amazon"};
    static final String[] DATES = {"1.01.2023", "15.06.2023", "30.11.2023"};
    static final double MIN_VALUE = 10.0;
    static final double MAX_VALUE = 190.0;
    static final double MIN_DROP = 1.0;
    static final double MAX_DROP = 90.0;
    static final double MIN_VARIATION = 0.1;
    static final double MAX_VARIATION = 1.9;
    static final String[] OPERATORS_STRING = {"=", "!="};
    static final String[] OPERATORS_NUMBERS_AND_DATE = {"=", ">=", ">", "<=", "<", "!="};


    static int COMPANY_CNT = COMPANY_PERCENTAGE * NUM_SUBSCRIPTIONS / 100;
    static int DATE_CNT = DATE_PERCENTAGE * NUM_SUBSCRIPTIONS / 100;
    static int VALUE_CNT = VALUE_PERCENTAGE * NUM_SUBSCRIPTIONS / 100;
    static int DROP_CNT = DROP_PERCENTAGE * NUM_SUBSCRIPTIONS / 100;
    static int VARIATION_CNT = VARIATION_PERCENTAGE * NUM_SUBSCRIPTIONS / 100;
    static int EQUAL_MIN_CNT = EQUAL_MIN_PERCENTAGE * COMPANY_CNT / 100;

}
