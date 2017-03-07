package application;

import application.model.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class VirtualCursor
{
	ToolPacks toolPacks = new ToolPacks();
	private static int row = 0;
	private static int column = 0;

	private static int thisPage_line = 0;

	private static GridPane gridPane;
	
	// When the first key is typed, start the timer.
	public static boolean isFirstStrike = true;

	public static boolean isInputCorrect = true;

	// lock the cursor to prevent it from moving
	public static boolean CursorLock = false;

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
		CursorLock = false;
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
		// If the cursor is locked, refuse to move.
		if (CursorLock)
		{
			System.out.println("Fail to move cursor: The cursor is locked.");
			return;
		}
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
				toolPacks.endOfPage();
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
		// If the cursor is locked, refuse to move.
		if (CursorLock)
		{
			System.out.println("Fail to move cursor backforward: The cursor is locked.");
			return;
		}
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
		return ToolPacks.getNodeByRowColumnIndex(row, column, gp);
	}

	public Node getNodeByRowColumnIndex(final int row, final int column)
	{
		return ToolPacks.getNodeByRowColumnIndex(row, column, gridPane);
	}

	public void ignoreBlank()
	{
		if (!Settings.ignoreBlanks())
			return;
		while (getCurrentElement(gridPane) != null && ((Label) getCurrentElement(gridPane)).getText().charAt(0) == ' ')
		{
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

}
