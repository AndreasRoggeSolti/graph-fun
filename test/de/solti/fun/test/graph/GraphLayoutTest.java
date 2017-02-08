package de.solti.fun.test.graph;

import java.awt.BorderLayout;
import java.awt.geom.Point2D;
import java.util.Map;

import javax.swing.JFrame;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Test;

import de.solti.fun.graph.layout.GraphLayouter;
import de.solti.fun.graph.model.WeightedEdge;
import de.solti.fun.graph.model.WeightedEdgeImpl;
import de.solti.fun.graph.ui.GraphControls;
import de.solti.fun.graph.ui.GraphPanel;

public class GraphLayoutTest {

	public static final double EDGE_DEFAULT_LENGTH = 60;
	
	@Test
	public void testLayout() throws Exception {
		WeightedGraph<String, WeightedEdge> stringGraph = createStringGraph(EDGE_DEFAULT_LENGTH);
		
		// note undirected edges are printed as: {<v1>,<v2>}
		System.out.println(stringGraph.toString());
		GraphLayouter<String, WeightedEdge> layouter = new GraphLayouter<>(EDGE_DEFAULT_LENGTH);
		layouter.setGraph(stringGraph);
		layouter.setCentralVertex("v7");
		
		GraphPanel<String, WeightedEdge> panel = new GraphPanel<>(layouter);
		GraphControls<String, WeightedEdge> controls = new GraphControls<>(layouter);
		
		JFrame frame = new JFrame("Graph layouter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.getContentPane().add(controls, BorderLayout.NORTH);
		frame.pack();
		frame.setVisible(true);
			
		layouter.getLayout();
	}

	/**
	 * Create a toy graph based on String objects.
	 *
	 * @return a graph based on String objects.
	 */
	private static WeightedGraph<String, WeightedEdge> createStringGraph(double edgeLength) {
		WeightedGraph<String, WeightedEdge> g = new SimpleWeightedGraph<>(WeightedEdge.class);

		String v1 = "v1";
		String v2 = "v2";
		String v3 = "v3";
		String v4 = "v4";
		String v5 = "v5";
		String v6 = "v6";
		String v7 = "v7";
		String v8 = "v8";
		
//		String v11 = "va";
		String v12 = "vb";
		String v13 = "vc";
		String v14 = "vd";
		String v15 = "ve";
//		String v16 = "vf";
		

		// add the vertices
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.addVertex(v4);
		g.addVertex(v5);
		g.addVertex(v6);
		g.addVertex(v7);
		g.addVertex(v8);
		
		//g.addVertex(v11);
		g.addVertex(v12);
		g.addVertex(v13);
		g.addVertex(v14);
		g.addVertex(v15);
		//g.addVertex(v16);
		

		// add edges to create a triangle
		connect(g, v1,v2, edgeLength);
		connect(g, v2,v3, edgeLength);
		connect(g, v3,v1, edgeLength);
		
		// 2-3, 4
		connect(g, v2, v4, edgeLength);
		connect(g, v3, v4, edgeLength);
		// 3-4, 5
		connect(g, v3, v5, edgeLength);
		connect(g, v4, v5, edgeLength);
		
		// 4-5, 6
		connect(g, v2, v6, edgeLength);
		connect(g, v4, v6, edgeLength);
		
		connect(g, v6, v7, edgeLength);
		connect(g, v6, v8, edgeLength);
		connect(g, v7, v8, edgeLength);

		connect(g, v1,v12, edgeLength);
		connect(g, v12,v13, edgeLength);
		connect(g, v13,v1, edgeLength);
		
		// 2-3, 4
		connect(g, v12, v14, edgeLength);
		connect(g, v13, v14, edgeLength);
		// 3-4, 5
		connect(g, v13, v15, edgeLength);
		connect(g, v14, v15, edgeLength);
		
		// 4-5, 6
		connect(g, v12, v8, edgeLength);
		connect(g, v14, v8, edgeLength);
		
		return g;
	}
	
	
	/**
	 * Create a toy graph based on String objects.
	 *
	 * @return a graph based on String objects.
	 */
	private static WeightedGraph<String, WeightedEdge> createStringGraphTriangle(int edgeLength) {
		WeightedGraph<String, WeightedEdge> g = new SimpleWeightedGraph<>(WeightedEdge.class);

		String v1 = "v1";
		String v2 = "v2";
		String v3 = "v3";
		String v4 = "v4";
		String v5 = "v5";
		String v6 = "v6";
		String v7 = "v7";
		String v8 = "v8";
		

		// add the vertices
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.addVertex(v4);
		g.addVertex(v5);
		g.addVertex(v6);
		g.addVertex(v7);
		g.addVertex(v8);
		

		// add edges to create a triangle
		connect(g, v1,v2, edgeLength);
		connect(g, v2,v3, edgeLength);
		connect(g, v3,v1, edgeLength);
		
		// 2-3, 4
		connect(g, v2, v4, edgeLength);
		connect(g, v3, v4, edgeLength);
		// 3-4, 5
		connect(g, v3, v5, edgeLength);
		connect(g, v4, v5, edgeLength);
		
		// 4-5, 6
		connect(g, v2, v6, edgeLength);
		connect(g, v4, v6, edgeLength);
		
		connect(g, v6, v7, edgeLength);
		connect(g, v6, v8, edgeLength);
		connect(g, v7, v8, edgeLength);

		return g;
	}
	
	
	
	@Test
	public void testHaribo() {
		WeightedGraph<String, WeightedEdge> g = new SimpleWeightedGraph<>(WeightedEdge.class);
		String x1 = "x1";
		String x2 = "x2";
		g.addVertex(x1);
		g.addVertex(x2);
		
		createHalfMoon(g, x1, x2, "a", EDGE_DEFAULT_LENGTH);
		createHalfMoon(g, x1, x2, "b", EDGE_DEFAULT_LENGTH);
		
		showGraph(g);
	}
	
	@Test
	public void testHaribo3() {
		WeightedGraph<String, WeightedEdge> g = new SimpleWeightedGraph<>(WeightedEdge.class);
		String x1 = "x1";
		String x2 = "x2";
		String x3 = "x3";

		g.addVertex(x1);
		g.addVertex(x2);
		g.addVertex(x3);
//		g.addVertex(x4);
		
		createHalfDoubleMoon(g, x1, x2, "a", EDGE_DEFAULT_LENGTH);
		createHalfDoubleMoon(g, x2, x3, "b", EDGE_DEFAULT_LENGTH);
		createHalfDoubleMoon(g, x3, x1, "c", EDGE_DEFAULT_LENGTH);
//		createHalfMoon(g, x1, x2, "d", EDGE_DEFAULT_LENGTH);
		
		showGraph(g);
	}
	
	@Test
	public void testHaribo2() {
		WeightedGraph<String, WeightedEdge> g = new SimpleWeightedGraph<>(WeightedEdge.class);
		String x1 = "x1";
		String x2 = "x2";
		String x3 = "x3";
		String x4 = "x4";
		g.addVertex(x1);
		g.addVertex(x2);
		g.addVertex(x3);
//		g.addVertex(x4);
		
		createHalfMoon(g, x1, x2, "a", EDGE_DEFAULT_LENGTH);
		createHalfMoon(g, x2, x3, "b", EDGE_DEFAULT_LENGTH);
		createHalfMoon(g, x3, x1, "c", EDGE_DEFAULT_LENGTH);
//		createHalfMoon(g, x1, x2, "d", EDGE_DEFAULT_LENGTH);
		
		showGraph(g);
	}
	
	private void createHalfDoubleMoon(WeightedGraph<String,WeightedEdge> g, String x1, String x2, String prefix, double edgeLength) {
		String center = prefix+"_c";
		String conn1 = prefix+"_d";
		
		g.addVertex(center);
		g.addVertex(conn1);
		
		createBird(g, center, x1, conn1, prefix+"a", EDGE_DEFAULT_LENGTH);
		createBird(g, center, conn1, x2, prefix+"b", EDGE_DEFAULT_LENGTH);
	}
	
	private void createHalfMoon(WeightedGraph<String,WeightedEdge> g, String x1, String x2, String prefix, double edgeLength) {
		String center = prefix+"_c";
		String conn1 = prefix+"_d";
		String conn2 = prefix+"_e";
		
		g.addVertex(center);
		g.addVertex(conn1);
		g.addVertex(conn2);
		
		createBird(g, center, x1, conn1, prefix+"a", EDGE_DEFAULT_LENGTH);
		createBird(g, center, conn1, conn2, prefix+"b", EDGE_DEFAULT_LENGTH);
		createBird(g, center, conn2, x2, prefix+"c", EDGE_DEFAULT_LENGTH);
		
	}

	private void createBird(WeightedGraph<String, WeightedEdge> g, String x1, String x2,
			String x3, String prefix, double edgeLength) {
		String v1 = prefix+"1";
		String v2 = prefix+"2";
		String v3 = prefix+"3";
		String v4 = prefix+"4";
		String v6 = prefix+"5";
		String v8 = prefix+"6";
		
		String v12 = prefix+"7";
		String v13 = prefix+"8";
		String v14 = prefix+"9";
		

		// add the vertices
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.addVertex(v4);
		g.addVertex(v6);
		g.addVertex(v8);
		
		g.addVertex(v12);
		g.addVertex(v13);
		g.addVertex(v14);

		// add edges to create a triangle
		connect(g, v1,v2, edgeLength);
		connect(g, v2,v3, edgeLength);
		connect(g, v3,v1, edgeLength);
		// 2-3, 4
		connect(g, v2, v4, edgeLength);
		connect(g, v3, v4, edgeLength);
		// 3-4, 5
		connect(g, v3, x2, edgeLength);
		connect(g, v4, x2, edgeLength);
		// 4-5, 6
		connect(g, v2, v6, edgeLength);
		connect(g, v4, v6, edgeLength);
		
		connect(g, v6, x1, edgeLength);
		connect(g, v6, v8, edgeLength);
		connect(g, x1, v8, edgeLength);

		connect(g, v1,v12, edgeLength);
		connect(g, v12,v13, edgeLength);
		connect(g, v13,v1, edgeLength);
		// 2-3, 4
		connect(g, v12, v14, edgeLength);
		connect(g, v13, v14, edgeLength);
		// 3-4, 5
		connect(g, v13, x3, edgeLength);
		connect(g, v14, x3, edgeLength);
		// 4-5, 6
		connect(g, v12, v8, edgeLength);
		connect(g, v14, v8, edgeLength);
	}

	@Test
	public void testLego() throws Exception {
		WeightedGraph<String, WeightedEdge> g = new SimpleWeightedGraph<>(WeightedEdge.class);
		
		String x1 = "x1";
		String x2 = "x2";
		String x3 = "x3";
		
		String x4 = "x4";
		String x5 = "x5";
		String x6 = "x6";
		
		String x7 = "x7";
		String x8 = "x8";
		String x9 = "x9";
		
		g.addVertex(x1);
		g.addVertex(x2);
		g.addVertex(x3);
		g.addVertex(x4);
		g.addVertex(x5);
		g.addVertex(x6);
		g.addVertex(x7);
		g.addVertex(x8);
		g.addVertex(x9);

		connect(g, x2, x3, EDGE_DEFAULT_LENGTH);
		connect(g, x5, x6, EDGE_DEFAULT_LENGTH);
		connect(g, x7, x8, EDGE_DEFAULT_LENGTH);
		
		createHalfLego(g, x1, x2, x4, x5, x7,x9, "a", EDGE_DEFAULT_LENGTH);
		createHalfLego(g, x1, x3, x4, x6, x8,x9, "b", EDGE_DEFAULT_LENGTH);
		showGraph(g);
	}
	
	
	private void createHalfLego(WeightedGraph<String, WeightedEdge> g, String x1, String x2, String x4, String x5,
			String x7, String x9, String prefix, double edgeLength) {
		
		String a1 = prefix+"1";
		String a2 = prefix+"2";
		String a3 = prefix+"3";
		String a4 = prefix+"4";
		String a5 = prefix+"5";
		String a6 = prefix+"6";
		String a7 = prefix+"7";
		
		String d1 = prefix+"a"; // d1
		String d2 = prefix+"b"; // d2
		String d3 = prefix+"c"; // d3
		String d4 = prefix+"d"; // d4
		String d5 = prefix+"e"; // d5
		
		String b2 = prefix+"f";
		String b3 = prefix+"g";
		String b4 = prefix+"h";
		String b5 = prefix+"i";
		String b6 = prefix+"j";
		String b7 = prefix+"k";
		
		String i1 = prefix+"l";
		String i2 = prefix+"m";
		String i3 = prefix+"n";
		String i4 = prefix+"o";
		
		
		
		
				
		// add the vertices
		g.addVertex(a1);
		g.addVertex(a2);
		g.addVertex(a3);
		g.addVertex(a4);
		g.addVertex(a5);
		g.addVertex(a6);
		g.addVertex(a7);
		
		g.addVertex(d1);
		g.addVertex(d2);
		g.addVertex(d3);
		g.addVertex(d4);
		g.addVertex(d5);

		g.addVertex(b2);
		g.addVertex(b3);
		g.addVertex(b4);
		g.addVertex(b5);
		g.addVertex(b6);
		g.addVertex(b7);
		
		g.addVertex(i1);
		g.addVertex(i2);
		g.addVertex(i3);
		g.addVertex(i4);
		
		
		// add edges to create a triangle
		connect(g, a1,x1, edgeLength);
		connect(g, a1,x2, edgeLength);
		connect(g, x1,x2, edgeLength);
		
		connect(g, a1,a2, edgeLength);
		connect(g, a1,a3, edgeLength);
		connect(g, a2,a3, edgeLength);
		connect(g, a2,a4, edgeLength);
		connect(g, a3,a4, edgeLength);
		connect(g, a3,a5, edgeLength);
		connect(g, a4,a5, edgeLength);
		
		connect(g, a4,a6, edgeLength);
		connect(g, a5,a6, edgeLength);
		connect(g, a5,a7, edgeLength);
		connect(g, a7,a6, edgeLength);
		
		connect(g, d5,d2, edgeLength);
		connect(g, d5,d4, edgeLength);
		connect(g, d1,d2, edgeLength);
		connect(g, d1,d3, edgeLength);
		connect(g, d2,d3, edgeLength);
		connect(g, d2,d4, edgeLength);
		connect(g, d3,d4, edgeLength);
		connect(g, d3,a7, edgeLength);
		connect(g, d4,a7, edgeLength);
		
		connect(g, d1,b2, edgeLength);
		connect(g, d1,b3, edgeLength);
		connect(g, b2,b3, edgeLength);
		connect(g, b2,b4, edgeLength);
		connect(g, b3,b4, edgeLength);
		connect(g, b3,b5, edgeLength);
		connect(g, b4,b5, edgeLength);
		
		connect(g, b4,b6, edgeLength);
		connect(g, b5,b6, edgeLength);
		connect(g, b5,b7, edgeLength);
		connect(g, b7,b6, edgeLength);
		
		connect(g, i1, a2, edgeLength);
		connect(g, i1, x2, edgeLength);
		connect(g, i1, i2, edgeLength);
		connect(g, i1, x4, edgeLength);
		connect(g, i2, a6, edgeLength);
		connect(g, i2, x4, edgeLength);
		connect(g, i2, d5, edgeLength);
		connect(g, i3, d5, edgeLength);
		connect(g, i3, b2, edgeLength);
		connect(g, i3, x5, edgeLength);
		connect(g, i3, i4, edgeLength);
		connect(g, i4, b6, edgeLength);
		connect(g, i4, x7, edgeLength);
		connect(g, i4, x5, edgeLength);
		connect(g, b7, x7, edgeLength);
		connect(g, b7, x9, edgeLength);
		connect(g, x7, x9, edgeLength);
		connect(g, x4, x5, edgeLength);
		
	}

	@Test
	public void testHarborth() throws Exception {
		WeightedGraph<String, WeightedEdge> g = new SimpleWeightedGraph<>(WeightedEdge.class);
		
		String x1 = "x1";
		String x2 = "x2";
		
		String x3 = "x3";
		String x4 = "x4";
		
		String x5 = "x5";
		String x6 = "x6";
		
		String x7 = "x7";
		String x8 = "x8";
		
		g.addVertex(x1);
		g.addVertex(x2);
		g.addVertex(x3);
		g.addVertex(x4);
		g.addVertex(x5);
		g.addVertex(x6);
		g.addVertex(x7);
		g.addVertex(x8);
		
		createQuarterHarborth(g, x2, x1, x3, x4, "a", EDGE_DEFAULT_LENGTH);
		createQuarterHarborth(g, x6, x5, x3, x4, "b", EDGE_DEFAULT_LENGTH);
		createQuarterHarborth(g, x6, x5, x7, x8, "c", EDGE_DEFAULT_LENGTH);
		createQuarterHarborth(g, x2, x1, x7, x8, "d", EDGE_DEFAULT_LENGTH);
		
		showGraph(g);
	}
	
	@Test
	public void testHarborthV2() throws Exception {
		WeightedGraph<String, WeightedEdge> g = new SimpleWeightedGraph<>(WeightedEdge.class);
		
		String x1 = "x1";
		String x2 = "x2";
		
		String x3 = "x3";
		String x4 = "x4";
		
		String x5 = "x5";
		String x6 = "x6";
		
		String x7 = "x7";
		String x8 = "x8";
		
		String y1 = "y1";
		String y2 = "y2";
		
		g.addVertex(x1);
		g.addVertex(x2);
		g.addVertex(x3);
		g.addVertex(x4);
		g.addVertex(x5);
		g.addVertex(x6);
		g.addVertex(x7);
		g.addVertex(x8);
		
		g.addVertex(y1);
		g.addVertex(y2);
		
		createQuarterHarborthV2(g, x2, x1, x3, x4, y1, "a", EDGE_DEFAULT_LENGTH);
		createQuarterHarborthV2(g, x6, x5, x3, x4, y2, "b", EDGE_DEFAULT_LENGTH);
		createQuarterHarborthV2(g, x6, x5, x7, x8, y2, "c", EDGE_DEFAULT_LENGTH);
		createQuarterHarborthV2(g, x2, x1, x7, x8, y1, "d", EDGE_DEFAULT_LENGTH);
		
		showGraph(g);
	}
	
	@Test
	public void testHarborthV3() throws Exception {
		WeightedGraph<String, WeightedEdge> g = new SimpleWeightedGraph<>(WeightedEdge.class);
		
		String x1 = "x1";
		String x2 = "x2";
		
		String x3 = "x3";
		String x4 = "x4";
		
		String x5 = "x5";
		String x6 = "x6";
		
		String x7 = "x7";
		String x8 = "x8";
		
		String y1 = "y1";
		
		g.addVertex(x1);
		g.addVertex(x2);
		g.addVertex(x3);
		g.addVertex(x4);
		g.addVertex(x5);
		g.addVertex(x6);
		g.addVertex(x7);
		g.addVertex(x8);
		
		g.addVertex(y1);
		
		createQuarterHarborthV2(g, x2, x1, x3, x4, y1, "a", EDGE_DEFAULT_LENGTH);
		createQuarterHarborth(g, x6, x5, x3, x4, "b", EDGE_DEFAULT_LENGTH);
		createQuarterHarborth(g, x6, x5, x7, x8, "c", EDGE_DEFAULT_LENGTH);
		createQuarterHarborthV2(g, x2, x1, x7, x8, y1, "d", EDGE_DEFAULT_LENGTH);
		
		showGraph(g);
	}
	

	public GraphLayouter<String, WeightedEdge> showGraph(WeightedGraph<String, WeightedEdge> g) {
		// note undirected edges are printed as: {<v1>,<v2>}
		GraphLayouter<String, WeightedEdge> layouter = new GraphLayouter<>(EDGE_DEFAULT_LENGTH);
		
		layouter.setGraph(g);
		layouter.setCentralVertex("x2");
		
//		layouter.setXZeroVertex("x1");
		
		GraphPanel<String, WeightedEdge> panel = new GraphPanel<>(layouter);
		GraphControls<String, WeightedEdge> controls = new GraphControls<>(layouter);
		
		JFrame frame = new JFrame("Graph layouter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(controls, BorderLayout.NORTH);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		Map<String, Point2D.Double> layout = layouter.getLayout();
		return layouter;
	}
	
	private static void createQuarterHarborth(WeightedGraph<String, WeightedEdge> g, String vA, String vB, String vC, String vD, String prefix, double edgeLength){
		String a2 = prefix+"2";
		String a3 = prefix+"3";
		String a4 = prefix+"4";
		String a5 = prefix+"5";
		String a6 = prefix+"6";
		String a7 = prefix+"7";
		
		String c2 = prefix+"8"; // c2
		String c3 = prefix+"9"; // c3
		
		String d2 = prefix+"a"; // d2
		String d3 = prefix+"b"; // d3
		String d4 = prefix+"c"; // d4
		
				// add the vertices
		g.addVertex(a2);
		g.addVertex(a3);
		g.addVertex(a4);
		g.addVertex(a5);
		g.addVertex(a6);
		g.addVertex(a7);
		
		g.addVertex(c2);
		g.addVertex(c3);
		
		g.addVertex(d2);
		g.addVertex(d3);
		g.addVertex(d4);
		
		// add edges to create a triangle
		connect(g, vA,a2, edgeLength);
		connect(g, vA,a3, edgeLength);
		connect(g, a2,a3, edgeLength);
		connect(g, a2,a4, edgeLength);
		connect(g, a3,a4, edgeLength);
		connect(g, a3,a5, edgeLength);
		connect(g, a4,a5, edgeLength);
		
		connect(g, a4,a6, edgeLength);
		connect(g, a5,a6, edgeLength);
		connect(g, a5,a7, edgeLength);
		connect(g, a7,a6, edgeLength);
		
		connect(g, vB,a2, edgeLength);
		connect(g, vB,c2, edgeLength);
		
		connect(g, vC,c2, edgeLength);
		connect(g, vC,c3, edgeLength);
		
		connect(g, c2,c3, edgeLength);
		connect(g, c2,a6, edgeLength);
		
		connect(g, c3,d2, edgeLength);
		connect(g, c3,d4, edgeLength);
		connect(g, vD,d2, edgeLength);
		connect(g, vD,d3, edgeLength);
		connect(g, d2,d3, edgeLength);
		connect(g, d2,d4, edgeLength);
		connect(g, d3,d4, edgeLength);
		connect(g, d3,a7, edgeLength);
		connect(g, d4,a7, edgeLength);
	}
	
	private static void createQuarterHarborthV2(WeightedGraph<String, WeightedEdge> g, String vA, String vB, String vC, String vD, String vE, String prefix, double edgeLength){
		String a2 = prefix+"2";
		String a3 = prefix+"3";
		String a4 = prefix+"4";
		String a5 = prefix+"5";
		String a6 = prefix+"6";
		String a7 = prefix+"7";
		
		String c2 = prefix+"8"; // c2
		String c3 = prefix+"9"; // c3
		
		String d2 = prefix+"a"; // d2
		String d3 = prefix+"b"; // d3
		String d4 = prefix+"c"; // d4
		
				// add the vertices
		g.addVertex(a2);
		g.addVertex(a3);
		g.addVertex(a4);
		g.addVertex(a5);
		g.addVertex(a6);
		g.addVertex(a7);
		
		g.addVertex(c2);
		g.addVertex(c3);
		
		g.addVertex(d2);
		g.addVertex(d3);
		g.addVertex(d4);
		
		// add edges to create a triangle
		connect(g, vA,a2, edgeLength);
		connect(g, vA,a3, edgeLength);
		connect(g, a2,a3, edgeLength);
		connect(g, a2,a4, edgeLength);
		connect(g, a3,a4, edgeLength);
		connect(g, a3,a5, edgeLength);
		connect(g, a4,a5, edgeLength);
		
		connect(g, a4,a6, edgeLength);
		connect(g, a5,a6, edgeLength);
		connect(g, a5,a7, edgeLength);
		connect(g, a7,a6, edgeLength);
		
		connect(g, vB,a2, edgeLength);
		connect(g, vB,c2, edgeLength);
		
		//connect(g, vC,c2, edgeLength);
		connect(g, vE,c2, edgeLength);
		connect(g, vE,vC, edgeLength);
		
		connect(g, vC,c3, edgeLength);
		
		connect(g, c2,c3, edgeLength);
		connect(g, c2,a6, edgeLength);
		
		connect(g, c3,d2, edgeLength);
		connect(g, c3,d4, edgeLength);
		connect(g, vD,d2, edgeLength);
		connect(g, vD,d3, edgeLength);
		connect(g, d2,d3, edgeLength);
		connect(g, d2,d4, edgeLength);
		connect(g, d3,d4, edgeLength);
		connect(g, d3,a7, edgeLength);
		connect(g, d4,a7, edgeLength);
	}

	private static void connect(WeightedGraph<String, WeightedEdge> g, String v1, String v2, double edgeLength){
		g.addEdge(v1, v2, new WeightedEdgeImpl(edgeLength));
	}
	
	private static void connect(WeightedGraph<String, WeightedEdge> g, String v1, String v2, int edgeLength) {
		connect(g, v1, v2, (double)edgeLength);
	}
}
