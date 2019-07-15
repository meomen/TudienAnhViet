package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_Update implements Initializable {
	@FXML
	private TextField inputWord;
	@FXML 
	private TextArea inputDescription;
	@FXML
	private Button btn_save;
	@FXML
	private Button btn_exit;
	private int flag;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	public void SaveUpdateWord (ActionEvent event) throws IOException {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		flag = 1;
		stage.close();
	}
	public void CannerUpdateWord (ActionEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		flag = 0;
		stage.close();
	}
	public String getWord () {
		if (flag == 1 ) {
			return inputWord.getText();
		}
		else {
			return "";
		}
	}
	public String getDescription () {
		if (flag == 1 ) {
			return inputDescription.getText();
		}
		else {
			return "";
		}
	}
}
