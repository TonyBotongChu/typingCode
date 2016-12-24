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
		// thisPage_line = 0;
	}

	public void clearThisPageLine()
	{
		thisPage_line = 0;
	}

	public void addNewThisPageLine()
	{
		thisPage_line++;
	}

	public void resetCursorLocation()
	{
		removeCursor();
		row = 0;
		column = 0;
		newLine();
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
		// Node temp = getNodeByRowColumnIndex(getLocation_row(),
		// getLocation_col());
		Node temp = getCurrentElement(gridPane);
		if (temp != null)
		{
			temp.setStyle("-fx-background-color: transparent");
			((Label) temp).setTextFill(Color.BLACK);
		}
	}

	public void showCursor()
	{
		// Node temp = getNodeByRowColumnIndex(getLocation_row(),
		// getLocation_col());
		Node temp = getCurrentElement(gridPane);
		temp.setStyle("-fx-background-color:green");
		((Label) temp).setTextFill(Color.WHITE);
	}

	public void moveCursor()
	{
		boolean isPageEnd = false;

		// System.out.println(temp);
		removeCursor();

		if (endOfLine())
		{
			row++;
			column = 0;
			// System.out.println("row"+row);
			// System.out.println("line"+thisPage_line);
			if (row >= Settings.getLine() || row >= thisPage_line)
			{
				isPageEnd = true;
				endOfPage();
			}
			else
			{
				newLine();
			}
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
		while (getCurrentElement(gridPane) != null && ((Label) getCurrentElement(gridPane)).getText().charAt(0) == ' ')
		{
			// System.out.println("row:"+row);
			// System.out.println("col:"+column);
			moveCursor();
		}
	}

	public void newLine()
	{
		ignoreBlank();
	}

	public boolean endOfLine()
	{
		Label current = (Label) getCurrentElement(gridPane);
		if (current.getText().length() != 1)
			return true;
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
	}

}
