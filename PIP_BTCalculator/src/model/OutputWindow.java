package model;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import java.awt.Font;


public class OutputWindow {

	private static JFrame frame;

	/**
	 * Launch the application.
	 */
	public void go() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OutputWindow(int[] cost, int[][] sum1, int[][] sum2, int[][] sum3, int pedix[][]) {
		initialize(cost,sum1,sum2,sum3,pedix);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int[] cost, int[][] sum1, int[][] sum2, int[][] sum3, int pedix[][]) {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 600);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		CplexSolution[] solution = Resolver.solveMe(cost, sum1, sum2, sum3);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Times New Roman", Font.BOLD, 18));
		textArea.setBackground(UIManager.getColor("Button.background"));
		textArea.setBounds(12, 13, 500, 525);
		
		String sol = "";
		if(solution ==null)
			sol += "No Solution\n";
		else
			for (int i = 0;i < solution.length; i++)
			{
				sol += toStringResolver(solution[i], pedix);
				sol+= "\n";
			}
	
		textArea.setText(outputString(cost,sum1,sum2,sum3,pedix) + "\n\n" + sol) ;
		JScrollPane scrollPane = new JScrollPane(textArea);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		
		
		
		
	}
	
	private String outputString(int[] cost, int[][]sum1,int[][]sum2,int[][]sum3, int[][] pedix)
	{
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
	
	private String toStringResolver(CplexSolution solution,int[][] pedix)
	{
		String result ="Solution status:" +  solution.getStatus() +"\n";
		Integer solValue = new Integer(solution.getSolutionValue());
		result += "Solution value: " + solValue.toString();
		int ncols = solution.getNumberOfColumns();
		int[] values = solution.getValues();
		result += "\n\nValues:\n";
		for(int i = 0; i < ncols; i++)
		{
			result += "x" + generateSubscript(pedix[i][0]) + "\u208B" +generateSubscript(+pedix[i][1]) + "= " +  values[i] + ";\n";
			
		}
		
		
		return result;
	}
	public String generateSubscript(int i) {
	    StringBuilder sb = new StringBuilder();
	    for (char ch : String.valueOf(i).toCharArray()) {
	        sb.append((char) ('\u2080' + (ch - '0')));
	    }
	    return sb.toString();
	}
	
}
