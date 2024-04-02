package org.example.publications;

public class ConstantsPublications {
    public static final int NUM_PUBLICATIONS = 5000000;
    public static final int NR_THREADS = 10;

    static final String[] COMPANY_NAMES = {"Google", "Microsoft", "Apple", "Amazon"};
    static final String[] DATES = {"1.01.2023", "15.06.2023", "30.11.2023"};
    static final double MIN_VALUE = 50.0;
    static final double MAX_VALUE = 150.0;
    static final double MIN_DROP = 5.0;
    static final double MAX_DROP = 20.0;
    static final double MIN_VARIATION = 0.5;
    static final double MAX_VARIATION = 1.5;
    static final int PUBLICATIONS_PER_THREAD = NUM_PUBLICATIONS / NR_THREADS;
}
