package application;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
//import javafx.scene.text.Font;

import application.model.*;

public class VirtualCursor
{
	private static int row = 0;
	private static int column = 0;

	public static int thisPage_line;

	private static GridPane gridPane;

	public VirtualCursor(GridPane gp)
	{
		this.gridPane = gp;
	}

	public void resetCursorLocation()
	{
		row = 0;
		column = 0;
		getNodeByRowColumnIndex(row, column, gridPane).setStyle("-fx-background-color:green");
	}

	public static int getLocation_row()
	{
		return row;
	}

	public void setLocation_row(int row)
	{
		this.row = row;
	}

	public static int getLocation_col()
	{
		return column;
	}

	public void setLocation_col(int column)
	{
		this.column = column;
	}
	
	public boolean endOfLine()
	{
		return getNodeByRowColumnIndex(getLocation_row(), getLocation_col() + 1) == null;
	}
	
	public void removeCursor()
	{
		Node temp = getNodeByRowColumnIndex(getLocation_row(), getLocation_col());
		temp.setStyle("-fx-background-color: transparent");
	}
	
	public void showCursor()
	{
		Node temp = getNodeByRowColumnIndex(getLocation_row(), getLocation_col());
		temp.setStyle("-fx-background-color:green");
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

	public static void setCursorLocation(GridPane gp, final int row, final int column, boolean isInputCorrect)
	{
		if (isInputCorrect)
		{
			VirtualCursor.getNodeByRowColumnIndex(row, column, gp).setStyle("-fx-background-color:green");
		}
		else
		{
			VirtualCursor.getNodeByRowColumnIndex(row, column, gp).setStyle("-fx-background-color:red");
		}
	}
	
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
			if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column)
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
			if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column)
			{
				result = node;
				break;
			}
		}

		return result;
	}

	public void ignoreBlank()
	{
		Label current = (Label) getNodeByRowColumnIndex(getLocation_row(), getLocation_col() + 1, gridPane);
		while (current != null && current.getText().charAt(0) == ' ')
		{
			column++;
		}
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
