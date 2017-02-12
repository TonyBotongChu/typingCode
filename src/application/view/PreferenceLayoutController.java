package application.view;

import java.net.URL;
import java.util.ResourceBundle;

import application.model.Settings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PreferenceLayoutController implements Initializable
{
	@FXML
	private AnchorPane anchorPane;
	
	public void FocusOnAnchorPane()
	{
		anchorPane.requestFocus();
	}
	
	@FXML
	private TextField TABINC;
	
	
	@FXML
	private TextField LinePerPage;
	
	@FXML
	private CheckBox ignoreBlanks;
	
	@FXML
	private Button cancel;
	
	@FXML
	private void handleCancel()
	{
		// close this window if cancel is clicked
		Stage stage = (Stage)cancel.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void handleOK()
	{
		Settings.setTABINC(Integer.parseInt(TABINC.getText()));
		Settings.setLine(Integer.parseInt(LinePerPage.getText()));
		Settings.ignoreBlanks_set(ignoreBlanks.isSelected());
		Settings.saveProperties();
		handleCancel();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		// TODO Auto-generated method stub
		TABINC.setText(String.valueOf(Settings.getTABINC()));
		LinePerPage.setText(String.valueOf(Settings.getLine()));
		// to make a numeric text field
		TABINC.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	                TABINC.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });
		LinePerPage.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	                LinePerPage.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });
		ignoreBlanks.setSelected(Settings.ignoreBlanks());
	}
}
