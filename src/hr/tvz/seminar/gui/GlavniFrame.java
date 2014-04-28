package hr.tvz.seminar.gui;

import hr.tvz.seminar.database.DataBaseUtils;
import hr.tvz.seminar.handlers.DeleteBookHandler;
import hr.tvz.seminar.handlers.EditBookHandler;
import hr.tvz.seminar.handlers.InsertBookHandler;
import hr.tvz.seminar.handlers.ReadBookHandler;
import hr.tvz.seminar.handlers.SearchHandler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class GlavniFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6848792808224620775L;

	public GlavniFrame(){
		super("Library");
		getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.PAGE_AXIS));
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel,BoxLayout.X_AXIS));
		searchPanel.setBackground(new Color(205,201,201));
		ImageIcon libraryIcon = new ImageIcon("slike/library.png");
		JLabel searchLabel = new JLabel("Search by key words:",libraryIcon,JLabel.TRAILING);
		searchLabel.setFont(new Font("font",Font.ITALIC,20));
		searchPanel.add(searchLabel);
		JTextField textField = new JTextField();
		textField.setFont(new Font("font2",Font.PLAIN,20));
		searchPanel.add(textField);
		ImageIcon insertBookIcon = new ImageIcon("slike/Book_Stack_Icon.jpg");
		JButton insertButton = new JButton("Insert Book",insertBookIcon);
		ImageIcon searchBookIcon = new ImageIcon("slike/Search-icon.png");
		JButton searchButton = new JButton(searchBookIcon);
		String[] opcije = new String[6];
		opcije[0]="Key words";
		opcije[1]="Name of the book";
		opcije[2]="Author's name";
		opcije[3]="Publisher";
		opcije[4]="Release date";
		opcije[5]="Book category";
		JComboBox comboBox = new JComboBox(opcije);
		comboBox.setMaximumSize(new Dimension(200,30));
		searchPanel.add(searchButton);
		searchPanel.add(Box.createHorizontalStrut(10));
		searchPanel.add(new JLabel("Category:"));
		searchPanel.add(comboBox);
		searchPanel.add(Box.createHorizontalStrut(20));
		searchPanel.add(insertButton);
		searchPanel.add(Box.createHorizontalStrut(20));
		JPanel bookPane = new JPanel();
		bookPane.setLayout(new BoxLayout(bookPane,BoxLayout.PAGE_AXIS));
		DataBaseUtils.connectToDataBase();
		ResultSet rs = DataBaseUtils.returnResults();
		try {
			while(rs.next()){
					String naslov = rs.getString("naslov");
					String ekstenzija = rs.getString("ekstenzija");
					ImageIcon bookIcon = new ImageIcon("slike/book.png");
					JLabel bookLabel = new JLabel(naslov+"."+ekstenzija,bookIcon,JLabel.TRAILING);
					JPanel singleBookPane = new JPanel();
					singleBookPane.setLayout(new GridBagLayout());
					singleBookPane.setMaximumSize(new Dimension(2000,80));
					ImageIcon readBookIcon = new ImageIcon("slike/book.jpg");
					ImageIcon editBookIcon = new ImageIcon("slike/Book_edit.png");
					JButton editBookButton = new JButton("Edit Book",editBookIcon);
					JButton readBookButton = new JButton("Read Book",readBookIcon);
					ImageIcon deleteBookIcon = new ImageIcon("slike/book_delete.png");
					JButton deleteBookButton = new JButton("Delete Book",deleteBookIcon);
					JSeparator separator = new JSeparator();
					separator.setMaximumSize(new Dimension(3000,2));
					DeleteBookHandler deleteBookHandler = new DeleteBookHandler(bookPane,naslov,singleBookPane,separator);
					deleteBookButton.addActionListener(deleteBookHandler);
					ReadBookHandler readBookHandler = new ReadBookHandler(naslov,ekstenzija);
					readBookButton.addActionListener(readBookHandler);
					EditBookHandler editBookHandler = new EditBookHandler(naslov);
					editBookButton.addActionListener(editBookHandler);
					GridBagConstraints c = new GridBagConstraints();
					c.anchor = GridBagConstraints.LINE_START;
					c.gridx = 0;
					c.gridy = 0;
					c.gridwidth = 3;
					c.weightx = 1.0;
					singleBookPane.add(bookLabel,c);
					c.weightx = 0.0;
					c.gridwidth = 1;
					c.gridx = 4;
					c.gridy = 0;
					c.insets = new Insets(0,0,0,10);
					singleBookPane.add(readBookButton,c);
					c.gridx = 5;
					c.gridy = 0;
					c.insets = new Insets(0,0,0,10);
					singleBookPane.add(deleteBookButton,c);
					c.gridx = 6;
					c.gridy = 0;
					c.insets = new Insets(0,0,0,10);
					singleBookPane.add(editBookButton,c);
					singleBookPane.setAlignmentX(JFrame.LEFT_ALIGNMENT);
					bookPane.add(singleBookPane);
					bookPane.add(separator);
			}
			rs.close();
			DataBaseUtils.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InsertBookHandler insertBookHandler = new InsertBookHandler(bookPane);
		insertButton.addActionListener(insertBookHandler);
		SearchHandler searchHandler = new SearchHandler(bookPane,textField,comboBox);
		searchButton.addActionListener(searchHandler);
		textField.addKeyListener(searchHandler);
		JScrollPane mainBookPane = new JScrollPane(bookPane);
		searchPanel.setMaximumSize(new Dimension(1100,30));
		getContentPane().add(searchPanel);
		getContentPane().add(mainBookPane);
	}
}
