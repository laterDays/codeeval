
# LongestLines

Solution for the code_eval (https://www.codeeval.com/open_challenges/2/) challenge "Longest Lines". The solution should print the longest lines of a text file to stdout in descending order given a file like the following:

```
3
123 123 123
This is a sentence

Hello!
A
```

The first line is a number that specifies how many of the longest lines to print out. The correct solution is:

```
This is a sentence
123 123 123
Hello!
```

# Solution

My solution was to create a min heap of limited size (it has a max size). Lines are added to the min heap if the size of the heap is less than the max size. If the heap has reached its max size, replace the smallest item in the heap if a line is read that is larger than the min. This way, the heap will contain only the largest lines. In the above example, a min heap of size 3 would be created. It would process the above file in the following fashion:

```
1. Create min heap of size 3
2. Add "123 123 123" to the min heap
3. Add "This is a sentence" to the min heap
4. Add "\n" to the min heap. The min heap has reached its max size.
5. Replace "\n" with "Hello!" because the length of "Hello!" is larger than the length of "\n"
	a. Fix the min heap (ensure children are larger than parents)
6. Do not add "A", its length is less than "Hello!"
```
