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
import application.model.Settings;
import application.view.*;

public class Main extends Application
{
	private Stage primaryStage;
	private BorderPane rootLayout;
	// this ArrayList is to store the source file in lines
	private ArrayList<String> sourceFile = new ArrayList<String>();

	//Settings settings = new Settings();

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

	@Override
	public void start(Stage primaryStage)
	{

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

			rootLayout.setCenter(new GridPane());
			// This code is to show the border of code area. Will be deleted if
			// necessary.
			rootLayout.getCenter().setStyle("-fx-border-color:black");

			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);

			showWelcomeWords();
			
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

	public void loadDataFromFile(File file)
	{
		GridPane gp = (GridPane) rootLayout.getCenter();
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
				// code for debug only, please commit it off before release
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
		}
		showCurrentPage(0);

		return;
	}

	private void showCurrentPage(int previousline)
	{// the int previousline is the num of lines already read by the program
		
		//System.out.println(Settings.getLine());
		GridPane gp = (GridPane) rootLayout.getCenter();
		gp.getChildren().clear();
		
		VirtualCursor cursor = new VirtualCursor(gp);
		cursor.clearThisPageLine();
		
		KeyboardListener KBlistener = new KeyboardListener();
		KBlistener.addKeyEvent(gp);
		KBlistener.setGridPane(gp);
		
		gp.requestFocus();
		for (int i = 0; i < Settings.getLine() && i + previousline < sourceFile.size(); i++)
		{
			String currentLine = sourceFile.get(i + previousline);
			for (int j = 0; j < currentLine.length(); j++)
			{
				if (j == currentLine.length()-1)
				{
					Label eol = new Label("  ");
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
	}
	
	private void showWelcomeWords()
	{
		sourceFile.clear();
		sourceFile.add("Welcome\n");
		sourceFile.add("Words");
		showCurrentPage(0);
	}
	
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
