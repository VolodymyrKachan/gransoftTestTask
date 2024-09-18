package test.task.gransoft;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * This project is a single-page Java Swing application designed to visualize the quick-sort algorithm.
 * The application has two main screens: the Intro screen, where the user enters the number of random
 * numbers to display and sort, and the Sort screen, which visualizes the quick-sort process step by step.
 *
 * In the Intro screen, the user specifies the number of random integers to generate, with an upper limit of 1000.
 * After the user enters a valid number, the application switches to the Sort screen, where the random numbers
 * The quick-sort algorithm is visually represented through color changes of the buttons.
 *
 * During sorting, the pivot is highlighted in red, and comparisons are shown by changing the colors of the buttons.
 * Once the sorting is complete, all the sorted numbers are highlighted in green. The user can reset the application
 * and return to the Intro screen by pressing the "Reset" button.
 */
public class GransoftTestTask extends JFrame {
    private JFrame sortingFrame;
    private JPanel numbersPanel;
    private JButton enterButton;
    private JButton sortButton;
    private JButton resetButton;
    private JTextField countInputField;
    private List<Integer> numberValues;
    private List<JButton> numberButtons;
    private int numberOfNumbers;
    private Thread sortThread;

    /**
     * Initializes the main GUI for the application,
     * where the user inputs the number of numbers to sort.
     */
    private void initializeGUI() {
        setTitle("Gransoft Test Task");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel numberCountLabel = new JLabel("How many numbers to display?");
        numberCountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        numberCountLabel.setBounds(450, 195, 300, 30);
        add(numberCountLabel);

        countInputField = new JTextField();
        countInputField.setPreferredSize(new Dimension(130, 30));
        countInputField.setBounds(525, 235, 130, 30);
        add(countInputField);

        enterButton = new JButton("Enter");
        enterButton.setPreferredSize(new Dimension(130, 30));
        enterButton.setBackground(Color.BLUE);
        enterButton.setForeground(Color.WHITE);
        enterButton.setBounds(525, 275, 130, 30);
        setupEnterButtonListener();
        add(enterButton);

        setVisible(true);
    }

    /**
     * Sets up the action listener for the "Enter" button.
     * It validates the input number and switches to the sorting screen if the input is valid.
     */
    private void setupEnterButtonListener() {
        enterButton.addActionListener(actionEvent -> {
            try {
                numberOfNumbers = Integer.parseInt(countInputField.getText());
                if (numberOfNumbers <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter a positive number.");
                } else if (numberOfNumbers > 1000) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter a number less than or equal to 1000.");
                } else {
                    setVisible(false);
                    createSortScreenFrame();
                    initializeNumberList(numberOfNumbers);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Invalid input. Please enter a number or .");
            }
        });
    }

    /**
     * Sets up the action listener for the "Sort" button, which starts the sorting process
     * in a separate thread and disables the buttons during sorting.
     */
    private void setupStartSortButtonListener() {
        sortButton.addActionListener(actionEvent -> {
            for (JButton btn : numberButtons) {
                btn.setBackground(Color.BLUE);
                btn.setForeground(Color.WHITE);
            }
            sortThread = new Thread(() -> quickSort(numberValues, 0, numberValues.size() - 1));
            sortThread.start();
            sortButton.setEnabled(false);
            resetButton.setEnabled(false);
        });
    }

    /**
     * Sets up the action listener for the "Reset" button,
     * which resets the sorting frame and returns to the initial input screen.
     */
    private void setupResetButtonListener() {
        resetButton.addActionListener(actionEvent -> {
            sortingFrame.setVisible(false);
            setVisible(true);
            sortingFrame.dispose();
        });
    }

    /**
     * Creates the sorting screen frame, which contains the number buttons,
     * "Sort" and "Reset" buttons, and initializes the layout.
     */
    private void createSortScreenFrame() {
        sortingFrame = new JFrame("Sort Screen");
        sortingFrame.setSize(1200, 600);
        sortingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sortingFrame.setLocationRelativeTo(null);
        sortingFrame.setLayout(null);

        numbersPanel = new JPanel();
        numbersPanel.setLayout(null);
        JScrollPane scrollPanel = new JScrollPane(numbersPanel);
        scrollPanel .setBounds(20, 20, 1000, 500);
        sortingFrame.add(scrollPanel );

        sortButton = new JButton("Sort");
        sortButton.setForeground(Color.WHITE);
        sortButton.setBackground(Color.GREEN);
        sortButton.setBounds(1050, 20, 120, 30);
        setupStartSortButtonListener();
        sortingFrame.add(sortButton);

        resetButton = new JButton("Reset");
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(Color.GREEN);
        resetButton.setBounds(1050, 60, 120, 30);
        setupResetButtonListener();
        sortingFrame.add(resetButton);

        sortingFrame.setVisible(true);
    }

    private void generateRandomNumbers() {
        Random random = new Random();
        numberValues = new ArrayList<>();

        for (int i = 0; i < numberOfNumbers; i++) {
            numberValues.add(random.nextInt(1000) + 1);
        }
        if (numberValues.stream().noneMatch(num -> num <= 30)) {
            numberValues.set(random.nextInt(numberValues.size()), random.nextInt(31));
        }
    }

