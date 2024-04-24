## Instructions for Executing Scala Scripts for Flight Data Analysis

This repository contains Scala scripts to analyze flight data and answer four questions based on the provided CSV files: flightData.csv and passengers.csv.

### Prerequisites 

Make sure you have the following installed on your system:

- Scala (installation instructions)
- Java Development Kit (JDK) 8 or higher (installation instructions)

### Instructions
1. Clone this repository to your local machine
2. Navigate to the directory containing the Scala scripts:
3. Place the CSV files (flightData.csv and passengers.csv) in the `data` directory.
4. Execute each question script individually by running the following scala files:
   - Question 1: Find the total number of flights for each month 
   - Question 2: Find the names of the 100 most frequent flyer
   - Question 3: Find the greatest number of countries a passenger has been in without being in the UK 
   - Question 4: Find the passengers who have been on more than N flights together within the range (from, to)
     
### Notes
- Ensure that the CSV files are correctly formatted with the specified columns (flightData.csv and passengers.csv). 
- Adjust the file paths in the scripts if the CSV files are located in a different directory. 
- Modify the input parameters as needed for Question 4 (atLeastNTimes, fromDate, toDate).
