import java.util.Collections;
import java.util.List;

class HeapSort {
    // Sorts a list using heap sort algorithm, relying on the natural ordering of the elements (compareTo).
    
    public static <T extends Comparable<T>> void sort(List<T> list) {
        int n = list.size();

        // Building a heap from the list
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(list, n, i);
        }

        // Extracting elements from the heap and placing them in the correct position
        for (int i = n - 1; i > 0; i--) {
            // Swap the root (largest value) of the heap with the last element
            Collections.swap(list, 0, i);

            // Call max heapify on the reduced heap to maintain the heap property
            heapify(list, i, 0);
        }
    }

    // Method to ensure the heap property in subtrees
    private static <T extends Comparable<T>> void heapify(List<T> list, int n, int i) {
        int largest = i; // initializes largest as root
        int left = 2 * i + 1; // index of left child
        int right = 2 * i + 2; // index of right child

        // checks if the left child exists and if it is larger than the root
        if (left < n && list.get(left).compareTo(list.get(largest)) > 0)
            largest = left;

        // checks if the right child exists and if it's larger than the current largest
        if (right < n && list.get(right).compareTo(list.get(largest)) > 0)
            largest = right;

        // checks if largest is not root, swap and continue heapifying
        if (largest != i) {
            Collections.swap(list, i, largest);

            // recursively heapify the affected subtree
            heapify(list, n, largest);
        }
    }
}
