package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PreferenceLayoutController
{
	@FXML
	private TextField TABINC;
	
	@FXML
	private TextField LinePerPage;
	
	@FXML
	private Button cancel;
	
	@FXML
	private void handleCancel()
	{
		Stage stage = (Stage)cancel.getScene().getWindow();
		stage.close();
	}
}
