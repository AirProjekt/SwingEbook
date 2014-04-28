package hr.tvz.seminar.database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DataBaseUtils {
	private static Connection veza;
	private static Statement stmt;
	private static ResultSet rs;
	
	public static void connectToDataBase(){
		try {
			veza = DriverManager.getConnection("jdbc:derby:baza/MyDB", "test", "test");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void closeConnection(){
		try {
			veza.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ResultSet returnResults(){
		try {
			stmt = veza.createStatement();
			rs = stmt.executeQuery("SELECT * FROM BAZA.BIBLIOTEKA");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public static ResultSet returnResultsWithCondition(String condition){
		try {
			stmt = veza.createStatement();
			rs = stmt.executeQuery("SELECT * FROM BAZA.BIBLIOTEKA WHERE naslov = '"+condition+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public static ResultSet pageNumbers(String text){
		try {
			stmt = veza.createStatement();
			String query;
			query = "SELECT * FROM BAZA.RIJECI WHERE rijeci LIKE '%"+text+"%'";
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public static ResultSet returnResultsCategory(int broj,String text){
		try {
			stmt = veza.createStatement();
			String query;
			if(broj == 0){
				query = "SELECT DISTINCT naslov,ekstenzija FROM BAZA.RIJECI WHERE rijeci LIKE '%"+text+"%'";
				rs = stmt.executeQuery(query);
			}
			else if(broj == 1){
				query = "SELECT * FROM BAZA.BIBLIOTEKA WHERE naslov LIKE '%"+text+"%'";
				rs = stmt.executeQuery(query);
			}
			else if(broj == 2){
				query = "SELECT * FROM BAZA.BIBLIOTEKA WHERE autor LIKE '%"+text+"%'";
				rs = stmt.executeQuery(query);
			}
			else if(broj == 3){
				query = "SELECT * FROM BAZA.BIBLIOTEKA WHERE publisher LIKE '%"+text+"%'";
				rs = stmt.executeQuery(query);
			}
			else if(broj == 4){
				query = "SELECT * FROM BAZA.BIBLIOTEKA WHERE releasedate LIKE '%"+text+"%'";
				rs = stmt.executeQuery(query);
			}
			else if(broj == 5){
				query = "SELECT * FROM BAZA.BIBLIOTEKA WHERE bookcategory LIKE '%"+text+"%'";
				rs = stmt.executeQuery(query);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public static int deleteFromDataBase(String condition){
		int delete = 0;
		try {
			stmt = veza.createStatement();
			delete = stmt.executeUpdate("DELETE FROM BAZA.BIBLIOTEKA WHERE naslov = '"+condition+"'");
			delete = stmt.executeUpdate("DELETE FROM BAZA.RIJECI WHERE naslov = '"+condition+"'");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Deleting book failed!","Message",JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
		return delete;
	}
	
	public static void insertIntoDataBase(String naslov,String autor,String ekstenzija,FileInputStream in){
		try {
			PreparedStatement pstmt = veza.prepareStatement("INSERT INTO BAZA.BIBLIOTEKA (naslov,ekstenzija,podatak,autor) VALUES(?,?,?,?)");
			pstmt.setString(1, naslov);
			pstmt.setString(2, ekstenzija);
			pstmt.setBinaryStream(3, in);
			pstmt.setString(4, autor);
			pstmt.execute();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Inserting book failed!","Message",JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public static void insertIntoDataBaseWords(String naslov,int stranica,String ekstenzija,String rijeci){
		try {
			PreparedStatement pstmt = veza.prepareStatement("INSERT INTO BAZA.RIJECI (naslov,ekstenzija,rijeci,stranica) VALUES(?,?,?,?)");
			pstmt.setString(1, naslov);
			pstmt.setString(2, ekstenzija);
			pstmt.setString(3, rijeci);
			pstmt.setInt(4, stranica);
			pstmt.execute();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Inserting book failed!","Message",JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public static int updateDataBase(String releaseDate,String type,String publisher,String naslov){
		int update = 0;
		try {
			stmt = veza.createStatement();
			String query = "UPDATE BAZA.BIBLIOTEKA SET releaseDate = '"+releaseDate+"',bookCategory = '"+type+"',publisher = '"+publisher+"' WHERE naslov = '"+naslov+"'";
			update = stmt.executeUpdate(query);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Update failed!","Message",JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
		return update;
	}
	
	public static int alterTable(){
		int delete = 0;
		try {
			stmt = veza.createStatement();
			delete = stmt.executeUpdate("alter table BAZA.BIBLIOTEKA add bookCategory varchar(20)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return delete;
	}
}
