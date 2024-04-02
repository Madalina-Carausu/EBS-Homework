package org.example.subscriptions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.example.subscriptions.ConstantsSubscriptions.*;

public class SubscriptionsGenerator {
    public void generateSubscriptions(boolean useThreads) {
        List<String> subscriptions;
        System.out.println("Company occurrences: " + COMPANY_CNT);
        System.out.println("Min. '=' occurrences: " + EQUAL_MIN_CNT);
        System.out.println("Value occurrences: " + VALUE_CNT);
        System.out.println("Drop occurrences: " + DROP_CNT);
        System.out.println("Variation occurrences: " + VARIATION_CNT);
        System.out.println("Date occurrences: " + DATE_CNT);
        if(useThreads) {
            subscriptions = generateSubscriptionsListWithThreads();
        } else {
            subscriptions = generateSubscriptionsList();
        }
        saveSubscriptions(subscriptions);
    }

    private List<String> generateSubscriptionsList() {

        List<String> subscriptions = fillAllSubscriptions();

        int i = 0;
        while(COMPANY_CNT > 0 || VALUE_CNT > 0 || DROP_CNT > 0 || VARIATION_CNT > 0 || DATE_CNT > 0) {
            i = i % NUM_SUBSCRIPTIONS;
            if(COMPANY_CNT > 0) {
                if(EQUAL_MIN_CNT > 0) {
                    subscriptions.set(i, subscriptions.get(i) + ";" + generateCompany("="));
                    EQUAL_MIN_CNT--;
                } else {
                    Random random = new Random();
                    subscriptions.set(i, subscriptions.get(i) + ";" + generateCompany(OPERATORS_STRING[random.nextInt(OPERATORS_STRING.length)]));
                }
                COMPANY_CNT--;
            } else if (VALUE_CNT > 0) {
                subscriptions.set(i, subscriptions.get(i) + ";" + generateValue());
                VALUE_CNT--;
            } else if (DROP_CNT > 0) {
                subscriptions.set(i, subscriptions.get(i) + ";" + generateDrop());
                DROP_CNT--;
            } else if (VARIATION_CNT > 0) {
                subscriptions.set(i, subscriptions.get(i) + ";" + generateVariation());
                VARIATION_CNT--;
            } else {
                subscriptions.set(i, subscriptions.get(i) + ";" + generateDate());
                DATE_CNT--;
            }

            i++;
        }

        return subscriptions;
    }

