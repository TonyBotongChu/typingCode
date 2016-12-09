package application;

import java.io.*;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import application.view.*;

public class Main extends Application
{
	private Stage primaryStage;
	private BorderPane rootLayout;
	private ArrayList<String> sourceFile = new ArrayList<String>();

	public BorderPane getRootLayout()
	{
		return rootLayout;
	}

	@Override
	public void start(Stage primaryStage)
	{

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("demo");

		initRootLayout();

		// showMainSubWin();
	}

	public void initRootLayout()
	{
		try
		{
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			rootLayout.setCenter(new GridPane());
			rootLayout.getCenter().setStyle("-fx-border-color:black");

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

	public Stage getPrimaryStage()
	{
		return primaryStage;
	}

	public void loadDataFromFile(File file)
	{
		GridPane gp = (GridPane) rootLayout.getCenter();
		gp.getChildren().clear();
		sourceFile.clear();
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null)
			{
				sourceFile.add(tempString);
			}
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}
		try
		{
			refreshCodeArea(0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return;
	}

	private void refreshCodeArea(int previousline) throws Exception
	{
		int line = 10;
		GridPane gp = (GridPane) rootLayout.getCenter();
		gp.getChildren().clear();
		for (int i = 0; i < line && i < sourceFile.size(); i++)
		{
			String currentLine = sourceFile.get(i + previousline);
			for (int j = 0; j < currentLine.length(); j++)
			{
				Label t = new Label(String.valueOf(currentLine.charAt(j)));
				GridPane.setHalignment(t, HPos.CENTER);
				gp.add(t, j, i);
			}
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
