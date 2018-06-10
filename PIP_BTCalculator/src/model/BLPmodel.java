package model;


public class BLPmodel
{
	private processRT[] tk;
	private Notation not;
	private int[][]deltaTasks;
	
	public BLPmodel(Notation not, processRT[] tk)
	{
		this.not = not;
		deltaTasks = not.deltaMatrix();
		this.tk=tk;
		
	}
	
	
	public int[] costfunction(int h)
	{
		String[] phiM= not.phiMhstarstar(h);
		int[] result = new int[phiM.length];
		
		for(int i = 0; i < phiM.length; i++)
		{
			int[] position = toInt(phiM[i]);
			result[i] = deltaTasks[position[0]-1][position[1]-1];
			
		}
		return result;
		
	}
	public int[][] firstSum(int h)
	{
		String[][] temp = new String[tk.length-h][];
		String[] phiM= not.phiMhstarstar(h);		
		
		int cont = 0;
		for(int i = h+1; i < tk.length; i++ )
		{
			
			temp[cont] = not.phiMhlstar(h, i);
			cont++;
			
		
		}
		cont=0;
		for (int i = 0; i < temp.length; i++)
		{
			if(temp[i] != null)
			{
				cont++;
			}
		}
		int[][] result= new int[cont][phiM.length];
		String[][] strRes = new String[cont][];
		cont=0;
		for (int i = 0; i < temp.length; i++)
		{
			if(temp[i] != null)
			{
				strRes[cont] = temp[i];
				cont++;
			}
		}
		
		for (int i = 0; i < strRes.length; i++)
		{
			for(int j = 0; j < phiM.length; j++)
			{	
				
				if(thereIs(strRes[i],phiM[j]))
				{
					result[i][j]= 1;
					continue;
				}
				else
					result [i][j]= 0;
				
			}
		}
		return  result;
		
	}
	public int[][] secondSum(int h)
	{
		
		String[] phiM= not.phiMhstarstar(h);
		int[]	mInvolved = not.psiMstar(h);//resources involved
		String[][] temp = new String[mInvolved.length][];
		//int cont = 0;
		 
		for(int i = 0; i < mInvolved.length; i++ )
		{
			temp[i] = not.phiMhstarm(h, mInvolved[i]);	
		
		}
		int[][] result= new int[temp.length][phiM.length];
		
		
		for (int i = 0; i < temp.length; i++)
		{
			for(int j = 0; j < phiM.length; j++)
			{	
				
				if(thereIs(temp[i],phiM[j]))
				{
					result[i][j]= 1;
					continue;
				}
				else
					result [i][j]= 0;
				
			}
		}
		return  result;
		
	}
	public int[][] thirdSum(int h)
	{
		
		int[] psil;
		
		int cont = tk.length - h;
		String[][] total = new String [100][];
		String[] theta;
		String[] phin;
		
		
		
		cont=0;
		
		for(int i = h +1; i <= tk.length; i++)
		{
			
			psil = not.psiM(h, i);
			for (int j = 0; j < psil.length; j++)
			{
				
				theta = not.thetaMhlm(h, i, psil[j]);

				phin = not.phiMhnm(h, i, psil[j]);

				
				if(theta.length > 0)
				{
					if(phin.length > 0 )
					{
						total[cont] = not.concat(theta, phin);
						cont++;
					}
					else
					{
						total[cont] = theta;
						cont++;
					}
				}
				else
				{
					if(phin.length >0)
					{
						total[cont]= phin;
						cont++;
					}
					
					
				}
				
				
				
			}
			
		}
		String[][] stringOut = new String[cont][];
		for (int i =0; i < cont; i++)
		{
			
				 stringOut[i] = total[i];
			
		}
		String[] phiM= not.phiMhstarstar(h);
		int[][] result= new int[stringOut.length][phiM.length];
		
		
		for (int i = 0; i < stringOut.length; i++)
		{
			for(int j = 0; j < phiM.length; j++)
			{	
				
				if(thereIs(stringOut[i],phiM[j]))
				{
					result[i][j]= 1;
					continue;
				}
				else
					result [i][j]= 0;
				
			}
			
		}
		
		return  result;
		
	}
	
	private int[] toInt(String gamma)
	{
		 
		String[] parts = gamma.split(",");
		int[] result = new int[2];
		
		result[0]= Integer.parseInt( parts[0]);
		result[1]= Integer.parseInt( parts[1]);
		return result;
		
	}
	
	public int[][] costfunctiongraphic(int h)
	{
		String[] phiM= not.phiMhstarstar(h);
		int[][] result = new int[phiM.length][2];
		
		for(int i = 0; i < phiM.length; i++)
		{
			result[i] = toInt(phiM[i]);			
		}
		return result;
		
	}
	
	private boolean thereIs(String[] array, String research)
	{
		boolean code = false;
		for(int i = 0; i < array.length;i++)
		{
			if(array[i].equals(research))
			{
				
				code = true;
				break;

			}
		}
		return code;
	}
	
}
