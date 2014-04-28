package hr.tvz.seminar.handlers;

import hr.tvz.seminar.database.DataBaseUtils;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;


public class ReadBookHandler implements ActionListener{
	
	String naslov;
	String ekstenzija;
	
	public ReadBookHandler (String naslov,String ekstenzija){
		this.naslov = naslov;
		this.ekstenzija = ekstenzija;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DataBaseUtils.connectToDataBase();
		ResultSet rs = DataBaseUtils.returnResultsWithCondition(naslov);
		try {
			rs.next();
			InputStream in = rs.getBinaryStream("podatak");
			// build a controller
			SwingController controller = new SwingController();
			// Build a SwingViewFactory configured with the controller
			SwingViewBuilder factory = new SwingViewBuilder(controller);
			// Use the factory to build a JPanel that is pre-configured
			//with a complete, active Viewer UI.
			JPanel viewerComponentPanel = factory.buildViewerPanel();
			// Create a JFrame to display the panel in
			JFrame window = new JFrame("Using the Viewer Component");
			window.getContentPane().add(viewerComponentPanel);
			window.pack();
			window.setVisible(true);
			// Open a PDF document to view
			controller.openDocument(in, "bla", "blabla");
			if(controller != null){
				DataBaseUtils.closeConnection();
				in.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
