package application;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import application.model.*;

//import application.model.*;

public class VirtualCursor
{
	private static int row = 0;
	private static int column = 0;

	private static int thisPage_line = 0;

	private static GridPane gridPane;
	
	public static boolean isInputCorrect = true;

	public VirtualCursor(GridPane gp)
	{
		VirtualCursor.gridPane = gp;
		//thisPage_line = 0;
	}
	
	public void addNewThisPageLine()
	{
		thisPage_line ++;
	}

	public void resetCursorLocation()
	{
		removeCursor();
		row = 0;
		column = 0;
		showCursor();
	}

	public static int getLocation_row()
	{
		return row;
	}

	public void setLocation_row(int row)
	{
		VirtualCursor.row = row;
	}

	public static int getLocation_col()
	{
		return column;
	}

	public void setLocation_col(int column)
	{
		VirtualCursor.column = column;
	}
	
	public void removeCursor()
	{
		//Node temp = getNodeByRowColumnIndex(getLocation_row(), getLocation_col());
		Node temp = getCurrentElement(gridPane);
		temp.setStyle("-fx-background-color: transparent");
		((Label)temp).setTextFill(Color.BLACK);
	}
	
	public void showCursor()
	{
		//Node temp = getNodeByRowColumnIndex(getLocation_row(), getLocation_col());
		Node temp = getCurrentElement(gridPane);
		temp.setStyle("-fx-background-color:green");
		((Label)temp).setTextFill(Color.WHITE);
	}

	public void moveCursor()
	{
		boolean isPageEnd = false;
		
		// System.out.println(temp);
		removeCursor();

		if (endOfLine())
		{
			row++;
			// System.out.println("row"+row);
			// System.out.println("line"+thisPage_line);
			if (row >= Settings.getLine() || row >= thisPage_line)
			{
				isPageEnd = true;
				endOfPage();
			}
			column = 0;
		}
		else
		{
			column++;
		}
		if (!isPageEnd)
			showCursor();
	}

	public void moveCursor_backforward()
	{
		// to be written later

		removeCursor();
		if (getLocation_col() <= 0)
		{
			if (getLocation_row() <= 0)
			{
				showCursor();
				return;
			}
			else
			{
				row--;
				column = 0;
				while (getNodeByRowColumnIndex(getLocation_row(), getLocation_col() + 1) != null)
				{
					column++;
				}
			}
		}
		else
		{
			column--;
		}
		showCursor();
	}

//	public static void setCursorLocation(GridPane gp, int row, int column, boolean isInputCorrect)
//	{//need to think about what will happen if row or column is larger than the children in GridPane
//		//Will fix it later
//		
//		
//		if (row < 0)
//			row = 0;
//		if (column < 0)
//			column = 0;
//		VirtualCursor.row = row;
//		VirtualCursor.column = column;
//		
//		if (isInputCorrect)
//		{
//			VirtualCursor.getNodeByRowColumnIndex(row, column, gp).setStyle("-fx-background-color:green");
//		}
//		else
//		{
//			VirtualCursor.getNodeByRowColumnIndex(row, column, gp).setStyle("-fx-background-color:red");
//		}
//	}

	public static Node getCurrentElement(GridPane gp)
	{
		return getNodeByRowColumnIndex(row, column, gp);
	}

	public Node getNodeByRowColumnIndex(final int row, final int column)
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

	public void ignoreBlank()
	{
		if (!Settings.ignoreBlanks)
			return;
		Label current = (Label) getCurrentElement(gridPane);
		while (current != null && current.getText().charAt(0) == ' ')
		{
			moveCursor();
		}
	}
	
	public boolean endOfLine()
	{
		return getNodeByRowColumnIndex(getLocation_row(), getLocation_col() + 1) == null;
	}

	public void endOfPage()
	{
		// do something...
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Caution");
		alert.setHeaderText(null);
		alert.setContentText("End of File!");

		alert.showAndWait();
		// System.exit(0);
	}

}
