package hr.tvz.seminar.handlers;

import hr.tvz.seminar.database.DataBaseUtils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class SearchHandler implements ActionListener,KeyListener{
	
	private JPanel bookPane;
	private JTextField text;
	private JComboBox cBox;
	
	public SearchHandler(JPanel panel,JTextField text,JComboBox cBox){
		this.bookPane = panel;
		this.text = text;
		this.cBox = cBox;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (text.getText().length() > 0) {
			bookPane.removeAll();
			bookPane.repaint();
			bookPane.revalidate();
			DataBaseUtils.connectToDataBase();
			String pageNumbers="";
			ResultSet rs;
			if(text.getText().equals("*")){
				rs = DataBaseUtils.returnResults();
			}
			else{
				rs = DataBaseUtils.returnResultsCategory(cBox.getSelectedIndex(), text.getText());
			}
			try {
				while (rs.next()) {
					String naslov = rs.getString("naslov");
					String ekstenzija = rs.getString("ekstenzija");
					ImageIcon bookIcon = new ImageIcon(
							"slike/book.png");
					JLabel bookLabel = new JLabel(naslov + "." + ekstenzija,
							bookIcon, JLabel.TRAILING);
					JPanel singleBookPane = new JPanel();
					singleBookPane.setLayout(new GridBagLayout());
					singleBookPane.setMaximumSize(new Dimension(2000, 80));
					ImageIcon readBookIcon = new ImageIcon(
							"slike/book.jpg");
					ImageIcon editBookIcon = new ImageIcon(
							"slike/Book_edit.png");
					JButton editBookButton = new JButton("Edit Book",
							editBookIcon);
					JButton readBookButton = new JButton("Read Book",
							readBookIcon);
					ImageIcon deleteBookIcon = new ImageIcon(
							"slike/book_delete.png");
					JButton deleteBookButton = new JButton("Delete Book",
							deleteBookIcon);
					JSeparator separator = new JSeparator();
					separator.setMaximumSize(new Dimension(6000, 2));
					DeleteBookHandler deleteBookHandler = new DeleteBookHandler(
							bookPane, naslov, singleBookPane, separator);
					deleteBookButton.addActionListener(deleteBookHandler);
					ReadBookHandler readBookHandler = new ReadBookHandler(
							naslov, ekstenzija);
					readBookButton.addActionListener(readBookHandler);
					EditBookHandler editBookHandler = new EditBookHandler(
							naslov);
					editBookButton.addActionListener(editBookHandler);
					GridBagConstraints c = new GridBagConstraints();
					c.anchor = GridBagConstraints.LINE_START;
					c.gridx = 0;
					c.gridy = 0;
					c.gridwidth = 3;
					c.weightx = 1.0;
					singleBookPane.add(bookLabel, c);
					c.weightx = 0.0;
					c.gridwidth = 1;
					c.gridx = 4;
					c.gridy = 0;
					c.insets = new Insets(0, 0, 0, 10);
					singleBookPane.add(readBookButton, c);
					c.gridx = 5;
					c.gridy = 0;
					c.insets = new Insets(0, 0, 0, 10);
					singleBookPane.add(deleteBookButton, c);
					c.gridx = 6;
					c.gridy = 0;
					c.insets = new Insets(0, 0, 0, 10);
					singleBookPane.add(editBookButton, c);
					singleBookPane.setAlignmentX(JFrame.LEFT_ALIGNMENT);
					bookPane.add(singleBookPane);
					if(cBox.getSelectedIndex() == 0 && !text.getText().equals("*")){
						ResultSet rs2 = DataBaseUtils.pageNumbers(text.getText());
						String naslovTemp;
						int pageNumber;
						while(rs2.next()){
							naslovTemp = rs2.getString("naslov");
							if(naslovTemp.equals(naslov)){
								pageNumber = rs2.getInt("stranica");
								pageNumbers += Integer.toString(pageNumber)+",";
							}
						}
						pageNumbers = pageNumbers.substring(0, pageNumbers.length()-1);
						JLabel pageLabel = new JLabel("Pages:");
						JLabel pageNumbersLabel = new JLabel(pageNumbers);
						pageNumbersLabel.setForeground(Color.red);
						bookPane.add(pageLabel);
						bookPane.add(pageNumbersLabel);
						bookPane.add(Box.createRigidArea(new Dimension(0, 10)));
						pageNumbers = "";
					}
					bookPane.add(separator);
				}
				bookPane.repaint();
				bookPane.revalidate();
				DataBaseUtils.closeConnection();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			actionPerformed(null);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
	
	

}
