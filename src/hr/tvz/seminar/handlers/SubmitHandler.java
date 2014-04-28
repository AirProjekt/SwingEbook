package hr.tvz.seminar.handlers;

import hr.tvz.seminar.database.DataBaseUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SubmitHandler implements ActionListener{
	
	private JTextField releaseDate;
	private JTextField publisher;
	private JComboBox bookCategory;
	private String naslov;
	
	public SubmitHandler(JTextField releaseDate,JTextField publisher,JComboBox bookCategory,String naslov){
		this.releaseDate = releaseDate;
		this.publisher = publisher;
		this.bookCategory = bookCategory;
		this.naslov = naslov;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DataBaseUtils.connectToDataBase();
		DataBaseUtils.updateDataBase(releaseDate.getText(), (String)bookCategory.getSelectedItem(), publisher.getText(), naslov);
		DataBaseUtils.closeConnection();
		JOptionPane.showMessageDialog(null, "Update successful!","Message",JOptionPane.INFORMATION_MESSAGE);
	}
	
}
