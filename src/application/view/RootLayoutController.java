package application.view;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import application.*;

import java.io.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
		GridPane gp = (GridPane) mainApp.getGridPane();
		VirtualCursor cursor = new VirtualCursor(gp);
		// System.out.println(mainApp.getRootLayout().getCenter());
		cursor.moveCursor();
	}

	@FXML
	private void OpenResourceFile()
	{
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		if (file != null)
			mainApp.loadDataFromFile(file);
		currentPage.setText("1");
		totalPage.setText(String.valueOf(mainApp.getNumOfPage()));
	}

	@FXML
	private void handleExit()
	{
		new WindowsCloseEvent(mainApp.getPrimaryStage()).showConfirmAlert();
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
	
	@FXML
	private void handlePreference()
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PreferenceLayout.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));  
            stage.show();
            PreferenceLayoutController pcontroller = (PreferenceLayoutController)fxmlLoader.getController();
            pcontroller.FocusOnAnchorPane();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static String getAuthor()
	{
		String name = "Zhu Botong, Wang Zhaopu.\n";
		String address = "Colleage of Software Engineering, BUAA.\n" + "Beijing, China.\n";
		return name + "\n" + address;
	}
	@FXML
	private Label timeLabel;
	
	private KTimer ktimer = new KTimer();

	@FXML
	private void startTimer()
	{
		// This function will be used to start the timer. 
		ktimer.startTimer(00);//show stoptimer
		ktimer.getSspTime().addListener(new InvalidationListener() {		
			public void invalidated(javafx.beans.Observable observable) {
				Platform.runLater(new Runnable(){
					public void run() {
						// TODO Auto-generated method stub
						timeLabel.setText(ktimer.getSspTime().get());
					}	
				});
			}
		});
	}
	
	@FXML
	private void pauseTimer()
	{
		//just pause the stoptimer
		String str = ktimer.getSspTime().get();
		System.out.println(ktimer.getTime()/1000);//show time when stopwatch pause
		ktimer.stopTimer();
		timeLabel.setText(str);
	}

	@FXML
	private void resumeTimer()
	{
		//resume the stoptimer after pause
		ktimer.startTimer(ktimer.getTime());
		ktimer.getSspTime().addListener(new InvalidationListener() {		
			public void invalidated(javafx.beans.Observable observable) {
				// TODO Auto-generated method stub
				Platform.runLater(new Runnable(){
					public void run() {
						// TODO Auto-generated method stub
						timeLabel.setText(ktimer.getSspTime().get());
					}	
				});
			}
		});
		//resumeTime
	}
	
	@FXML
	private void resetTimer()
	{
		//reset stoptimer
		ktimer.stopit();
		timeLabel.setText(ktimer.getSspTime().get());
	}
	
	@FXML
	private void PreviousPage()
	{
		if (mainApp.getCurrentPage() <= 1)
			return;
		mainApp.setCurrentPage(mainApp.getCurrentPage() - 1);
		mainApp.showCurrentPage(mainApp.getCurrentPage());
		currentPage.setText(String.valueOf(mainApp.getCurrentPage()));
	}

	@FXML
	private void NextPage()
	{
		if (mainApp.getCurrentPage() >= mainApp.getNumOfPage())
			return;
		mainApp.setCurrentPage(mainApp.getCurrentPage() + 1);
		mainApp.showCurrentPage(mainApp.getCurrentPage());
		currentPage.setText(String.valueOf(mainApp.getCurrentPage()));
	}

	@FXML
	private Label currentPage;

	@FXML
	private Label totalPage;
}
