package application;

// This class is written by Wang Zhaopu.
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.property.SimpleStringProperty;

public class KTimer
{
	private SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:S");
	private String[] split;
	private SimpleStringProperty sspTime;
	private static long time;
	private Timer t = new Timer("Metronome", true);
	private TimerTask tt;
	boolean timing = false;

	/*
	 * //check public static void main(String[] args) throws
	 * InterruptedException { // TODO Auto-generated method stub KTimer k = new
	 * KTimer(); k.startTimer(0); for(int i = 0;i < 1000;i++) {
	 * Thread.currentThread().sleep(100);
	 * System.out.println(k.getSspTime().get()); } }
	 */

	public KTimer()
	{
		sspTime = new SimpleStringProperty("00:00:00");
	}

	// the stoptimer starts to work
	public void startTimer(final long time)
	{
		this.time = time;
		timing = true;
		tt = new TimerTask()
		{
			public void run()
			{
				if (!timing)
				{
					try
					{
						tt.cancel();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					updateTime();
				}
			}
		};
		t.scheduleAtFixedRate(tt, 10, 10);
	}

	public synchronized void stopTimer()
	{
		timing = false;
	}

	// reset the stoptimer to "00:00:00"
	public synchronized void stopit()
	{
		this.time = 0;
		this.sspTime = new SimpleStringProperty("00:00:00");
		tt.cancel();
	}

	public synchronized void updateTime()
	{
		this.time = this.time + 10;
		split = sdf.format(new Date(this.time)).split(":");
		sspTime.set(
				split[0] + ":" + split[1] + ":" + (split[2].length() == 1 ? "0" + split[2] : split[2].substring(0, 2)));
	}

	/*
	 * public synchronized void moveToTime(long time) { stopTimer(); this.time =
	 * time; split = sdf.format(new Date(time)).split(":"); sspTime.set(split[0]
	 * + ":" + split[1] + ":" + (split[2].length() == 1 ? "0" + split[2] :
	 * split[2].substring(0, 2))); }
	 */
	public static synchronized long getTime()
	{
		return time;
	}

	public synchronized SimpleStringProperty getSspTime()
	{
		return sspTime;
	}
}