package de.solti.fun.graph.model;

public interface WeightedEdge {

	public Double getWeight();
	
	/**
	 * Sets whether an edge is crossing. 
	 * @param crossing
	 */
	public void setCrossing(boolean crossing);
	/**
	 * Gets whether the edge is crossing.
	 * @return
	 */
	public boolean isCrossing();

	public void setCorrect(boolean b);
	
	public boolean isCorrect();
}
