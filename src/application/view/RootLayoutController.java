package application.view;


import javafx.fxml.FXML;
import application.Main;
import java.io.*;
import javafx.stage.*;
import javafx.stage.FileChooser;

public class RootLayoutController
{
	private Main mainApp;
	
	public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
	
	@FXML
	private void OpenResourceFile()
	{
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		if (file != null)
			mainApp.loadDataFromFile();
	}
}
