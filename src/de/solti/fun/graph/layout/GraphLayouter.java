package de.solti.fun.graph.layout;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;

import org.jgrapht.Graph;

import de.solti.fun.graph.Utils;
import de.solti.fun.graph.model.WeightedEdge;

public class GraphLayouter<V,E> extends Observable implements Layouter<V, E>{

	private static final long MAX_ITERATIONS = 100000;

	private static final int CROSSING_DETECTION_THRESHOLD = 200;
	private static final int PUSH_AWAY_THRESHOLD = 2000;
	private static final int PUSH_AWAY_OUTAGE = 400;
	
	private String[] pointsForAngleComputation = null;
	
	private double angle = 0;
	
	
	private static final double OVERLAP_DETECTION_THRESHOLD = 0.0000001;
	
	private static final double DAMPENING = 0.35;
	
	public static int SIZE = 800;
	

	private Graph<V, E> graph;
	private List<E> edges;
	
	private Map<V, Point2D.Double> nodePositions;
	Point2D.Double crisisRegion;
	
	private boolean[] okNodes;

	private V centerNode = null;
	
	private List<V> xZeroNodes;

	private double targetEdgeLength;

	private boolean pause;
	private boolean changed = true;
	private int count = 0;

	private Map<V, Integer> nodeMap;

	private double percentageError = 0;

	private double lastEdgesSwitched = 1;
	
	public GraphLayouter(double edgeLength){
		this.targetEdgeLength = edgeLength;
		this.pause = true;
		this.xZeroNodes = new LinkedList<>();
	}
	
	public void setGraph(Graph<V,E> g){
		this.graph = g;
		this.edges = new ArrayList<>(g.edgeSet());
		this.nodeMap = new HashMap<V, Integer>();
		int i = 0;
		this.okNodes = new boolean[g.vertexSet().size()];
		for (V vertex : g.vertexSet()){
			nodeMap.put(vertex, i);
			okNodes[i++] = true;
		}
		
		init();
	}
	public void setCentralVertex(V centralVertex){
		this.centerNode = centralVertex;
	}

	private void init() {
		this.centerNode  = graph.vertexSet().iterator().next();
		restart();
	}

	public void restart() {
		// assign random Positions between 0-100 
		changed = true;
		count = 0;
		this.nodePositions = new HashMap<>();
		for (V vertex : graph.vertexSet()){
			relocateVertex(vertex);
		}
	}
	
	private void relocateVertex(V vertex) {
		Point2D.Double newPos = Utils.getRandomPosition(SIZE,SIZE);
		nodePositions.put(vertex, newPos);
		
		for (E e : graph.edgesOf(vertex)){
			V v2 = graph.getEdgeSource(e);
			if (vertex.equals(v2)){
				v2 = graph.getEdgeTarget(e);
			}
			Point2D.Double randomDev = Utils.getRandomPosition(SIZE/10,SIZE/10);
			nodePositions.put(v2, new Point2D.Double(newPos.getX()+randomDev.getX(), newPos.getY()+randomDev.getY()));
		}
				
	}

	public Map<V, Point2D.Double> getLayout(){
		long iter = 0;
		while (iter < MAX_ITERATIONS && changed){
			if (pause){
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				step();
	//			try {
	//				Thread.sleep(30);
	//			} catch (InterruptedException e) {
	//				e.printStackTrace();
	//			}
			}
		}
		return this.nodePositions;
	}

