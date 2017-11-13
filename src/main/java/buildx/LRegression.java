package buildx;

/**
 * Linear Regression in Java.
 * 
 * @author rgeangu
 *
 */
public class LRegression {

	/**
	 * Calculates the sum of every value in the array of floats.
	 * @param values An array of values.
	 * @return The sum of the values in the array.
	 */
	private static float sum(float[] values) {
		float sum = 0;

		for (float value : values) {
			sum += value;
		}

		return sum;
	}

	/**
	 * The mean of the values in the array.
	 * @param values An array of floats.
	 * @return The mean of the values in the array.
	 */
	private static float mean(float[] values) {
		int length = values.length;

		if (length == 0) {
			return 0;
		}

		return sum(values) / length;
	}

	/**
	 * Multiplies each value in the two arrays and adds them to a resulted array.
	 * @param oneArray One array of floats.
	 * @param anotherArray Another array of floats.
	 * @return An array of floats resulted from the multiplication of each value in the two arrays
	 */
	private static float[] multiplyArrays(float[] oneArray, float[] anotherArray) {
		int length = oneArray.length;
		float[] resultArray = new float[length];

		for (int i = 0; i < length; ++i) {
			resultArray[i] = oneArray[i] * anotherArray[i];
		}

		return resultArray;
	}
	
	/**
	 * Subtracts each value from one array using the values from the another array.
	 * @param oneArray An array of floats to be subtracted.
	 * @param anotherArray An Array of floats to subtract from.
	 * @return An array of floats resulted from the subtraction of each value in the two arrays.
	 */
	private static float[] subtractArrays(float[] oneArray, float[] anotherArray) {
		int length = oneArray.length;
		float[] resultArray = new float[length];

		for (int i = 0; i < length; ++i) {
			resultArray[i] = oneArray[i] - anotherArray[i];
		}

		return resultArray;
	}

	/**
	 * The slope (m) calculated using the formula from maths.
	 * @param xs The array of x values.
	 * @param ys The array of y values.
	 * @return The slope as a float.
	 */
	public static float slope(float[] xs, float[] ys) {
		float mean_xs = mean(xs);
		float mean_ys = mean(ys);
		float mean_xsys = mean(multiplyArrays(xs, ys));
		float mean_xs_squared = mean_xs * mean_xs;
		float mean_squared_xs = mean(multiplyArrays(xs, xs));
		
		return (mean_xs * mean_ys - mean_xsys) / (mean_xs_squared - mean_squared_xs);
    }
	
	/**
	 * The intercept (b) calculated using the formula from maths.
	 * @param xs The array of x values.
	 * @param ys The array of y values.
	 * @return The intercept as a float.
	 */
    public static float intercept(float[] xs, float[] ys) {
        
        return mean(ys) - slope(xs, ys) * mean(xs);
    }
    
    /**
     * The squared error of our regression calculated from the difference of the original ys and predicted ys.
     * @param ysOrig The original y values of the data set.
     * @param ysLine The y values from the regression line.
     * @return The squared error.
     */
    private static float squaredError(float[] ysOrig, float[] ysLine) {
    		float[] result = subtractArrays(ysLine, ysOrig);
    		return sum(multiplyArrays(result, result));
    }
    
    /**
     * The coefficient of determination which tells about how good the best fit line is.
     * @param ysOrig The original y values of the data set.
     * @param ysLine The y values from the regression line.
     * @return The coefficient of determination.
     */
    public static float coefficientOfDetermination(float[] ysOrig, float[] ysLine) {
    		float[] ysMeanLine = new float[ysOrig.length];
    		
    		for (int i = 0; i < ysOrig.length; ++i) {
    			ysMeanLine[i] = mean(ysOrig);
    		}
    		
    		float squaredErrorRegr = squaredError(ysOrig, ysLine);
    		float squaredErrorYsMean = squaredError(ysOrig, ysMeanLine);
    		
		return 1 - (squaredErrorRegr / squaredErrorYsMean);
    }
}
