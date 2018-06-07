package model;

public class Semaphore
{
	private int priorityCeiling;
	public Semaphore ()
	{
		this.priorityCeiling = -1;
	}
	public int getPC()
	{
		return this.priorityCeiling;
	}
	public void setPC(int pc)
	{
		this.priorityCeiling = pc;
	}
	
}
