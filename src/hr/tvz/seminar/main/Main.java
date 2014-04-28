package hr.tvz.seminar.main;

import java.awt.Dimension;

import javax.swing.JFrame;


import hr.tvz.seminar.gui.GlavniFrame;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GlavniFrame frame = new GlavniFrame();
		frame.setPreferredSize(new Dimension(1100,400));
		Dimension dim = frame.getToolkit().getScreenSize();
		frame.setLocation(dim.width/6 - frame.getHeight()/2,
				dim.height/4 - frame.getHeight()/2);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
