package dsa;

import java.util.*;
import javax.swing.*;

public class MinimizeCashFlowTest {

    public static String[] results = new String[0];

    public static void minimumCashFlow(int[] amount, JTextArea resultArea) {
        int maximumCreditPerson = getMaxPerson(amount);
        int maximumDebitPerson = getMinPerson(amount);

        if (amount[maximumCreditPerson] == 0 || amount[maximumDebitPerson] == 0) {
            return;
        }
        int min = Math.min(-amount[maximumDebitPerson], amount[maximumCreditPerson]);

        amount[maximumCreditPerson] -= min;
        amount[maximumDebitPerson] += min;

        results = Arrays.copyOf(results, results.length + 1);
        results[results.length - 1] = "Person(" + maximumDebitPerson + ") pays " + min + " to Person("
                + maximumCreditPerson + ")";

        maximumCreditPerson = getMaxPerson(amount);
        maximumDebitPerson = getMinPerson(amount);

        resultArea.setText("");
        for (String s : results) {
            resultArea.append(s + "\n");
        }
    }

    static int getMinPerson(int[] amount) {
        int minPerson = 0;

        for (int i = 1; i < amount.length; i++) {
            if (amount[i] < amount[minPerson]) {
                minPerson = i;
            }
        }

        return minPerson;
    }

    static int getMaxPerson(int[] amount) {
        int maxPerson = 0;
        for (int i = 1; i < amount.length; i++) {
            if (amount[i] > amount[maxPerson]) {
                maxPerson = i;
            }
        }
        return maxPerson;
    }

    static int[] getAmount(int[][] graph) {
        int[] amount = new int[graph.length];

        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                /*
                 * Here graph[j][i] => incoming amount
                 * and graph[i][j] => outgoing amount
                 */
                amount[i] += graph[j][i] - graph[i][j];
            }
        }

        return amount;
    }

}
