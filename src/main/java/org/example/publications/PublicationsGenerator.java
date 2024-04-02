package org.example.publications;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.example.publications.ConstantsPublications.*;

public class PublicationsGenerator {
    public void generatePublications() {
        List<String> publications = generatePublicationsList();
        savePublications(publications);
    }

    private List<String> generatePublicationsList() {
        List<String> publications = new ArrayList<>();
        Thread[] threads = new Thread[NR_THREADS];
        for (int i = 0; i < NR_THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < PUBLICATIONS_PER_THREAD; j++) {
                    String publication = generatePublication();
                    synchronized (publications) {
                        publications.add(publication);
                    }
                }
            });
            threads[i].start();
        }
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return publications;
    }

    private String generatePublication() {
        Random random = new Random();
        return "{(company,\"" + COMPANY_NAMES[random.nextInt(COMPANY_NAMES.length)] + "\");" +
                "(value," + (MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextDouble()) + ");" +
                "(drop," + (MIN_DROP + (MAX_DROP - MIN_DROP) * random.nextDouble()) + ");" +
                "(variation," + (MIN_VARIATION + (MAX_VARIATION - MIN_VARIATION) * random.nextDouble()) + ");" +
                "(date," + DATES[random.nextInt(DATES.length)] + ")}";
    }

    private void savePublications(List<String> generatedPublications) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fileName = "publications_" + dateFormat.format(new Date()) + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String publication : generatedPublications) {
                writer.write(publication);
                writer.newLine();
            }
            System.out.println("All publications saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
