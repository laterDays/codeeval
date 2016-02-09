import java.util.EmptyStackException;
/*
 * mStack - simple stack implementation using an array as the
 * internal data structure. The index "last" will point to the
 * position just after the last item in the stack. Pushing will
 * advance last, popping will retract last.
 */
public class mStack 
{
	int array [];
	int last = 0;
	int max_size = 10;
	
	public mStack ()
	{
		this.array = new int [max_size];
	}
	
	public void Push(int n)
	{
		if (last == max_size - 1) Extend();	// Extend the array when it 
		array[last++] = n;					// is full.
	}
	
	public int Pop()
	{
		if (last == 0) 
		{
			throw new EmptyStackException ();
		}
		int n = array[--last];
		return n;
	}
	
	private void Extend ()
	{
		int temp [] = array;
		max_size = (int)(max_size * 1.5);
		array = new int [max_size];
		System.arraycopy(temp, 0, array, 0, last + 1);
	}
	
	public int Size()
	{
		return last;
	}
}
