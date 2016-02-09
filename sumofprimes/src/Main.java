import java.util.LinkedList;
/*
 * Sum of Primes - https://www.codeeval.com/open_challenges/4/
 * Problem - Find the sum of prime numbers from 0 to 1000.
 */
class Main
{	
	/*
	 * GetPrimeTotal - get the sum of the first n prime numbers.
	 */
	private static int GetPrimeTotal (int n)
	{
		LinkedList<Integer> primes = new LinkedList<Integer>();
		boolean is_prime = true;
		int prime_total = 0;
		
		if (n > 1) 
		{
			primes.add(2); prime_total += 2;					// Add 2 to the prime list
			if (n > 2)
			{
				primes.add(3); prime_total += 3;				// Add 3 to the prime list
				for (int i = 5; primes.size() < n; i = i + 2)	// Check if i is a prime
				{												// until we have n primes.
					int sqrt = (int)Math.sqrt(i);
					is_prime = true;
					for (Integer p : primes)					// Can i be divided by
					{											// by primes?
						if (p > sqrt) break;					// Check for divisibility only up
																// to sqrt(i)
						if (i % p == 0)							
						{
							is_prime = false;
							break;
						}
					}
					if (is_prime)
					{
						primes.add(i); prime_total += i;
					}
				}
			}
		}
		return prime_total;
	}
	
	public static void main (String args[])
	{
		System.out.println(GetPrimeTotal(1000));
	}
}