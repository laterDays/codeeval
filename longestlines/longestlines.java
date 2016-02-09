import java.io.*;
import java.nio.file.*;

/*
 * Longest Lines - https://www.codeeval.com/open_challenges/2/
 * Problem - Print the longest lines contained in a text file in descending order.
 * The first line of the text file indicates the number of lines to output.
 */
class Main
{
	/*
	 * Heap - this is a limited size minimum heap. Index 0 will contain
	 * nothing. Index 1 will always have the minimum. The left and right
	 * children are located at index * 2 and index * 2 + 1. Strings are
	 * added only if they are longer than the minimum. The result is that
	 * this heap keeps only the largest strings and easily replaces the
	 * smallest string (the min).
	 */
	class Heap
	{
		int size;		// Current heap size
		int max_size;	// Max heap size
		String array[];	// The heap
		
		public Heap (int max_size)
		{
			this.max_size = max_size;
			this.array = new String[max_size + 1];
			this.size = 0;
		}
		
		public void Add (String string)
		{
			if (this.size < this.max_size)			
			{										
				this.array[this.size + 1] = string;	// if the heap has space,
				this.size++;						// add it to the end
				FixUp(this.size);							// then fix the heap
			}
			else
			{
				if (this.array[1].length() < string.length())	// if the heap is full
				{												// add only if the new string
					this.array[1] = string;						// is larger than the min
					FixDown(1);									// add to the top and fix down
				}
			}
		}
		
		public String RemoveMin ()
		{
			String min = this.array[1];
			Swap(1, this.size);
			this.size--;
			FixDown(1);
			return min;
		}
		
		private void Swap (int i, int j)
		{
			String t = this.array[i];
			this.array[i] = this.array[j];
			this.array[j] = t;
		}
		
		private void FixUp (int index)
		{
			int p_index = index / 2;
			if (p_index < 1) return;
			if (this.array[p_index].length() > this.array[index].length())
			{
				Swap(p_index, index);
				FixUp(p_index);
			}
		}
		
		private void FixDown (int index)
		{
			if (index * 2 >= this.size + 1) return;	// make sure the left
			String left = this.array[index * 2];	// child is larger,
			if (left.length() < this.array[index].length())	// if not, fix down
			{
				Swap(index * 2, index);
				FixDown(index * 2);
			}
			
			if ((index * 2 + 1) >= this.size + 1) return;	// make sure the right
			String right = this.array[(index * 2) + 1];		// child is larger
			if (right.length() < this.array[index].length())
			{
				Swap(index * 2 + 1, index);
				FixDown(index * 2 + 1);
			}
		}
		
		public void Print ()
		{
			for (int i = 1; i < this.size + 1; i++)
			{
				System.out.println(this.array[i]);
			}
			System.out.println();
		}
		
		public void PrintDumpOrdered ()
		{
			String rev[] = new String[this.size];
			int s = this.size;
			int r = s - 1;
			String str;
			
			while(r >= 0)
			{
				str = RemoveMin();
				
				rev[r] = str;
				r--;
			}
			while (r < s - 1)
			{
				r++;
				System.out.println(rev[r]);
			}
		}
	}
	
	private void Run (String filename)
	{
		try 
		{
			// Set up file reading
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = bufferedReader.readLine();
			Heap heap = new Heap (Integer.valueOf(line));	// Create heap, size is
															// set by the first line of
															// the input file
			// Add strings to the heap as appropriate
			while ((line = bufferedReader.readLine()) != null)
			{
				heap.Add(line);
			}
			heap.PrintDumpOrdered();
			
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
	
	public static void main (String[] args)
	{
		Main longestlines = new Main ();
		longestlines.Run(args[0]);
	}
}