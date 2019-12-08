package alittlejava;

import java.util.*;

public class MoneyBags {

    // INSTANCE VARIABLES
    int score;
    int bagsLeft;
    int attemptsLeft;
    char[][] moneyGrid;
  	
    // CONSTRUCTOR
    // Specify the total number of guesses allowed and the grid dimension (grid is square). Set up the grid.
    MoneyBags(int numAttempts, int gridDimension) {    
    	attemptsLeft = numAttempts;
    	moneyGrid = new char[gridDimension][gridDimension];
    	for (int i = 0; i < moneyGrid.length; i++) {
    	    for (int j = 0; j < moneyGrid.length; j++) {
    		moneyGrid[i][j] = '*';
    	    }
    	}
    }

    // METHODS
    
    // Place money bags in the grid using random numbers passed to the place method.
    // If the position specified by x and y has no bag yet, place one there (replace * with B) and return true.
    // If a bag already exists there, return false.
    public boolean place(int x, int y) {  
	if (moneyGrid[x][y] == '*') {
	    moneyGrid[x][y] = 'B';
	    bagsLeft++;
	    return true;
	}
	else {
	    return false;
	}	
    }
  	
    
    //Print the grid without showing where the bags of money are. 
    //For every spot that has recorded a found bag of money, print out a $, or * otherwise.
    public void printGridToUser() {
	for(int i = 0; i < moneyGrid.length; i++){
	    for(int j = 0; j < moneyGrid.length; j++)    {
		if (moneyGrid[i][j] == 'B') {
		    System.out.print('*');
		}
		else {
		    System.out.print(moneyGrid[i][j]);
		}
	    }
	    System.out.println();
	}
    }
    
    
    // Print the complete grid - 
    // Unfound money bag positions are Bs, found bags are $s, other positions are *s.
    public void printGrid() {
	for(int i = 0; i < moneyGrid.length; i++){
	     for(int j = 0; j < moneyGrid.length; j++)    {
	         System.out.print(moneyGrid[i][j]);
	     }
	     System.out.println();
	 }
    }
    
    
    // Given a location, take a guess at that location. 
    // If that location contains a bag of money, update the score, number of attemptsLeft and bagLeft.
    // If that location does not contain a bag of money, only attemptsLeft is updated.    
    public void guess(int x, int y) { 
    	// Subtract 1 from each input number because arrays are 0-9 instead of 1-10
    	int newX = x - 1;
    	int newY = y - 1;
    	// Validate input
    	if (newX < 0 || newX > 9 || newY < 0 || newY > 9) {
    	    //invalid
    	    attemptsLeft--;
    	}
    	else {
    	    if (moneyGrid[newX][newY] == 'B') {
	    	moneyGrid[newX][newY] = '$';
	    	score++;
	    	bagsLeft--;
	    	attemptsLeft--;
	    }
	    else {
	    	attemptsLeft--;
	    }
    	}
    }
      	
  	
    public static void main(String[] args) {
	
    	// Variables to hold number of attempts allowed and grid dimension (number of rows and columns in square grid)
    	int allowedAttempts = 50;
    	int gridDimension = 10;
    	
    	// Variables to be used when placing money bags in the grid
    	int totalBags = 10; //total number of money bags to be hidden in the grid
    	int newBagX; //x position for new money bag to be placed
    	int newBagY; //y position for new money bag to be placed
    	
    	// Create the scanner object to receive input from the game player	
    	// and variables to hold the player's row and column position guesses.
    	Scanner scanner = new Scanner(System.in);	
    	int userInputX;
    	int userInputY;
    	
    	// Variable to see if money bag was found/score is increased
    	int priorScore = 0;
    	
    	// Variables for proper output - singular or plural for score and attemptsLeft.
    	// Default to plural.
    	String bagsOut = " bags of money";
    	String attemptsLeftOut = " attempts";

    	// Call the constructor to create the new object bagsOMoney of class MoneyBags.
    	// Pass maximum allowed attempts and grid size.
    	MoneyBags bagsOMoney = new MoneyBags(allowedAttempts, gridDimension);
		
    	//Place the bags of money in the grid -- 	
    	//Generate random x and y positions, pass them to the place method.
    	//If the place method returns true, the money bag was placed.
    	//If it returns false, the money bag could not be placed (there was a bag already in that position).	
    	while (bagsOMoney.bagsLeft < totalBags) {
    	    //bagsLeft is updated by place() when a money bag is successfully placed.
    	    newBagX = (int) (Math.random() * gridDimension); 
    	    newBagY = (int) (Math.random() * gridDimension);
    	    bagsOMoney.place(newBagX, newBagY);
    	}
    		
    	// Setup is complete. Begin the game!!
    	System.out.println("Welcome to Money Bags! There are " + totalBags + " bags of money hidden in the field. You have " + allowedAttempts + " chances to find them."); 
    	
    	while (bagsOMoney.attemptsLeft > 0 && bagsOMoney.bagsLeft > 0) {
    	// bagsOMoney.attemptsLeft will be reduced by the guess method when each guess is evaluated
    	// bagsOMoney.bagsLeft will be reduced by the guess method when a bag of money is found
    		
    	    //Show the grid to the player (with unfound money bags hidden).
    	    bagsOMoney.printGridToUser();
        	
    	    // Get the row number
    	    System.out.println("Guess the row where you think there is a bag of money -- enter a number between 1 and 10.");
    	    userInputX = scanner.nextInt(); 
    	    // Get the column number
    	    System.out.println("Now guess the column where you think there is a bag of money -- enter a number between 1 and 10.");
    	    userInputY = scanner.nextInt();
        	
    	    System.out.println("You guessed row " + userInputX + " and column " + userInputY + ".");
    		
    	    // Check if the player is giving up
    	    if (userInputX == -1 && userInputY == -1) {
    		// Player gave up. Show them the final grid including hidden bags of money.
    		System.out.println("Thanks for playing! Here are the bags of money: ");
    		bagsOMoney.printGrid();
    		break;
    	    }
    	    else {
    		// Player is not giving up so continue.
    		// Call guess method.
            	// Save the score first, to compare to the score after it runs.
            	priorScore = bagsOMoney.score;
            	bagsOMoney.guess(userInputX, userInputY);
            		
            	// Update output vars (singular or plural) for score and attemptsLeft if needed
            	if (bagsOMoney.score == 1) {
            	    bagsOut = " bag of money";
            	}
            	else {
            	    bagsOut = " bags of money";
            	}
            	if (bagsOMoney.attemptsLeft == 1) {
            	    attemptsLeftOut = " attempt";
            	}
            	else {
            	    attemptsLeftOut = " attempts";
            	}
            	
            	// Was it a good guess? Tell the player if they were correct 
            	if (priorScore < bagsOMoney.score) {
            	    // New money bag was found
            	    System.out.println("Success! You found a bag of money. ");
            	}
            	else {
            	    // New money bag not not found
            	    System.out.println("Sorry! There's no money bag there.");
            	}
            		
            	// Now tell them what happens next.
            	if (bagsOMoney.bagsLeft == 0) {
            	    // All bags have been found -- show them the final grid including hidden money bags.
            	    System.out.println("You've found them all! Congratulations!");
            	    bagsOMoney.printGrid();
            	}
            	else if (bagsOMoney.attemptsLeft == 0) {
            	    // No more tries left -- show them the final grid including hidden money bags.
            	    System.out.println("Game over! You found " + bagsOMoney.score + bagsOut + " but you've used up all your guesses. Here are all the money bags:");
            	    bagsOMoney.printGrid();
            	}
            	else {
            	    // Give player an updated status and ask them to guess again.
            	    System.out.println("So far you've found " + bagsOMoney.score + bagsOut + " and you have " + bagsOMoney.attemptsLeft + attemptsLeftOut + " left to find them all.");
            	    System.out.println("If you don't want to continue, enter -1 for your next row and column guesses.");
            	}
            		
        	    } // end check input for -1 -1
             		
        	} // end while attemptsLeft and bagsLeft > 0
        	
        	//close the scanner object
        	scanner.close();
        	
    } // end main method

}
