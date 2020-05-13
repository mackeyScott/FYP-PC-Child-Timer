package model;
/*The following is the model for the Unix Time Stamp. */
public class TimeStamp {
	private long time;
	public TimeStamp() {
		/*Init method
		 * -Creates the time in Unix TimeStamp form. */
		time = System.currentTimeMillis()/1000L;
	}
	public String getTimeString() {
		//Returns the time stamp as a string.
		String returnText = " ";
		return returnText.valueOf(time);
	}
	public long getTimeLong() {
		//Returns the time stamp as long.
		return time;
	}
	public void updateTime() {
		//Updates the time stamp to an updated one.
		time = System.currentTimeMillis()/1000L;
	}
	public Long initTime() {
		long init = System.currentTimeMillis();
		return init;
	}
	
	@Override
	public String toString() {
		//Returns the time stamp in a neat string.
		this.updateTime();
		String returningString = "Current TimeStamp["+ this.getTimeString() +"]";
		return returningString; 
	}
}
