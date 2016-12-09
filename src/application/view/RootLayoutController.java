package application.view;

import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import application.Main;
import java.io.*;
import javafx.stage.FileChooser;

public class RootLayoutController
{
	private Main mainApp;

	public void setMainApp(Main mainApp)
	{
		this.mainApp = mainApp;
	}

	@FXML
	private void OpenResourceFile()
	{
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		if (file != null)
			mainApp.loadDataFromFile(file);
	}

	@FXML
	private void handleExit()
	{
		System.exit(0);
	}
	
	@FXML
	private void handleAbout()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText(null);
		alert.setContentText("Author: " + getAuthor());

		alert.showAndWait();
	}
	
	private static String getAuthor()
	{
		String name = "Zhu Botong, Wang Zhaopu.\n";
		String address = "Colleage of Software Engineering, BUAA.\n";
		return name+"\n"+address;
	}
}
