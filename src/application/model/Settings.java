//This class is used to store user settings.
package application.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class Settings
{
	public Settings()
	{
		Settings.loadProperties();
	}

	// the file that stores global preferences
	static String profilepath = "preferences.data";
	private static Properties props = new Properties();
	private static ArrayList<String> PreferenceList = new ArrayList<String>();
	// private static ArrayList<String> ValueList = new ArrayList<String>();
	static
	{
		PreferenceList.add("linePerPage");
		PreferenceList.add("TABINC");
		PreferenceList.add("ignoreBlanks");
		PreferenceList.add("ignoreComment");
	}

	public static int getTABINC()
	{
		return Integer.parseInt(props.getProperty("TABINC"));
	}

	public static void setTABINC(int tabinc)
	{
		if (tabinc > 0)
		{
			props.setProperty("TABINC", String.valueOf(tabinc));
		}
	}

	public static int getLine()
	{
		return Integer.parseInt(props.getProperty("linePerPage"));
	}

	public static void setLine(int line)
	{
		if (line > 0)
		{
			props.setProperty("linePerPage", String.valueOf(line));
		}
	}

	public static void addLine()
	{
		props.setProperty("linePerPage", String.valueOf(getLine() + 1));
	}

	public static boolean ignoreBlanks()
	{
		return Boolean.valueOf(props.getProperty("ignoreBlanks"));
	}

	public static void ignoreBlanks_set(boolean b)
	{
		props.setProperty("ignoreBlanks", String.valueOf(b));
	}

	public static void loadDefaultProperties()
	{
		props.setProperty("linePerPage", "15");
		props.setProperty("TABINC", "4");
		props.setProperty("ignoreBlanks", "true");
	}

	public static void loadProperties()
	{
		// ValueList.clear();
		try
		{
			props.load(new FileInputStream(profilepath));
		}
		catch (FileNotFoundException e)
		{
			loadDefaultProperties();
			saveProperties();
			e.printStackTrace();
			// System.exit(-1);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static void saveProperties()
	{
		try
		{
			File file = new File(profilepath);
			if (!file.exists())
			{
				file.createNewFile();
			}
			FileOutputStream oFile = new FileOutputStream(file);
			props.store(oFile, "Global Settings");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
