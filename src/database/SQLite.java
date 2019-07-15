package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

// Đối tượng liên kết database SQLite
public class SQLite {
	
	private Connection connection;
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public SQLite () {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:data.db");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// phương thức lấy ra dữ liệu của 1 từ
	public String GetData (String word,String TableName) {
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		
		//viết câu truy vấn , khởi động nhà máy tạo ResultSet
		String quyre = "SELECT * FROM "+TableName+" WHERE word =?";
		try {
			preparedStatement = connection.prepareStatement(quyre);
			preparedStatement.setString(1,word);
			resultSet = preparedStatement.executeQuery();
			String content="";
			if (!resultSet.isClosed()) {
				content = "<html>"+resultSet.getString(3)+"</html>"; 
				resultSet.close();
				preparedStatement.close();
			}
			return content; // lấy và trả về giá trị cột 3 (tên cột 3: html)
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// xóa dữ liệu 1 từ vào database
	public boolean delete (String word, String TableName) {
		try {
			Statement stt  = connection.createStatement();
			String quyre = "DELETE FROM "+TableName+" WHERE word = '"+word+"'";
			stt.executeUpdate(quyre);
			stt.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	//Xóa hết dữ liệu 1 bảng 
	public void DeleteAll (String TableName) {
		try {
			Statement stt  = connection.createStatement();
			String quyre = "DELETE FROM "+TableName;
			stt.executeUpdate(quyre);
			stt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// thêm dữ liệu một từ vào database
	public boolean insert (String word,String description, String TableName) {
		try {
			Statement stt  = connection.createStatement();
			String quyre = "INSERT INTO "+TableName+" (word,html,description) VALUES ('"
							+word+"', '"
							+description+"' , '"
							+description+"' )";				
			stt.executeUpdate(quyre);
			stt.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	// cập nhập dữ liệu một từ trong database
	public boolean update (String word_old,String word_new,String description, String TableName) {
		try {
			Statement stt  = connection.createStatement();
			String quyre = "UPDATE "+TableName+" SET word = '"
							+word_new+"', "
							+"html = '"
							+description+"', "
							+"description = '"
							+description
							+"' WHERE word = '"
							+word_old+"'";
			stt.executeUpdate(quyre);
			stt.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	// xuất ra toàn bộ list word 
	public ArrayList<String> GetAllWord (String TableName) {
		ArrayList<String> listWord  = new ArrayList<String>();
		try {
			Statement stt  = connection.createStatement();
			ResultSet resultSet =stt.executeQuery("SELECT * FROM "+TableName);
			while (resultSet.next()) {
				String a = resultSet.getString(2);
				listWord.add(a);
			}
			return listWord;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// thêm 1 từ vào danhsach favorite hoặc history
	public boolean addItem (String word,String TableName) {
		try {
			Statement stt  = connection.createStatement();
			String quyre = "INSERT INTO "+TableName+" (word) VALUES ('"
							+word+"' )";				
			stt.executeUpdate(quyre);
			stt.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	// lấy danh sách trong bảng history( history phải lấy ngược)
	public ArrayList<String> GetHistory (String TableName) {
		ArrayList<String> listWord  = new ArrayList<String>();
		try {
			Statement stt  = connection.createStatement();
			ResultSet resultSet =stt.executeQuery("SELECT * FROM "+TableName);
			while (resultSet.next()) {
				String a = resultSet.getString(2);
				listWord.add(a);
			}
			Collections.reverse(listWord);
			return listWord;
		} catch (Exception e) {
			e.printStackTrace();
			return listWord;
		}
	}
	// tìm từ trong database
	public boolean NotWord (String Word, String TableName) {
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		
		//viết câu truy vấn , khởi động nhà máy tạo ResultSet
		String quyre = "SELECT * FROM "+TableName+" WHERE word =?";
		try {
			preparedStatement = connection.prepareStatement(quyre);
			preparedStatement.setString(1,Word);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.isClosed()) {
				return true;
			}
			else {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
}
