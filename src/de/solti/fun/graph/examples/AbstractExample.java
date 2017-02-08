package de.solti.fun.graph.examples;

import de.solti.fun.graph.model.WeightedEdge;
import de.solti.fun.graph.model.WeightedEdgeImpl;
import org.jgrapht.WeightedGraph;

/**
 * Created by andreas on 2/8/17.
 */
public class AbstractExample {

    public static final double EDGE_DEFAULT_LENGTH = 60;

    public static void connect(WeightedGraph<String, WeightedEdge> g, String v1, String v2, double edgeLength){
        g.addEdge(v1, v2, new WeightedEdgeImpl(edgeLength));
    }

    public static void connect(WeightedGraph<String, WeightedEdge> g, String v1, String v2, int edgeLength) {
        connect(g, v1, v2, (double)edgeLength);
    }

    public static void createHalfDoubleMoon(WeightedGraph<String,WeightedEdge> g, String x1, String x2, String prefix, double edgeLength) {
        String center = prefix+"_c";
        String conn1 = prefix+"_d";

        g.addVertex(center);
        g.addVertex(conn1);

        createBird(g, center, x1, conn1, prefix+"a", EDGE_DEFAULT_LENGTH);
        createBird(g, center, conn1, x2, prefix+"b", EDGE_DEFAULT_LENGTH);
    }

    public static void createHalfMoon(WeightedGraph<String,WeightedEdge> g, String x1, String x2, String prefix, double edgeLength) {
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

    public static void createBird(WeightedGraph<String, WeightedEdge> g, String x1, String x2,
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
}
