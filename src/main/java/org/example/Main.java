package org.example;

import org.example.publications.PublicationsGenerator;
import org.example.subscriptions.SubscriptionsGenerator;

import static org.example.publications.ConstantsPublications.NR_THREADS;
import static org.example.publications.ConstantsPublications.NUM_PUBLICATIONS;
import static org.example.subscriptions.ConstantsSubscriptions.NUM_SUBSCRIPTIONS;

public class Main {
    public static void main(String[] args) {
//        PublicationsGenerator publicationsGenerator = new PublicationsGenerator();
//        long startTime = System.currentTimeMillis();
//        publicationsGenerator.generatePublications();
//        long endTime = System.currentTimeMillis();
//        System.out.println(NUM_PUBLICATIONS + " publications generated in " + (endTime - startTime) + " milliseconds using " + NR_THREADS + " threads.");

        SubscriptionsGenerator subscriptionsGenerator = new SubscriptionsGenerator();
        long startTime = System.currentTimeMillis();
        subscriptionsGenerator.generateSubscriptions(true);
        long endTime = System.currentTimeMillis();
        System.out.println(NUM_SUBSCRIPTIONS + " subscriptions generated in " + (endTime - startTime) + " milliseconds.");

    }
}
