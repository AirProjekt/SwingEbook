package hr.tvz.seminar.handlers;

import hr.tvz.seminar.database.DataBaseUtils;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.graphics.text.PageText;


public class InsertBookHandler implements ActionListener{
	
	private JFileChooser fc;
	private JPanel bookPane;
	
	public InsertBookHandler(JPanel panel){
		this.bookPane = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "PDF & DOC files", "pdf", "doc");
		fc.setFileFilter(filter);
		int returnVal = fc.showSaveDialog(new JFrame());
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			String tekst = file.getName();
			int pozicija = tekst.lastIndexOf(".");
			String naslov = tekst.substring(0, pozicija);
			String ekstenzija = tekst.substring(pozicija+1, tekst.length());
			String autor = JOptionPane.showInputDialog("Enter the author's name");
			try {
				DataBaseUtils.connectToDataBase();
				FileInputStream in = new FileInputStream(file);
				FileInputStream in2 = new FileInputStream(file);
				DataBaseUtils.insertIntoDataBase(naslov,autor, ekstenzija, in);
				in.close();
				if(ekstenzija.equals("doc")){
					WordExtractor extractor = new WordExtractor(in2);
					String text = extractor.getText();
					String rijeci[];
					int dijelova = text.length()/32000;
					rijeci = new String[dijelova]; 
					for(int i=0;i<dijelova;i++){
						rijeci[i] = text.substring(i*32000, (i*32000)+32000);
					}
					if(text.length() <= 32000){
						DataBaseUtils.insertIntoDataBaseWords(naslov, 1, ekstenzija, text);
					}
					else{
						for(int i=0;i<rijeci.length;i++){
							DataBaseUtils.insertIntoDataBaseWords(naslov, i+1, ekstenzija, rijeci[i]);
						}
					}
				}
				else{
			        String filePath = file.getAbsolutePath();
			        Document document = new Document();
			        try {
			            document.setFile(filePath);
			        } catch (PDFException ex) {
			            System.out.println("Error parsing PDF document " + ex);
			        } catch (PDFSecurityException ex) {
			            System.out.println("Error encryption not supported " + ex);
			        } catch (FileNotFoundException ex) {
			            System.out.println("Error file not found " + ex);
			        } catch (IOException ex) {
			            System.out.println("Error handling PDF document " + ex);
			        }
			        for(int i=0;i<document.getNumberOfPages();i++){
			        	PageText pageText = document.getPageText(i);
			        	DataBaseUtils.insertIntoDataBaseWords(naslov, i+1, ekstenzija, pageText.toString());
			        }
			        // clean up resources
			        document.dispose();
				}
				in2.close();
				DataBaseUtils.closeConnection();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Book inserted!","Message",JOptionPane.INFORMATION_MESSAGE);
			JPanel singleBookPane = new JPanel();
			ImageIcon bookIcon = new ImageIcon("slike/book.png");
			JLabel bookLabel = new JLabel(naslov+"."+ekstenzija,bookIcon,JLabel.TRAILING);
			singleBookPane.setLayout(new GridBagLayout());
			singleBookPane.setMaximumSize(new Dimension(2000,80));
			ImageIcon readBookIcon = new ImageIcon("slike/book.jpg");
			JButton readBookButton = new JButton("Read Book",readBookIcon);
			ImageIcon deleteBookIcon = new ImageIcon("slike/book_delete.png");
			ImageIcon editBookIcon = new ImageIcon("slike/Book_edit.png");
			JButton deleteBookButton = new JButton("Delete Book",deleteBookIcon);
			JButton editBookButton = new JButton("Edit Book",editBookIcon);
			JSeparator separator = new JSeparator();
			separator.setMaximumSize(new Dimension(3000,2));
			DeleteBookHandler deleteBookHandler = new DeleteBookHandler(bookPane,naslov,singleBookPane,separator);
			deleteBookButton.addActionListener(deleteBookHandler);
			ReadBookHandler readBookHandler = new ReadBookHandler(naslov, ekstenzija);
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
			bookPane.repaint();
			bookPane.revalidate();
		}
	}

}
