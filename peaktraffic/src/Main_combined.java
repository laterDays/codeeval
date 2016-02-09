import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

class Main_combined
{
	public class Graph<T>
	{
		// TODO - change Node to private class
		public class Node
		{
			T value;						// Value this node stores (also its key)
			HashMap<T, Node> directed_edges;	// Uni-directional edge
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
		
		public boolean AddNode (T val)
		{
			if (map.containsKey(val)) return false;
			Node n = new Node (val);
			map.put(val, n);
			return true;
		}
		
		public boolean DeleteNode (T val)
		{
			if (!map.containsKey(val)) return false;
			map.remove(val);
			return true;
		}
		
		public boolean HasNode (T val)
		{
			return map.containsKey(val);
		}
		
		public void AddEdge (T n, T m) throws Exception
		{
			if (!map.containsKey(n)) throw new Exception ("Graph.AddEdge(): cannot create edge from " + n + " to " + m + ". " + n + " does not exist.\n");
			if (!map.containsKey(m)) throw new Exception ("Graph.AddEdge(): cannot create edge from " + n + " to " + m + ". " + m + " does not exist.\n");
			map.get(n).AddEdgeTo(map.get(m));
		}
		
		public void RemoveEdge (T n, T m) throws Exception
		{
			if (!map.containsKey(n)) throw new Exception ("Graph.RemoveEdge(): cannot remove edge from " + n + " to " + m + ". " + n + " does not exist.\n");
			if (!map.containsKey(m)) throw new Exception ("Graph.RemoveEdge(): cannot remove edge from " + n + " to " + m + ". " + m + " does not exist.\n");
			map.get(n).RemoveEdgeTo(map.get(m));
		}
		
		public Set<T> GetNodes ()
		{
			return map.keySet();
		}
		
		public Set<T> GetNeighbors (T n)
		{
			return map.get(n).GetEdges().keySet();
		}
		
		public int Size ()
		{
			return map.size();
		}
	}
	LinkedList<Set<String>> cliques = new LinkedList<Set<String>>();
	
	public void BronKerbosch (Graph<String> G, Set<String> clique, Set<String> possibles, Set<String> seen)
	{

		if (possibles.size() == 0 && seen.size() == 0) cliques.add(clique);
		
		Iterator<String> it = possibles.iterator();
		
		while (it.hasNext())
		{
			String v = it.next();
			
			Set<String> neighbors = G.GetNeighbors(v);
			
			Set<String> new_clique = new HashSet<String> ();	// clique U v
			new_clique.addAll(clique);							// try adding the current
			new_clique.add(v);									// node to the clique
			
			Set<String> new_possibles = new HashSet<String> ();	// possibles AND neighbors
			new_possibles.addAll(possibles);					// the only possible other nodes to build a
			new_possibles.retainAll(neighbors);					// clique with v are its neighbors
			
			Set<String> new_seen = new HashSet<String> ();		// seen AND neighbors
			new_seen.addAll(seen);								// only consider the seen neighbors
			new_seen.retainAll(neighbors);						// for excluding on the next recursion
			
			BronKerbosch(G, new_clique, new_possibles, new_seen);
			
			it.remove();	// possibles.remove(v);
			seen.add(v);	// v has been seen
		}
	}
	
	public void PrintOrderedCliques ()
	{
		// To hold the final ordered list
		List<String> cliques_list = new LinkedList<String>();	
		
		// Order items within a clique
		for (Set<String> set : cliques)
		{
			if (set.size() >= 3 )
			{
				List<String> list = new LinkedList<String>(set); 
				Collections.sort(list);
				String clique_str = list.toString();
				
				cliques_list.add(clique_str.substring(1, clique_str.length()-1));		
			}
		}
		
		// Order the whole list of cliques
		Collections.sort(cliques_list);
		for (String c : cliques_list)
		{
			System.out.println(c);
		}
	}
	
	public LinkedList<Set<String>> GetCliques ()
	{
		return cliques;
	}
	
	public void PrintCliques ()
	{
		for (Set<String> set : cliques)
		{
			for (String s : set)
			{
				System.out.print(s + ", ");
			}
			System.out.println();
		}
	}
	
	public void Run (String filename)
	{
		try 
		{
			// Set up file reading
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			String entry [];
			
			Graph<String> G = new Graph<String>();
			
			// Add strings to the heap as appropriate
			while ((line = bufferedReader.readLine()) != null)
			{
				entry = line.split("\t");

				G.AddNode(entry[1]);
				G.AddNode(entry[2]);
				try
				{
					G.AddEdge(entry[1], entry[2]);
				}
				catch (Exception e)
				{
					System.err.println("Main.Run(), exception: " + e);
				}
			}
			
			BronKerbosch (G, new HashSet<String>(), G.GetNodes(), new HashSet<String>());
			PrintOrderedCliques();
			
			// Close file reader
			bufferedReader.close();
		}
		catch (FileNotFoundException x)
		{
			System.err.println("File not found: " + x);
		}
		catch (IOException x)
		{
			System.err.println("Error reading file: " + x);	
		}
	}
	
	public static void main_combined (String args[])
	{
		Main_combined m = new Main_combined ();
		m.Run (args[0]);
	}
}