    /**
     * Initializes the panel with buttons representing random numbers and adds
     * them to the sorting screen, with layout adjustments based on the number of numbers.
     *
     * @param size The number of buttons to display.
     */
    private void initializeNumberList(int size) {
        final int MAX_ROWS = 10;
        final int BUTTON_WIDTH = 95;
        final int BUTTON_HEIGHT = 40;
        final int HORIZONTAL_INDENT = 5;
        final int VERTICAL_INDENT = 5;

        numbersPanel.removeAll();
        numberButtons = new ArrayList<>();
        generateRandomNumbers();

        int totalColumns = (size + MAX_ROWS - 1) / MAX_ROWS;

        for (int i = 0; i < size; i++) {
            JButton numberButton = new JButton(String.valueOf(numberValues.get(i)));
            numberButton.setForeground(Color.WHITE);
            numberButton.setBackground(Color.BLUE);

            int row = i % MAX_ROWS;
            int column = i / MAX_ROWS;
            int buttonXPosition = column * (BUTTON_WIDTH + HORIZONTAL_INDENT);
            int buttonYPosition = row * (BUTTON_HEIGHT + VERTICAL_INDENT);

            numberButton.setBounds(buttonXPosition, buttonYPosition, BUTTON_WIDTH, BUTTON_HEIGHT);

            numberButton.addActionListener(actionEvent -> {
                int buttonValue = Integer.parseInt(((JButton) actionEvent.getSource()).getText());

                if (buttonValue <= 30) {
                    initializeNumberList(size);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Please select a value smaller or equal to 30.");
                }
            });
            numberButtons.add(numberButton);
            numbersPanel.add(numberButton);
        }

        int panelWidth = totalColumns * (BUTTON_WIDTH + HORIZONTAL_INDENT);
        int panelHeight = MAX_ROWS * (BUTTON_HEIGHT + VERTICAL_INDENT);

        numbersPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        numbersPanel.revalidate();
        numbersPanel.repaint();
    }

    /**
     * Implements the QuickSort algorithm for sorting the list of numbers.
     * Visual feedback is provided by changing button colors during the sorting process.
     *
     * @param numberList The list of numbers to be sorted.
     * @param lowIndex The starting index of the list/sublist to sort.
     * @param highIndex The ending index of the list/sublist to sort.
     */
    private void quickSort(List<Integer> numberList, int lowIndex, int highIndex) {
        if (lowIndex < highIndex) {
            numberButtons.forEach(button -> button.setEnabled(false));
            int pivotIndex = partitionListByPivot(numberList, lowIndex, highIndex);
            quickSort(numberList, lowIndex, pivotIndex - 1);
            quickSort(numberList, pivotIndex, highIndex);
        } else {
            for (int i = lowIndex; i <= highIndex; i++) {
                numberButtons.get(i).setBackground(Color.GREEN);
            }
        }
        if (numberButtons.get(numberValues.size() - 1).getBackground() == Color.GREEN) {
            resetButton.setEnabled(true);
            sortButton.setEnabled(true);
            numberButtons.forEach(button -> button.setEnabled(true));
        }
    }

    /**
     * Partitions the list into two halves based on the pivot value,
     * moving elements smaller than the pivot to the left and larger elements to the right.
     *
     * @param numberList The list of numbers to partition.
     * @param lowIndex The starting index of the sublist to partition.
     * @param highIndex The ending index of the sublist to partition.
     * @return The index where the partitioning ends.
     */
    private int partitionListByPivot(List<Integer> numberList, int lowIndex, int highIndex) {
        int left = lowIndex;
        int right = highIndex;
        int pivotValue = numberList.get(lowIndex + (highIndex - lowIndex) / 2);
        numberButtons.get(lowIndex + (highIndex - lowIndex) / 2).setBackground(Color.RED);

        while (left <= right) {
            while (numberList.get(left) < pivotValue) {
                try {
                    numberButtons.get(left).setBackground(Color.DARK_GRAY);
                    Thread.sleep(500);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                numberButtons.get(left).setBackground(Color.BLUE);
                left++;
            }

            while (numberList.get(right) > pivotValue) {
                try {
                    numberButtons.get(right).setBackground(Color.DARK_GRAY);
                    Thread.sleep(500);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                numberButtons.get(right).setBackground(Color.BLUE);

                right--;
            }

            if (left <= right) {
                Collections.swap(numberList, left, right);
                swapButtons(left, right);
                left++;
                right--;
            }
        }
        return left;
    }

    /**
     * Swaps the text and colors of two number buttons, giving visual feedback
     * during the sorting process.
     *
     * @param index1 The index of the first button to swap.
     * @param index2 The index of the second button to swap.
     */
    private void swapButtons(int index1, int index2) {
        String tempText = numberButtons.get(index1).getText();
        numberButtons.get(index1).setText(numberButtons.get(index2).getText());
        numberButtons.get(index2).setText(tempText);
        numberButtons.get(index1).setBackground(Color.YELLOW);
        numberButtons.get(index2).setBackground(Color.YELLOW);
        numberButtons.get(index1).setForeground(Color.BLUE);
        numberButtons.get(index2).setForeground(Color.BLUE);
        try {
            Thread.sleep(500);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        numberButtons.get(index1).setBackground(Color.BLUE);
        numberButtons.get(index2).setBackground(Color.BLUE);
        numberButtons.get(index1).setForeground(Color.WHITE);
        numberButtons.get(index2).setForeground(Color.WHITE);
    }

    public static void main(String[] args) {
        GransoftTestTask grandSoftTestTask = new GransoftTestTask();
        grandSoftTestTask.initializeGUI();
    }
}
