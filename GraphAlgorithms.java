package com.company.homework10;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Emily Hang
 * @version 1.0
 * @userid ehang3
 * @GTID 903590279
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Both the start vertex and graph can not be null.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex has to exist in the graph");
        }
        List<Vertex<T>> list = new ArrayList<>();
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        list.add(start);
        visitedSet.add(start);
        queue.add(start);
        while (queue.size() != 0) {
            Vertex<T> v = queue.poll();
            for (VertexDistance<T> w1: adjList.get(v)) {
                Vertex<T> w2 = w1.getVertex();
                if (!visitedSet.contains(w2)) {
                    list.add(w2);
                    visitedSet.add(w2);
                    queue.add(w2);
                }
            }
        }
        return list;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Both the start vertex and graph can not be null.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex has to exist in the graph");
        }
        List<Vertex<T>> list = new ArrayList<>();
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        dfs(start, list, visitedSet, adjList);
        return list;
    }

    /**
     * helper recursive method for depth first search.
     * @param start starting vertex
     * @param list list that holds all vertices that have been traversed
     * @param visitedSet set holding previously visited vertices
     * @param adjList list of adjacent vertices to a vertex
     * @param <T> generic type of data
     */
    private static <T> void dfs(Vertex<T> start, List<Vertex<T>> list,
                                Set<Vertex<T>> visitedSet, Map<Vertex<T>, List<VertexDistance<T>>> adjList) {
        list.add(start);
        visitedSet.add(start);
        for (VertexDistance<T> vertDist : adjList.get(start)) {
            if (!visitedSet.contains(vertDist.getVertex())) {
                dfs(vertDist.getVertex(), list, visitedSet, adjList);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Both the start vertex and graph can not be null.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex has to exist in the graph");
        }
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();
        Queue<VertexDistance<T>> pq = new PriorityQueue<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        for (Vertex<T> vertex: graph.getVertices()) {
            distanceMap.put(vertex, Integer.MAX_VALUE);
        }
        pq.add(new VertexDistance<>(start, 0));
        while (pq.size() != 0 && visitedSet.size() < graph.getVertices().size()) {
            VertexDistance<T> vertexDistance = pq.poll();
            Vertex<T> a = vertexDistance.getVertex();
            int distance = vertexDistance.getDistance();
            if (!visitedSet.contains(a)) {
                visitedSet.add(a);
                distanceMap.put(a, distance);
                for (VertexDistance<T> vd: adjList.get(a)) {
                    if (!visitedSet.contains(vd.getVertex())) {
                        pq.add(new VertexDistance<>(vd.getVertex(), distance + vd.getDistance()));
                    }
                }
            }
        }
        return distanceMap;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph can not be null.");
        }
        DisjointSet<T> disjointSet = new DisjointSet<>();
        Set<Edge<T>> mst = new HashSet<>();
        Set<Edge<T>> edgesMST = new HashSet<>();
        for (Edge<T> edge: graph.getEdges()) {
            if (!edgesMST.contains(flip(edge))) {
                edgesMST.add(edge);
            }
        }
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>(edgesMST);
        while (pq.size() > 0 && mst.size() < graph.getEdges().size() - 1) {
            Edge<T> edge = pq.poll();
            if (disjointSet.find(edge.getU()) != disjointSet.find(edge.getV())) {
                mst.add(edge);
                mst.add(flip(edge));
                disjointSet.union(edge.getU().getData(), edge.getV().getData());
            }
        }
        if (mst.size() != 2 * (graph.getVertices().size() - 1)) {
            return null;
        }
        return mst;
    }
    /**
     * Flips an edge by switching u and v
     * @param edge the edge that is being flipped
     * @param <T> the generic typing of the data
     * @return the flipped edge
     */
    private static <T> Edge<T> flip(Edge<T> edge) {
        Edge<T> flipped = new Edge<>(new Vertex<>(edge.getV().getData()), new Vertex<>(edge.getU().getData()), edge.getWeight());
        return flipped;
    }
}
