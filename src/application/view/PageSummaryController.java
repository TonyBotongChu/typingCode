package application.view;

import application.model.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PageSummaryController
{
	@FXML
	private Button OK;

	@FXML
	private void handleCancel()
	{
		// close this window
		Stage stage = (Stage) OK.getScene().getWindow();
		stage.close();
	}

	public void initSummaryWindow(long second)
	{
		TypeableCharacters.setText(String.valueOf(UserData.getRightNumber()));
		TypedCharacters.setText(String.valueOf(UserData.getInputNumber()));
		UnproductiveKeystrokes.setText(String.valueOf(UserData.getWrongNumber()));
		long minute = second / 60;
		String etime = minute / 60 + " : " + minute % 60;
		Time.setText(etime);
		long cpm = 0;
		if (minute != 0)
			cpm = UserData.getRightNumber() / minute;
		CPM.setText(String.valueOf(cpm));
	}

	@FXML
	private Label TypeableCharacters;
	@FXML
	private Label TypedCharacters;
	@FXML
	private Label UnproductiveKeystrokes;
	@FXML
	private Label Time;
	@FXML
	private Label CPM;// character per minute
}
