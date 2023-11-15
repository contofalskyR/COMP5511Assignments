import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class HeapSort {
    // Sorts a list using heap sort algorithm. The comparator defines the order of elements.
    
    public static <T> void sort(List<T> list, Comparator<? super T> comparator) {
        int n = list.size();

    // Building a heap from the list
    for (int i = n / 2 - 1; i >= 0; i--)
        heapify(list, n, i, comparator);

    // extracing elements from the heap and place them in the correct position
    for (int i = n - 1; i > 0; i--) {
        // Swap the root (largest value) of the heap with the last element
        Collections.swap(list, 0, i);

    // Call max heapify on the reduced heap to maintain the heap property
    heapify(list, i, 0, comparator);
    }
}


    // Method to ensuring the heap property in subtrees 
    private static <T> void heapify(List<T> list, int n, int i, Comparator<? super T> comparator) {
        int largest = i; // intitializes largest as root
        int left = 2 * i + 1; // index of left child
        int right = 2 * i + 2; // index of right child

        // checks if the left child exists and if it is larger than the root
        if (left < n && comparator.compare(list.get(left), list.get(largest)) > 0)
            largest = left;

        // checks if the right child exists and if it's larger than the current largest
        if (right < n && comparator.compare(list.get(right), list.get(largest)) > 0)
            largest = right;

        // checks if largest is not root, swap and continue heapifying
        if (largest != i) {
            Collections.swap(list, i, largest);

            // recursively heapify the affected subtree
            heapify(list, n, largest, comparator);
        }
    }

}
