package hr.tvz.seminar.handlers;

import hr.tvz.seminar.database.DataBaseUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class DeleteBookHandler implements ActionListener{
	
	JPanel panel;
	JPanel subPanel;
	JSeparator separator;
	String text;
	
	public DeleteBookHandler(JPanel panel,String text,JPanel subPanel,JSeparator separator){
		this.panel = panel;
		this.text = text;
		this.subPanel = subPanel;
		this.separator = separator;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		DataBaseUtils.connectToDataBase();
		DataBaseUtils.deleteFromDataBase(text);
		DataBaseUtils.closeConnection();
		panel.remove(subPanel);
		panel.remove(separator);
		panel.repaint();
		panel.revalidate();
		JOptionPane.showMessageDialog(null, "Book deleted!","Message",JOptionPane.INFORMATION_MESSAGE);
	}

}
