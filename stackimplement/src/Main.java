import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/*
 * Stack Implementation - https://www.codeeval.com/open_challenges/9/
 * Problem - Read lines of numbers from a text. For each line, push
 * all the numbers into a stack. Pop all items from the stack but 
 * print only every other number to standard output.
 */
class Main
{
	private void Run (String filename)
	{
		try 
		{
			// Set up file reading
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			mStack stack = new mStack ();
			String line;
			String numbers [];
			
			// Add strings to the heap as appropriate
			while ((line = bufferedReader.readLine()) != null)
			{
				numbers = line.split("\\s");
				
				// Push all numbers into the stack
				for(int i = 0; i < numbers.length; i++)
				{
					stack.Push(Integer.valueOf(numbers[i]));
				}
				
				// Pop all numbers from the stack but
				// print out only every other number.
				boolean flag = true;
				while (stack.Size() > 0)
				{
					if (flag)
					{
						System.out.print(stack.Pop() + " ");
					}
					else
					{
						stack.Pop();
					}
					flag = !flag;
				}
				System.out.println();
			}
			
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