package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.darkprograms.speech.synthesiser.SynthesiserV2;
import com.darkprograms.speech.translator.GoogleTranslate;

import database.SQLite;
import datastruct.Trie;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class Activity {
	private static Trie datastruct = new Trie();
	private static SQLite database = new SQLite();
	
	public Trie getDatastruct() {
		return datastruct;
	}

	public void setDatastruct(Trie datastruct) {
		this.datastruct = datastruct;
	}

	public SQLite getDatabase() {
		return database;
	}

	public void setDatabase(SQLite database) {
		this.database = database;
	}

	public Activity () {}
	
	public static String GetHTML (String word,String TableName) {
		return database.GetData(word, TableName);
	}
	public static void  CreateTrie (String TableName) {
		try {
			Statement stt = database.getConnection().createStatement();
			String quyre  = "SELECT * FROM "+TableName;
			ResultSet resultSet = stt.executeQuery(quyre);
			while (resultSet.next()) {
				String a = resultSet.getString(2);
				datastruct.insert(a);
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void insert (String word,String description,String TableName) {
		datastruct.insert(word);
		database.insert(word, description, TableName);
	}
	public static void delete (String word, String TableName) {
		if (datastruct.find(word)!=null) {
			database.delete(word, TableName);
			datastruct.delete(word);
		}		
	}
	public static void DeleteAll (String TableName) {
		database.DeleteAll(TableName);
	}
	public static void update (String word_old,String word_new,String description, String TableName) {
		if (datastruct.find(word_old)!=null) {
			database.update(word_old, word_new, description, TableName);
			datastruct.update(word_old, word_new);
		}	
	}
	public static ArrayList<String> search (String word) {
		return datastruct.find(word);
	}
	public static ArrayList<String> GetAllWord (String TableName) {
		return database.GetAllWord(TableName);
	}
	public static void addToFavorite (String word, String TableName) {
		if (database.NotWord(word, TableName)) {
			database.addItem(word, TableName);
		}
	}
	public static void addToHistory (String word ,String TableName) {
		if (!database.NotWord(word, TableName)) {
			database.delete(word, TableName);
			database.addItem(word, TableName);
		}
		else if (word != null) {
			database.addItem(word, TableName);
		}
	}
	public static ArrayList<String> GetHistory(String TableName) {
		return database.GetHistory(TableName);
	}
	
	// 2 hàm dưới tham khảo Github. Link : https://github.com/goxr3plus/Java-Google-Text-To-Speech
	// Thank for goxr3plus
	public static void TalkWord (String text,String TableName) {
		if (text == null) return;
		String language ="";
		if (TableName.equals("av")) {
			language = "en";
		}
		else {
			language = "vi";
		}
		SynthesiserV2 synthesizer = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
		synthesizer.setLanguage(language);
		Thread thread = new Thread(() -> {
			try {
				
				AdvancedPlayer player = new AdvancedPlayer(synthesizer.getMP3Data(text));
				player.play();
				
			} catch (IOException | JavaLayerException e) {
				e.printStackTrace();	
			}
		});
		thread.setDaemon(false);
		thread.start();	
	}
	public static void TalkText(String text,String language) {
		SynthesiserV2 synthesizer = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
		synthesizer.setLanguage(language);
		Thread thread = new Thread(() -> {
			try {
				
				AdvancedPlayer player = new AdvancedPlayer(synthesizer.getMP3Data(text));
				player.play();
				
			} catch (IOException | JavaLayerException e) {
				e.printStackTrace();	
			}
		});
		thread.setDaemon(false);
		thread.start();	
	}
	public static String Translate (String languageForm,String languageTo,String text) throws IOException {
		return GoogleTranslate.translate(languageForm,languageTo,text);
	}

}
