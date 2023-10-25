import java.util.Random;
import java.util.Arrays;

public class Sorting{

    public static int[] generateRandomSortedList(int size) {
        int[] arr = new int[size];
        Random random = new Random();
        arr[0] = random.nextInt(10);  // Starting with a random number
        for (int i = 1; i < size; i++) {
            // Ensuring the list remains sorted
            arr[i] = arr[i - 1] + random.nextInt(10) + 1;
        }
        return arr;
    }

    //randomizes the contents of a sorted list
    public static int[] randomizeList(int[] arr){
        Random random = new Random();
        int index;
        int toSwap;

        for (int i=0;i<arr.length;i++){
            index = random.nextInt(arr.length-1);
            toSwap=arr[index];
            arr[index]=arr[i];
            arr[i]=toSwap;
        }
        return arr;
    }

    public static void sortAndPrint(int[] A){
        int n = A.length;  
        // creating an array B for sorted numbers and array C for counting
        int[] B = new int[n];
        int[] C = new int[n];

        // For each element in array A
        for (int i = 0; i < n; i++) {
            // initializing counter to 0
            C[i] = 0;
            // check if A[i] is greater than A[j] or if its equal and if its index is less
            for (int j = 0; j < n; j++) {
                if (A[i] > A[j] || (A[i] == A[j] && i < j)) {
                    C[i]++;
                }
            }
        }

        // populating the sorted array B based on the counts in array C
        for (int i = 0; i < n; i++) {
            B[C[i]] = A[i];
        }

        // Printing the original array A
        System.out.println("Original Array: " + Arrays.toString(A));
        // Pring the sorted array B
        System.out.println("Sorted Array: " + Arrays.toString(B));
    }
    

    public static void main(String[] args) {

        // defining the size of the array
        int n = 20;

        //filling array A with unique, random ints
        int[] A = randomizeList(generateRandomSortedList(n));

        System.out.println("\nArray with unique values");

        //sorting the array, A, and printing its contents
        sortAndPrint(A);

        // creatting an array of size n
        int[] A1 = new int[n];

        // object to generate random numbers
        Random rand = new Random();

        // populating the array with random numbers between 0 and n (inclusive)
        for (int i = 0; i < n; i++) {
            A1[i] = rand.nextInt(n + 1);
        }

        //sorting and printing the array A1 with the modified algorithm to account for duplicate values
        System.out.println("\nArray with non-unique values");
        sortAndPrint(A1);



    }
}