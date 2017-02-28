package binarytictactoe;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/*MAIN CLASS containing main() method*/
public class TestBinaryTicTacToe 
{
	static final double VERSION = 2.1; //No bugs this time
	
    public static void main(String[]args)
    {
          // Create a frame
        JFrame frame = new JFrame("TicTacToe " + VERSION);

        // Create an instance of the applet
        BinaryTicTacToe applet = new BinaryTicTacToe();

        // Add the applet instance to the frame
        frame.add(applet, BorderLayout.CENTER);

        // Display the frame
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    } 
}
