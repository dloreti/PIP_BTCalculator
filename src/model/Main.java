package model;

public class Main {

	public static void main(String[] args) 
	{
		int[] job1 = {0,2,0,4,0,1,0,0,0,0,0,0,0};
		int[] job2 = {0,1,4,4,4,4,4,4,4,4,4,1,0};
		int[] job3 = {0,1,1,1,1,1,1,1,1,0,0,0,0};
		int[] job4 = {0,2,3,2,0};
		int[] job5 = {0,4,2,4,4,0};
		int[] job6 = {0,3,3,0};
		processRT[] t = new processRT[6];
		
		
		t[0] =  new processRT(0, 0, 0, job1, 6, 0, null);
		t[1] =  new processRT(0, 0, 0, job2, 5, 0, null);
		t[2] =  new processRT(0, 0, 0, job3, 4, 0, null);
		t[3] =  new processRT(0, 0, 0, job4, 3, 0, null);
		t[4] =  new processRT(0, 0, 0, job5, 2, 0, null);
		t[5] =  new processRT(0, 0, 0, job6, 1, 0, null);
		Notation nt = new Notation(t,5);
		BLPmodel blpm = new BLPmodel(nt,t);
	//visualizzazione Risorse
		System.out.println("Resources");
		for(int j = 0; j< t.length; j++)
		{
			for(int i = 0; i < t[j].getResource().length; i++)
			{
				System.out.print(t[j].getResource()[i] + " ");
				
			}
			System.out.println("");
		}
		
//		System.out.println("");
//		System.out.println("gammaMWithDeltam");
//		int[][] result = nt.deltaMatrix();
//		for(int j = 0; j<result.length; j++)
//		{
//			
//			
//			for(int i = 0; i < result[j].length; i++)
//			{
//				System.out.print(result[j][i] + " ");
//				
//			}
//			System.out.println("");
//			
//			
//		}
//		
//		
//		System.out.println("");
//		System.out.println("sigmam");
//		for(int j = 0; j< t.length; j++)
//		{
//			int[] result2 = nt.sigmam(t[j]);
//			for(int i = 0; i < result2.length; i++)
//			{
//				System.out.print(result2[i] + " ");
//				
//			}
//			System.out.println("");
//			
//			
//		}
//		
//		
//		System.out.println("");
//		System.out.println("psiMstar");
//		int [] res = nt.psiMstar(2);
//		for (int i = 0; i < res.length; i++)
//		{
//			System.out.print(res[i] + " ");
//		}
//		
//		System.out.println("");
//		System.out.println("\nphiMhlstar");
//		String[] r = nt.phiMhlstar(2, 3);
//		for (int i = 0; i < r.length; i++)
//		{
//			System.out.print(r[i] + " ");
//		}
//		
//		System.out.println("");
//		System.out.println("\nphiMlstarm");
//		String[] s = nt.phiMhstarm(2, 1);
//		for (int i = 0; i < s.length; i++)
//		{
//			System.out.print(s[i] + " ");
//		}
//		
//		System.out.println("");
//		System.out.println("\nphiMlstarstar");
//		String [] u = nt.phiMhstarstar(2);
//		for (int i = 0; i < u.length; i++)
//		{
//			System.out.print(u[i] + " ");
//		}
//
//		System.out.println("");
//		System.out.println("\n phimhlm");
//		String [] um = nt.phiMhlm(2,5,4);
//		for (int i = 0; i < um.length; i++)
//		{
//			System.out.print(um[i] + " ");
//		}
//		
//
		System.out.println("");
		System.out.println("\n thetahlm");
		String [] ud = nt.thetaMhlm(1,5,4);
		for (int i = 0; i < ud.length; i++)
		{
			System.out.print(ud[i] + " ");
		}
//		
//
		System.out.println("");
		System.out.println("\n phimhnm");
		String [] um2 = nt.phiMhnm(1,4,2);
		for (int i = 0; i < um2.length; i++)
		{
			System.out.print(um2[i] + " ");
		}
		

		System.out.println("");
		System.out.println("\n cost");
		int [] bp = blpm.costfunction(5);
		for (int i = 0; i < bp.length; i++)
		{
			System.out.print(bp[i] + " ");
		}
		
		System.out.println("");
		System.out.println("first sum");
		int[][] result2 = blpm.firstSum(5);
		for(int j = 0; j<result2.length; j++)
		{
			
			
			for(int i = 0; i < result2[j].length; i++)
			{
				System.out.print(result2[j][i] + " ");
				
			}
			System.out.println("");
			
			
		}
		
		
		System.out.println("");
		System.out.println("sec sum");
		int[][] result3 = blpm.secondSum(5);
		for(int j = 0; j<result3.length; j++)
		{
			
			
			for(int i = 0; i < result3[j].length; i++)
			{
				System.out.print(result3[j][i] + " ");
				
			}
			System.out.println("");
			
			
		}

		System.out.println("");
		System.out.println("th sum");
		
		int[][] resul4 = blpm.thirdSum(5);
		for(int j = 0; j<resul4.length; j++)
		{
			
			
			for(int i = 0; i < resul4[j].length; i++)
			{
				System.out.print(resul4[j][i] + " ");
				
			}
			System.out.println("");
			
			
		}
		int[] cost = blpm.costfunction(1);
		int[][] sum1 = blpm.firstSum(1);
		int[][] sum2 = blpm.secondSum(1);
		int[][] sum3 = blpm.thirdSum(1);
			Resolver.solveMe( cost, sum1, sum2,sum3);
		
		
	}

}
