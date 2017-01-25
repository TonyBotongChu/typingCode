package application.view;


import javafx.fxml.FXML;
//import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import application.Main;
import application.VirtualCursor;

import java.io.*;
import javafx.stage.FileChooser;

public class RootLayoutController
{
	private Main mainApp;

	// Is called by the main application to give a reference back to itself.
	public void setMainApp(Main mainApp)
	{
		this.mainApp = mainApp;
	}

	@FXML
	private void moveCursor()
	{		
		GridPane gp = (GridPane) mainApp.getRootLayout().getCenter();
		VirtualCursor cursor = new VirtualCursor(gp);
		//System.out.println(mainApp.getRootLayout().getCenter());
		cursor.moveCursor();
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
		String address = "Colleage of Software Engineering, BUAA.\n" + "Beijing, China.\n";
		return name + "\n" + address;
	}

	@FXML
	private Button pause;

	@FXML
	private void startTimer()
	{
		// This function will be used to start the timer. But at present I will
		// use it to do something else.
	}
}
