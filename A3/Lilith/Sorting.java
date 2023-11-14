import java.util.Random;
import java.util.Arrays;


public class Sorting{

    //Swaps the items at a[i] and a[j]
    public static void swap(int[] a, int i, int j){
        int toSwap=a[j];
        a[j]=a[i];
        a[i]=toSwap;
    }


    //iterates through the array, allowing the largest item to bubble up
    //to the end of the array. Once the array has been sorted (no swaps were made 
    //for an entire run), the program exits.

    //A two item long[] array is returned holding [swaps,comparisons]
    public static long[] bubbleSort(int[] arr){
        int n = arr.length-1;
        long swaps = 0;
        long swapsThisRun = 0;
        long comparisons = 0;
        long swapsComparisons[] = new long[]{0,0};

        if (n>1000){
            swapsComparisons = insertionSort(arr, 5);
            swaps+=swapsComparisons[0]; comparisons+=swapsComparisons[1];
        }

        

        for (int x = n; x>0; x--){
            swapsThisRun = 0;
            for(int i = 0; i<x; i++){
                comparisons++;
                if (arr[i]>arr[i+1]){
                    swap(arr,i,i+1);
                    swapsThisRun++;
                }
            }
            if (swapsThisRun==0){
                //Breaking here brings down the number of comparisons
                break;
            }else{
                swaps+=swapsThisRun;
            }
        }
        return new long[]{swaps,comparisons};
    }


/*


def selectionSort(array):

    for i in range(0,len(array)-1):
        minimum = i

        for j in range(i+1,len(array)):
            if array[j]<array[minimum]:
                minimum = j

        if(minimum!=i):
            swap(array,i,minimum)
*/


    //Is shellsort a good improvement?
    //maybe look at characteristics of the data to decide on the increment?
    //maybe do Lemon's improvement?
    //consider doing insertionsort for the first half of the array.
    public static long[] insertionSort(int[] arr,int incr){
        int n = arr.length;
        long swaps = 0;
        long comparisons = 0;
        int i;
        int j;

        for (i=incr;i<n;i+=incr){
            for (j = i; j>=incr && arr[j]<arr[j-incr];j-=incr){
                swap(arr,j,j-incr);
                swaps++;
                comparisons++;
            }
            if(j>=incr){
                comparisons++;
            }
        }
        return new long[]{swaps,comparisons};
    }

    public static void shellSort(int[] arr){
        int n = arr.length;
        for(int i=n/2;i>2;i/=2){
            for (int j=0;j<i;j++){
                insertionSort(arr, i);
            }
        }
        insertionSort(arr, 1);
    }

    public static void quickSort(int[] arr, int start, int end){
        if(end-start<2){
            return;
        }else{
            int pivot = pivotHelper(arr, start, end);
            quickSort(arr,start,pivot);
            quickSort(arr,pivot+1,end);
        }
    }

    public static int pivotHelper(int[] arr, int start, int end){
        int pivotCount = start;
        int pivot=arr[start];

        for(int i=start+1;i<end;i++){
            if (pivot>arr[i]){
                pivotCount++;
                swap(arr,pivotCount,i);
            }
        }

        swap(arr,start,pivotCount);

        return pivotCount;
    }

    //need to add a pivot selection helper. Is that enough of an improvement?

    public static int[] makeArray(int size, int lowerBound, int upperBound){
        int[] arr = new int[size];
        Random random = new Random();
        for (int i=0 ; i<size; i++){
            arr[i] = random.nextInt(lowerBound,upperBound);
        }
        return arr;
    }

    public static void main(String[] args){
        int[] array1 = makeArray(100, 1, 9999);
        long[] swapsComparisons = bubbleSort(array1);
        System.out.println("Bubble swaps on size 100: "+swapsComparisons[0]);
        System.out.println("Bubble comparisons on size 100: "+swapsComparisons[1]);

        int[] array2 = makeArray(1000, 1, 9999);
        swapsComparisons = bubbleSort(array2);
        System.out.println("Bubble swaps on size 1000: "+swapsComparisons[0]);
        System.out.println("Bubble comparisons on size 1000: "+swapsComparisons[1]);

        int[] array3 = makeArray(10000, 1, 9999);
        swapsComparisons = bubbleSort(array3);
        System.out.println("Bubble swaps on size 10000: "+swapsComparisons[0]);
        System.out.println("Bubble comparisons on size 10000: "+swapsComparisons[1]);

        /*int[] array4 = makeArray(100000, 1, 9999);
        swapsComparisons = bubbleSort(array4);
        System.out.println("Bubble swaps on size 100000: "+swapsComparisons[0]);
        System.out.println("Bubble comparisons on size 100000: "+swapsComparisons[1]);

        int[] array5 = makeArray(1000000, 1, 9999);
        System.out.println("array made");
        swapsComparisons = bubbleSort(array5);
        System.out.println("Bubble swaps on size 1000000: "+swapsComparisons[0]);
        System.out.println("Bubble comparisons on size 1000000: "+swapsComparisons[1]);

        */


        //System.out.println(Arrays.toString(array));
    }

}