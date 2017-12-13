public class Experimentation {

	public static int numVars = 4;
	
	public static int[] direction = new int[numVars];					// this determines which direction steps will be taken in,
																	// 0 = negative, 1 = none, 2 = positive
	/*** MANUAL CHANGES ***/
	public static double[] center = {0.234, 0.234, 0.162, 0.197};		// this is the center point where steps are taken from
																	// note: new coordinates cannot be written into this array
																	//       until all steps have been evaluated in the nextStep function
	
	public static double[] better = new double[numVars];				// this is the where values of better coordinates are stored
																	// during execution of the nextStep function
	
	public static double betterValue;
	
	/*** MANUAL CHANGES ***/
	public static double stepSize = 1.0;								// sets initial stepSize
	public static double resolution = 0.01;							// sets resolution
	
	public static double valueFunction(double[] center) {				// this is a minimization function
		double ab = 0.2*(center[0] - 11)*(center[0] - 11) + 0.2*(center[1] - 10.1)*(center[1] - 10.1);
		double cd = 0.2*(center[2] - 14.4)*(center[2] - 14.4) + 0.2*(center[3] - 14.3)*(center[3] - 14.3);
		double f = ab + cd;
		return f;
	}

	/**
	 * 
	 * @param element is the number of variables in the equation
	 * @return returns the objective value of a step taken in the best direction
	 */
	public static double nextStep(int element) {
		element -= 1;
		if (element == 0) {
			for (int j = 0; j < 3; j++) {
				direction[element] = j;
				double[] step = new double[numVars];
				// this sets all step values
				for (int k = 0; k < numVars; k++) {
					step[k] = center[k] - stepSize + direction[k] * stepSize;
				}
				double stepValue = valueFunction(step);				/*** MANUAL CHANGES ***/
				if (stepValue < betterValue) {						/*** MANUAL CHANGES * (only to change btwn max to min objective ***/
					betterValue = stepValue;
					for (int l = 0; l < numVars; l++) {
						better[l] = step[l];
					}
				}
			}
		} else {
			for (int i = 0; i < 3; i++) {
				direction[element] = i;
				nextStep(element);
			}
		}
		return betterValue;
	}

	public static void main(String[] args) {

		double bestStepValue = valueFunction(center);				// initializes bestStepValue with objective value at starting coordinates
		betterValue = bestStepValue;									// initializes betterValue so that (stepValue < betterValue) can be evaluated in nextStep function
		
		while(stepSize >= resolution) {
			nextStep(numVars);										// if we take a step in any direction, betterValue is changed
			if(betterValue != bestStepValue) {
				bestStepValue = betterValue;
				for(int i = 0; i < numVars; i++) {
					center[i] = better[i];
				}
			} else {
				stepSize /= 10.0;
			}
			System.out.println("--------------------------------------");
			System.out.println("Objective Value: " + bestStepValue);
			for(int i = 0; i < numVars; i ++) {
				System.out.print("x" + i + ": ");
				System.out.format("%6.3f", better[i]);
				System.out.println();
			}
		}
	}

}