package de.solti.fun.graph.layout;

import java.awt.geom.Point2D;
import java.util.Map;

import org.jgrapht.Graph;

public interface Layouter<V,E> {
	
	public void setGraph(Graph<V,E> g);
	
	public Map<V,Point2D.Double> getLayout();

}
