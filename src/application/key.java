package application;
//import java.util.*;

//import javafx.scene.input.KeyCode;

//import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class key
{
	static char keyBoardInput;
	static int keyCode;

	public char getKeyboardInput()
	{
		if (keyBoardInput >= 32 && keyBoardInput <= 126)
			return keyBoardInput;
		else if (keyCode == 0x9)
			return '\t';
		else if (keyCode == 10)
			return '\n';

		return 0;

	}

	private class KeyEvents extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			String s = "你按下的键是：" + e.getKeyChar();
			s += "\n这个键对应的编码是：" + e.getKeyCode();

			keyBoardInput = e.getKeyChar();
			keyCode = e.getKeyCode();

		}

		@Override
		public void keyReleased(KeyEvent e)
		{

		}
	}
	 public static void main(String[] args) {
		//KeyEvents ke = new KeyEvents();
		
	}
}
