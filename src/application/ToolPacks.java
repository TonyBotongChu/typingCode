/*
 * This class is for tools that may be used by other classes in this package.
 * By Zhu Botong
 */
package application;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import application.model.*;

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
}
