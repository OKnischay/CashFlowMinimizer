package dsa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MinimizeCashFlowTestGUI extends JFrame implements ActionListener {

    private final JTextArea resultArea;
    private final JTextField nField;
    private int[][] graph;

    public MinimizeCashFlowTestGUI() {
        super("Minimize Cash Flow");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create input panel
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        inputPanel.add(new JLabel("Number of Friends:"));
        nField = new JTextField();
        inputPanel.add(nField);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        inputPanel.add(submitButton);

        // create result panel
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);

        // set frame properties
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // initialize graph with 0s
        graph = new int[1][1];
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Submit")) {
            try {
                int n = Integer.parseInt(nField.getText());

                // create cost matrix panel
                JPanel matrixPanel = new JPanel(new GridLayout(n, n));
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        JTextField textField = new JTextField();
                        matrixPanel.add(textField);
                    }
                }

                // show cost matrix panel
                int result = JOptionPane.showConfirmDialog(this, matrixPanel,
                        "Enter Cost Matrix", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    // get input from cost matrix panel
                    graph = new int[n][n];
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            graph[i][j] = Integer
                                    .parseInt(((JTextField) matrixPanel.getComponent(i * n + j)).getText());
                        }
                    }

                    // calculate minimum cash flow and show result in text area
                    int[] amount = MinimizeCashFlowTest.getAmount(graph);
                    MinimizeCashFlowTest.minimumCashFlow(amount, resultArea);
                    int maximumCreditPerson = MinimizeCashFlowTest.getMaxPerson(amount);
                    int maximumDebitPerson = MinimizeCashFlowTest.getMinPerson(amount);
                    int min = Math.min(-amount[maximumDebitPerson], amount[maximumCreditPerson]);
                    resultArea.setText("Person(" + maximumDebitPerson + ") pays " + min + " to Person("
                            + maximumCreditPerson + ")");
                    for (String s : MinimizeCashFlowTest.results) {
                        resultArea.append("\n" + s);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new MinimizeCashFlowTestGUI();
    }
}
