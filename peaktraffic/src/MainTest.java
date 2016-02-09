import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Test;

public class MainTest {
	
	private boolean CliquesEqual (LinkedList<Set<String>> n, LinkedList<Set<String>> m)
	{
		if (n.size() != m.size()) return false;	// If the list of cliques is different
												// they aren't equal.
		
		for (Set<String> n_set : n)				// Run through all the sets in n
		{
			boolean contains = false;
			for (Set<String> m_set : m)			// Run through all the sets in m
			{
				if (n_set.equals(m_set))		// if we found a match
				{								// note it and break out of the loop
					contains = true;
					break;
				}
			}
			if (!contains) return false;		// if no match was found, return false
		}
		return true;
	}

	@Test
	public void test1() throws Exception
	{
		Main m = new Main ();
		Graph<String> G = new Graph<String>();
		G.AddNode("1");
		G.AddNode("2");
		G.AddNode("3");
		G.AddNode("4");
		try
		{
			G.AddEdge("1", "2"); G.AddEdge("2", "1");
			G.AddEdge("1", "3"); G.AddEdge("3", "1");
			G.AddEdge("2", "3"); G.AddEdge("3", "2");
			G.AddEdge("2", "4"); G.AddEdge("4", "2");
		}
		catch (Exception e)
		{
			System.err.println("Exception: " + e);
		}
		
		m.BronKerbosch (G, new HashSet<String>(), G.GetNodes(), new HashSet<String>());
		
		LinkedList<Set<String>> answer = new LinkedList<Set<String>>();
		Set<String> a = new HashSet<String> ();
		a.add("1"); a.add("2"); a.add("3");
		Set<String> b = new HashSet<String> ();
		b.add("2"); b.add("4");
		answer.add(a);
		answer.add(b);
		
		//m.PrintCliques();
		
		assertEquals(true, CliquesEqual(answer, m.GetCliques()));
	}

	@Test
	public void test2() throws Exception
	{
		Main m = new Main ();
		Graph<String> G = new Graph<String>();
		G.AddNode("1");
		G.AddNode("2");
		G.AddNode("3");
		G.AddNode("4");
		G.AddNode("5");
		G.AddNode("6");
		G.AddNode("7");
		try
		{
			G.AddEdge("1", "2"); G.AddEdge("2", "1");
			G.AddEdge("1", "3"); G.AddEdge("3", "1");
			G.AddEdge("2", "3"); G.AddEdge("3", "2");
			G.AddEdge("1", "7"); G.AddEdge("7", "1");
			G.AddEdge("2", "7"); G.AddEdge("7", "2");
			G.AddEdge("7", "6"); G.AddEdge("6", "7");
			G.AddEdge("6", "3"); G.AddEdge("3", "6");
			G.AddEdge("6", "5"); G.AddEdge("5", "6");
			G.AddEdge("6", "4"); G.AddEdge("4", "6");
			G.AddEdge("5", "3"); G.AddEdge("3", "5");
			G.AddEdge("5", "4"); G.AddEdge("4", "5");
			G.AddEdge("4", "3"); G.AddEdge("3", "4");
		}
		catch (Exception e)
		{
			System.err.println("Exception: " + e);
		}
		
		m.BronKerbosch (G, new HashSet<String>(), G.GetNodes(), new HashSet<String>());
		
		LinkedList<Set<String>> answer = new LinkedList<Set<String>>();
		Set<String> a = new HashSet<String> ();
		a.add("1"); a.add("2"); a.add("3");
		Set<String> b = new HashSet<String> ();
		b.add("1"); b.add("2"); b.add("7");
		Set<String> c = new HashSet<String> ();
		c.add("3"); c.add("4"); c.add("5"); c.add("6");
		Set<String> d = new HashSet<String> ();
		d.add("6"); d.add("7");
		answer.add(a);
		answer.add(b);
		answer.add(c);
		answer.add(d);
		
		//m.PrintCliques();
		
		assertEquals(true, CliquesEqual(answer, m.GetCliques()));
	}
}
