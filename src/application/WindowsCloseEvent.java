package application;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Alert.AlertType;
import application.model.Settings;

public class WindowsCloseEvent implements EventHandler<WindowEvent>
{

	private Stage stage;

	public WindowsCloseEvent(Stage stage)
	{
		this.stage = stage;
	}
	
	public void showConfirmAlert()
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Exit");
		alert.setHeaderText(null);
		alert.setContentText("Exit TypingCode? ");

		alert.showAndWait().ifPresent(response ->
		{
			if (response == ButtonType.OK)
			{
				Settings.saveProperties();
				stage.close();
			}
		});
	}

	public void handle(WindowEvent event)
	{

		event.consume();

		showConfirmAlert();
	}
}