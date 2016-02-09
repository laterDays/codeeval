import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GraphTest {

	@Test
	public void GraphAddDelete () throws Exception
	{
		/* 
		 * Add some nodes, delete some, 
		 * is the size correct? Does the
		 * remaining nodes exist?
		 */
		Graph<Integer> g = new Graph<Integer> ();
		g.AddNode(1);
		assertEquals(1, g.Size());
		g.AddNode(2);
		g.DeleteNode(1);
		assertEquals(1, g.Size());
		assertEquals(true, g.HasNode(2));
	}
	
	@Test
	public void GraphAddExisting ()
	{
		Graph<Integer> g = new Graph<Integer> ();
		g.AddNode(1);
		assertEquals(false, g.AddNode(1));
	}
	
	@Test
	public void GraphDeleteNonexisting ()
	{
		Graph<Integer> g = new Graph<Integer> ();
		assertEquals(false, g.DeleteNode(1));
	}
	
	@Test(expected = Exception.class)
	public void GraphAddEdgeWithoutCorrectNodes1 () throws Exception 
	{
		Graph<Integer> g = new Graph<Integer> ();
		g.AddNode(1);
		g.AddNode(2);
		g.DeleteNode(2);
		g.AddEdge(1, 2);
	}
	
	@Test(expected = Exception.class)
	public void GraphAddEdgeWithoutCorrectNodes2 () throws Exception 
	{
		Graph<Integer> g = new Graph<Integer> ();
		g.AddNode(1);
		g.AddNode(2);
		g.DeleteNode(1);
		g.AddEdge(1, 2);
	}
	
	@Test(expected = Exception.class)
	public void GraphRemoveEdgeWithoutCorrectNodes1 () throws Exception 
	{
		Graph<Integer> g = new Graph<Integer> ();
		g.AddNode(1);
		g.AddNode(2);
		g.DeleteNode(2);
		g.RemoveEdge(1, 2);
	}
	
	@Test(expected = Exception.class)
	public void GraphRemoveEdgeWithoutCorrectNodes2 () throws Exception 
	{
		Graph<Integer> g = new Graph<Integer> ();
		g.AddNode(1);
		g.AddNode(2);
		g.DeleteNode(1);
		g.RemoveEdge(1, 2);
	}

	@Test
	public void NodeConstruct ()
	{
		Graph<Integer> g = new Graph<Integer> ();
		Graph<Integer>.Node n = g.new Node (1);
		assertEquals(1, (int)n.GetValue());
	}
	
	@Test
	public void NodeDirectedEdgeAddDelete ()
	{
		/*
		 * Add 2 directed edges away from node n
		 * and remove one edge. Check that the list of
		 * directed edges is correct and that the 
		 * remaining edge can be referenced from n
		 */
		Graph<Integer> g = new Graph<Integer> ();
		Graph<Integer>.Node n = g.new Node (1);
		Graph<Integer>.Node m = g.new Node (2);
		n.AddEdgeTo(m);
		n.AddEdgeTo(g.new Node (3));
		n.RemoveEdgeTo(m);
		assertEquals(1, n.GetDirectedEdges().size());
		assertEquals(false, n.HasDirectedEdge(2));
	}
	
	@Test
	public void NodeEdgeAddDelete ()
	{
		/*
		 * Create a bi-directional edge between nodes n
		 * and m. Remove one directional edge, is the
		 * bi-directional edge list updated? Try adding
		 * another edge and removing another. Is it 
		 * properly updating?
		 */
		Graph<Integer> g = new Graph<Integer> ();
		Graph<Integer>.Node n = g.new Node (1);
		Graph<Integer>.Node m = g.new Node (2);
		n.AddEdgeTo(m);
		m.AddEdgeTo(n);
		assertEquals(1, n.GetDirectedEdges().size());
		assertEquals(1, m.GetDirectedEdges().size());
		assertEquals(1, n.GetEdges().size());
		n.RemoveEdgeTo(m);
		assertEquals(0, n.GetDirectedEdges().size());
		assertEquals(1, m.GetDirectedEdges().size());
		assertEquals(0, n.GetEdges().size());	
		n.AddEdgeTo(m);
		m.RemoveEdgeTo(n);
		assertEquals(1, n.GetDirectedEdges().size());
		assertEquals(0, m.GetDirectedEdges().size());
		assertEquals(0, n.GetEdges().size());
	}
}