	public void step() {
		changed = computeAndApplyForces(++count);
		if (count > Math.max(CROSSING_DETECTION_THRESHOLD, PUSH_AWAY_THRESHOLD)*2){
			count = 0;
		}
		if (pointsForAngleComputation != null){
			Point2D.Double p1 = nodePositions.get(pointsForAngleComputation[0]);
			Point2D.Double p2 = nodePositions.get(pointsForAngleComputation[1]);
			Point2D.Double p3 = nodePositions.get(pointsForAngleComputation[2]);
			
			double p12 = p1.distance(p2);
			double p23 = p2.distance(p3);
			double p13 = p1.distance(p3);
			
			angle = Math.acos( (Math.pow(p12, 2)+Math.pow(p23, 2) - Math.pow(p13, 2)) / (2 * p12 * p23)) / Math.PI * 180;
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * 2 pass iteration: 
	 * 
	 * 1. compute forces keeping the current position of the nodes
	 * 2. apply sum of forces to the real positions (if non-zero, we return true).
	 * @return true, if the layout was affected by the forces.
	 */
	public boolean computeAndApplyForces(int count){
		
		Map<V, Point2D.Double> forces = getZeroForces();
		computeEdgeForces(DAMPENING, forces);
		
		boolean changed = false;
		
		for (E edge : graph.edgeSet()){
			if (edge instanceof WeightedEdge){
				WeightedEdge we = (WeightedEdge) edge;
				we.setCrossing(false);
			}
		}
		boolean edgesCross = testEdgesCross(count % CROSSING_DETECTION_THRESHOLD==0); 
//		if (){
//			changed = testEdgesCross() || changed;
//		}
		if (edgesCross){
			boolean pushAway = (count % (PUSH_AWAY_THRESHOLD+PUSH_AWAY_OUTAGE)) - PUSH_AWAY_THRESHOLD < 0;
			computeNodeForces(DAMPENING, forces, pushAway);
		}
		changed = applyComputedForces(forces, changed);
		changed = testNodeOnTop() || changed;
		
		return changed;
	}

	public boolean applyComputedForces(Map<V, Point2D.Double> forces, boolean changed) {
		// graphCenter = average of all node positions
		Point2D.Double graphCenter = new Point2D.Double(0, 0);
		for (V vertex : graph.vertexSet()){
			graphCenter.x += nodePositions.get(vertex).x;
			graphCenter.y += nodePositions.get(vertex).y;
			
			Point2D.Double force = forces.get(vertex);
			double dist = force.distance(new Point2D.Double(0.,0.)); 
			if (dist > 2){
				force = new Point2D.Double(2*force.x / dist, 2*force.y / dist);
			}
			Point2D.Double nodePos = nodePositions.get(vertex);
			if (force.getX() != 0 || force.getY() != 0){
				nodePos.x += force.getX();
				nodePos.y += force.getY();
				nodePositions.put(vertex, nodePos);
				changed = true;
			}
		}
		graphCenter.x /= graph.vertexSet().size();
		graphCenter.y /= graph.vertexSet().size();
		Point2D correctionVector = new Point2D.Double(SIZE/2-graphCenter.x, SIZE/2 - graphCenter.y);
		
		for (V vertex : xZeroNodes){
			nodePositions.put(vertex, new Point2D.Double(SIZE/2, nodePositions.get(vertex).getY()));
		}
		// reposition graph:
		for (V vertex : graph.vertexSet()){
			Point2D.Double nodePos = nodePositions.get(vertex);
			nodePositions.put(vertex, new Point2D.Double(nodePos.getX()+correctionVector.getX(), nodePos.getY()+correctionVector.getY()));
		}
		return changed;
	}

	public void computeEdgeForces(double dampening, Map<V, Point2D.Double> forces) {
		percentageError = 0.0;
		for (E e: graph.edgeSet()){
			V source = graph.getEdgeSource(e);
			Point2D.Double sourcePos = nodePositions.get(source);
			V target = graph.getEdgeTarget(e);
			Point2D.Double targetPos = nodePositions.get(target);
//			System.out.println("relocating Edge "+source.toString()+" - "+target.toString());
			double desiredDistance = (e instanceof WeightedEdge) ? ((WeightedEdge)e).getWeight() : 1.0;
			double currentDistance = sourcePos.distance(targetPos);
			// add force to the current forces of the node
			double delta = (currentDistance - desiredDistance)/currentDistance;
			percentageError += Math.abs(delta);
			((WeightedEdge)e).setCorrect(Math.abs(delta)<0.0001);
			// the source is pulled towards the target:
			Point2D.Double vector = new Point2D.Double(targetPos.x-sourcePos.x, targetPos.y-sourcePos.y);
			Point2D.Double sourceForce = forces.get(source);
			sourceForce.x += dampening*delta*vector.x;
			sourceForce.y += dampening*delta*vector.y;
			forces.put(source, sourceForce); 
			
			// the target is pulled towards the source:
			Point2D.Double targetForce = forces.get(target);
			targetForce.x -= dampening*delta*vector.x;
			targetForce.y -= dampening*delta*vector.y;
			forces.put(target, targetForce);
		}
	}
	public void computeNodeForces(double dampening, Map<V, Point2D.Double> forces, boolean pushAway) {
		List<V> vertices = new ArrayList<>(graph.vertexSet());
		for (int i = 0; i < vertices.size(); i++){
			V node1 = vertices.get(i);
			for (int j = i+1; j < vertices.size(); j++){
				V node2 = vertices.get(j);
				Point2D.Double p1 = this.nodePositions.get(node1);
				Point2D.Double p2 = this.nodePositions.get(node2);
				double distanceToCrisisRegion = Math.max(0.5, (p1.distance(crisisRegion) / (targetEdgeLength*2)));
				if (p1.distance(p2) < targetEdgeLength*Math.min(distanceToCrisisRegion, 1.3)){
					// push them apart:
					Point2D.Double vector = new Point2D.Double(p2.x-p1.x, p2.y-p1.y);
					double length = vector.distance(0.0, 0.0);
					if (length < 0.1){ // avoid division by zero!
						vector = new Point2D.Double(0.1, 0.0);
						length = 0.1;
					}
					Point2D.Double unitVector = new Point2D.Double(vector.x/length, vector.y/length);
					Point2D.Double force1 = forces.get(node1);
					Point2D.Double force2 = forces.get(node2);
					if (pushAway || okNodes[nodeMap.get(node1)] || okNodes[nodeMap.get(node2)]){
						forces.put(node1, new Point2D.Double(force1.x-(targetEdgeLength*unitVector.x)*dampening, force1.y-(targetEdgeLength*unitVector.y)*dampening));
						forces.put(node2, new Point2D.Double(force2.x+(targetEdgeLength*unitVector.x)*dampening, force2.y+(targetEdgeLength*unitVector.y)*dampening));
					}
				}
			}
		}
	}
		

	private boolean testEdgesCross(boolean applyCrossRepair) {
		crisisRegion = new Point2D.Double(0, 0);
		int pointCount = 0;
		int edgesSwitched = 0;
		boolean edgesOverlap = false;
		boolean crossing = false;
		Arrays.fill(okNodes, true);
		Collections.shuffle(edges);
		for (int i = 0; i < edges.size(); i++){
			E edge = edges.get(i);
			if (edge instanceof WeightedEdge && !crossing){
				WeightedEdge we = (WeightedEdge) edge;
				if (!we.isCrossing()){
					V source = graph.getEdgeSource(edge);
					V target = graph.getEdgeTarget(edge);
					Point2D.Double pS = this.nodePositions.get(source);
					Point2D.Double pT = this.nodePositions.get(target);
					for (int j = i+1 ; j < edges.size(); j++){
						if (!crossing){
							E e2 = edges.get(j);
							WeightedEdge we2 = (WeightedEdge) e2;
							V s2 = graph.getEdgeSource(e2);
							V t2 = graph.getEdgeTarget(e2);
							Point2D.Double pS2 = this.nodePositions.get(s2);
							Point2D.Double pT2 = this.nodePositions.get(t2);
							if (!we2.isCrossing()){
								if (pS.distance(pS2) < 2*targetEdgeLength){
									// potential intersection:
									Point2D.Double v1 = new Point2D.Double(pT.x-pS.x, pT.y-pS.y);
									Point2D.Double v2 = new Point2D.Double(pT2.x-pS2.x, pT2.y-pS2.y);
									double f = 0.01;
									if (Line2D.linesIntersect(pS.x+f*v1.x, pS.y+f*v1.y, pT.x-f*v1.x, pT.y-f*v1.y, 
											pS2.x+f*v2.x, pS2.y+f*v2.y, pT2.x-f*v2.x, pT2.y-f*v2.y)){
										edgesOverlap = true;
										crisisRegion.x+=pS.x;
										crisisRegion.x+=pT.x;
										crisisRegion.y+=pS.y;
										crisisRegion.y+=pT.y;
										pointCount += 2;
										okNodes[nodeMap.get(source)] = false;
										okNodes[nodeMap.get(target)] = false;
										okNodes[nodeMap.get(s2)] = false;
										okNodes[nodeMap.get(t2)] = false;
										
										if (e2 instanceof WeightedEdge){ 
											we2.setCrossing(true);
											we.setCrossing(true);
											if (edgesSwitched++ < lastEdgesSwitched/2 && applyCrossRepair){
												nodePositions.put(source, pS2);
												nodePositions.put(s2, pS);
												crossing = true;
											}
										}
									}
								}
							}
						}
					}
					crossing = false;
				}
			}
		}
		lastEdgesSwitched  = edgesSwitched;
		if (pointCount>0){
			crisisRegion.x /= pointCount;
			crisisRegion.y /= pointCount;
		}
		return edgesOverlap;
	}

	private boolean testNodeOnTop() {
		boolean changed = false;
		for (V vertex : graph.vertexSet()){
			Point2D.Double nodePos = nodePositions.get(vertex);
			for (Entry<V, Point2D.Double> pos : nodePositions.entrySet()){
				if (!pos.getKey().equals(vertex) && nodePos.distance(pos.getValue()) < OVERLAP_DETECTION_THRESHOLD){
					if (vertex.equals(centerNode)){
						relocateVertex(pos.getKey());
						changed = true;
					} else {
						Point2D.Double oldPos = nodePositions.get(vertex);
						
//						Point2D.Double centerPos = nodePositions.get(centerNode);
//						Point2D.Double vectorToZero = new Point2D.Double(nodePos.x-centerPos.x, nodePos.y-centerPos.y);
//						nodePositions.put(vertex, new Point2D.Double(centerPos.x+vectorToZero.x*1.5, centerPos.y+vectorToZero.y*1.5));
						relocateVertex(vertex);
						Point2D.Double newPos = nodePositions.get(vertex);
						Point2D.Double move = new Point2D.Double(newPos.x-oldPos.x, newPos.y-oldPos.y);
						for (E edge : graph.edgesOf(vertex)){
							V vEnd = graph.getEdgeSource(edge);
							if (vertex.equals(vEnd)){
								vEnd = graph.getEdgeTarget(edge);
							}
							Point2D.Double endPos = nodePositions.get(vEnd);
							nodePositions.put(vEnd, new Point2D.Double(endPos.x+move.x, endPos.y+move.y));
							changed = true;	
						}
					}
				}
			}
		}
		return changed;
	}

	private Map<V, Double> getZeroForces() {
		Map<V, Point2D.Double> forces = new HashMap<>();
		for (V vertex : graph.vertexSet()){
			forces.put(vertex, new Point2D.Double(0, 0));
		}
		return forces;
	}
	
	public Graph<V, E> getGraph(){
		return graph;
	}

	public double getXPos(V vertex) {
		return nodePositions.get(vertex).getX();
	}
	public double getYPos(V vertex) {
		return nodePositions.get(vertex).getY();
	}

	public void setXZeroVertex(V vertex) {
		this.xZeroNodes.add(vertex);
	}

	public boolean togglePause() {
		this.pause = !this.pause;
		return this.pause;
	}

	public double getPercentageError() {
		return percentageError;
	}
	
	public double getAngle() {
		return angle;
	}

	public void setPointsAngles(String[] points) {
		this.pointsForAngleComputation = points;
	}
	
}
