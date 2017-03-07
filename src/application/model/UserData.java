/*
 * This class is created to store statistical data of user behaviors.
 * Preferences are not stored in this class.
 * */
package application.model;

public class UserData
{
	// will be written later.
	private static int inputNumber = 0;
	private static int wrongNumber = 0;
	private static int rightNumber = 0;// the num of typeable characters.
	
	private static int TimeUsed_second = 0;

	public static void resetUserData()
	{
		inputNumber = 0;
		wrongNumber = 0;
		rightNumber = 0;
		TimeUsed_second = 0;
	}

	public static void setInputNumber(int num)
	{
		if (num < 0)
			return;
		inputNumber = num;
	}

	public static void setWrongNumber(int num)
	{
		if (num < 0)
			return;
		wrongNumber = num;
	}

	public static void setRightNumber(int num)
	{
		if (num < 0)
			return;
		rightNumber = num;
	}
	
	public static void setTime(int num)
	{
		if (num < 0)
			return;
		TimeUsed_second = num;
	}
	
	public static int getInputNumber()
	{
		return inputNumber;
	}

	public static int getWrongNumber()
	{
		return wrongNumber;
	}

	public static int getRightNumber()
	{
		return rightNumber;
	}
	
	public static int getTimeUsed()
	{
		return TimeUsed_second;
	}
}
