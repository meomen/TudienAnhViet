package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_Add implements Initializable {
	@FXML
	private TextField inputWord;
	@FXML 
	private TextArea inputDescription;
	@FXML
	private Button btn_save;
	@FXML
	private Button btn_exit;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	public void SaveAddWord (ActionEvent event) throws IOException {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader load = new FXMLLoader();
		load.setLocation(getClass().getResource("/fileFXML/GiaoDien_Main.fxml"));
		load.load();
		MyController controller = load.getController();
		String word = inputWord.getText();
		String description = inputDescription.getText();
		controller.enterNewWord(word, description);
		controller.OnOffbtn(false);
		stage.close();
	}
	public void CannerAddWord (ActionEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.close();
	}
}
