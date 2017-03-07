/*
 * This class is the main class of the whole application.
 * Methods in this file is for core functions only.
 * Be sure not to pile up all your functions here.
 * 
 * By Zhu Botong
 */

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
import javafx.scene.paint.Color;
import application.model.*;
import application.view.*;

public class Main extends Application
{
	Settings s = new Settings();
	private Stage primaryStage;
	private BorderPane rootLayout;
	// this ArrayList is to store the source file in lines
	private ArrayList<String> sourceFile = new ArrayList<String>();

	private int NumOfPage = 1;
	private int CurrentPage = 1;

	public void setCurrentPage(int page)
	{
		if (page < 0)
			page = 0;
		else if (page > NumOfPage)
			page = NumOfPage;
		CurrentPage = page;
	}

	public int getCurrentPage()
	{
		return CurrentPage;
	}

	public int getNumOfPage()
	{
		return NumOfPage;
	}

	public Stage getPrimaryStage()
	{
		return primaryStage;
	}

	public BorderPane getRootLayout()
	{
		return rootLayout;
	}

	public ArrayList<String> getSourceFile()
	{
		return sourceFile;
	}

	public GridPane getGridPane()
	{
		return (GridPane) rootLayout.getCenter();
		// return (GridPane)((Pane)rootLayout.getCenter()).getChildren();
	}

	@Override
	public void start(Stage primaryStage)
	{
		primaryStage.setOnCloseRequest(new WindowsCloseEvent(primaryStage));
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("typingCode - demo");

		initRootLayout();
	}

	public void initRootLayout()
	{
		try
		{
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Pane center = (Pane)rootLayout.getCenter();
			// center.getChildren().add(new GridPane());
			rootLayout.setCenter(new GridPane());
			// This code is to show the border of code area. Will be deleted if
			// necessary.
			getGridPane().setStyle("-fx-border-color:black");

			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);

			showWelcomeWords();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			// fix window size
			primaryStage.setResizable(false);
			primaryStage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void loadDataFromFile(File file)
	{
		GridPane gp = getGridPane();
		// clear the GridPane and FileReader to avoid conflicts
		gp.getChildren().clear();
		sourceFile.clear();
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null)
			{
				tempString += "\n";
				sourceFile.add(ToolPacks.TAB2BLANK(tempString));
				// code for debug only, please comment it off before release
				// System.out.print(tempString);
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
			NumOfPage = sourceFile.size() / Settings.getLine();
			if (sourceFile.size() % Settings.getLine() != 0)
				NumOfPage++;
			// System.out.println("Num of Page : " + NumOfPage);
		}
		showCurrentPage(CurrentPage);

		return;
	}

	public void showCurrentPage(int pageNumber)
	{

		pageNumber--;
		// System.out.println(Settings.getLine());
		GridPane gp = (GridPane) rootLayout.getCenter();
		gp.getChildren().clear();

		VirtualCursor cursor = new VirtualCursor(gp);
		cursor.clearThisPageLine();

		KeyboardListener KBlistener = new KeyboardListener();
		KBlistener.addKeyEvent(gp);
		KBlistener.setGridPane(gp);

		gp.requestFocus();
		for (int i = 0; i < Settings.getLine() && i + pageNumber * Settings.getLine() < sourceFile.size(); i++)
		{
			String currentLine = sourceFile.get(i + pageNumber * Settings.getLine());
			for (int j = 0; j < currentLine.length(); j++)
			{
				if (j == currentLine.length() - 1)
				{
					Label eol = new Label("  ");// eol means "end of line" 
					gp.add(eol, j, i);
					continue;
				}
				Label t = new Label(String.valueOf(currentLine.charAt(j)));
				t.setStyle("-fx-background-color: transparent");
				t.setTextFill(Color.BLACK);
				GridPane.setHalignment(t, HPos.CENTER);
				gp.add(t, j, i);
			}
			cursor.addNewThisPageLine();
		}
		cursor.resetCursorLocation();
		UserData.resetUserData();
	}

	private void showWelcomeWords()
	{
		sourceFile.clear();
		sourceFile.add("Welcome\n");
		sourceFile.add("Words");
		NumOfPage = 1;
		showCurrentPage(CurrentPage);
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
