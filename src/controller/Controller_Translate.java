package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import application.Language;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

public class Controller_Translate implements Initializable{
	@FXML
	private Button btn_hoandoi;
	@FXML
	private Button btn_talkFrom;
	@FXML
	private Button btn_talkTo;
	@FXML
	private ChoiceBox<Language> choiceFrom;
	@FXML
	private ChoiceBox<Language> choiceTo;
	@FXML
	private Label ouputText;
	@FXML
	private TextArea inputText;
	
	private String languageFrom = "en";
	private String languageTo = "vi";
	
	Language tiengviet = new Language("vi", "Tiếng Việt");
	Language tienganh = new Language("en","Tiếng Anh");
	ObservableList<Language> languages = FXCollections.observableArrayList(tiengviet,tienganh);
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		NapNgonNgu();
		StartTag();
	}
	// bắt sự kiện button hoán đổi
	public void HoanDoi (ActionEvent event) {
		Language from = choiceFrom.getSelectionModel().getSelectedItem();
		Language to = choiceTo.getSelectionModel().getSelectedItem();
		choiceFrom.getSelectionModel().select(to);
		choiceTo.getSelectionModel().select(from);
	}
	// bắt sự kiện khi thay đổi TextArea
	public void input (KeyEvent event) throws IOException {
		Translate();
	}
	// bắt sự kiện button TalkFrom
	public void TalkFrom (ActionEvent event) {
		Activity.TalkText(inputText.getText(), languageFrom);
	}
	// bắt sự kiện button TalkTo
	public void TalkTo(ActionEvent event) {
		Activity.TalkText(ouputText.getText(), languageTo);
	}
	
	public void StartTag () {
		choiceFrom.getItems().addAll(languages);
		choiceFrom.getSelectionModel().select(tienganh);
		choiceTo.getItems().addAll(languages);
		choiceTo.getSelectionModel().select(tiengviet);
		
		choiceFrom.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Language>() {

			@Override
			public void changed(ObservableValue<? extends Language> language, Language languageOld, Language languageNew) {
				languageFrom = languageNew.getCode();
				try {
					Translate();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		choiceTo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Language>() {

			@Override
			public void changed(ObservableValue<? extends Language> language, Language languageOld, Language languageNew) {
				languageTo = languageNew.getCode();
				try {
					Translate();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}
	
	public void Translate () throws IOException {
		String textFrom = inputText.getText();
		String textTo = Activity.Translate(languageFrom, languageTo, textFrom);
		ouputText.setText(textTo);
	}
	
	public void NapNgonNgu () {
		ArrayList<Language> listLanguage = new ArrayList<Language>();
		listLanguage.add(new Language("ga", "Ai-len"));
		listLanguage.add(new Language("ar-XA", "Ả Rập"));
		listLanguage.add(new Language("pl", "Ba Lan"));
		listLanguage.add(new Language("pt", "Bồ Đào Nha"));
		listLanguage.add(new Language("bg", "Bungari"));
		listLanguage.add(new Language("km", "Cam-pu-chia"));
		listLanguage.add(new Language("hr", "Croatia"));
		listLanguage.add(new Language("he", "Do Thái"));
		listLanguage.add(new Language("da", "Đan Mạch"));
		listLanguage.add(new Language("de", "Đức"));
		listLanguage.add(new Language("et", "Estonia"));
		listLanguage.add(new Language("nl", "Hà Lan"));
		listLanguage.add(new Language("ko", "Hàn"));
		listLanguage.add(new Language("hi", "Hindi"));
		listLanguage.add(new Language("hu", "Hungary"));
		listLanguage.add(new Language("el", "Hy Lạp"));
		listLanguage.add(new Language("lv", "Latvia"));
		listLanguage.add(new Language("lt", "Lithuania"));
		listLanguage.add(new Language("no", "Na Uy"));
		listLanguage.add(new Language("ru", "Nga"));
		listLanguage.add(new Language("ja", "Nhật Bản"));
		listLanguage.add(new Language("fi", "Phần Lan"));
		listLanguage.add(new Language("fr", "Pháp"));
		listLanguage.add(new Language("ro", "Rumani"));
		listLanguage.add(new Language("cs", "Séc"));
		listLanguage.add(new Language("sr", "Serbia"));
		listLanguage.add(new Language("sk", "Slovak"));
		listLanguage.add(new Language("sl", "Slovenia"));
		listLanguage.add(new Language("es", "Tây Ban Nha"));
		listLanguage.add(new Language("th", "Thái"));
		listLanguage.add(new Language("tr", "Thổ Nhĩ Kỳ"));
		listLanguage.add(new Language("zh", "Trung Quốc"));
		listLanguage.add(new Language("sv", "Thụy Điển"));
		listLanguage.add(new Language("uk", "Ukraina"));
		listLanguage.add(new Language("it", "Ý"));
		languages.addAll(listLanguage);
	}
}
