package application;

import application.model.Settings;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.*;

public class VirtualCursor
{
	private int row;
	private int column;

	private GridPane gridPane;

	public VirtualCursor(GridPane gridPane)
	{
		this.gridPane = gridPane;
		resetCursorLocation();
	}

	public void resetCursorLocation()
	{
		row = 0;
		column = 0;
		getNodeByRowColumnIndex(row, column, gridPane).setStyle("-fx-background-color:green");
	}

	public int getLocation_row()
	{
		return row;
	}

	public void setLocation_row(int row)
	{
		this.row = row;
	}

	public int getLocation_col()
	{
		return column;
	}

	public void setLocation_col(int column)
	{
		this.column = column;
	}
	
	private void moveCursor()
	{
		// to be written later
		Node temp = getNodeByRowColumnIndex(getLocation_row(), getLocation_col());
		temp.setStyle("");
		if (temp.getAccessibleText().charAt(0) == '\n')
		{
			row++;
			if (row >= Settings.getLine())
			{
				endOfPage();
			}
			column = 0;
		}
		else
		{
			column++;
		}
		getNodeByRowColumnIndex(row, column, gridPane).setStyle("-fx-background-color:green");
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

	public void endOfPage()
	{
		// do something...
	}

}
