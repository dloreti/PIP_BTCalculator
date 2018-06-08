package model;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import java.awt.Font;


public class SolutionExtractor {

	int[] cost; 
	int[][] sum1, sum2, sum3, pedix;
	StyledDocument docModel,docSolution;
	

	CplexSolution[] solution;
	/**
	 * Create the application.
	 */
	public SolutionExtractor(int[] cost, int[][] sum1, int[][] sum2, int[][] sum3, int pedix[][], StyledDocument docModel, StyledDocument docSolution) {
		this.cost=cost;
		this.sum1=sum1;
		this.sum2=sum2;
		this.sum3=sum3;
		this.pedix=pedix;
		this.docModel=docModel;
		this.docSolution=docSolution;
		
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		Style regular = docModel.addStyle("regular", def);
		StyleConstants.setFontFamily(def, "SansSerif");
		Style s = docModel.addStyle("small", regular);
		StyleConstants.setFontSize(s, 15);
		
		Style def2 = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		Style regular2 = docSolution.addStyle("regular", def2);
		Style s2 = docSolution.addStyle("small", regular2);
		StyleConstants.setFontSize(s2, 15);
		
		solve();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void solve() {

		solution = Resolver.solveMe(cost, sum1, sum2, sum3);

	}

	public int getSolutionValue() {
		if (solution==null)
			solve();
		if(solution==null)
			return 0;
		else return solution[0].getSolutionValue();
	}
	
	public int getSolutionNumber() {
		if (solution==null)
			solve();
		if(solution==null)
			return 0;
		return solution.length;
	}
	
	public int[] getSolution(int i) {
		if (solution==null)
			solve();
		if(solution.length <= i)
			return null;
		return solution[i].getValues();
	}
	
	
	public void modelToStringPro() throws BadLocationException { 		
		docModel.insertString(docModel.getLength(), "Cost Function\nmax(", docModel.getStyle("regular"));
		for( int i = 0; i < cost.length; i++){
			String costI ="";
			if (cost[i] != 1)
				costI = Integer.toString(cost[i]);
			if(i == 0) 
				docModel.insertString(docModel.getLength(), costI + "x", docModel.getStyle("regular"));
			else 
				docModel.insertString(docModel.getLength(), " +" + costI + "x" , docModel.getStyle("regular"));
			docModel.insertString(docModel.getLength(), generateSubscript(pedix[i][0])+","+generateSubscript(+pedix[i][1]) , docModel.getStyle("small"));
		}
		
		docModel.insertString(docModel.getLength(), ")\nFirst set of constraints\n", docModel.getStyle("regular"));
		//String[] sum1String = new String[sum1.length];
		for (int i = 0; i < sum1.length; i++){

			//sum1String[i]= "";
			int otherOnes=0;
			for(int j = 0; j < sum1[i].length; j++)			{
				if(sum1[i][j] == 1)	{
					if(otherOnes == 0)	{
						docModel.insertString(docModel.getLength(), "x", docModel.getStyle("regular"));
						otherOnes = 1;
					}else
						docModel.insertString(docModel.getLength(), " +x", docModel.getStyle("regular"));
					docModel.insertString(docModel.getLength(), generateSubscript(pedix[j][0])+","+generateSubscript(+pedix[j][1]) , docModel.getStyle("small"));
				}
			}
			if( otherOnes != 0 )
				docModel.insertString(docModel.getLength(), " \u2264 1\n", docModel.getStyle("regular"));
//			if(!sum1String[i].isEmpty())
//				finalString += sum1String[i] +"\n";
		}

		//String[] sum2String = new String[sum2.length];
		docModel.insertString(docModel.getLength(), "\nSecond set of constraints\n", docModel.getStyle("regular"));
		
		for (int i = 0; i < sum2.length; i++){
			int otherOnes=0;
			for(int j = 0; j < sum2[i].length; j++)	{
				if(sum2[i][j] == 1){
					if(otherOnes == 0){
						docModel.insertString(docModel.getLength(), "x", docModel.getStyle("regular"));
						otherOnes = 1;
					}
					else
						docModel.insertString(docModel.getLength(), " +x", docModel.getStyle("regular"));
					docModel.insertString(docModel.getLength(), generateSubscript(pedix[j][0])+","+generateSubscript(+pedix[j][1]) , docModel.getStyle("small"));
				}


			}
			if( otherOnes != 0 )
				docModel.insertString(docModel.getLength(), " \u2264 1\n", docModel.getStyle("regular"));
//			if(!sum2String[i].isEmpty())
//				finalString +=  sum2String[i]+ "\n";
		}

		//String[] sum3String = new String[sum3.length];
		//finalString+="Third set of constraints\n";
		docModel.insertString(docModel.getLength(), "\nThird set of constraints\n", docModel.getStyle("regular"));
		
		for (int i = 0; i < sum3.length; i++){
			int otherOnes=0;
			for(int j = 0; j < sum3[i].length; j++){
				if(sum3[i][j] == 1){
					if(otherOnes == 0){
						docModel.insertString(docModel.getLength(), "x", docModel.getStyle("regular"));
						otherOnes = 1;
					}
					else
						docModel.insertString(docModel.getLength(), " +x", docModel.getStyle("regular"));
					docModel.insertString(docModel.getLength(), generateSubscript(pedix[j][0])+","+generateSubscript(+pedix[j][1]) , docModel.getStyle("small"));

				}

			}

			if( otherOnes != 0 )
				docModel.insertString(docModel.getLength(), " \u2264 1\n", docModel.getStyle("regular"));
//			if(!sum3String[i].isEmpty())
//				finalString +=  sum3String[i]+"\n";
		}

		//return finalString;
	}
	
	
	public String modelToString() { 
	
		String finalString = "";
		String costString = "Cost Function\nmax(";


		for( int i = 0; i < cost.length; i++)
		{
			if(i == 0)
				costString += cost[i] + "x" + generateSubscript(pedix[i][0]) + "\u208B" +generateSubscript(+pedix[i][1]) ;
			else
				costString += " +" + cost[i] + "x" + generateSubscript(pedix[i][0]) + "\u208B" +generateSubscript(+pedix[i][1]) ;
		}
		costString += ")";
		finalString = costString;	
		String[] sum1String = new String[sum1.length];
		finalString+="\nFirst set of constraints\n";
		for (int i = 0; i < sum1.length; i++)
		{

			sum1String[i]= "";
			int otherOnes=0;
			for(int j = 0; j < sum1[i].length; j++)
			{
				if(sum1[i][j] == 1)
				{
					if(otherOnes == 0)
					{
						sum1String[i] +=  "x"+ generateSubscript(pedix[j][0]) +"\u208B" + generateSubscript(pedix[j][1]);
						otherOnes = 1;
					}
					else
						sum1String[i] += " +" + "x"+ generateSubscript(pedix[j][0]) +"\u208B" + generateSubscript(pedix[j][1]);
				}

			}
			if( otherOnes == 0 )
				sum1String[i] += "";
			else 
				sum1String[i] += " \u2264 1";
			if(!sum1String[i].isEmpty())
				finalString += sum1String[i] +"\n";
		}

		String[] sum2String = new String[sum2.length];
		finalString+="Second set of constraints\n";
		for (int i = 0; i < sum2.length; i++)
		{
			sum2String[i]= "";
			int otherOnes=0;
			for(int j = 0; j < sum2[i].length; j++)
			{
				if(sum2[i][j] == 1)
				{
					if(otherOnes == 0)
					{
						sum2String[i] +=  "x"+ generateSubscript(pedix[j][0]) +"\u208B" + generateSubscript(pedix[j][1]);
						otherOnes = 1;
					}
					else
						sum2String[i] += " +" + "x"+ generateSubscript(pedix[j][0]) +"\u208B" + generateSubscript(pedix[j][1]);
				}


			}
			if(otherOnes == 0)
			{
				sum2String[i] += "";
			}
			else
			{
				sum2String[i] += " \u2264 1";
			}
			if(!sum2String[i].isEmpty())
				finalString +=  sum2String[i]+ "\n";
		}

		String[] sum3String = new String[sum3.length];
		finalString+="Third set of constraints\n";
		for (int i = 0; i < sum3.length; i++)
		{

			sum3String[i]= "";
			int otherOnes=0;
			for(int j = 0; j < sum3[i].length; j++)
			{
				if(sum3[i][j] == 1)
				{
					if(otherOnes == 0)
					{
						sum3String[i] +=  "x"+ generateSubscript(pedix[j][0]) +"\u208B" + generateSubscript(pedix[j][1]);
						otherOnes = 1;
					}
					else
						sum3String[i] += " +" + "x"+ generateSubscript(pedix[j][0]) +"\u208B" + generateSubscript(pedix[j][1]);

				}

			}
			if(otherOnes == 0)
			{
				sum3String[i] += "";
			}
			else
			{
				sum3String[i] += " \u2264 1";
			}
			if(!sum3String[i].isEmpty())
				finalString +=  sum3String[i]+"\n";

		}

		return finalString;
	}

	public String solutionToString(int i){//(CplexSolution solution,int[][] pedix)
//		String result ="Solution status:" +  solution[i].getStatus() +"\n";
//		Integer solValue = new Integer(solution[i].getSolutionValue());
//		result += "Solution value: " + solValue.toString();
		
		String result ="Solution number "+(i+1)+":\n";
		
		int ncols = solution[i].getNumberOfColumns();
		int[] values = solution[i].getValues();
		//result += "Values:\n";
		for(int j = 0; j < ncols; j++){
			result += "x" + generateSubscript(pedix[j][0]) + "\u208B" +generateSubscript(+pedix[j][1]) + "= " +  values[j] + ";\n";
		}
		return result;
	}
	
	public void solutionToStringPro(int i) throws BadLocationException{
		
		//String result ="Solution number "+(i+1)+":\n";
		docSolution.insertString(docSolution.getLength(), "Solution number "+(i+1)+":\n" , docSolution.getStyle("regular"));
		
		int ncols = solution[i].getNumberOfColumns();
		int[] values = solution[i].getValues();
		//result += "Values:\n";
		for(int j = 0; j < ncols; j++){
			docSolution.insertString(docSolution.getLength(), "x", docSolution.getStyle("regular"));
			docSolution.insertString(docSolution.getLength(), generateSubscript(pedix[j][0])+","+generateSubscript(+pedix[j][1]) , docSolution.getStyle("small"));
			docSolution.insertString(docSolution.getLength(), "= " +  values[j] + ";\n", docSolution.getStyle("regular"));
			//result += "x" + generateSubscript(pedix[j][0]) + "\u208B" +generateSubscript(+pedix[j][1]) + "= " +  values[j] + ";\n";
		}
		//return result;
	}
	
	public String generateSubscript(int i) {
		StringBuilder sb = new StringBuilder();
		for (char ch : String.valueOf(i).toCharArray()) {
			sb.append((char) ('\u2080' + (ch - '0')));
		}
		return sb.toString();
	}

	public int[][] getPedix() {
		return pedix;
	}
}
