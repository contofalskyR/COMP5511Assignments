// Java program for implementation of Heap Sort

import java.io.*;

public class heapSort {
	
	// To heapify a subtree rooted with node i which is
	// an index in arr[]. n is size of heap
	static void heapify(int arr[], int n, int i)
	{
		int largest = i; // Initialize largest as root
		int l = 2 * i + 1; // left = 2*i + 1
		int r = 2 * i + 2; // right = 2*i + 2

		// If left child is smaller than root
		if (l < n && arr[l] > arr[largest])
			largest = l;

		// If right child is smaller than largest so far
		if (r < n && arr[r] > arr[largest])
			largest = r;

		// If largest is not root
		if (largest != i) {
			int temp = arr[i];
			arr[i] = arr[largest];
			arr[largest] = temp;

			// Recursively heapify the affected sub-tree
			heapify(arr, n, largest);
		}
	}

	// main function to do heap sort
	static void heapSort(int arr[], int n)
	{
		// Build heap (rearrange array)
		for (int i = n / 2 - 1; i >= 0; i--)
			heapify(arr, n, i);

		// One by one extract an element from heap
		for (int i = n - 1; i >= 0; i--) {
			
			// Move current root to end
			int temp = arr[0];
			arr[0] = arr[i];
			arr[i] = temp;

			// call min heapify on the reduced heap
			heapify(arr, i, 0);
		}
	}

	/* A utility function to print array of size n */
	static void printArray(int arr[], int n)
	{
		for (int i = 0; i < n; ++i)
			System.out.print(arr[i] + " ");
		System.out.println();
	}

	// Driver program
	public static void main(String[] args)
	{
		int arr[] = { 4, 6, 3, 2, 9 };
		int n = arr.length;

		heapSort(arr, n);

		System.out.println("Sorted array is ");
		printArray(arr, n);
	}
}

// This code is contributed by vt_m.
