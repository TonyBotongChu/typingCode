//This class is used to store user settings.
package application.model;

public class Settings
{
	private static int line = 5;//default setting
		
	public static int getLine()
	{
		return line;
	}
	
	public static void setLine(int line)
	{
		Settings.line = line;
	}
}
