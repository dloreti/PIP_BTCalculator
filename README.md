# PIP_BTCalculator
A java prototype to allow worst-case blocking time computation in case of priority inheritance protocol

# Getting started

Download IBM ILOG CPLEX Optimization Studio 12.8 from https://www.ibm.com/products/ilog-cplex-optimization-studio and follow the installation instructions.
We address CPLEX installation directory with ${CPLEX_HOME} in the following.

Modify the `cplexjar_path` and configuration file in `PIP_BTCalculator/target/config.properties` as follows:

	# cplex installation
	
	# path to cplex.jar in your cplex installation folder
	cplexjar_path=${CPLEX_HOME}/cplex/lib/cplex.jar 
	
	# path to the cplex binary in your cplex installation folder e.g.,
	# 	on mac osx: ${CPLEX_HOME}/cplex/bin/x86-64_osx
	#   on windows: ${CPLEX_HOME}\\cplex\\bin\\x64_win64\\
	cplexlib_path=${CPLEX_HOME}/cplex/bin/x86-64_osx

# Rebuild with Maven

If you want you can rebuild the project with the following steps:

change the `<cplex.home>` property of the `pom.xml` file and run

`maven clean install`

# User Guide

Open a terminal and `cd` to the project main directory `PIP_BTCalculator`
Run the appropriate `PIP_BTCalculator-$(YOUR_OS).jar` in the `target` folder with:

`java -jar ./target/PIP_BTCalculator-$(YOUR_OS).jar`

Write the execution sequences of your application in the text area. Each line represents a task execution sequence. Time units should be separated by commas. No nested accesses allowed. 
`0` identifies a time unit of normal execution (without any access to shared resources). 
A number `N` (with `N!=0`) identifies a time unit of execution with mutually exclusive access to the shared resource `N`.

![alt text](https://raw.githubusercontent.com/dloreti/PIP_BTCalculator/master/img/img1.png)

Click the `Continue` button to switch to the colored interface. You can also change the colors associated to each resource with the corresponding buttons above.

![alt text](https://raw.githubusercontent.com/dloreti/PIP_BTCalculator/master/img/img2.png)

Select the task to consider with the radio button on the left and click `Calculate` to see the model and the first solution that generates the worst case blocking.

![alt text](https://raw.githubusercontent.com/dloreti/PIP_BTCalculator/master/img/img3.png)

Click the `Next solution` button to see other possible solutions with the same worst case blocking, if any.



