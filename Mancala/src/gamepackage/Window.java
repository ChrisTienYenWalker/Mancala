/*File name:  Window

Purpose: Displays GUI and gets data from GameBackend(to display this data)
 * 
 *Creation date: May 6, 2022
 *Finished date: June 3, 2022
 *
functions:
Constructors public Window():
 * Draws buttons, score bins and other text boxes
 * These are later seen inside the window 
 * 
 * public void twoPlayer():
 * 	runs game UI for two player game mode
 * 
 * public void singlePlayer():
 * runs game UI for player vs AI
 * 
 * public void mouseClicked(MouseEvent e):
 * redirect to singlePlayer or twoPlayer
 * Checks if game is over and then modifies display if game is over
 * 
 * public void updateScores():
 * update the values in the bin, the players score and the values in each button on the game board
 * 
 * public void actionPerformed(): 
 * Runs the reset game buttons(twoplayer and AI modes) and the exit button
 * 
 * mousePressed(), mouseReleased(), mourseExited():
 * does nothing
 * 
 * mouseEntered():
 * displays which spot is being hovered(so that the player can understand which tiles the AI presed
 * 
 * public static void main(String args[]):
 * creates the window that uses the values in the constructor 
 * 
**************************************************/

package gamepackage;

import java.awt.Color;
import java.awt.*;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;



public class Window extends JFrame implements ActionListener,MouseListener{
	static final int WIDTH = 560; 
	static final int HEIGHT = 500;
	ArrayList<Integer> moves = new ArrayList<Integer>(); 
	
	private JButton[] button;
	BufferedImage board, pieces; 
	private int smallBoxHeight = 50;
	private int smallBoxWidth = 50;
	private int LargeBoxWidth = 50;
	private int LargeBoxHeight = 110;
	private JLabel Player, Score1, Score2, bin1, bin2, errorMsg, numberOfPieces;
	private JButton exitButton, resetButton1, resetButton2;
	//resetButton1 = 2-player game
	//resetButton2 = AI game
	private Font labelFont;
	
	GameBackend game = new GameBackend();
	
	public Window() {

    	getContentPane().setLayout(null); 
    	getContentPane().setBackground(new Color(255,228,196)); 
		
    	//buttons are in the same positions as the array in GameBackend
    	button = new JButton[12];
		
		//creates all the buttons on the top row
    	for(int j = 0; j < 6; j++) {
	    		button[j] = new JButton(Integer.toString(4));
				button[j].setBackground(new Color(160,82,45)); 
				button[j].setLocation((100+(smallBoxWidth*j) + 10*j), (150)); 
			    button[j].setSize(smallBoxWidth,smallBoxHeight); 
			    getContentPane().add(button[j]);
		     
    		
		        //Set all buttons to work with the event handlers
		    button[j ].addActionListener(this);
		    button[ j ].addMouseListener(this);
		    add(button[j]);
    	}
    	
    	//creates all the buttons on the bottom row
    	for(int j = 0; j < 6; j++) {
    		button[11- j] = new JButton(Integer.toString(4));
			button[11-j].setBackground(new Color(160,82,45)); 
			button[11-j].setLocation((100+(smallBoxWidth*j) + 10*j), (150+(smallBoxHeight)+10)); 
		    button[11-j].setSize(smallBoxWidth,smallBoxHeight); 
		    getContentPane().add(button[j]);
	     
		
	        //Set all buttons to work with the event handlers
	    button[11-j ].addActionListener(this);
	    button[11- j ].addMouseListener(this);
	    add(button[11-j]);
		}
    	   
    	
    	labelFont = new Font("Verdana", Font.BOLD, 28);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        
        //creating the rest of the buttons and labels
        exitButton = new JButton("Exit"); 
		exitButton.setBackground(SystemColor.control); 
		exitButton.setLocation(400, 400); 
	    exitButton.setSize(100,40); 
	    getContentPane().add(exitButton);
	    exitButton.addActionListener(this);
	    exitButton.addMouseListener(this);
	    add(exitButton);
	    
	    resetButton1 = new JButton("New Game 2 Player"); 
		resetButton1.setBackground(SystemColor.control); 
		resetButton1.setLocation(50, 400); 
	    resetButton1.setSize(150,40); 
	    getContentPane().add(resetButton1);
	    resetButton1.addActionListener(this);
	    resetButton1.addMouseListener(this);
	    add(resetButton1);
	    
	    resetButton2 = new JButton("New Game AI"); 
		resetButton2.setBackground(SystemColor.control); 
		resetButton2.setLocation(225, 400); 
	    resetButton2.setSize(150,40); 
	    getContentPane().add(resetButton2);
	    resetButton2.addActionListener(this);
	    resetButton2.addMouseListener(this);
	    add(resetButton2);
	    
    	bin1 = new JLabel(" 0"); 
		bin1.setFont(labelFont);
		bin1.setBackground(new Color(160,82,45));
		bin1.setBounds(40, 150, LargeBoxWidth,LargeBoxHeight);
		bin1.setBorder(border);
		bin1.setOpaque(true);
		getContentPane().add(bin1);
		
		bin2 = new JLabel(" 0"); 
		bin2.setFont(labelFont);
		bin2.setBackground(new Color(160,82,45));
		bin2.setOpaque(true);
		bin2.setBounds(460, 150, LargeBoxWidth,LargeBoxHeight); 
		bin2.setBorder(border);
		getContentPane().add(bin2);
		
		numberOfPieces = new JLabel("");
		numberOfPieces.setFont(labelFont);
		numberOfPieces.setBounds(135, 300, 320,LargeBoxHeight); 
		getContentPane().add(numberOfPieces);
    	
    	Player = new JLabel("Player 1's Turn");
    	Player.setFont(labelFont);
    	Player.setBounds(155, 40, 240, 45);
		getContentPane().add(Player);
		
		errorMsg = new JLabel("");
		errorMsg.setFont(labelFont);
		errorMsg.setBounds(100, 70, 500, 45);
		getContentPane().add(errorMsg);
		
		Score1 = new JLabel("Player1: 0");
		Score1.setFont(labelFont);
		Score1.setBounds(40, 270, 240, 45);
		getContentPane().add(Score1);

		Score2 = new JLabel("Player2: 0");
		Score2.setFont(labelFont);
		Score2.setBounds(340, 270, 240, 45);
		getContentPane().add(Score2);
    	
	}
	
