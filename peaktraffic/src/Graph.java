import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 
 * Graph<T> - a simple directed graph structure with one
 * complication: we will consider two types of edges,
 * directed edges and bi-directional edges. For example,
 * 
 * if we have an edge from a to b:
 * 		a ---> b
 * 
 * Then we add an edge from b to a:
 * 		a <--- b
 * 
 * Then, the Graph will register a bidirectional edge
 * between a and b:
 * 		a ---- b
 * 
 * @param <T>
 */
public class Graph<T>
{
	// TODO - change Node to private class
	public class Node
	{
		T value;						// Value this node stores (also its key)
		HashMap<T, Node> directed_edges;// Uni-directional edge
		HashMap<T, Node> edges;			// Bi-directional edge
		
		public Node (T val)
		{
			value = val;
			directed_edges = new HashMap<T, Node>();
			edges = new HashMap<T, Node>();
		}
		
		public T GetValue ()
		{
			return value;
		}
		
		public boolean HasDirectedEdge (T val)
		{
			return directed_edges.containsKey(val);
		}
		
		public HashMap<T, Node> GetDirectedEdges ()
		{
			return directed_edges;
		}
		
		public HashMap<T, Node> GetEdges ()
		{
			return edges;
		}
		
		private void SetBiNeighbor (Node n)
		{
			edges.put(n.GetValue(), n);
		}
		
		private void RemoveBiNeighbor (Node n)
		{
			edges.remove(n.GetValue());
		}
		
		public void AddEdgeTo (Node n)
		{
			directed_edges.put(n.GetValue(), n);	// this -edge-> n
		
			if (n.HasDirectedEdge(value))			// If n -edge-> this
			{			
				edges.put(n.GetValue(), n);			// Add this edge to the list of bi-directional edges
				n.SetBiNeighbor(this);				// Do this for n also!
			}
		}
		
		public void RemoveEdgeTo (Node n)
		{
			directed_edges.remove(n.GetValue());	// this -edge-> n
			
			if (edges.containsKey(n.GetValue()))	// if it was bi-directional
			{
				edges.remove(n.GetValue());			// remove it from the bi-directional list
				n.RemoveBiNeighbor(this);
			}
		}
	}
	
	HashMap<T, Node> map;
	
	public Graph ()
	{
		map = new HashMap<T, Node>();
	}
	
	
	/**
	 * Add a new node to this graph. You must add nodes to the graph before you create 
	 * edges between them.
	 * 
	 * @param val - the value (key) of the node.
	 * @return true if a node was added, false if a node with that value already exists.
	 */
	public boolean AddNode (T val)
	{
		if (map.containsKey(val)) return false;
		Node n = new Node (val);
		map.put(val, n);
		return true;
	}
	
	
	/**
	 * Delete an existing node.
	 * 
	 * @param val - delete the node that contains this value (key).
	 * @return true if a node was deleted, false if the node doesn't exist.
	 */
	public boolean DeleteNode (T val)
	{
		if (!map.containsKey(val)) return false;
		map.remove(val);
		return true;
	}
	
	
	/**
	 * Find out if the graph contains a node with a specific value (key).
	 * 
	 * @param val - value or key we are searching for.
	 * @return true if the node exists.
	 */
	public boolean HasNode (T val)
	{
		return map.containsKey(val);
	}
	
	
	/**
	 * Add a directed edge from Node n to Node m. If an edge exists from m to n, 
	 * the graph will register a bi-directional edge.
	 * 
	 * @param n - origin node
	 * @param m - target node
	 * @throws Exception
	 */
	public void AddEdge (T n, T m) throws Exception
	{
		if (!map.containsKey(n)) throw new Exception ("Graph.AddEdge(): cannot create edge from " + n + " to " + m + ". " + n + " does not exist.\n");
		if (!map.containsKey(m)) throw new Exception ("Graph.AddEdge(): cannot create edge from " + n + " to " + m + ". " + m + " does not exist.\n");
		map.get(n).AddEdgeTo(map.get(m));
	}
	
	
	/**
	 * Remove a directed edge from Node n to Node m. If there was a bi-directional
	 * edge between these nodes, it will be removed from the list of bi-directional
	 * nodes.
	 * 
	 * @param n - origin node
	 * @param m - target node
	 * @throws Exception
	 */
	public void RemoveEdge (T n, T m) throws Exception
	{
		if (!map.containsKey(n)) throw new Exception ("Graph.RemoveEdge(): cannot remove edge from " + n + " to " + m + ". " + n + " does not exist.\n");
		if (!map.containsKey(m)) throw new Exception ("Graph.RemoveEdge(): cannot remove edge from " + n + " to " + m + ". " + m + " does not exist.\n");
		map.get(n).RemoveEdgeTo(map.get(m));
	}
	
	
	/**
	 * Get all the Nodes of the graph as a Set<T>.
	 * 
	 * @return
	 */
	public Set<T> GetNodes ()
	{
		return map.keySet();
	}
	
	
	/**
	 * Get the neighbors (only bi-directional neighbors) of a specific Node.
	 * 
	 * @param n
	 * @return Set<T> or n's neighbors.
	 */
	public Set<T> GetNeighbors (T n)
	{
		return map.get(n).GetEdges().keySet();
	}
	
	
	/**
	 * Returns the number of Nodes contained in this graph.
	 * 
	 * @return - the number of Nodes in the graph.
	 */
	public int Size ()
	{
		return map.size();
	}
}