package application;

import java.io.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import application.view.*;

public class Main extends Application
{
	private Stage primaryStage;
	private BorderPane rootLayout;
	
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
		// For test only. I'll rewrite this function later.
		GridPane gp = (GridPane)rootLayout.getCenter();
		gp.getChildren().clear();
		BufferedReader reader = null;
		try
		{// need to rewrite
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while ((tempString = reader.readLine()) != null)
			{
				Text t = new Text(tempString);
				gp.add(t, 0, line);
				System.out.println("line " + line + ": " + tempString);
				line++;
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
		return;
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
