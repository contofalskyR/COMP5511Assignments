import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class HeapSort {
    // Sorts a list using heap sort algorithm. The comparator defines the order of elements.
    public static <T> void sort(List<T> list, Comparator<? super T> comparator) {
        // Convert list to array for sorting
        T[] array = (T[]) list.toArray();

        // Sort the array
        sortArray(array, comparator);

        // Clear the original list and add sorted elements back
        list.clear();
        Collections.addAll(list, array);
    }

    // Sorts an array using heap sort algorithm. The comparator defines the order of elements.
    private static <T> void sortArray(T[] array, Comparator<? super T> comparator) {
        int n = array.length;

        // Build a heap from the array
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(array, n, i, comparator);

        // Extract elements from heap and place them in the correct position
        for (int i = n - 1; i > 0; i--) {
            // Swap the root (largest value) of the heap with the last element
            T temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            // Call max heapify on the reduced heap to maintain the heap property
            heapify(array, i, 0, comparator);
        }
    }

    // Method to ensure the heap property in subtree
    // Heapify subtree rooted with node i which is an index in array[]
    private static <T> void heapify(T[] array, int n, int i, Comparator<? super T> comparator) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // Index of left child
        int right = 2 * i + 2; // Index of right child

        // Check if the left child exists and if it is larger than the root
        if (left < n && comparator.compare(array[left], array[largest]) > 0)
            largest = left;

        // Check if the right child exists and if it's larger than the current largest
        if (right < n && comparator.compare(array[right], array[largest]) > 0)
            largest = right;

        // Check if largest is not root, swap and continue heapifying
        if (largest != i) {
            T swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;

            // Recursively heapify to maintain heap property
            heapify(array, n, largest, comparator);
        }
    }
}
