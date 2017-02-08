package de.solti.fun.graph.examples;

import de.solti.fun.graph.model.WeightedEdge;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

/**
 * Created by andreas on 2/8/17.
 */
public class Harborth extends AbstractExample {

    public WeightedGraph<String, WeightedEdge> getHarborth() {
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

        return g;
    }
    public WeightedGraph<String, WeightedEdge> getHarborthV2(){
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
        return g;
    }

    public void createQuarterHarborth(WeightedGraph<String, WeightedEdge> g, String vA, String vB, String vC, String vD, String prefix, double edgeLength){
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

    public void createQuarterHarborthV2(WeightedGraph<String, WeightedEdge> g, String vA, String vB, String vC, String vD, String vE, String prefix, double edgeLength){
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
        AbstractExample.connect(g, vA,a2, edgeLength);
        AbstractExample.connect(g, vA,a3, edgeLength);
        AbstractExample.connect(g, a2,a3, edgeLength);
        AbstractExample.connect(g, a2,a4, edgeLength);
        AbstractExample.connect(g, a3,a4, edgeLength);
        AbstractExample.connect(g, a3,a5, edgeLength);
        AbstractExample.connect(g, a4,a5, edgeLength);

        AbstractExample.connect(g, a4,a6, edgeLength);
        AbstractExample.connect(g, a5,a6, edgeLength);
        AbstractExample.connect(g, a5,a7, edgeLength);
        AbstractExample.connect(g, a7,a6, edgeLength);

        AbstractExample.connect(g, vB,a2, edgeLength);
        AbstractExample.connect(g, vB,c2, edgeLength);

        //connect(g, vC,c2, edgeLength);
        AbstractExample.connect(g, vE,c2, edgeLength);
        AbstractExample.connect(g, vE,vC, edgeLength);

        AbstractExample.connect(g, vC,c3, edgeLength);

        AbstractExample.connect(g, c2,c3, edgeLength);
        AbstractExample.connect(g, c2,a6, edgeLength);

        AbstractExample.connect(g, c3,d2, edgeLength);
        AbstractExample.connect(g, c3,d4, edgeLength);
        AbstractExample.connect(g, vD,d2, edgeLength);
        AbstractExample.connect(g, vD,d3, edgeLength);
        AbstractExample.connect(g, d2,d3, edgeLength);
        AbstractExample.connect(g, d2,d4, edgeLength);
        AbstractExample.connect(g, d3,d4, edgeLength);
        AbstractExample.connect(g, d3,a7, edgeLength);
        AbstractExample.connect(g, d4,a7, edgeLength);
    }
}
