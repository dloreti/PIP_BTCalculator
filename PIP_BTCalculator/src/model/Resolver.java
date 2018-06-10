package model;


	import ilog.concert.IloException;
import ilog.concert.IloLPMatrix;
import ilog.concert.IloLinearNumExpr;
	import ilog.concert.IloNumVar;
	import ilog.cplex.IloCplex;

	public class Resolver 
	{
		  static final double EPSZERO = 1.0E-10;
		  static double objvalue;
		public static CplexSolution[] solveMe(int[] cost, int[][] sum1, int[][] sum2, int[][] sum3) 
		{
			//String solution = "";
			CplexSolution[] result = null;
			try {
 
				IloCplex cplex = new IloCplex();
				//variables
				double[] delta = new double[cost.length];
				double[][] first= new double[sum1.length][cost.length];
				double[][] second= new double[sum2.length][cost.length];
				double[][] third = new double[sum3.length][cost.length];
				IloNumVar[] x = new IloNumVar[cost.length];
				for (int i = 0; i < cost.length; i++)
				{
					delta[i] = cost[i];
					x[i] = cplex.boolVar();
				}
				for (int i = 0; i < first.length; i++)
				{
					for(int j = 0; j < first[i].length; j++)
						first[i][j]= sum1[i][j];
				}
				for (int i = 0; i < second.length; i++)
				{
					for(int j = 0; j < second[i].length; j++)
						second[i][j]= sum2[i][j];
				}
				for (int i = 0; i < third.length; i++)
				{
					for(int j = 0; j < third[i].length; j++)
						third[i][j]= sum3[i][j];
				}
				//objective
				IloLinearNumExpr obj = cplex.linearNumExpr();
				for (int i = 0; i < cost.length; i++)
				{
					obj.addTerm(delta[i], x[i]);
				}
				cplex.addMaximize(obj);
				//constraints
				
				for(int i = 0; i < first.length; i++)
				{
					IloLinearNumExpr expr = cplex.linearNumExpr();
					expr.addTerms(x, first[i]);
					cplex.addLe(expr, 1);

				}
				
				for(int i = 0; i < second.length; i++)
				{
					IloLinearNumExpr expr = cplex.linearNumExpr();
					expr.addTerms(x, second[i]);
					cplex.addLe(expr, 1);

				}
				for(int i = 0; i < third.length; i++)
				{
					IloLinearNumExpr expr = cplex.linearNumExpr();
					expr.addTerms(x, third[i]);
					cplex.addLe(expr, 1);

				}
//				IloLinearNumExpr expr = cplex.linearNumExpr();
//				expr.addTerm(x[4], 1);
//				cplex.addEq(expr, 1);		
//				System.out.println(cplex.toString());			
//				 if ( cplex.solve() ) {
//				        //cplex.output().println("Solution status = " + cplex.getStatus());
//				        // solution += "Solution status = " + cplex.getStatus() + "\n";
//				        //cplex.output().println("Solution value  = " + cplex.getObjValue());
//				        //  solution += "Solution value  = " + cplex.getObjValue() + "\n\n";
//				        double[] val = cplex.getValues(x);
//				        int ncols = cplex.getNcols();
//				        for (int j = 0; j < ncols; ++j)
//				        {	
//				        	int index = j+1;
//				         // cplex.output().println("Column: " + j + " Value = " + val[j]);
//				         // solution+= "Column: " + index + " Value = " + val[j] + "\n";
//				        }
//				      
//				        
//				 }
				 cplex.solve();
				 cplex.exportModel("blp.sav");
			     cplex.end();			
				
			} 
			catch (IloException e) {
				
				e.printStackTrace();
			}
			 try {
				 //incapsulo il BLP in un MIP per trovare le altre soluzioni 
		         IloCplex cplex = new IloCplex();	       
		         cplex.importModel("blp.sav");
		         cplex.setParam(IloCplex.Param.MIP.Pool.RelGap, 0.0);
		         cplex.setParam(IloCplex.Param.MIP.Pool.Intensity, 4);
		       
		         if ( cplex.populate() ) {

		            IloLPMatrix lp = (IloLPMatrix)cplex.LPMatrixIterator().next();
		            int numsol = cplex.getSolnPoolNsolns();	            
		            result = new CplexSolution[numsol];		            
		            for (int i = 0; i < numsol; i++) {		            	
		                double[] x = cplex.getValues(lp, i);
		                result[i]= new CplexSolution(cplex.getStatus().toString(), cplex.getObjValue(i),cplex.getNcols(),x);		            
		            }
		         }
		         cplex.end();
		      }
		      catch (IloException e) {
		         System.err.println("Concert exception caught: " + e);
		      }
			 
			 
			
			return result;
			
			
		}
	}