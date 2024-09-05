# 🚀 Sorting Visualizer Application

This project is a single-page Java Swing application designed to visualize the **quick-sort** algorithm. The application has two main screens: the **Intro** screen and the **Sort** screen.

## 🖥️ Application Overview

### 1. Intro Screen
- The user is prompted to input the number of random numbers they wish to sort.
- After entering a number and clicking **Enter**, the application navigates to the sorting screen.

### 2. Sort Screen
- Displays a list of randomly generated numbers.
- **Button Layout**:
    - A maximum of 10 numbers is displayed per column.
    - Additional columns are created if more than 10 numbers are input.
- Includes three primary actions:
  - **Sort**: Sort the numbers using the quick-sort algorithm.
  - **Reset**: Returns to the Intro screen.
  - **Number Click**: Clicking a number performs additional actions.

## 🧩 Features

### 🎲 Random Number Generation:
- Generates a list of random numbers (max value of 1000).
- At least one number is guaranteed to be less than or equal to 30.

### 🔄 Sorting Behavior:
- The user can toggle between sorting in **descending** and **ascending** order by clicking the **Sort** button.
- The sorting process is animated, with updates shown at each iteration of the quick-sort algorithm.
- **Sorted elements** are highlighted in **green** after sorting is complete.

### 🎯 Clickable Numbers:
- If a number **less than or equal to 30** is clicked, a new set of random numbers is generated.
- If a number **greater than 30** is clicked, a pop-up message appears: `"Please select a value smaller or equal to 30."`

## 💻 How It Works

- The sorting is done using the **quick-sort algorithm**, which divides the list by selecting a pivot element and sorting the numbers around it.
- **Animations**: The sorting process is animated. The pivot element is highlighted in **red**, the numbers being compared are highlighted in **dark gray**, and swapped numbers are briefly highlighted in **yellow**.
- After sorting, all the numbers are highlighted in **green**.

## 🛠️ Project Structure

- **Intro Screen**: Displays the input field and "Enter" button.
- **Sort Screen**: Contains the number buttons, "Sort" button, and "Reset" button.
- **Sorting Logic**: The sorting is done on a separate thread to ensure the UI remains responsive during sorting.
  