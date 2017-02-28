package binarytictactoe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

//This serializable class does not declare a static final serialVersionUID field of type long
public class BinaryTicTacToe extends JApplet 
{
   
   private static boolean playerVsPlayer = false; // default: false (play computer by default)
   //private static int clicksCount      = 0;
   private int[][]  ticTacToeBoard       = new int[3][3];
   private int[] winningNumbers          = {7, 56, 73, 84, 146, 273, 292, 448};
   private static int XScore             = 0;
   private static int OScore             = 0;
   private static boolean isWin          = false;
   private int compare                   = 0;
  
 // Indicate which player has a turn, initially it is the X player
 private char whoseTurn = 'X'; // X start off

 // Create and initialize cells
 private Cell[][] cells =  new Cell[3][3]; // a 3 by 3 2Dimensional array holding Cell objects

 // Create and initialize a status label
 private JLabel jlblStatus = new JLabel("X's turn to play");
 
 /** Constructor for User Interface */
 public BinaryTicTacToe()
 {
   // Panel p to hold cells
   JPanel p = new JPanel(new GridLayout(3, 3, 0, 0));
  
   int iteration = 0;
   for (int i = 0; i < 3; i++)
   {
     for (int j = 0; j < 3; j++)
     {
       p.add(cells[i][j] = new Cell());
       ticTacToeBoard[i][j] = (int)(Math.pow(2, iteration));
       /*print the ticTacToe board to console as it's constructed which demonstrates how the bitwise comparison 
        * algorithm determines win combinations*/
       System.out.println("ticTacToeBoard[" + i + "][" + j +"] = " + ticTacToeBoard[i][j]);
       iteration++;
     } // close inner for(j)
   } // close outer for (i)
  
   // Set line borders on the cells panel and the status label
   p.setBorder(new LineBorder(Color.red, 1));
   jlblStatus.setBorder(new LineBorder(Color.yellow, 1));

   // Place the panel and the label to the applet
   add(p, BorderLayout.CENTER);
   add(jlblStatus, BorderLayout.SOUTH);
 } // close TicTacToe() constructor method

 
 /** Create a method to check if the cells are all occupied */
 public boolean isFull()
 {
     //run through all the squares to check for a ' '
   for (int i = 0; i < 3; i++)
   {
     for (int j = 0; j < 3; j++)
     {
       if (cells[i][j].getToken() == ' ') // ' ' for a blank space
         return false; // If there is a blank space return false (not full)
     } // close inner for(j)
   } // close outer for(i)

   return true; // yes, it is full (there were no ' ' found
 } // close isFull() method

 

 // An inner class for a cell. This serializable class does not declare a static final serialVersionUID field of type long either
 public class Cell extends JPanel // includes methods: Cell(), getToken(), setToken(), paintComponent(g) and INNER CLASS: MouseListener
 {
   // Token used for this cell
   private char token = ' '; // seets all cells to ' ' first time invoked

   public Cell()
   {
     setBorder(new LineBorder(Color.black, 1)); // Set cell's border
     addMouseListener(new MyMouseListener());  // Register Mouselistener
   }

   /** Return token to find out whose turn it is  */
   public char getToken()
   {
     return token;
   }

   /** Set a new token indicating whose turn it is on and repaint component */
   public void setToken(char c) // is passed whoseTurn --> c
   {
     token = c; // c == whoseTurn ('X' or 'O')
     repaint();
   }

   @Override /** Paint the cell */
   protected void paintComponent(Graphics g)
   {
     super.paintComponent(g);

     if (token == 'X')
     {
       g.drawLine(10, 10, getWidth() - 10, getHeight() - 10);
       g.drawLine(getWidth() - 10, 10, 10, getHeight() - 10);
     }
     else if (token == 'O')
     {
       g.drawOval(10, 10, getWidth() - 20, getHeight() - 20);
     }
   } // close paintComponent() method

  
   
