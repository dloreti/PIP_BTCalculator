package model;

import java.util.Arrays;

public class Notation
{
	private processRT [] tasks;
	private Semaphore [] sp;	
	public Notation (processRT[] task, int sp)
	{
		tasks = task;
		this.sp = new Semaphore[sp];
		for (int i = 0; i < this.sp.length; i++)
			this.sp[i]= new Semaphore();
		calculatesp();
	
	}	
	int[] gammaMWithDeltam (processRT task)
	{
		int[] temp = new int[task.getResource().length];
		int j = 0;
		int prec = 0;
		int numPrec = 1;
		
		
		for(int i = 0; i < task.getResource().length; i++)
		{
			if (task.getResource()[i] == 0)
			{
				if (prec != 0)
				{
					temp[j] = numPrec;
					j++;
					prec =task.getResource()[i];
					numPrec = 1;
					
				}
			}
			else if (task.getResource()[i] > 0)
			{
				if (prec == 0)
				{
					prec = task.getResource()[i];
				}
				else if (task.getResource()[i] == prec)
				{
					numPrec++;
				}
				else 
				{
					temp[j] = numPrec;
					j++;
					prec =task.getResource()[i];
					numPrec = 1;
				}
				if (i == (task.getResource().length -1))
				{
					temp[j] = numPrec;
					j++;
				}
			}
			
			
		}
		int[] result = new int[j];
		for (int i = 0; i< j; i++)
		{
			result[i] = temp[i];
			
		}
	
		return result;
		
	}
	int[][] deltaMatrix()
	{
		int[][] deltas = new int[tasks.length][];
		for(int i = 0; i < tasks.length;i++)
		{
			deltas[i] = gammaMWithDeltam(tasks[i]);
			
		}
		return deltas;
	}
	int[] sigmam (processRT task)
	{
		int[] temp = new int[task.getResource().length];
		int j = 0;
		int prec = 0;
			
		for(int i = 0; i < task.getResource().length; i++)
		{
			if (task.getResource()[i] == 0)
			{
				if (prec != 0)
				{
					temp[j] = prec;
					j++;
					prec =task.getResource()[i];	
				}
			}
			else if (task.getResource()[i] > 0)
			{
				if (prec == 0 || task.getResource()[i] == prec)
				{
					prec = task.getResource()[i];
				}
				else 
				{
					temp[j] = prec;
					j++;
					prec =task.getResource()[i];
				}
				if (i == (task.getResource().length -1))
				{
					temp[j] = prec;
					j++;
				}
			}
			
			
		}
		int[] result = new int[j];
		for (int i = 0; i< j; i++)
		{
			result[i] = temp[i];
			
		}
		return result;
		
	}
	int[] psiM (int h, int l)
	{
		int[] nullres = new int[0];
		
		if (h > tasks.length || l < 1 || h > l)
		{
			
			return nullres;
			
		}
		int temph = h-1;
		int templ = l-1;
		
		int[] tempspl = sigmam(tasks[templ]);
		
		
		int[] tempRes = new int[tempspl.length];
		
		int count = 0 ;
		
		for (int i = 0; i < tempspl.length; i++ )
		{
		
						if (sp[tempspl[i]].getPC() >= tasks[temph].getPriority() )
			{
				
				tempRes[count] = tempspl[i];
				count++;
			}
			
		}
		int temp[] = new int[count];
		for (int i = 0; i < count; i++)
		{
			temp[i] = tempRes[i];
		}
		int[] result = rimuoviDuplicati(temp);
		Arrays.sort(result);
		return result;
	}
	int[] psiMstar(int h)
	{
		int [] temp = new int[0];
		for (int i = h; i < tasks.length; i++)
		{
			temp = addAll(temp,psiM(h,i));
			
		}
		int[] result = rimuoviDuplicati(temp);
		Arrays.sort(result);
		return result;
	}
	String[] phiMhlstar(int l, int h)
	{
		int[] phi = psiM (l, h);
		int [] sigma = sigmam(tasks[h-1]);
		String[] temp = new String[sigma.length ];
		int count = 0;
		for (int i = 0; i < phi.length; i++)
		{
			for(int j=0; j < sigma.length;j++)
			{				
				if(phi[i] == sigma[j])
				{
					
					int index = j+1;
					String partial = h + "," + index;
					temp[j] = partial;
					count++;
						
				}
			}
		}
		String[] result = new String[count];
		 count = -1;
		for(String s : temp) 
		{
		    if(s != null) { 
		        result[++count] = s;
		    }
		}
		return result;
		
	}
	String[] phiMhstarm(int h, int m)
	{
		int[] psi = psiMstar(h);
		int check = 0;
		int count = 0;
		int[][] sigmastar = new int[tasks.length][];
		String[] nullres = new String[0];

		for (int i = 0; i < tasks.length; i++ )
		{
			sigmastar[i] = this.sigmam(tasks[i]);
		}
		for(int i : psi )
		{
			if(m != i) 
			{
				check = 1;
				
			}
			else 
			{	
				check = 0;
				break;
			
			}	
		}
		
		if (check == 1)
		{
			return nullres;
		}
		for (int i = h; i < sigmastar.length; i++)
		{
		   for(int j = 0; j < sigmastar[i].length; j++)
		   {
			   if(m == sigmastar[i][j])
			   {
				   count++;
			   }
		   }
		}
		String[] result = new String[count];
		count = 0;
		for (int i = h; i < sigmastar.length; i++)
		{
		   for(int j = 0; j < sigmastar[i].length; j++)
		   {
			   if(m == sigmastar[i][j])
			   {
				 
				   int row = i+1;
				   int col = j+1;
				   
				   result[count]= row + "," + col ;
				   count++;
			   }
		   }
		}
		return result;
	}
	String[] phiMhstarstar(int h)
	{
		int[] psi = psiMstar(h);
		String [] temp = new String[0];

		for (int i= 0; i < psi.length; i++ )
		{
			
			
			
			temp = concat(temp,phiMhstarm(h,psi[i]));
			
			
		}
		Arrays.sort(temp);
		return temp;
	}	
	String[] phiMhlm (int h, int l, int m)
	{
		int[] psi = psiM(h,l);
		int check = 0;
		int count = 0;
		int[] sigma = this.sigmam(tasks[l-1]);
		String[] nullres = new String[0];
		for(int i : psi )
		{
			if(m != i) 
			{
				check = 1;
				
			}
			else 
			{	
				check = 0;
				break;
			
			}	
		}
		
		if (check == 1)
		{
			return nullres;
		}
		
		   for(int j = 0; j < sigma.length; j++)
		   {
			   if(m == sigma[j])
			   {
				   count++;
			   }
		   }
		
		String[] result = new String[count];
		count = 0;
		
		   for(int j = 0; j < sigma.length; j++)
		   {
			   if(m == sigma[j])
			   {
				 
				
				   int col = j+1;
				   
				   result[count]= l + "," + col ;
				   count++;
			   }
		   }
		
		return result;
		
	}
	String[] thetaMhlm(int h, int l, int m)
	{
		String[] phiMhlmresult = phiMhlm (h,l,m);
		String phimlm =   phiMhlmresult[0];
		String[] phistar = phiMhlstar(h,l);
		
		int phimlmpos = this.getPosizione(phimlm, phistar);
		
		int newarraylength = phistar.length - phimlmpos;
		String [] partialtheta = new String[newarraylength];
		for(int i = 0; i < newarraylength; i++)
		{
			partialtheta[i]= phistar[phimlmpos+i];
		}
		
		String[] result= sottraiArray(partialtheta, phiMhlmresult);
		
		return result;
		
	}
	String[] phiMhnm (int h, int n, int m)
	{
		int[] psi = psiMstar(h);
		int check = 0;
		int count = 0;
		int[][] sigmastar = new int[tasks.length][];
		String[] nullres = new String[0];
		for (int i = 0; i < tasks.length; i++ )
		{
			sigmastar[i] = this.sigmam(tasks[i]);
		}
		for(int i : psi )
		{
			if(m != i) 
			{
				check = 1;
				
			}
			else 
			{	
				check = 0;
				break;
			
			}	
		}

		if (check == 1)
		{

			return nullres;
		}
		for (int i = n; i < sigmastar.length; i++)
		{
		   for(int j = 0; j < sigmastar[i].length; j++)
		   {
			   if(m == sigmastar[i][j])
			   {
				   count++;
			   }
		   }
		}
		String[] result = new String[count];
		count = 0;
		for (int i = n; i < sigmastar.length; i++)
		{
		   for(int j = 0; j < sigmastar[i].length; j++)
		   {
			   if(m == sigmastar[i][j])
			   {
				 
				   int row = i+1;
				   int col = j+1;
				   
				   result[count]= row + "," + col ;
				   count++;
			   }
		   }
		}
		return result;
	}
	//utility
	private static int[] rimuoviDuplicati(int[] arr) {

	    int lungtot = arr.length;

	    for (int i = 0; i < lungtot; i++) {
	        for (int j = i + 1; j < lungtot; j++) {
	            if (arr[i] == arr[j]) {                  
	                int shiftLeft = j;
	                for (int k = j+1; k < lungtot; k++, shiftLeft++) {
	                    arr[shiftLeft] = arr[k];
	                }
	                lungtot--;
	                j--;
	            }
	        }
	    }

	    int[] whitelist = new int[lungtot];
	    for(int i = 0; i < lungtot; i++){
	        whitelist[i] = arr[i];
	    }
	    return whitelist;
	}	
	void calculatesp()
	{
		int[] res;
		for (int i = 0; i < tasks.length; i++)
		{ 
			res = sigmam(tasks[i]);

			for(int j = 0; j < sp.length; j++)
				
				for(int k = 0; k < res.length; k++)
					{
					//
					if(sp[res[k]].getPC() <= tasks[i].getPriority())
					sp[res[k]].setPC(tasks[i].getPriority());
					}
		}
		
	}
	void printsp()
	{
		for(int i = 1; i < sp.length; i++)
		{
			System.out.println("res S" + i + ":" + sp[i].getPC());
		}
	}
	private static int[] addAll(int[] array1, int[] array2) 
	  {
		            
		  int[] joinedArray = new int[array1.length + array2.length];
		  System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		  System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		  return joinedArray;
	 }
	public String[] concat(String[] array1, String[] array2)
	{
		String[] joinedArray = new String[array1.length + array2.length];
		  System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		  System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		  return joinedArray;
	}
	private int getPosizione (String stringa, String [] arrayListe)
	{
		int posizione = -1;
		for (int i=0; i<arrayListe.length;i++)
		{
			if (arrayListe[i].equals(stringa))
			{
				posizione = i;
			}
		}
		
		return posizione;
		
	}	
	private String[] sottraiArray(String[] minuendo, String[] sottraendo)
	{
		String[] temp = new String[minuendo.length - sottraendo.length];
		int element = 0;
		int k= 0;
		
		for (int i = 0; i < minuendo.length; i++)
		{
			element = getPosizione(minuendo[i], sottraendo);
			if (element == -1)
			{
				temp[k] = minuendo[i];
				k++;
			}
		}
		return temp;
	}
	public String[] removeNullelements(String[] array)
	{
		int cont=0;
		
		for (int i = 0; i < array.length; i++)
		{
			if(array[i] != null)
			{
				cont++;
			}
		}

		String[]strRes = new String[cont];
		cont=0;
		for (int i = 0; i < array.length; i++)
		{
			if(array[i] != null)
			{
				strRes[cont] = array[i];
				cont++;
			}
		}
		return strRes;
	}
	
}
