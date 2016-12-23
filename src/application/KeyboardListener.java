//This class is written by Wang Zhaopu. Thanks a lot!
package application;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class KeyboardListener
{
	private GridPane gp;

	public void setGridPane(GridPane gridPane)
	{
		gp = gridPane;
	}

	private String keyValue;

	public String getKeyValue()
	{
		return keyValue;
	}

	private void moveCursor(String labelText)
	{
		if (labelText.length() <= 0 || keyValue.length() <= 0)
			return;
		VirtualCursor cursor = new VirtualCursor(gp);
		if (keyValue.length() == 1)
		{
			if (keyValue.charAt(0) == labelText.charAt(0))
			{
				cursor.moveCursor();
			}
		}
		else
		{
			//System.out.println(keyValue.length());
			if (keyValue.equals("ENTER"))
			{
				if (cursor.endOfLine())
					cursor.moveCursor();
			}
			else if (keyValue.equals("BACK_SPACE"))
			{
				cursor.moveCursor_backforward();
			}
		}
	}

	public void check()
	{
		//System.out.println("you pressed :" + keyValue);
		String labelText = ((Label) VirtualCursor.getCurrentElement(gp)).getText();
		moveCursor(labelText);
	}

	public void addKeyEvent(Node tf)
	{
		ChangeListener<Boolean> focusLossListener = (observable, wasFocused, isFocused) ->
		{
			if (!isFocused)
			{
				tf.requestFocus();
			}
		};
		// lock the focus
		tf.focusedProperty().addListener(focusLossListener);
		tf.setOnKeyTyped(new EventHandler<KeyEvent>()
		{

			public void handle(KeyEvent event)
			{

				char[] a = event.getCharacter().toCharArray();
				char keyChar = a[0];
				if (keyChar > 31 && keyChar < 127)
					keyValue = event.getCharacter();

				// TODO
				check();

			}

		});

		tf.setOnKeyPressed(new EventHandler<KeyEvent>()
		{

			public void handle(KeyEvent event)
			{

				if (event.getCode() == KeyCode.ENTER)
					keyValue = "ENTER";
				else if (event.getCode() == KeyCode.TAB)
					keyValue = "TAB";
				else if (event.getCode() == KeyCode.BACK_SPACE)
					keyValue = "BACK_SPACE";
			}

		});

		tf.setOnKeyReleased(new EventHandler<KeyEvent>()
		{

			public void handle(KeyEvent event)
			{

			}

		});
	}
}
