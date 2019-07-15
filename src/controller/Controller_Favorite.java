package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Controller_Favorite implements Initializable{
	@FXML
	private ListView<String> list_Item;
	@FXML
	private Button btn_talk;
	@FXML
	private WebView webHtml;
	@FXML
	private Button btn_delItem;
	@FXML
	private Button btn_back;
	@FXML
	private Button btn_deltemAll;
	private String TableName;
	private String ListName;
	private ArrayList<String> arrayItem;
	private ObservableList<String> oblistWord;
	private String bodem;
	private int indexSeclection = 0;
	
	
	public Controller_Favorite(String tableName) {
		this.TableName = tableName;
		if (TableName.equals("va")) {
			ListName = "fa_va";
		}
		else {
			ListName = "fa_av";
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		StartApp();
	}
	// Xóa 1 lịch sử
	public void deleteItem (ActionEvent event) {
		Activity.delete(bodem, ListName);
		list_Item.getItems().remove(indexSeclection);
		arrayItem.remove(indexSeclection);
	}
	// Xóa hết lịch sử
	public void DeleteAll (ActionEvent event) {
		Activity.DeleteAll(ListName);
		list_Item.getItems().clear();
		arrayItem.clear();
		OnOffbtn(false);
	}
	// Trở lại màn hình main
	public void BackMain (ActionEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.close();
	}
	// Khởi tạo windown 
	public void StartApp() {
		arrayItem = Activity.GetAllWord(ListName);
		oblistWord = FXCollections.observableArrayList(arrayItem);
		list_Item.setItems(oblistWord);
		list_Item.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);	
		list_Item.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			 // bắt sự kiện khi chọn 1 item trong list view
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				WebEngine webEngine = webHtml.getEngine();				// hiện thị nghĩa của từ
				String html = Activity.GetHTML(newValue,TableName);
				webEngine.loadContent(html);
				bodem = newValue;
				indexSeclection = list_Item.getSelectionModel().getSelectedIndex();
				OnOffbtn(true);
			}
		});

	}
	// bắt sự kiện button Talk
		public void TalkWord (ActionEvent event) {
			Activity.TalkWord(bodem,TableName);
		}
	// bật tắt nút xóa
		public void OnOffbtn (boolean flag) {
			try {
				if (flag && !bodem.isEmpty()) {
					btn_delItem.setDisable(false);
					btn_talk.setDisable(false);
				}
				else {
					btn_delItem.setDisable(true);
					btn_talk.setDisable(true);
					bodem="";
					indexSeclection=0;
				}
			} catch (Exception e) {
				btn_talk.setDisable(true);
				btn_delItem.setDisable(true);
				bodem="";
				indexSeclection=0;
			}
		}
}