   private class MyMouseListener extends MouseAdapter
   {
     @Override /** Handle mouse click on a cell */
     public void mouseClicked(MouseEvent e) //This method is called every time a mouse click occurs
{
       // If clicked cell is empty and game is not over (' ' == game over, ,man)
       if (token == ' ' && whoseTurn != ' ')
       {
         setToken(whoseTurn); // Set token in the cell
         updateScore(whoseTurn);
         //clicksCount++;

         // Check game status and update players score
         if (bitwiseWinChecker(whoseTurn)) // if somebody checker return true for isWin
         {
           jlblStatus.setText(whoseTurn + " won! The game is over");// ...display the winner...
           whoseTurn = ' '; // ... and end the game. Game is over
         }
         else if (isFull()) // After checking there is no winner and all the squares are filled...
         {
           jlblStatus.setText("Cat! The game is Draw"); // ...Then the game is a cat
           whoseTurn = ' '; // Game is over
         }
         else // If there is no game over or tie then change whoseTurn to the other player
         {
           // Change the turn
           whoseTurn = (whoseTurn == 'X') ? 'O' : 'X'; // If whoseTurn is 'X' then it's now 'O's turn; otherwise whoseTurn is X
           // Display whose turn
           jlblStatus.setText(whoseTurn + "'s turn");
          
           /**The following set of if statements is run only in PlayerVsComputer mode**************/
           if (whoseTurn == 'O' && playerVsPlayer == false) // If it is now the computers turn to play
               {
               computerMove(); // pass whoseTurn? No   
               System.out.println("Computer's move complete. It's now" + whoseTurn + "'s turn");
              
               // Check game status and update players score
               if (bitwiseWinChecker(whoseTurn)) // if somebody won...
               {
                 jlblStatus.setText(whoseTurn + " won! The game is over");// ...display the winner...
                 whoseTurn = ' '; // ... and end the game. Game is over
               }
               else if (isFull()) // After checking there is no winner and all the squares are filled...
               {
                 jlblStatus.setText("Cat! The game is Draw"); // ...Then the game is a cat
                 whoseTurn = ' '; // Game is over
               }
              
               } //------------ exit method and wait for 'X' to move again **********************
         } // close else
       } // close if-loop
     } // close mouseClicked() method
     //tallyScore();
   } //CLOSE inner(nested) CLASS: MyMouseListener
  
 } // CLOSE CLASS: Cell
 
 public void updateScore(int token) // call the following two methods each time a move is made
 {
     XScore = 0; //reset the scores back to 0
     OScore = 0; //...and recalculate them afresh e/ time this method is called
     //1st Tally Up Score
       for(int i = 0; i < 3; i++)
       {
           //Iterate through all the cells and add up points for each X or O (depending on the current token)
           for(int j = 0; j < 3; j++)
           {
           if(cells[i][j].getToken() == token && token == 'X')
           {
           XScore = XScore + ticTacToeBoard[i][j]; // update X score

           } else if(cells[i][j].getToken() == token && token == 'O')
           {
           OScore = OScore + ticTacToeBoard[i][j]; // update O (computer) Score
           }

           } // close inner for(j)
       } // close outer for(i)
 }
  
