package model;


public class processRT 
{
	private int numberofP;
	private int arrival;
	private int priority;
	private int deadline;
	private int computationTime;
	private int[] jobs;
	@SuppressWarnings("unused")
	private int currentPriority;
	private String status;
	
	
	public processRT(int numberofP, int arrival, int deadline, int[] jobs, int priority, int computationTime, String status)
	{
		this.numberofP = numberofP;
		this.arrival = arrival;
		this.deadline = deadline;
		this.jobs = jobs;
		this.priority = priority;
		this.computationTime = computationTime;
		this.currentPriority = priority;
		this.status = status;
		
		
	}
	
	public int getNumberofP()
	{
		return this.numberofP;
	}
	
	public int getArrival()
	{
		return this.arrival;
	}
	
	public int getPriority()
	{
		return this.priority;
	}
	
	public int getDeadline()
	{
		return this.deadline;
	}
	
	public int getCtime()
	{
		return this.computationTime;
	}
	
	public   int[] getResource()
	{
		return this.jobs;
	}
	
	public int getCurrentPriority()
	{
		return this.getCurrentPriority();
	}
	
	public void setCurrentPriority(int cpriority)
	{
		this.currentPriority = cpriority;
	}
	
	public String getStatus()
	{ 
		return 	this.status; 
	}
	
	public String setStatus(String status)
	{ 
			return this.status = status; 
	}
	 

}
