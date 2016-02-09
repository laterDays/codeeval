#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct node
{
	int length;
	char * string;
} Node;

typedef struct heap
{
	Node ** array;
	int size;
	int max_size;
} Heap;

/*

	NODE FUNCTIONS 

*/

// Create a node with the passed string value
Node * NodeInit (char * string)
{
	int c;
	for (c = 0; string[c] != '\0'; c++);
	Node * n = (Node *) malloc (sizeof(Node));
	n->length = c;
	n->string = string;
	return n;
}

// Make a copy of the passed string value
Node * NodeCopy (Node * n)
{
	Node * c = (Node *) malloc (sizeof(Node));
	c->length = n->length;
	c->string = (char *) malloc (sizeof(char) * c->length);
	strcpy(c->string, n->string);
	return c;
}

// Swap the values of two nodes
void NodeSwap (Node * x, Node * y)
{
	Node t = * x;
	*x = *y;
	*y = t;
}

/*

	HEAP FUNCTIONS

*/

// Create an initialize a min-heap of a certain size
Heap * HeapInit (int size)
{
	Heap * heap = (Heap *) malloc (sizeof(Heap));
	heap->array = (Node **) malloc (sizeof(Node *) * (size +  1));
	heap->max_size = size;
	heap->size = 0;
	return heap;
}

void HeapFixUp (Heap * heap, int current_index)
{
	int p_index = current_index / 2;
	if (p_index < 1) return;

	Node * parent = heap->array[p_index];
	Node * current = heap->array[current_index];

	if (parent->length > current->length)
	{
		NodeSwap(parent, current);
		HeapFixUp(heap, p_index);
	}
}

// Ensure a node at the specified index has children
// that are smaller than itself. Recurse down the heap
void HeapFixDown (Heap * heap, int current_index)
{
	Node * current = heap->array[current_index];

	// Check the left child
	if (current_index * 2 >= heap->size + 1) return;	// Ensure child exists
	Node * left = heap->array[current_index * 2];		
	if (left->length < current->length)					// Child less than current?
	{
		NodeSwap(left, current);						// If so, swap current and child
		HeapFixDown(heap, current_index * 2);			// Fix descendants
	}

	// Check the right child
	if ((current_index * 2) + 1 >= heap->size + 1) return;
	Node * right = heap->array[(current_index * 2) + 1];
	if (right->length < current->length)
	{
		NodeSwap(right, current);
		HeapFixDown(heap, (current_index * 2) + 1);
	}
}

// Remove the min from the heap
Node * HeapRemoveMin (Heap * heap)
{
	Node * min = NodeCopy(heap->array[1]);				// Keep reference to min (at heap root)
	NodeSwap(heap->array[1], heap->array[heap->size]);	// swap root with last position
	heap->size--;
	HeapFixDown(heap, 1);								// Fix descendants
	return min;
}

void HeapAddString (Heap * heap, char * string)
{
	Node * n = NodeInit(string);

	if (heap->size < heap->max_size)	// add the node to the heap
	{									// if it isn't full
		heap->array[heap->size + 1] = n;
		heap->size++;
		HeapFixUp(heap, heap->size);
	}
	else
	{
		if (heap->array[1]->length < n->length)	// if it is full, only add
		{										// the node if it has a larger
			heap->array[1] = n;					// value than the min
			HeapFixDown(heap, 1);
		}
	}
}

void HeapDestroy (Heap * heap)
{
	for(int i = 1; i <= heap->size; i++)
	{
		free(heap->array[i]);
	}
	free(heap->array);
	free(heap);
}


char * GetLine (FILE * file)
{
	if (file == NULL)
	{
		printf("Error, file is null.");
		exit(1);
	}

	int chars = 128;
	char * buffer = (char *) malloc (sizeof(char) * chars);

	if (buffer == NULL)
	{
		printf("Error allocating memory for buffer.");
		exit(1);
	}

	char c = getc(file);
	int count = 0;

	while ((c != '\n') && (c != EOF))
	{
		if (count + 1 == chars)
		{
			chars += 128;
			buffer = realloc(buffer, chars);
			if (buffer == NULL)
			{
				printf("Error reallocating memory for buffer.");
				exit(1);
			}
		}
		buffer[count] = c;
		count++;
		c = getc(file);
	}
	if (c == '\n') 
	{
		buffer[count] = '\n';
	}
	else
	{
		buffer[count] = '\0';
	}
	return buffer;
}

int main (int argc, const char **argv)
{
	FILE * file = fopen(argv[1], "r");
	char * line = GetLine(file);
	Heap * heap = HeapInit(atoi(line));

	while(line[0] != '\0')			// Build min-heap
	{
		line = GetLine(file);
		HeapAddString(heap, line);
	}

	int size = heap->size;			
	char * list[size];				 
	int i = size - 1;
	while(heap->size > 0)						// Remove mins from
	{											// the min-heap
		list[i] = HeapRemoveMin(heap)->string;	// and store in the
		i--;									// array in reverse order
	}											// (stored in descending order)
	for (int s = 0; s < size; s++)
	{
		printf("%s", list[s]);
		free(list[s]);
	}

	fclose(file);
	HeapDestroy(heap);
	return 0;
}