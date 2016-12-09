package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;

import application.view.*;


public class Main extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("demo");

		initRootLayout();
		
		//showMainSubWin();
	}
	
	public void initRootLayout()
	{
		try
		{
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			RootLayoutController controller = loader.getController();
	        controller.setMainApp(this);

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	/*
	public void showMainSubWin()//abandoned
	{
		try {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class.getResource("view/MainSubWin.fxml"));
	        AnchorPane MainSubWin = (AnchorPane) loader.load();

	        rootLayout.setCenter(MainSubWin);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	*/
	public Stage getPrimaryStage()
	{
		return primaryStage;
	}
	
	public void loadDataFromFile()
	{
		//remain to be improved.
		return;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
