// This class is written by Wang Zhaopu. Thanks a lot!
package application;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import application.model.*;

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
	
	private char echar;
	
	private void moveCursor(String labelText)
	{
		if (labelText.length() <= 0 || keyValue.length() <= 0)
			return;
		VirtualCursor cursor = new VirtualCursor(gp);
		if (keyValue.length() == 1)
		{
			// if the length of keyValue is equal to 1, it means that the user
			// typed a original character
			if (keyValue.charAt(0) == labelText.charAt(0))
			{
				// if this charactor is the same as that in the Label, just move
				// the cursor, or the program will do nothing.
				cursor.moveCursor();
				if(echar!=' ')
					{UserData.setRightNumber(UserData.getRightNumber()+1);}
			}
			else
			{
				if(echar != ' ')
				{
					UserData.setWrongNumber(UserData.getWrongNumber()+1);
				}
			}
			
		}
		else
		{
			// if the length of keyValue is not equal to 1, it means that the
			// user typed keys like ENTER, BACKSPACE, TAB, etc.
			// System.out.println(keyValue.length());
			if (keyValue.equals("ENTER"))
			{
				if (cursor.endOfLine())
					cursor.moveCursor();
			}
			else if (keyValue.equals("BACK_SPACE"))
			{
				// if the user typed BACKSPACE, move the cursor backforward
				cursor.moveCursor_backforward();
			}
		}
	}

	public void check()
	{
		// System.out.println("you pressed :" + keyValue);
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
		// automatically lock the focus
		tf.focusedProperty().addListener(focusLossListener);
		tf.setOnKeyTyped(new EventHandler<KeyEvent>()
		{

			public void handle(KeyEvent event)
			{

				char[] a = event.getCharacter().toCharArray();
				char keyChar = a[0];
				
				
				if (keyChar > 31 && keyChar < 127)
				{
					keyValue = event.getCharacter();
					echar = keyChar;
					if(echar!=' '){
						UserData.setInputNumber(UserData.getInputNumber()+1);
					}
				}			
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
				{
					keyValue = "BACK_SPACE";
					if(UserData.getRightNumber() >0 )
					{
						if(keyValue!=" ")
							UserData.setRightNumber(UserData.getRightNumber()-1);
					}
						
				}
			}

		});

		tf.setOnKeyReleased(new EventHandler<KeyEvent>()
		{

			public void handle(KeyEvent event)
			{	
				//TODO
				//System.out.println(inputNumber + " " + rightNumber + " " + wrongNumber );
			}

		});
	}
}
