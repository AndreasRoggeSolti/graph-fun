package de.solti.fun.graph;

import java.awt.geom.Point2D;
import java.util.Random;

public class Utils {

	private static final Random random = new Random();
	
	public static Point2D.Double getRandomPosition(int width, int height){
		Point2D.Double pos = new Point2D.Double(random.nextDouble()*width, random.nextDouble()*height);
		return pos;
	}
}
