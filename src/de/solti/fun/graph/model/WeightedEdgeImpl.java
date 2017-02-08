package de.solti.fun.graph.model;

public class WeightedEdgeImpl implements WeightedEdge{
	
	protected double weight;
	protected boolean crossing = false;
	protected boolean correct = false;
	
	public WeightedEdgeImpl(Double weight) {
		this.weight = weight;
	}
	
	public Double getWeight() {
		return this.weight;
	}

	public String toString(){
		return String.valueOf(weight);
	}

	@Override
	public void setCrossing(boolean crossing) {
		this.crossing = crossing;
	}

	@Override
	public boolean isCrossing() {
		return this.crossing;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	
}