	public void twoPlayer(Object e) {
		
		for(int i =0; i <12; i++) {
			
			//checks which buttons was pressed
			if(e == button[i]) {
				
				errorMsg.setText("");
				
				//checks if that is a valid move for the player
				if(game.player1IsNext == true && i <6 && game.board[i] != 0) {
					
					//applies the move
					game.applymove(i);
					
					//checks if the turns switch 
			    	if(!game.player1IsNext)
			    		Player.setText("Player 2's Turn");
			    	
					updateScores();
					break;
				}

				//checks if that is a valid move for the player
				if(game.player1IsNext == false && i >=6 && game.board[i] != 0) {
					
					//applies the move
					game.applymove(i);
					
					//check if the turns switch
					if(game.player1IsNext)
			    		Player.setText("Player 1's Turn");
					updateScores();
					break;
				}
				
				//if the move isn't valid display an error msg
				else {
					errorMsg.setText("You can't click that");
					break;
				}
			}
		}
		
//		game.displayBoard();	

	}
	
	public void singlePlayer(Object e) {
		
		for(int i =0; i <12; i++) {
			
			//finds buttons location 
			if(e == button[i]) {
				errorMsg.setText("");
				
				//checks if that is a valid move for the player
				if(game.player1IsNext == true && i <6 && game.board[i] != 0) {
					
					//runs move algorithm
					game.applymove(i);
					
					//checks if it's the AI's turn
					//this might be useless if I can't get things to pause
			    	if(!game.player1IsNext)
			    		Player.setText("AI Turn");
					updateScores();
				}
				
				//if the move isn't valid display an error msg
				else {
					errorMsg.setText("You can't click that");
					break;
				}
			}
		}
		game.displayBoard();	

		//if it's the Ai's turns
		if(game.player1IsNext == false && !game.gameEnd()) {
			
			//while it is still the Ai's turn keep letting it make moves
			while( game.player1IsNext == false){
				
				//adds moves to an array(so that it can output what space it moved
		        moves.add(game.AImakemove());
				game.applymove(moves.get(moves.size()-1));
			
			if(game.player1IsNext) {
				String listOfmoves = "";			
	    		Player.setText("Player 1's Turn");
	    		
	    	//creates a list of the moves made by the AI 
	    	//so that the moves can be displayed
			for(int i = moves.size()-1; i >= 0; i--) {
				listOfmoves += moves.get(i) + ", ";
			}
				errorMsg.setText("AI moved #" + listOfmoves);
				moves.removeAll(moves);
				
			updateScores();
			}
			}
		}
	}
	
