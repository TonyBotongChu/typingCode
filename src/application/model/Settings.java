//This class is used to store user settings.
package application.model;

public class Settings
{
	private static int line = 15;//default setting
	private static int TABINC = 4;
	
	public static int getTABINC()
	{
		return TABINC;
	}
	
	public static void setTABINC(int tabinc)
	{
		if (tabinc > 0)
		{
			TABINC = tabinc;
		}
	}
	
	public static boolean ignoreBlanks = true;
		
	public static int getLine()
	{
		return line;
	}
	
	public static void setLine(int line)
	{
		Settings.line = line;
	}
	
	public static void addLine()
	{
		Settings.line++;
	}
}
