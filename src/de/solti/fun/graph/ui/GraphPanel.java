package de.solti.fun.graph.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.solti.fun.graph.layout.GraphLayouter;
import de.solti.fun.graph.model.WeightedEdge;

public class GraphPanel<V,E> extends JPanel implements Observer, ActionListener {
	private static final long serialVersionUID = 7999461315673570986L;
	private GraphLayouter<V,E> layouter;

	private boolean showEdges = true;
	
	public GraphPanel(GraphLayouter<V,E> layouter){
		this.layouter = layouter;
		this.layouter.addObserver(this);
		
		this.setPreferredSize(new Dimension(GraphLayouter.SIZE,GraphLayouter.SIZE));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(Color.BLACK);
		if (layouter != null){
			// draw the graph nodes at the current positions
			for (V vertex : layouter.getGraph().vertexSet()){
				if (vertex.toString().startsWith("a")){
					g.setColor(Color.BLUE);
				} else if (vertex.toString().startsWith("b")){
					g.setColor(Color.RED);
				} else if (vertex.toString().startsWith("c")){
					g.setColor(Color.GREEN);
				} else if (vertex.toString().startsWith("d")){
					g.setColor(Color.ORANGE);
				} else {
					g.setColor(Color.BLACK);
				}
 				
				
				g2.drawString(vertex.toString(), (int)layouter.getXPos(vertex)-5, (int)layouter.getYPos(vertex)+5);
			}
			
			if (showEdges){
				double dist = 0.15;
				for (E edge : layouter.getGraph().edgeSet()){
					if (edge instanceof WeightedEdge){
						WeightedEdge we = (WeightedEdge) edge;
						// draw 1 / 3rd of the edge in the middle of the connection
						V s = layouter.getGraph().getEdgeSource(edge);
						V t = layouter.getGraph().getEdgeTarget(edge);
						Double xs = layouter.getXPos(s);
						Double ys = layouter.getYPos(s);
						
						Double xt = layouter.getXPos(t);
						Double yt = layouter.getYPos(t);
						
						Point2D.Double vector = new Point2D.Double(xt-xs, yt-ys);
						if (we.isCrossing()){
							g2.setColor(Color.RED);
							g2.setStroke(new BasicStroke(2f));
						} else {
							if (we.isCorrect()){
								g2.setColor(Color.GREEN.darker());
							} else {
								g2.setColor(Color.BLACK);	
							}
							g2.setStroke(new BasicStroke(1f));
						}
						g2.drawLine((int)(xs+dist*vector.x), (int)(ys+dist*vector.y), (int)(xt-dist*vector.x), (int)(yt-dist*vector.y));
					}
				}	
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		this.revalidate();
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}
