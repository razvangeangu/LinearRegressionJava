package buildx;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Plot extends ApplicationFrame {
	private static final long serialVersionUID = 5617332778114239913L;
	private static String title;
	public Plot(String title, JFreeChart chart) {
		super(title);	
		Plot.title = title;

		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);
	}
	protected static JFreeChart createChart(final XYDataset dataset) {

		// create the chart...
		final JFreeChart chart = ChartFactory.createXYLineChart(title, // chart title
				"X", // x axis label
				"Y", // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
		);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		chart.setBackgroundPaint(Color.white);

		// get a reference to the plot for further customisation...
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);
		// plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, false);
		renderer.setSeriesShapesVisible(1, false);
		plot.setRenderer(renderer);

		// change the auto tick unit selection to integer units only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;
	}
	protected static XYSeriesCollection dataset = new XYSeriesCollection();
	protected static void plotData(String title, float[] xs, float[] ys) {
		final XYSeries data = new XYSeries(title);
		for (int i = 0; i < xs.length; ++i) {
			data.add(xs[i], ys[i]);
		}
		
		dataset.addSeries(data);
	}
	private static void showChart() {
		final JFreeChart chart = createChart(dataset);
		final Plot demo = new Plot("Linear Regression", chart);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}
	
	public static float[] createYs(int howMany, int variance, int step, String correlation) {
		int val = 1;
		float[] ys = new float[howMany];
		
		for (int i = 0; i < howMany; i++) {
			int y = val + randomInRange(-variance, variance);
			ys[i] = y;
			
			if (correlation.equals("pos")) {
				val += step;
			} else if (correlation.equals("neg")) {
				val -= step;
			}
		}
		
		return ys;
	}
	public static float[] createXs(int howMany) {
		float[] xs = new float[howMany];
		for (int i = 0; i < howMany; i++) {
			xs[i] = i;
		}
		
		return xs;
	}
	
	public static int randomInRange(int min, int max) {
	     return (int) (Math.random()*(max - min)) + min;
	}
	
	public static void main(final String[] args) {  	
    		// float[] xs = {1, 2, 3, 4, 5, 6};
    		// float[] ys = {5, 4, 6, 5, 6, 7};
		
		int howMany = 40;
		float[] xs = createXs(howMany);
		float[] ys = createYs(howMany, 80, 2, "pos");

    		float m = LRegression.slope(xs, ys);
    		float b = LRegression.intercept(xs, ys);
    		
    		// Show original values on the plot
    		plotData("Original", xs, ys);
    		
    		// Create line for the regression values
    		float[] ysPredict = new float[xs.length];
    		for (int i = 0; i < ysPredict.length; i++) {
			ysPredict[i] = m * xs[i] + b;
		}
    		// Show regression values on the plot
    		plotData("Linear Regression", xs, ysPredict);
   
//    		// Show prediction of a single value on the plot
//    		float x_predict = 10;
//    		float y_predict = m * x_predict + b;
//    		plotData("Prediction: " + y_predict, new float[]{x_predict}, new float[]{y_predict});
    		
    		System.out.println(LRegression.coefficientOfDetermination(ys, ysPredict));
    		
    		showChart();
	}
}
