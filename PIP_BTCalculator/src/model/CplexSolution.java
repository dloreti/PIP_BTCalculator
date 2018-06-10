package model;

public class CplexSolution {
	
	private String status;
	private double solValue;
	private int ncols;
	private double[] val;
	public CplexSolution(String status, double solValue, int ncols, double[] val)
	{
		this.status = status;
		this.ncols = ncols;
		this.solValue = solValue;
		this.val = val; 
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public int getSolutionValue()
	{
		Double d = new Double(solValue);
		return d.intValue();
	}
	public int getNumberOfColumns()
	{
		return ncols;
	}
	
	public int[] getValues()
	{
		int[] solution = new int[val.length];
		for(int i = 0; i< val.length; i++)
		{
		 Double d = new Double(val[i]);
		 solution[i] = d.intValue();
		}
		return solution;
	}
	
}
