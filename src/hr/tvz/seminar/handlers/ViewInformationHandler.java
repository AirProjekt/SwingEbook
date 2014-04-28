package hr.tvz.seminar.handlers;

import hr.tvz.seminar.database.DataBaseUtils;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewInformationHandler implements ActionListener{
	
	private String naslov;
	
	public ViewInformationHandler(String naslov){
		this.naslov = naslov;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DataBaseUtils.connectToDataBase();
		ResultSet rs = DataBaseUtils.returnResultsWithCondition(naslov);
		String autor = "";
		String ekstenzija = "";
		String releaseDate = "";
		String publisher = "";
		String bookCategory = "";
		try {
			while(rs.next()){
				autor += rs.getString("autor");
				ekstenzija += rs.getString("ekstenzija");
				releaseDate += rs.getString("releaseDate");
				publisher += rs.getString("publisher");
				bookCategory += rs.getString("bookCategory");
			}
			DataBaseUtils.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JFrame frame = new JFrame("Book information");
		frame.getContentPane().setLayout(new FlowLayout());
		JPanel panel = new JPanel(new GridLayout(6,2,10,10));
		JLabel naslovLabel = new JLabel("Title : ");
		JLabel naslovL = new JLabel(naslov);
		JLabel autorLabel = new JLabel("Autor : ");
		JLabel autorL = new JLabel(autor);
		JLabel ekstenzijaLabel = new JLabel("Type of the document : ");
		JLabel ekstenzijaL = new JLabel(ekstenzija);
		JLabel releaseLabel = new JLabel("Release date : ");
		JLabel releaseL = new JLabel(releaseDate);
		JLabel publisherLabel = new JLabel("Publisher : ");
		JLabel publisherL = new JLabel(publisher);
		JLabel categoryLabel = new JLabel("Book category : ");
		JLabel categoryL = new JLabel(bookCategory);
		panel.add(naslovLabel);
		panel.add(naslovL);
		panel.add(autorLabel);
		panel.add(autorL);
		panel.add(ekstenzijaLabel);
		panel.add(ekstenzijaL);
		panel.add(releaseLabel);
		panel.add(releaseL);
		panel.add(publisherLabel);
		panel.add(publisherL);
		panel.add(categoryLabel);
		panel.add(categoryL);
		frame.add(panel);
		frame.setPreferredSize(new Dimension(600,200));
		frame.pack();
		frame.setVisible(true);
	}

}
