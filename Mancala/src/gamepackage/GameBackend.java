/*File name:  GameBackend

Purpose: does all the Mancala game logic
 * 
 *Creation date: May 30, 2022
 *Finished date: June 3, 2022
 *
functions:
*
*Constructor(): 
*sets some default values
*
 * initializeBoard():
 * sets the array that is used for the board. All values are 4
 * 
 * resetBoard();
 * resets the board values to 4 and the player turn and the scores/bins
 * 
 * swapTurns():
 * 	swaps the players turns
 * 
 * applymove(int move):
 * applies the players move to the array and will run swapTurns() and add scores to the bins
 * 
 *  displayBoard():
 *  displays the board in the console
 *  
 *  AImakemove():
 *  returns an int value of the best move the ai can make. 
 *  The int is the location on the board
 *  
 *  gameEnd():
 *  returns a boolean value if the game is still going or not
 *   
**************************************************/

package gamepackage;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class GameBackend {
	 int[] board;
	 int bin1, bin2;
	 boolean player1IsNext; // If true, player 1 is the next player. Otherwise, player 2 is the next player.
	 boolean GameType; //If true game is 2 player, if false game is vs AI
	
	 //constructor
	 public GameBackend() {
		 //creates default values
		 
		  board  = initializeBoard();
		  player1IsNext = true;
		  bin1 = 0;
		  GameType = true;
		  bin2 = 0;
	  }

	 //creates the array 
	 public int[] initializeBoard() {
		    int[] array = new int[12];
		    Arrays.fill(array, 4);
		    return array;
	 }
	 
	 //resets player turn, scores and the array
	 public void resetBoard() {
		    player1IsNext = true;
		    bin1 = 0;
		    bin2 = 0;
		    Arrays.fill(board, 4);
	 }
	 
	 //changes the players turn
	 public void swapTurns() {
		 if(player1IsNext) {
			 player1IsNext = false;
		 }
		 else{
			 player1IsNext = true;
		 }
	 }
	 
	 //makes the corresponding decision for the button that the player clicked
	 public void applymove(int move) {
		 
		 //creates a tempmove that will be used for tracking location
		 int tempCounter = move;
		 boolean gotInBin = false;
		 
		 // adds 1 to every bin that pieces pass through
		 // it moves a certain number of pieces counterclockwise and will add 1 to the next spots until it runs out of pieces
		 for(int i = 0 ; i < board[move]; i++) {
			 
			 //adds 1 to location
			 board[tempCounter-i] +=1;
//			 System.out.println(tempCounter-i);

			 //if it is about to pass a bin
			 if(tempCounter-i == 0) {
				 
				 //both if won't run unless it's player1's turn
				 
				 //if there's still a piece left for next turn and it's player1's turn
				 // it will add a piece to the bin and make sure that player1 goes again
				 if(i+2 == board[move] && player1IsNext) {
					 
					 //adds points to the bin
					 bin1++;
					 i++;
					 
					 //at the end of the function the swapTurns will cancel out
					 //this let's the player go again
					 swapTurns();
					 gotInBin = true;

				 }
				 //if there's still pieces left for next turn and it's player1's turn 
				 //but it's not ending in the bin
				 if(i+1 < board[move] && player1IsNext) {

					 //adds points to the bin
					 bin1++;
					 i++;
				 }
				 
				 //reset the counter to start at the end of the array
				 if(!(i+1 == board[move] )) {
					 tempCounter = 12+i;
				 }

			 }
			 
			 //if it passes bin 2
			 if(tempCounter - i == 6 ) {
				 
				 //both if won't run unless it's player2's turn

				 
				 //if it's last move will be adding to player two's bin
				 if(i+2 == board[move] && !player1IsNext) {
					 
					 //adds a point to bin2
					 bin2++;
					 i++;
					 
					 //at the end of the function the swapTurns will cancel out
					 //this let's the player go again
					 swapTurns();
					 gotInBin = true;
				 }
				 if(i+1 < board[move] && !player1IsNext) {
					 
					 //add a point to bin1
					 bin2++;
					 i++;
					 tempCounter += 1;
				 }
			 }
		 }

		 
		 //if the last move wasn't in the bin
		 if(gotInBin == false) {
			 
			 //if it landed on the top and it's player1's move
			 if(tempCounter-board[move]+1 <= 5 && player1IsNext) {
//				 System.out.println("moved");
				 
				 //if the last space was empty(it's now 1) and the other side of the board isn't empty
				 if(board[tempCounter-board[move] + 1] == 1 && board[11-(tempCounter-board[move] + 1)] != 0) {
					 
					 //System.out.println(11-(tempCounter-board[move] + 1));
					 
					 //adds the points on the other side to the score(player2's side)
					 bin1 += board[11-(tempCounter-board[move] + 1)];
					 //adds one bc of piece that landed on the empty space
					 bin1 ++;

					 //empty the two spaces 
					 board[11-(tempCounter-board[move] + 1)] = 0;
					 board[tempCounter-board[move] + 1] = 0;
				 }
			 } 

			 //if the space was just added
			 if(tempCounter-board[move]+1 >= 6 && !player1IsNext) {
				 
				 //if the last space was empty(it's now 1) and the other side of the board isn't empty
				 if(board[tempCounter-board[move] + 1] == 1 && board[11-(tempCounter-board[move] + 1)] != 0) {
					 //System.out.println(11-(tempCounter-board[move] + 1));
					 
					 //adds the points on the other side to the score(player1's side)
					 bin2 += board[11-(tempCounter-board[move] + 1)];
					 //adds one bc of piece that landed on the empty space
					 bin2 ++;
					 
					 board[11-(tempCounter-board[move] + 1)] = 0;
					 board[tempCounter-board[move] + 1] = 0;
				 }
			 }
		 }
		 
		 //switches turn
		 //if they got it in the bin this will cause the player to go again
		 swapTurns();
		 
		 //emptys original space
		 board[move] = 0;

	 }
	 
	 //displays board in console
	  public void displayBoard() {
		    System.out.println();
		    // Player 1's houses
		    System.out.print("    ");
		    for (int i = 0; i < 6; i++) {
		      System.out.printf("%2d:%2d  ", i, board[i]);
		    }
		    
		    //print the bins
		    System.out.println();
		    System.out.printf("Store 1:%2d                            ", bin1);
		    System.out.printf("Store 2:%2d\n", bin2);
		    
		    // Player 2's houses
		    System.out.print("    ");
		    for (int i = 11; i > 5; i--) {
		      System.out.printf("%2d:%2d  ", i, board[i]);
		    }
		    System.out.println();
		  }
	
	  
	  //AI logic that return an int that corresponds to the location it wants to go on the board
	  public int AImakemove() {
		
		  //calculates which move offers the most points
		  int mostPoints = 0;
		  int move = 0;
			
		  //check if it can pick up points from using the empty spot pickup
		  //if the tile lands in a empty spot pick up points from the other side
		  
		  //cycles through each move
			for(int i = 0; i < 6; i++) {
				
				//does the math for where it will land and checks if that's a valid spot
				if(11-i - board[11-i] >= 6 && 11-i - board[11-i]  <= 11) {
					
					//checks if the spot is empty and the opposite side is empty
					if(board[11-i - board[11-i]] == 0 && board[11-i] != 0) {
						
						//checks if it offers the most points
						if(mostPoints < board[11-(11-i - board[11-i])]){
							
							//store move and links mostPoints to move
							mostPoints = board[11-(11-i - board[11-i])];
							move = 11-i;
						}
					}
					
				}
				
				//does the math for where it will land and checks if that's a valid spot
				//this one looks if it goes around the board once 
				else if(11-i - board[11-i] >= -7 && 11-i - board[11-i]  <=-2) {
					
					//checks if the spot is empty and the opposite side is empty
					if(board[13-(11-i - board[11-i])*-1] == 0 && board[11-i] != 0) {
						
						//checks if it offers the most points
						if(mostPoints < board[11-(13-(11-i - board[11-i])*-1)]){
							
							//store move and links mostPoints to move
							mostPoints = board[11-(13-(11-i - board[11-i])*-1)];
							move = 11-i;
						}
					}
				}
			}

			  // if it can get points in the pit
				for(int i = 0; i < 6; i++) {
					
					//if it's location is going to place it in the bin
					if(board[11-i] == 6-i) {
						
						//if the mostPoints is 1 or 0
						//since the array go from spot 11 to 6 
						// if spot 6 and 7 and 8 can all go into the bin and give an extra turn
						// it will add spot 6 first than 7 than 8
						if(mostPoints < 2) {
							move = 11-i;
							break;
						}
						
						//if it can send it into the pit without interfering  
						// with the move that gives more points
						if(mostPoints > 2) {
							if(11-i < move) {
								move = 11-i;
							}
						}
					}
				}

				//If no other move has been made
				if(move== 0) {
					//screw over other player
					//it looks if the other player will have the chance to score in the bin
					// if they do and the AI can increase that number by one
					// which doesn't allow the player to get it in the bin
					// the AI's move is to screw the other player over
					for(int i = 0; i < 6; i++) {
						if(board[i] == i+1) {
							for(int j = 0; j < 6; j++) {
								//12 bc it adds one to the bin
								if(12-board[11-j] == i) {
									move = 11-j;
									break;
								}
							}
						}
					}
					
					//If no other move has been made
					//play the space with the highest number of pieces
					for(int i = 0; i <6; i++ ) {
						if(board[11-i] != 0) {
							if(mostPoints < board[11-i]) {
								mostPoints = board[11-i];
								move = 11-i;
							}
						}
					}
				}
//				System.out.print(move);
			return move;
	  }
	  
	  //checks winner and adds leftover points
	  public boolean gameEnd() {
		  // checks if the top row is empty
		  for(int i =0; i < 6; i++) {
			  if(board[i] != 0) {	
				  break;
			  }
			  
			  //collects all the pieces on the other side and adds them to player 2
			  if(i==5 && board[i] == 0) {
					for(int j = 6; j < 12; j++) {
						bin2 = board[j] + bin2;
					}
					return true;  
					
			  }
		  }

		  //checks bottom row if empty
		  for(int i =6; i < 12; i++) {
			  if(board[i] != 0) {
				  break;
			  }
			  //add all pieces on other side to player1
			  if(i==11 && board[i] == 0) {
				  for(int j = 0; j < 6; j++) {
						bin1 = board[j] + bin1;
					}
				return true;  
			  }
		  }
		  return false;
	}

}
