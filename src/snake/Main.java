package snake;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main {

	public static void main(String[] args) {
		
		JFrame obj = new JFrame();
		GameviewAndKeyListener gameplay= new GameviewAndKeyListener();
		
		obj.setBounds(10,10,905,700);
		obj.setBackground(Color.DARK_GRAY);;
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		obj.add(gameplay);
	}

}
