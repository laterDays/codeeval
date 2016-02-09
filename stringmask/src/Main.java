import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/*
 * String Mask - https://www.codeeval.com/open_challenges/199/
 * Problem - Read lines from a text file. Each line consists of
 * a word followed by a binary code. The binary code is a mask
 * where the value of 1 indicates a letter should be capitalized.
 * For example, given: 
 * 		monster 1100001 
 * Output: MOnsteR
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
			
			String line;
			String section [];
			StringBuilder word;
			
			// Add strings to the heap as appropriate
			while ((line = bufferedReader.readLine()) != null)
			{
				section = line.split("\\s");
				word = new StringBuilder(section[0]);
				
				for (int i = 0; i < section[1].length(); i++)
				{
					
					if (section[1].charAt(i) == '1')
					{
						word.setCharAt(i, Character.toUpperCase(word.charAt(i)));
					}
				}
				System.out.println(word);
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