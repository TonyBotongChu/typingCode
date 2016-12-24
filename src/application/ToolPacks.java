/*
 * This class is for tools that may be used by other classes in this package.
 * By Zhu Botong
 */
package application;

import application.model.Settings;

public class ToolPacks
{
	public static String TAB2BLANK(String source)
	{
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
}
