import static org.junit.Assert.assertEquals;

import java.util.EmptyStackException;

import org.junit.Test;

public class mStackTest 
{

	@Test
	public void PushPopSimple()
	{
		mStack s = new mStack();
		int size_i = s.Size();
		int val_i = 1;
		s.Push(val_i);
		assertEquals(1, s.Size());
		
		int val_f = s.Pop();
		assertEquals(val_i, val_f);
		assertEquals(0, s.Size());
	}
	
	@Test(expected = EmptyStackException.class)
	public void PopEmpty()
	{
		mStack s = new mStack();
		for (int i = 0; i < 10; i++)
		{
			s.Push(i);
		}
		for (int i = 0; i < 10; i++)
		{
			s.Pop();
		}
		s.Pop();
	}
	
	@Test
	public void LastInFirstOut ()
	{
		mStack s = new mStack ();
		s.Push(1);
		assertEquals(1, s.Pop());
		s.Push(2);
		s.Push(3);
		assertEquals(3, s.Pop());
	}
	
	@Test
	public void Extend()
	{
		mStack s = new mStack ();
		for (int i = 0; i < 1000; i++)
		{
			s.Push(i);
		}
		for (int i = 999; i >= 0; i--)
		{
			assertEquals(i, s.Pop());
		}
	}
}
