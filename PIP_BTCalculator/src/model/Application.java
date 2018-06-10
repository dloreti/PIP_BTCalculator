package model;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.JTextArea;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;

public class Application {

	private JFrame frame;
	private ButtonGroup groupRadioButtonTask;
	private JRadioButton[] rdbtnTask;
	private List<Color> col;
	private Color testC;
	private JButton[] colorButton;
	private Random rand = new Random();
	private int[][] timeLine;
	private JLabel[][] grid;
	private static final Font LABEL_FONT = new Font(Font.DIALOG, Font.PLAIN, 24);
	private static final int MAX=50;

	private static int currentDisplaySolution = 0 ;
	private SolutionExtractor solEx;

	private static String example_path =System.getProperty("user.dir"); //+File.separator+"example.txt";

	private JTextArea textArea;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 1112, 624);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));


		/*File configFile = new File(configfile_path);

		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);

			cplexjar_path = props.getProperty("cplexjar_path");
			cplexlib_path = props.getProperty("cplexlib_path");

			System.out.println("cplexjar_path is: " + cplexjar_path);
			System.out.println("cplexlib_path is: " + cplexlib_path);

			reader.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(frame,"An error occurred while loading the configuration file. File not found.");
			System.exit(1);
		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(frame,"An error occurred while reading the configuration file. ");
			System.exit(1);
		}

		//loading cplex bin
		System.setProperty( "java.library.path", cplexlib_path );

		Field fieldSysPath;
		try {
			fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
			fieldSysPath.setAccessible( true ); 
			fieldSysPath.set( null, null );
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame,"An error occurred while loading the configuration file. File not found.");
		} 
		 */

		System.err.println("java.library.path is : \n"+System.getProperty("java.library.path"));

		JLabel lblBlockingTimeCalculator = new JLabel("Blocking Time Calculator");
		frame.getContentPane().add(lblBlockingTimeCalculator, BorderLayout.NORTH);
		lblBlockingTimeCalculator.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblBlockingTimeCalculator.setHorizontalAlignment(SwingConstants.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		//panel declaration

		JPanel panel = new JPanel();
		panel.setBorder(null);
		scrollPane.setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));


		JPanel panelArea = new JPanel();
		panel.add(panelArea, BorderLayout.CENTER);
		panelArea.setLayout(new BorderLayout(0, 0));

		textArea = new JTextArea();
		textArea.setEnabled(false);
		textArea.setEditable(false);
		textArea.setFont(new Font("Arial Black", Font.BOLD, 18));
		panelArea.add(textArea);


		JPanel choosingPanel = new JPanel();
		panel.add(choosingPanel, BorderLayout.NORTH);
		choosingPanel.setLayout(new BorderLayout(0, 0));

		JPanel colorPanel = new JPanel();
		colorPanel.setBorder(null);
		choosingPanel.add(colorPanel, BorderLayout.SOUTH);

		JPanel panelLabels = new JPanel();

		//buttonDeclaration
		JButton btnEdit = new JButton("Edit");
		JButton btnCalc = new JButton("Calculate");	

		JPanel radioButtonPanel = new JPanel();
		panel.add(radioButtonPanel, BorderLayout.WEST);
		radioButtonPanel.setLayout(new BoxLayout(radioButtonPanel, BoxLayout.Y_AXIS));

		JPanel panel_last = new JPanel();
		panel.add(panel_last, BorderLayout.SOUTH);
		panel_last.setLayout(new BorderLayout(0, 0));


		JPanel panel_1 = new JPanel();
		panel_last.add(panel_1, BorderLayout.SOUTH);
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);

		JLabel tipsLabel = new JLabel("Tips:");
		panel_1.add(tipsLabel);
		tipsLabel.setEnabled(true);

		JLabel lblNewLabel = new JLabel("Load the cplex.jar module");
		lblNewLabel.setEnabled(true);
		panel_1.add(lblNewLabel);

		JSplitPane splitPane = new JSplitPane();
		//panel.add(splitPane, BorderLayout.CENTER);
		splitPane.setBorder(BorderFactory.createEmptyBorder());

		JPanel calcPanel = new JPanel();
		panel_last.add(calcPanel, BorderLayout.CENTER);

		JButton buttonLoad= new JButton("Load");
		//buttonLoad.setEnabled(false);
		buttonLoad.setHorizontalAlignment(SwingConstants.LEFT);
		calcPanel.add(buttonLoad);

		buttonLoad.addActionListener(new ActionListener() 
		{
			private BufferedReader reader;
			public void actionPerformed(ActionEvent arg) {
				JFileChooser c = new JFileChooser(example_path);
				// Demonstrate "Open" dialog:
				int rVal = c.showOpenDialog(frame);
				String text="";
				if (rVal == JFileChooser.APPROVE_OPTION) {
					example_path = c.getCurrentDirectory().toString()+ File.separator +c.getSelectedFile().getName();
					FileReader file;
					try {
						file = new FileReader(example_path);
						reader = new BufferedReader(file);
						String line = reader.readLine();
						while (line != null) {
							text += line +"\n";
							line = reader.readLine();
						}
					} catch (IOException e) {
						JOptionPane.showMessageDialog(frame,"An error occurred while loading the application file");
						e.printStackTrace();
					}
				}
				textArea.setText(text);
				panelArea.updateUI();
				panel.updateUI();
			}
		}
				);
		JButton buttonSave = new JButton("Save");		
		//buttonSave.setEnabled(false);
		buttonSave.setHorizontalAlignment(SwingConstants.LEFT);
		calcPanel.add(buttonSave);

		JButton buttonContinue = new JButton("Continue...");
		calcPanel.add(buttonContinue);
		buttonContinue.setHorizontalAlignment(SwingConstants.LEADING);
		buttonContinue.setToolTipText("Load colors and enable solution computation");
		//buttonContinue.setEnabled(false);

		/* enabling all back to test the config file*/
		lblNewLabel.setEnabled(true);
		lblNewLabel.setText("Insert a number for each resources (0 for normal execution), separated by a comma, each row defines a task (ex 0,1,4,1,0)");
		textArea.setEditable(true);
		textArea.setEnabled(true);
		buttonLoad.setEnabled(true);
		buttonSave.setEnabled(true);
		buttonContinue.setEnabled(true);

		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				JFileChooser c = new JFileChooser(example_path);
				String text=textArea.getText();
				int rVal = c.showSaveDialog(frame);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					example_path = c.getCurrentDirectory().toString() +File.separator + c.getSelectedFile().getName();
					BufferedWriter writer = null;
					try{
						String[] tasks;
						tasks = text.split("\n");
						writer = new BufferedWriter( new FileWriter( example_path ));
						for(int i = 0; i < tasks.length; i++){	
							writer.write(tasks[i]);
							writer.newLine();
						}
					}catch ( IOException e){
						JOptionPane.showMessageDialog(frame,"An error occurred while saving the application file");
						e.printStackTrace();
					}finally{
						try{ if ( writer != null) writer.close( );
						}catch ( IOException ex) {ex.printStackTrace();}
					}
				}

			}
		}
				);


		JPanel solPanel = new JPanel();
		solPanel.setLayout(new BorderLayout(0, 0));
		JPanel solPanelArea = new JPanel();
		solPanel.add(solPanelArea, BorderLayout.NORTH);
		solPanelArea.setLayout(new BorderLayout(0, 0));

		JLabel lblModel = new JLabel("Model:");
		solPanelArea.add(lblModel, BorderLayout.NORTH);
		lblModel.setHorizontalAlignment(SwingConstants.LEFT);
		lblModel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		splitPane.setRightComponent(solPanel);

		JPanel panel_2 = new JPanel();
		//buttonNextSol.setHorizontalAlignment(SwingConstants.LEADING);
		solPanel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		JLabel lblNumberOfSolutions = new JLabel("Number of solutions: ");
		solPanelArea.add(lblNumberOfSolutions, BorderLayout.SOUTH);
		lblNumberOfSolutions.setHorizontalAlignment(SwingConstants.LEFT);
		lblNumberOfSolutions.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JTextPane modelTextArea = new JTextPane();
		JScrollPane scrollPane_1 = new JScrollPane(modelTextArea);
		solPanelArea.add(scrollPane_1, BorderLayout.CENTER);
		scrollPane_1.setViewportView(modelTextArea);
		scrollPane_1.setPreferredSize(new Dimension(200, 200));
		modelTextArea.setEditable(false);

		StyledDocument docModel = modelTextArea.getStyledDocument();

		JLabel lblWorstCase = new JLabel("Worst-case blocking time:");
		lblWorstCase.setHorizontalAlignment(SwingConstants.LEFT);
		lblWorstCase.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_2.add(lblWorstCase, BorderLayout.NORTH);


		JTextPane solutionTextArea = new JTextPane();
		JScrollPane scrollPane_2 = new JScrollPane(solutionTextArea);
		panel_2.add(scrollPane_2, BorderLayout.CENTER);
		scrollPane_2.setViewportView(solutionTextArea);
		scrollPane_2.setPreferredSize(new Dimension(200, 200));
		solutionTextArea.setEditable(false);

		StyledDocument docSolution = solutionTextArea.getStyledDocument();


		buttonContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				loadColorPanel(colorPanel,calcPanel, buttonContinue);

				timeLine=generateTaskNumber(textArea.getText());
				int printi = 0, printj = 0;
				boolean check = false;
				for (int i = 0; i< timeLine.length;i++){
					for (int j = 0; j< timeLine[i].length; j++){
						if(timeLine[i][j] > getCurrentResources().size()-1){
							printi = i+1;
							printj = j+1;
							check = true;
							break;
						}
					}
					if(check == true) break;
				}
				if (check == true){
					JOptionPane.showMessageDialog(frame, "ERROR: \"" + timeLine[printi-1][printj-1] + "\" at ["+ printi + "," + printj +   "]   is not a valid resource" );
				}else{
					panel.remove(panelArea);
					panel.add(splitPane, BorderLayout.CENTER);
					//panel.remove(textArea);
					int maxTimeline=0;
					for (int i = 0; i < timeLine.length;i++){
						if(maxTimeline < timeLine[i].length){
							maxTimeline = timeLine[i].length;
						}
					}
					grid= new JLabel[timeLine.length][maxTimeline];

					panelLabels.removeAll();
					panelLabels.setLayout(null);
					panelLabels.setPreferredSize(new Dimension(500,500));


					int x = 0;
					int y = 0;
					for (int row = 0; row < grid.length; row++) {
						x=10;
						for (int colu = 0; colu < grid[row].length; colu++) {
							if (colu < timeLine[row].length){	
								grid[row][colu] = new JLabel(" "+ timeLine[row][colu]+" ", SwingConstants.CENTER);
								grid[row][colu].setSize(MAX,MAX);
								grid[row][colu].setOpaque(true);
								//grid[row][colu].setBackground(col[timeLine[row][colu]]);
								grid[row][colu].setBackground(col.get(timeLine[row][colu]));
								grid[row][colu].setFont(LABEL_FONT); 
								grid[row][colu].setBounds(0 + x,0 + y,MAX,MAX);
								grid[row][colu].setBorder(BorderFactory.createLineBorder(Color.black));
								panelLabels.add(grid[row][colu]);
							}else{
								grid[row][colu] = new JLabel("     ", SwingConstants.CENTER);
								grid[row][colu].setSize(new Dimension(MAX,MAX));
								grid[row][colu].setOpaque(true);
								grid[row][colu].setFont(LABEL_FONT); 
								grid[row][colu].setBounds(0 + x,0 + y,MAX,MAX);
								panelLabels.add(grid[row][colu]);
							}
							x= x+MAX;
						}
						y = y+MAX;    
					}


					//				priorityPanel.removeAll();
					radioButtonPanel.removeAll();

					rdbtnTask= new JRadioButton[timeLine.length];
					groupRadioButtonTask = new ButtonGroup();
					for(int i = 0; i < timeLine.length;i++)
					{

						int iprint= i+1;
						rdbtnTask[i] = new JRadioButton("Task" + iprint);
						rdbtnTask[i].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0){
								btnCalc.setText("Calculate");
							}});
						groupRadioButtonTask.add(rdbtnTask[i]);
						radioButtonPanel.add(Box.createVerticalStrut(15));
						radioButtonPanel.add(rdbtnTask[i]);
						radioButtonPanel.add(Box.createVerticalStrut(12));
					}


					lblNewLabel.setText("Use the upper buttons to choose the preferred colors for resources. Select the task and click Calculate to get the maximum blocking time");
					panelLabels.setPreferredSize(new Dimension(x,y));
					splitPane.setLeftComponent(panelLabels);
					calcPanel.removeAll();
					calcPanel.add(btnEdit);
					calcPanel.add(btnCalc);
					calcPanel.updateUI();
					panelLabels.updateUI();
					panel.updateUI();
					radioButtonPanel.updateUI();

					btnCalc.setText("Calculate");
					modelTextArea.setText("");
					solutionTextArea.setText("");
				}


			}
		});		


		btnEdit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)
			{
				panel.remove(panelLabels);
				panel.add(panelArea);
				calcPanel.removeAll();
				panel.remove(splitPane);

				calcPanel.add(buttonSave);
				calcPanel.add(buttonLoad);
				calcPanel.add(buttonContinue);
				lblNewLabel.setText("Insert a number for each resources (0 for normal execution), separated by a comma, each row defines a task (ex 0,1,4,1,0)");
				textArea.setEditable(true);
				textArea.setEnabled(true);
				buttonLoad.setEnabled(true);
				buttonSave.setEnabled(true);
				buttonContinue.setEnabled(true);

				colorPanel.removeAll();


				radioButtonPanel.removeAll();
				radioButtonPanel.updateUI();
				panel.updateUI();


			}
		}
				);

		btnCalc.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)
			{
				//panel.remove(btnCalc);
				//panel.updateUI();

				//				
				processRT[] t = new processRT[timeLine.length];	
				int taskcalculation=0;
				int priority = timeLine.length;
				for (int i = 0; i <timeLine.length; i++){
					t[i] =  new processRT(0, 0, 0, timeLine[i],priority, 0, null);
					priority--;
					if (rdbtnTask[i].isSelected()){
						taskcalculation=i;
					}
				}
				if (taskcalculation==0) rdbtnTask[0].setSelected(true);
				//output(t,Integer.parseInt(spinnerResources.getValue().toString())+1, taskcalculation +1);
				outputModel(t,getCurrentResources().size()+1, taskcalculation +1, modelTextArea,solutionTextArea,docModel, docSolution);
				modelTextArea.setCaretPosition(0);
				outputNextSolution(solutionTextArea);
				solutionTextArea.setCaretPosition(0);
				lblNumberOfSolutions.setText("Number of solutions: "+ ((solEx==null) ? "0" : solEx.getSolutionNumber() ) );
				lblWorstCase.setText("Worst-case blocking time:: "+((solEx==null) ? "0" : solEx.getSolutionValue() )  );
				//buttonNextSol.setEnabled(true);
				btnCalc.setText("Next solution");
				lblNewLabel.setText("Click on the Next solution button to see other solutions with the same worst case blocking time.");
			}

		}
				);		
	}

	private List<String>  getCurrentResources() {

		String[] tasks = textArea.getText().split("\n");
		List<String> resources = new ArrayList<String>() ;
		for (String tline : tasks) {
			String[] res = tline.split(",");
			for (String r : res) {
				if (!resources.contains(r)) {
					resources.add(r);
				}
			}
		}
		return resources;
	}

	private String[] removeNullelements(String[] array)
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

	@SuppressWarnings("unused")
	private static boolean isNumeric(String str)  
	{  
		try  
		{  
			int d = Integer.parseInt(str);  
		}catch(NumberFormatException nfe)  {  
			return false;  
		}  
		return true;  
	}

	private int[][] generateTaskNumber(String text){
		int[][] finalResult;
		String[] tasks;
		String[][] tasksAndRes;
		tasks = text.split("\n");
		tasks = removeNullelements(tasks);
		tasksAndRes = new String[tasks.length][];
		finalResult = new int[tasks.length][];
		for (int i = 0; i< tasks.length;i++){
			tasksAndRes[i]= tasks[i].split(",");
			finalResult[i] = new int[tasksAndRes[i].length];

		}

		for (int i = 0; i< tasksAndRes.length;i++){
			for (int j = 0; j< tasksAndRes[i].length;j++){
				if(isNumeric(tasksAndRes[i][j])){
					finalResult[i][j] = Integer.parseInt(tasksAndRes[i][j]);
				}else{
					int iprint = i+1;
					int jprint = j+1;
					JOptionPane.showMessageDialog(frame, "ERROR: \"" + tasksAndRes[i][j] + "\" at ["+ iprint + "," + jprint +   "]   is not a number" );
					return null;
				}

			}
		}

		return finalResult;
	}

	private void outputModel(processRT[] t, int numberOfS, int taskToBe, JTextPane modelTextArea,JTextPane solutionTextArea,StyledDocument docModel, StyledDocument docSolution)
	{
		modelTextArea.setText("");
		Notation nt = new Notation(t,numberOfS);
		if (nt.phiMhstarstar(taskToBe).length == 0 ) {
			modelTextArea.setText("No lower priority task can block task "+taskToBe);
			solutionTextArea.setText("");
			return;
		}

		BLPmodel blpm = new BLPmodel(nt,t);
		int[] cost = blpm.costfunction(taskToBe);
		int[][] sum1 = blpm.firstSum(taskToBe);
		int[][] sum2 = blpm.secondSum(taskToBe);
		int[][] sum3 = blpm.thirdSum(taskToBe);
		int[][] pedix = blpm.costfunctiongraphic(taskToBe);
		solEx = new SolutionExtractor(cost, sum1, sum2, sum3, pedix,docModel,docSolution);
		try {
			solEx.modelToStringPro();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		//modelTextArea.setText(solEx.modelToString());
	}

	private void outputNextSolution(JTextPane solutionTextArea){
		for (int row = 0; row < grid.length; row++) {
			for (int colu = 0; colu < grid[row].length; colu++) {
				if (colu < timeLine[row].length){	
					grid[row][colu].setBackground(col.get(timeLine[row][colu]));
					//grid[row][colu].setFont(LABEL_FONT);
				}
			}   
		}

		if (solEx==null || solEx.getSolutionNumber()==0)
			return;

		int solToGet=currentDisplaySolution % solEx.getSolutionNumber();
		if (solEx.getSolution(solToGet)==null){
			solutionTextArea.setText("");
			return;
		};
		int[] values =solEx.getSolution(solToGet);
		int [][] pedix = solEx.getPedix();
		for (int i = 0; i < values.length; i++) {
			Boolean foundFirst=false;
			//System.out.println("values["+i+"]="+values[i] + "--> x"+pedix[i][0]+"-"+pedix[i][1] +" timeLine[row].length="+timeLine[pedix[i][0]-1].length);
			int row =pedix[i][0]-1;
			int cs = 1;
			for (int colu = 0; colu < timeLine[row].length; colu++) {
				if ( foundFirst && colu>0 && timeLine[row][colu]!=timeLine[row][colu-1] ) {
					foundFirst=false; //fine sez critica
				}
				if (colu>0 
						&& timeLine[row][colu]!=timeLine[row][colu-1] 
								&& timeLine[row][colu-1]!=0)
					cs++;
				if (values[i] == 1 
						&& timeLine[row][colu]!=0 
						&& (colu==0 || timeLine[row][colu]!=timeLine[row][colu-1])
						&& cs == pedix[i][1]){	
					foundFirst=true;
					grid[row][colu].setBackground(new Color(255, 255, 255));
					//grid[row][colu].setFont(new Font(Font.DIALOG, Font.BOLD, 24));

				}
				//System.out.println("timeLine["+row+"]["+colu+"]="+timeLine[row][colu]+" cs="+cs+" foundFirst="+foundFirst);
				if ( foundFirst && colu>0 && timeLine[row][colu]==timeLine[row][colu-1] 
						&& cs == pedix[i][1]) {
					grid[row][colu].setBackground(new Color(255, 255, 255));
					//grid[row][colu].setFont(new Font(Font.DIALOG, Font.BOLD, 24));
				}

			}
		}

		solutionTextArea.setText("");
		//solutionTextArea.setText(solEx.solutionToString(solToGet));
		try {
			solEx.solutionToStringPro(solToGet);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		currentDisplaySolution++;
		//solEx.go(solutionTextArea);
	}


	public void loadColorPanel(JPanel colorPanel, JPanel calcPanel, JButton buttonContinue) {
		//col = new Color[getCurrentResources().size()+1];
		col = new ArrayList<Color>();
		colorButton = new JButton[getCurrentResources().size()+1];
		colorPanel.removeAll();
		//col.[0]= Color.RED;		
		col.add(0,Color.RED);		
		colorButton[0] = new JButton(" 0 ");
		colorButton[0].setFont(new Font("Tahoma", Font.PLAIN, 15));
		//colorButton[0].setBackground(col[0]);
		colorButton[0].setBackground(col.get(0));
		colorButton[0].setOpaque(true);
		colorPanel.add(colorButton[0]);
		//for (int i = 1; i < col.length-1; i++){


		for (int i = 1; i < getCurrentResources().size(); i++){
			if (i<6) {
				if (i==1) col.add(i, new Color(255,255,0));
				if (i==2) col.add(i, new Color(204, 153, 255));
				if (i==3) col.add(i, new Color(0, 204, 0));
				if (i==4) col.add(i, new Color(255, 153, 0));
				if (i==5) col.add(i, new Color(51, 102, 255));
			}else {
				int r = rand.nextInt(255) +1;
				int g = rand.nextInt(255) +1;
				int b = rand.nextInt(255) +1;
				Color color = new Color(r,g,b);
				while(Arrays.asList(col).contains(color))
				{
					r = rand.nextInt(255) +1;
					g = rand.nextInt(255) +1;
					b = rand.nextInt(255) +1;
					color = new Color(r,g,b);
				}
				//col[i] = new Color(r,g,b);
				col.add(i, new Color(r,g,b));
			}
			colorButton[i] = new JButton("  " + Integer.toString(i) + " ");
			colorButton[i].setFont(new Font("Tahoma", Font.PLAIN, 15));
			colorButton[i].setBackground(col.get(i));
			colorButton[i].setOpaque(true);
			int refi= i;

			colorButton[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					testC = JColorChooser.showDialog(null, "Choose another Color", col.get(refi));
					int j;
					for (j = 0; j < col.size(); j++) {
						if (col.get(j).equals(testC)) break;
					}

					if ( j < col.size()){
						JOptionPane.showMessageDialog(frame, "this color already exists");
					}else{
						//System.out.println("j="+j);
						col.add(refi, testC);
						colorButton[refi].setBackground(testC);
						colorButton[refi].setOpaque(true);
						colorPanel.updateUI();

						for (int row = 0; row < grid.length; row++) {
							for (int colu = 0; colu < grid[row].length; colu++) {
								if (colu < timeLine[row].length && timeLine[row][colu] == refi){	
									//if (timeLine[row][colu] == refi){	
									grid[row][colu].setBackground(col.get(timeLine[row][colu]));
								}
							}   
						}

					}
				}
			});
			colorPanel.add(colorButton[i]);					
		}
		/*if(calcPanel.getComponentCount() == 0){
			calcPanel.add(buttonContinue);
		}*/

		colorPanel.updateUI();
	}

}
