/*
 * This class is for tools that may be used by other classes in this package.
 * By Zhu Botong
 */
package application;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

import application.model.*;
import application.view.PageSummaryController;

public class ToolPacks
{
	public static String TAB2BLANK(String source)
	{
		// transfer tab to blanks, depending on Settings.getTABINC
		String s = "";
		for (int i = 0; i < source.length(); i++)
		{
			if (source.charAt(i) == '\t')
			{
				for (int j = 0; j < Settings.getTABINC(); j++)
				{
					s += " ";
				}
			}
			else
			{
				s += source.charAt(i);
			}
		}
		return s;
	}

	public static Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane)
	{
		Node result = null;
		ObservableList<Node> childrens = gridPane.getChildren();

		for (Node node : childrens)
		{
			if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column)
			{
				result = node;
				break;
			}
		}

		return result;
	}
	
	public void endOfPage()
	{
		// do something...
		VirtualCursor.CursorLock = true;
		showPageSummary();
		VirtualCursor.isFirstStrike = true;
	}
	
//	public static void showPageSummary()
//	{
//		Alert alert = new Alert(AlertType.INFORMATION);
//		alert.setTitle("Lesson Summary");
//		alert.setHeaderText(null);
//		String TypeableCharacters = "Typeable Characters: " + UserData.getRightNumber() + "\n";
//		String TypedCharacters = "Typed Characters: " + UserData.getInputNumber() + "\n";
//		String UnproductiveStroke = "Unproductive Keystroke Overhead: " + (int)(UserData.getWrongNumber()/UserData.getRightNumber()) + "\n";
//		String content = TypeableCharacters+TypedCharacters+UnproductiveStroke;
//		alert.setContentText(content);
//		
//		alert.showAndWait();
//	}
	
	public void showPageSummary()
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/PageSummaryLayout.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));  
            stage.show();
            PageSummaryController pcontroller = (PageSummaryController)fxmlLoader.getController();
            pcontroller.initSummaryWindow(KTimer.getTime());
            //pcontroller.FocusOnAnchorPane();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void endOfFile()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Caution");
		alert.setHeaderText(null);
		alert.setContentText("End of File!");

		alert.showAndWait();
	}
}
