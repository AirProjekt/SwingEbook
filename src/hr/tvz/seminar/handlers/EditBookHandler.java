package hr.tvz.seminar.handlers;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditBookHandler implements ActionListener{
	
	private String naslov;
	
	public EditBookHandler(String naslov){
		this.naslov = naslov;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFrame frame = new JFrame("Edit Book");
		JLabel releaseLabel = new JLabel("Release date:");
		JTextField releaseField = new JTextField();
		JLabel typeLabel = new JLabel("Book category:");
		String[] categories = {"Horror","Fiction","Rouman","Science fiction","Religious","Spiritual"};
		JComboBox categorieBox = new JComboBox(categories);
		JLabel publisherLabel = new JLabel("Publisher:");
		JTextField publisherField = new JTextField();
		JButton submitButton = new JButton("Submit");
		SubmitHandler submitHandler = new SubmitHandler(releaseField, publisherField, categorieBox,naslov);
		submitButton.addActionListener(submitHandler);
		JButton viewButton = new JButton("View book information");
		ViewInformationHandler viewHandler = new ViewInformationHandler(naslov);
		viewButton.addActionListener(viewHandler);
		JPanel panel = new JPanel(new GridLayout(5,2,10,10));
		panel.add(releaseLabel);
		panel.add(releaseField);
		panel.add(typeLabel);
		panel.add(categorieBox);
		panel.add(publisherLabel);
		panel.add(publisherField);
		panel.add(new JLabel(" "));
		panel.add(submitButton);
		panel.add(new JLabel(" "));
		panel.add(viewButton);
		frame.getContentPane().setLayout(new FlowLayout());
		frame.add(panel);
		frame.setPreferredSize(new Dimension(420,220));
		frame.pack();
		frame.setVisible(true);
	}

}
