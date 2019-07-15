package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.ButtonType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MyController implements Initializable {
	@FXML
	private WebView web_ShowHTML = new WebView();
	@FXML
	private Button btn_talk;
	@FXML
	private Button btn_delete;
	@FXML 
	private Button btn_update;
	@FXML 
	private Button btn_add;
	@FXML
	private Button btn_language;
	@FXML 
	private Button btn_favorite;
	@FXML
	private Button btn_history;
	@FXML
	private Button btn_Like;
	@FXML
	private Button btn_Translate;
	@FXML
	private Hyperlink link_ggtrans;
	@FXML
	private ListView<String> list_ListWord;
	@FXML 
	private TextField tf_input;
	private ArrayList<String> arrayWord;
	private ObservableList<String> oblistWord;
	private String TableName = "av";
	private String bodem="";
	private int indexSeclection = 0;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { 
		 StartApp();
	}
	
	// bắt sự kiện khi có thay đổi ở TextFied
	public void  released (KeyEvent event) {
		String word = tf_input.getText();
		SearchWord(word);
		OnOffbtn(false); //tắt sửa xóa khi đang tìm
		
	}
	
	// phương thức khởi tạo tài nguyên cho chương trình
	public void StartApp() {
		Activity.CreateTrie(TableName);					// tạo cấu trúc Trie và nạp database
		 arrayWord = Activity.GetAllWord(TableName);	
		 oblistWord = FXCollections.observableArrayList(arrayWord);							// Khởi tạo List Word
		 list_ListWord.setItems(oblistWord);												// và tạo hoạt động của List
		 list_ListWord.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);		
		 list_ListWord.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			 // bắt sự kiện khi chọn 1 item trong list view
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				WebEngine webEngine = web_ShowHTML.getEngine();				// hiện thị nghĩa của từ
				String html = Activity.GetHTML(newValue, TableName);
				webEngine.loadContent(html);
				bodem = newValue;
				indexSeclection = list_ListWord.getSelectionModel().getSelectedIndex();
				Activity.addToHistory(newValue, "hi_"+TableName);
				OnOffbtn(true);
			}
		});
	}

	// bắt sự kiện button add,thêm từ
	public void addWord (ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fileFXML/GiaoDien_Add.fxml"));
		Parent AddView = loader.load();
		Scene scene = new Scene(AddView);
		Stage AddWindown = new Stage();
		AddWindown.setTitle("Thêm");
		AddWindown.setScene(scene);
		
		AddWindown.initModality(Modality.WINDOW_MODAL);
		AddWindown.initOwner(stage);
		AddWindown.showAndWait();
		OnOffbtn(false);
	}
	
	// bắt sự kiện button delete,xóa từ 
	public void deleteWord(ActionEvent event) throws IOException  {
		Alert ThongBao =  new Alert(AlertType.CONFIRMATION);
		ThongBao.setTitle("Xóa");
		ThongBao.setHeaderText("Bạn có chắc chắn xóa từ này ?");
		ThongBao.setContentText("Từ này sẽ bị loại bỏ vĩnh viễn khỏi bộ lưu trữ");
		
		// bắt sự kiện các button
		Optional<ButtonType>option = ThongBao.showAndWait();
		if(option.get()==ButtonType.OK) {
			Activity.delete(bodem, TableName);
			Activity.delete(bodem, "hi_"+TableName);
			Activity.delete(bodem, "fa_"+TableName);
			list_ListWord.getItems().remove(indexSeclection);    // thay đổi trên listview
			arrayWord.remove(indexSeclection);
		}
		else if (option.get()==ButtonType.CANCEL) {}
		else {}
	}
	
	// bắt sự kiện button update,sửa từ
	public void updateWord(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fileFXML/GiaoDien_Update.fxml"));	//
		Parent UpdateView = loader.load();												// Tạo windown con Update
		Scene scene = new Scene(UpdateView);											//
		Stage UpdateWindown = new Stage();
		UpdateWindown.setTitle("Sửa");
		UpdateWindown.setScene(scene);
		Controller_Update controller = loader.getController();			// kết nối dữ liệu trực tiếp giữa 2 stage
		UpdateWindown.initModality(Modality.WINDOW_MODAL);
		UpdateWindown.initOwner(stage);
		UpdateWindown.showAndWait();
		if (controller.getWord().equals("")  && !controller.getDescription().equals("")) {
			Activity.update(bodem, bodem, controller.getDescription(), TableName);
			WebEngine webEngine = web_ShowHTML.getEngine();				// hiện thị nghĩa của từ
			String html = Activity.GetHTML(bodem, TableName);
			webEngine.loadContent(html);
		}
		else if (!controller.getWord().equals("")  && !controller.getDescription().equals("")) {
			// thay đổi trên listview
			Activity.update(bodem, controller.getWord(), controller.getDescription(), TableName);
			list_ListWord.getItems().set(indexSeclection, controller.getWord());
			arrayWord.set(indexSeclection, controller.getWord());
		}
		OnOffbtn(false);
	}
	
	// bắt sự kiện button language, thay đổi từ điển
	public void changeLanguage(ActionEvent event) {
		if (btn_language.getText().equals("Anh - Việt")) {
			btn_language.setText("Việt - Anh");
			TableName = "va";
		}
		else {
			btn_language.setText("Anh - Việt");
			TableName = "av";
		}
		Activity.CreateTrie(TableName);					// tạo cấu trúc Trie và nạp database
		 arrayWord = Activity.GetAllWord(TableName);	
		 oblistWord = FXCollections.observableArrayList(arrayWord);							// Khởi tạo lại List Word
		 list_ListWord.setItems(oblistWord);	
		 OnOffbtn(false);
	}
	
	// bật windown History
	public void enterHistory (ActionEvent event) throws IOException {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fileFXML/GiaoDien_History.fxml"));
		loader.setController(new Controller_History(TableName));
		Parent HistoryView = loader.load();
		Scene scene = new Scene(HistoryView);
		Stage HistoryWindown = new Stage();
		HistoryWindown.setTitle("Lịch Sử");
		HistoryWindown.setScene(scene);
		HistoryWindown.initModality(Modality.WINDOW_MODAL);
		HistoryWindown.initOwner(stage);
		stage.hide();
		HistoryWindown.showAndWait();
		stage.show();
	}

	// bật windown Favorite
	public void enterFavorite (ActionEvent event) throws IOException {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fileFXML/GiaoDien_Favorite.fxml"));
		loader.setController(new Controller_Favorite(TableName));
		Parent FavoriteView = loader.load();
		Scene scene = new Scene(FavoriteView);
		Stage FavoriteWindown = new Stage();
		FavoriteWindown.setTitle("Yêu Thích");
		FavoriteWindown.setScene(scene);
		FavoriteWindown.initModality(Modality.WINDOW_MODAL);
		FavoriteWindown.initOwner(stage);
		stage.hide();
		FavoriteWindown.showAndWait();
		stage.show();
		}

	// bật Translate Online
	public void enterTranslate (ActionEvent event) throws IOException {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fileFXML/GiaoDien_GGTranslate.fxml"));
		Parent TranslateView = loader.load();
		Scene scene = new Scene(TranslateView);
		Stage TranslateWindown = new Stage();
		TranslateWindown.setTitle("Translate Online");
		TranslateWindown.setScene(scene);
		TranslateWindown.initModality(Modality.NONE);
		TranslateWindown.initOwner(stage);
		TranslateWindown.show();
	}
	
	// bắt sự kiện btn_talk
	public void TalkWord (ActionEvent event) {
		Activity.TalkWord(bodem,TableName);
	}
	
	// phương thức bắt button yêu thích
	public void LikeWord (ActionEvent event) {
		Activity.addToFavorite(bodem, "fa_"+TableName);
	}
	
	// phương thức nhận dữ liệu từ Add windown
	public void enterNewWord (String newWord,String newDescription) {
		Activity.insert(newWord, newDescription, TableName);
	}
	
	// hàm tắt bật button sửa , xóa
	public void OnOffbtn (boolean flag) {
		try {
			if (flag && !bodem.isEmpty()) {
				btn_talk.setDisable(false);
				btn_update.setDisable(false);
				btn_delete.setDisable(false);
				btn_Like.setDisable(false);
			}
			else {
				btn_talk.setDisable(true);
				btn_Like.setDisable(true);
				btn_update.setDisable(true);
				btn_delete.setDisable(true);
				bodem="";
				indexSeclection=0;
			}
		} catch (Exception e) {
			btn_talk.setDisable(true);
			btn_Like.setDisable(true);
			btn_update.setDisable(true);
			btn_delete.setDisable(true);
			bodem="";
			indexSeclection=0;
		}
	}
	
	// hàm tìm kiếm từ 
	public void SearchWord (String word) {
		if (word.isEmpty()) {   // nếu để trống , load lại tất cả word
			oblistWord = FXCollections.observableArrayList(arrayWord);
			list_ListWord.setItems(oblistWord);
			WebEngine webEngine = web_ShowHTML.getEngine();	// không hiện thị gì khi để trống TextFied
			String html="";
			webEngine.loadContent(html);
		}
		else {
			list_ListWord.getItems().clear();
			ArrayList<String>arrayWord_search= Activity.search(word);		// tìm và xuất ra list các từ giống từ đó nhất
			oblistWord =FXCollections.observableArrayList(arrayWord_search);
			list_ListWord.setItems(oblistWord);		// nạp vào listview
			if (!arrayWord_search.isEmpty()) {
				WebEngine webEngine = web_ShowHTML.getEngine();
				String html = Activity.GetHTML(arrayWord_search.get(0),TableName); // hiện thị nghĩa ra từ đầu tiên tìm được
				webEngine.loadContent(html);
				list_ListWord.scrollTo(0);
			}
			else {
				WebEngine webEngine = web_ShowHTML.getEngine();	// không hiện thị gì khi không tìm thấy
				String html="";
				webEngine.loadContent(html);
			}
		}
	}
}