    private List<String> generateSubscriptionsListWithThreads() {

        List<String> subscriptions = fillAllSubscriptions();
        Thread[] threads = new Thread[5];

        threads[0] = new Thread(() -> {
            synchronized (subscriptions) {
                threadFunctionCompany(subscriptions);
            }
        });

        threads[1] = new Thread(() -> {
            synchronized (subscriptions) {
                threadFunctionValue(COMPANY_CNT, subscriptions);
            }
        });

        threads[2] = new Thread(() -> {
            synchronized (subscriptions) {
                threadFunctionDrop(COMPANY_CNT + VALUE_CNT, subscriptions);
            }
        });

        threads[3] = new Thread(() -> {
            synchronized (subscriptions) {
                threadFunctionVariation(COMPANY_CNT + VALUE_CNT + DROP_CNT, subscriptions);
            }
        });

        threads[4] = new Thread(() -> {
            synchronized (subscriptions) {
                threadFunctionDate(COMPANY_CNT + VALUE_CNT + DROP_CNT + VARIATION_CNT, subscriptions);
            }
        });

        for (Thread thread : threads) {
            thread.start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return subscriptions;
    }

    private List<String> fillAllSubscriptions() {

        List<String> subscriptions = new ArrayList<>();
        for(int i = 0; i < NUM_SUBSCRIPTIONS; i++) {
            if(COMPANY_CNT > 0) {
                if(EQUAL_MIN_CNT > 0) {
                    subscriptions.add(generateCompany("="));
                    EQUAL_MIN_CNT--;
                } else {
                    Random random = new Random();
                    subscriptions.add(generateCompany(OPERATORS_STRING[random.nextInt(OPERATORS_STRING.length)]));
                }
                COMPANY_CNT--;
            } else if (VALUE_CNT > 0) {
                subscriptions.add(generateValue());
                VALUE_CNT--;
            } else if (DROP_CNT > 0) {
                subscriptions.add(generateDrop());
                DROP_CNT--;
            } else if (VARIATION_CNT > 0) {
                subscriptions.add(generateVariation());
                VARIATION_CNT--;
            } else if (DATE_CNT > 0) {
                subscriptions.add(generateDate());
                DATE_CNT--;
            }
        }
        return subscriptions;
    }

    private void threadFunctionCompany(List<String> subscriptions) {
        int index = 0, contor = COMPANY_CNT;

        while(contor > 0) {
            index = index % NUM_SUBSCRIPTIONS;
                if (EQUAL_MIN_CNT > 0) {
                    subscriptions.set(index, subscriptions.get(index) + ";" + generateCompany("="));
                    EQUAL_MIN_CNT--;
                } else {
                    Random random = new Random();
                    subscriptions.set(index, subscriptions.get(index) + ";" + generateCompany(OPERATORS_STRING[random.nextInt(OPERATORS_STRING.length)]));
                }
                contor--;
            index++;
        }
    }

    private void threadFunctionValue(int index, List<String> subscriptions) {
        int contor = VALUE_CNT;

        while(contor > 0) {
            index = index % NUM_SUBSCRIPTIONS;
            subscriptions.set(index, subscriptions.get(index) + ";" + generateValue());
            contor--;
            index++;
        }
    }

    private void threadFunctionDrop(int index, List<String> subscriptions) {
        int contor = DROP_CNT;

        while(contor > 0) {
            index = index % NUM_SUBSCRIPTIONS;
            subscriptions.set(index, subscriptions.get(index) + ";" + generateDrop());
            contor--;
            index++;
        }
    }

    private void threadFunctionVariation(int index, List<String> subscriptions) {
        int contor = VARIATION_CNT;

        while(contor > 0) {
            index = index % NUM_SUBSCRIPTIONS;
            subscriptions.set(index, subscriptions.get(index) + ";" + generateVariation());
            contor--;
            index++;
        }
    }

    private void threadFunctionDate(int index, List<String> subscriptions) {
        int contor = DATE_CNT;

        while(contor > 0) {
            index = index % NUM_SUBSCRIPTIONS;
            subscriptions.set(index, subscriptions.get(index) + ";" + generateDate());
            contor--;
            index++;
        }
    }

    private void saveSubscriptions(List<String> generatedSubscriptions) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fileName = "subscriptions_" + dateFormat.format(new Date()) + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String subscription : generatedSubscriptions) {
                writer.write("{" + subscription + "}");
                writer.newLine();
            }
            System.out.println("All subscriptions saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateCompany(String operator) {
        Random random = new Random();
        return "(company," + operator + ",\"" + COMPANY_NAMES[random.nextInt(COMPANY_NAMES.length)] + "\")";
    }

    private String generateValue() {
        Random random = new Random();
        return "(value," + OPERATORS_NUMBERS_AND_DATE[random.nextInt(OPERATORS_NUMBERS_AND_DATE.length)] + "," + (MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextDouble()) + ")";
    }

    private String generateDrop() {
        Random random = new Random();
        return "(drop," + OPERATORS_NUMBERS_AND_DATE[random.nextInt(OPERATORS_NUMBERS_AND_DATE.length)] + "," + (MIN_DROP + (MAX_DROP - MIN_DROP) * random.nextDouble()) + ")";
    }

    private String generateVariation() {
        Random random = new Random();
        return "(variation," + OPERATORS_NUMBERS_AND_DATE[random.nextInt(OPERATORS_NUMBERS_AND_DATE.length)] + "," + (MIN_VARIATION + (MAX_VARIATION - MIN_VARIATION) * random.nextDouble()) + ")";
    }

    private String generateDate() {
        Random random = new Random();
        return "(date," + OPERATORS_NUMBERS_AND_DATE[random.nextInt(OPERATORS_NUMBERS_AND_DATE.length)] + "," + DATES[random.nextInt(DATES.length)] + ")";
    }
}