	@Override
	//when a button is pressed execute a board or text change
	public void mouseClicked(MouseEvent e) {

//		game.displayBoard();
		
		//check if the game is still going
		if(!game.gameEnd()) {
			
			//run the twoplayer or single player game 
			if(game.GameType == true) {
				twoPlayer(e.getSource());
			}
			if(game.GameType == false) {
				singlePlayer(e.getSource());
			}
		}
		
		//if the game is over display who won and a GameOver Sign
		if(game.gameEnd()) {
			updateScores();
			int subtract = 0;
			for(int i =0; i < 6; i++) {
				subtract += game.board[i];
			}
			bin1.setText( String.valueOf(game.bin1-subtract));
			
			for(int i =0; i < 6; i++) {
				subtract += game.board[11-i];
			}
			bin2.setText( String.valueOf(game.bin2 - subtract));
			
			Player.setText("	GameOver");
			if(game.bin1 > game.bin2) {
				errorMsg.setText("	Player1 Wins");
				
		}else if(game.bin1 < game.bin2) {
				if(game.GameType == true) {
					
				errorMsg.setText("		Player2 Wins");
				}else {
					errorMsg.setText("		  AI Wins");

				}
			}
			else if(game.bin1 == game.bin2) {
				errorMsg.setText("		  		Tie");

			}
		}
//		game.AImakemove();

	}

	//change the scores of the buttons and the bins
	public void updateScores() {
		
		//change the values of the Text scores
			Score1.setText("Player1: " + String.valueOf(game.bin1));
		
		if(game.GameType == true) {
			Score2.setText("Player2: " + String.valueOf(game.bin2));
		}else {
			Score2.setText("       AI: " + String.valueOf(game.bin2));
		}
		
		//change the values of the bin scores
		bin1.setText( String.valueOf(game.bin1));
		bin2.setText( String.valueOf(game.bin2));
		

		
		//change the values of the button scores
		for(int i = 0; i < 6; i++) {
			button[i].setText(String.valueOf(game.board[i]));
		}
		
		//these are reversed!!!
		for(int i = 6; i < 12; i++) {
			button[i].setText(String.valueOf(game.board[i]));
		}

		
	}

	public void actionPerformed(ActionEvent e) { 
        //Ask the event which button it represents 
		
		//closes program
        if (e.getActionCommand().equals("Exit")) 
            System.exit(0); 
        
        //resets the board and runs the twoplayer game option
        if (e.getActionCommand().equals("New Game 2 Player")) {
        	game.resetBoard();
    		Player.setText("Player 1's Turn");
    		errorMsg.setText("");
        	game.GameType = true;
    		Score2.setText("Player2: ");
        	updateScores();
        }
        
        //resets the board and runs the singlePlayer game option
        if (e.getActionCommand().equals("New Game AI")) {
        	Score2.setText("AI: ");
        	game.resetBoard();
        	game.GameType = false;
    		Player.setText("Player 1's Turn");
    		errorMsg.setText("");
        	updateScores();
        }
            
       // else 
         //   System.out.println("Ouch! Stop that!"); 
    }
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}


	@Override
	//displays the spot number
	public void mouseEntered(MouseEvent e) {
    	//System.out.println("MouseEntered"); 
		
		//when a button is hovered it displays which number in the array it is
    	for(int i=0; i < 12; i++) {
	    	if(e.getSource() == button[i] && i < 6) {
	    		numberOfPieces.setText("Space #" + i );
	    		break;
	    	}
	    	else if(e.getSource() == button[i] && i >= 6) {
	    		numberOfPieces.setText("Space #" + i);
	    		break;
	    	}
    	}
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		numberOfPieces.setText("");

	}
	
    
	//creates window
    public static void main(String args[]) { 
         // Instantiate a FirstApplication object so you can display it 
    	 Window frame =  new Window(); 
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
         frame.setDefaultCloseOperation(EXIT_ON_CLOSE); 

         // Set the size of the application window (frame) 
         frame.setSize(WIDTH, HEIGHT); 
         frame.setVisible(true); // Show the application (frame) 
         frame.setTitle("Manacala Game");
    }
}
