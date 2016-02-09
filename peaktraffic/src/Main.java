import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 
 * Peak Traffic - https://www.codeeval.com/open_challenges/49/
 * 
 * Problem - Read an input file like that the included "input.txt" file.
 * The last two emails indicate an interaction between two users, where
 * the first email indicates the actor and the second email indicates the
 * recipient.
 * 
 * Generate a list of maximal cliques where we will only consider
 * cliques of N >= 3 persons. 
 * 
 * Output - sort emails within cliques alphabetically. Print out all 
 * cliques, treating cliques as Strings for ordering purposes.
 *
 */
class Main
{
	LinkedList<Set<String>> cliques = new LinkedList<Set<String>>();
	
	/**
	 * Implementation of the Bron-Kerbosch maximal clique algorithm.
	 * 
	 * @param G - the graph object
	 * @param clique - Set containing the clique we are trying to build
	 * 		in this recursion.
	 * @param possibles - A list of Nodes that may possibly be part 
	 * 		of the clique.
	 * @param seen - A list of Nodes that we have examined and will
	 * 		be excluded from consideration in this clique.
	 */
	public void BronKerbosch (Graph<String> G, Set<String> clique, Set<String> possibles, Set<String> seen)
	{

		if (possibles.size() == 0 && seen.size() == 0) cliques.add(clique);
		
		Iterator<String> it = possibles.iterator();
		
		/*	BronKerbosch (clique, possibles, seen)
		 * 		For each node v which is a candidate for a clique
		 * 			clique' <-- try to build a clique using v
		 * 			possibles' <-- possible members of the clique are v's neighbors
		 * 			seen' <-- don't check nodes that have been examined at this level,
		 * 				(this will prevent us from duplicate cliques)
		 * 
		 * 			BronKerbosch(clique', possibles', seen')
		 * 
		 * 			possibles = possibles - v
		 * 			seen = seen + v
		 * 			
		 */
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
			if (set.size() >= 3)
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
	
				//System.out.println(entry[1] + "-" + entry[2]);
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
	

	
	public static void main (String args[])
	{
		Main m = new Main ();
		m.Run (args[0]);
	}
}