   public boolean bitwiseWinChecker(char token) // is passed whoseTurn as token. This method is run everytime a move is made
   {

   //Then, check for a winner
   if (token == 'X')
   {
        for (int x = 0; x < winningNumbers.length; x++)
        {
            compare = XScore & winningNumbers[x]; //Perform bitwise AND comparison of current with wins Array...
        if (compare == winningNumbers[x]) // ...then check that value against the numbers in the winsArray alone
        {
            isWin = true;  // If they are the same there was a win
            return isWin;
        }
        // close for-loop
        } //else
        isWin = false;
        return isWin;
       

   } else if (token == 'O')
   {
        for (int o = 0; o < winningNumbers.length; o++)
        {
           
            compare = OScore & winningNumbers[o]; //Perform bitwise AND comparison of current with wins Array...
        if (compare == winningNumbers[o]) // ...then, check that value against the numbers in the winsArray alone
        {
            isWin = true;  // If they are the same there was a win
            return isWin;
        }
        // close for-loop
        } // else
       
        isWin = false;
        return isWin;
       
   } // close O's-turn-if-loop
   else {
       isWin = false;
       return isWin;
   }

   } // close isWon() method 
  
 
 private  void computerMove()
 {
   
     for(int row = 0; row < cells.length; row++) // This loop runs almost to the end of the method
     {
        

     for(int col = 0; col < cells[row].length; col++) /*START inner for loop(col) **/
     {
         

          if(cells[row][col].getToken() == ' ' && whoseTurn == 'O') //If the square is empty and it's O's(computer's) turn
          {
              for(int num = 0; num < winningNumbers.length; num++)
         {
                 
                 
        int option1Score      = ticTacToeBoard[row][col] + XScore; //create var for hypothetical score if option 1 is taken
        int option1BitCompare = option1Score & winningNumbers[num]; //then make a bitwise AND comparison of hypothetical score and win array
        
        int option2Score      = ticTacToeBoard[row][col] + OScore;
        int option2BitCompare = option2Score & winningNumbers[num];
        
         if(option1BitCompare == winningNumbers[num] && whoseTurn != ' ') //check to see if X opponent has a potential winning move
         {  
             cells[row][col].setToken(whoseTurn); //TAKE MOVE OPTION(#1)
             updateScore(whoseTurn);//update the score factoring in move just made
             //clicksCount++;
            
               // Check game status and update players score
               if (bitwiseWinChecker(whoseTurn)) // if somebody won...
               {
                 jlblStatus.setText(whoseTurn + " won! The game is over");//WINNER! ...display the winner...
                 whoseTurn = ' '; // ... and end the game. Game is over
                 return;
               }
               else if (isFull()) // CAT After checking there is no winner and all the squares are filled...
               {
                 jlblStatus.setText("CAT! The game is Draw"); // ...Then the game is a cat
                 whoseTurn = ' '; // Game is over
                 return;
               } else { // switch players and exit computerMove() method
            
             //whoseTurn = 'X';
                  whoseTurn = (whoseTurn == 'X') ? 'O' : 'X'; // If whoseTurn is 'X' then it's now 'O's turn; otherwise whoseTurn is X
             //System.out.println("Blocked:X's current score is " + XScore + " It's now " + whoseTurn + "'s turn.");
              jlblStatus.setText(whoseTurn + "'s turn");
             return;
               } // close else
              
      
         } else if(option2BitCompare == winningNumbers[num] && whoseTurn != ' ') //MOVE OPTON (#2)--If not check for O'S potential winning move
         {
         cells[row][col].setToken(whoseTurn); // TAKE OPTION(#2) MOVE! update score******
         updateScore(whoseTurn);
         //clicksCount++;
        
        
           // Check game status and update players score
         if (bitwiseWinChecker(whoseTurn)) // if somebody won...
         {
           jlblStatus.setText(whoseTurn + " won! The game is over");// ...display the winner...
           whoseTurn = ' '; // ... and end the game. Game is over
           return;
         }
         else if (isFull()) // After checking there is no winner. check if all the squares are filled...
         {
           jlblStatus.setText("Cat! The game is Draw"); // ...Then the game is a cat
           whoseTurn = ' '; // Game is over
           return;
         } else { // switch the token and jlabel status back to player X and and exit method
        
         //whoseTurn = 'X';
             whoseTurn = (whoseTurn == 'X') ? 'O' : 'X'; // If whoseTurn is 'X' then it's now 'O's turn; otherwise whoseTurn is X
         //System.out.println("For the Win:O's current score is " + OScore + " It's now " + whoseTurn + "'s turn.");
          jlblStatus.setText(whoseTurn + "'s turn");
          //bitwiseWinChecker(whoseTurn); // check for a win one last time? Not necessary
         return;
         }
         } // close for-the-win else-if statement
    

             } // close num-for loop
                 
             
             
              if (whoseTurn == 'X')
              {
                  System.out.println("It's " + whoseTurn + "'s turn. returning, a 2nd time, out of if loop... ");
              return; // break a 2nd time out of if loop;
              }
          }// close if(' ')
         
         
          if (whoseTurn == 'X')
          return; // ...and break again, a 3rd time, out of inner, nested for-loop(j)
    
          if (whoseTurn == 'O' && row == 2 && col == 2 && whoseTurn != ' ') // OPTION (#3)******If almost to the end and no potential win or move to block...
         {
              boolean broken = false;
              for (int a = 0; a < 3; a++)
              {
                  for(int b = 0; b < 3; b++)
                  {
                      if(cells[a][b].getToken() == ' ' && whoseTurn == 'O') // If it's blank and still the computer's turn
                      {
                          cells[a][b].setToken(whoseTurn); // TAKE OPTION (#3) MOVE.******* else just take the first current empty square
                          updateScore(whoseTurn);
                          //isWon(whoseTurn);//update the score first by running isWon() method
                          //clicksCount++;
                          broken = true;
                          break;
                      }
                 
                  }
                  if (broken == true)
                     {
                         break; // ..then, break one more time out of the entire for-loop
                     }
                    
              }
              // Check game status and update players score
               if (bitwiseWinChecker(whoseTurn)) // if somebody won...
               {
                 jlblStatus.setText(whoseTurn + " won! The game is over");// ...display the winner...
                 whoseTurn = ' '; // ... and end the game. Game is over
                 return;
               }
               else if (isFull()) // After checking there is no winner and all the squares are filled...
               {
                 jlblStatus.setText("Cat! The game is Draw"); // ...Then the game is a cat
                 whoseTurn = ' '; // Game is over
                 return;
               } else {
                   whoseTurn = 'X';
                   System.out.println("Arbitrary, last-resort Move: O's score is " + OScore + " It's now " + whoseTurn + "'s turn.");
                    jlblStatus.setText(whoseTurn + "'s turn");
                    //updateScore(whoseTurn); // update the score factoring in the move just made
                   return; //exit computerMove() method
               } //close else      
         } // close option 3 else-if loop   
     } //close INNER for loop(col)
     } // close outer for loop(row)
    
 } // close entire computerMove() method  
   
} // CLOSE CLASS: TicTacToe
