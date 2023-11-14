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

        //Using a partially sorted array means the bubble sort exits far more quickly
        if (n>1000){
            for (int i = n/2; i>100;i/=2){
                swapsComparisons = insertionSort(arr, i);
                swaps+=swapsComparisons[0]; comparisons+=swapsComparisons[1];
            }

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


    public static long[] selectionSort(int[] arr,int start,int end){
        long comparisons = 0;
        long swaps = 0;
        long swapsComparisons[] = new long[]{swaps,comparisons};
        int minimum;

        if((end-start)>100){
            int mid = (start+end)/2;

            swapsComparisons = selectionSort(arr,start,mid);
            swaps+=swapsComparisons[0];
            comparisons+=swapsComparisons[1];

            swapsComparisons = selectionSort(arr,mid,end);
            swaps+=swapsComparisons[0];
            comparisons+=swapsComparisons[1];
            
            merge(arr, new int[arr.length], start, end, mid);
        }else{

            for (int i=start;i<end;i++){
                minimum = i;
                for (int j=i+1;j<end;j++){
                    comparisons++;
                    if (arr[j]<arr[minimum]){
                        minimum=j;   
                    }
                }
                //This improvement is only helpful
                //when swaps are expensive.
                if(minimum!=i){
                    swap(arr,i,minimum);
                    swaps++;
                }
            }
        }


        return new long[]{swaps,comparisons};
        
    }


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

        for (i=incr;i<n;i+=1){
            for (j = i; j>=incr && arr[j]<=arr[j-incr];j-=incr){
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

    public static long[] shellSort(int[] arr){
        long[] swapsComparisons = new long[]{0,0};
        long swaps=0;
        long comparisons=0;

        int n = arr.length;
        for(int i=n/2;i>3;i/=2){
            swapsComparisons = insertionSort(arr, i);
            swaps += swapsComparisons[0];
            comparisons += swapsComparisons[1];
        }
        swapsComparisons = insertionSort(arr, 1);
        swaps += swapsComparisons[0];
        comparisons += swapsComparisons[1];

        return new long[]{swaps,comparisons};
    }

    public static long[] quickSort(int[] arr, int start, int end){
        long swaps = 0;
        long comparisons = 0;
        long[] swapsComparisonsPivot = new long[]{0,0,0};

        if(end-start<10){
            long[] swapsComparisons = insertionSort(arr, start, end);
            swaps+=swapsComparisons[0];
            comparisons+=swapsComparisons[1];
        }else{
            swapsComparisonsPivot = pivotHelper(arr, start, end);
            int pivot = (int)swapsComparisonsPivot[2];
            swaps+= swapsComparisonsPivot[0];
            comparisons+=swapsComparisonsPivot[1];

            quickSort(arr,start,pivot);            
            quickSort(arr,pivot+1,end);
        }
        return new long[]{swaps,comparisons};
    }

    public static long[] pivotHelper(int[] arr, int start, int end){
        int pivotCount = start;
        int pivot=arr[start];
        long swaps = 0;
        long comparisons = 0;

        for(int i=start+1;i<end;i++){
            comparisons++;
            if (pivot>arr[i]){
                pivotCount++;
                swap(arr,pivotCount,i);
                swaps++;
            }
        }
        swaps++;
        swap(arr,start,pivotCount);

        long longpc = (long) pivotCount;

        return new long[]{swaps,comparisons,longpc};
    }

    //Given an array and two indices, this function sorts all
    //elements that fall between the indices (start inclusive)
    //This is the basic insertion sort, without shellsort functionality
    public static long[] insertionSort(int[]arr,int start,int end){
        long comparisons=0;
        long swaps = 0;
        int i;
        int j;

        for(i=start+1;i<end;i++){
            for(j=i;j>start && arr[j]<arr[j-1];j--){
                swap(arr,j,j-1);
                swaps++;
                comparisons++;
                //adding to the comparison counter
                
            }
            if(j>start){
                //if j>start, we know that the comparison 
                //arr[j]<arr[j-1] took place
                comparisons++;
            }
        }
        return new long[]{swaps,comparisons};
    }


    /*merge()
    Takes in two arrays the indexes of the start, end, and middle 
    of those arrays that will be merged together.

    The two arrays should be longer than or equal to the variable end.

    The array "arr" should be sorted from indexes start-mid and mid+1-end

    By the end of this function, the first array passed as argument will be sorted
    in ascending order
    */
    public static long[] merge(int[] arr, int[] temp, int start, int end, int mid){
        //this keeps track of the comparisons done
        long comparisons=0;

        //this keeps track of the swaps
        long swaps=0;

        //this keeps count of the first sub array from start-mid
        int count1=start;

        //this keeps count of the second sub array from mid+1-end
        int count2=mid+1;

        //this keeps count of final array from start-end
        int finalCount=start;

                
        //copying the elements from array start-end into temp
        for (int i=start; i<end; i++){
            temp[i]=arr[i];
            swaps++;

        }

        //this runs while both sub arrays still have elements
        //which have not yet been placed into the final array (arr)
        while(count1<=mid && count2<end){

            //if the first sub array has the smaller element,
            //the element gets added to the final array and 
            //the counters for the first sub array and the
            //final array are incremented
            if(temp[count1]<temp[count2]){
                arr[finalCount]=temp[count1];
                finalCount++;
                count1++;

            //if the second sub array element is 
            //smaller than or equal to the first sub
            //array element, we do the same procedure as
            //above with the second sub array
            }else{
                arr[finalCount]=temp[count2];
                finalCount++;
                count2++;
            }
            comparisons++;
            swaps++;
        }


        //if the first sub array still has elements
        //which have not been sorted into the final array,
        //the rest of the first sub array gets copied into 
        //the final array
        while(count1<=mid){
            arr[finalCount]=temp[count1];
            finalCount++;
            count1++; 
            swaps++;
        }

        //if the second sub array still has elements
        //which have not been sorted into the final array,
        //the rest of the second sub array gets copied into 
        //the final array
        while(count2<end){
            arr[finalCount]=temp[count2];
            finalCount++;
            count2++;
            swaps++;
        }
        
        return new long[]{swaps,comparisons};
    }

    public static long[] mergeSort(int[] arr,int[] temp, int start, int end){
        
        
        //This keeps track of the number of comparisons done while sorting the list
        long comparisons=0;

        //this keeps trackof the number of swaps
        long swaps = 0;

        //this holds both variables for ease of return
        long[] swapsComparisons = new long[]{swaps,comparisons};

        //this keeps an index of the middle element in the array (or sub array)
        int mid = (start+end)/2;

        //sub array is smaller than 10 elements, do 
        //an insertion sort 
        if(end-start<10){
            swapsComparisons = insertionSort(arr, start, end);
            swaps+=swapsComparisons[0];
            comparisons+=swapsComparisons[1];

        }else{
            //merge sorting both halfs of the array when it is <10 items long
            //adding the comparisons and swaps to the running tally
            swapsComparisons = mergeSort(arr, temp, start, mid);
            swaps+=swapsComparisons[0];
            comparisons+=swapsComparisons[1]; 

            swapsComparisons = mergeSort(arr, temp, mid, end);
            swaps+=swapsComparisons[0];
            comparisons+=swapsComparisons[1];

            //merging the final two sorted arrays together
            swapsComparisons = merge(arr,temp,start,end,mid-1);
            swaps+=swapsComparisons[0];
            comparisons+=swapsComparisons[1];
            
        }

        
        

        return new long[]{swaps,comparisons};

    }

    //need to add a pivot selection helper. Is that enough of an improvement?

    public static int[] makeArray(int size, int lowerBound, int upperBound){
        int[] arr = new int[size];
        Random random = new Random();
        //this creates a backwards array
        /*for (int i=0;i<size;i++){
            arr[i]=i;
        } 
        for(int i = 0; i < size / 2; i++)
        {
            int temp = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = temp;
        }*/
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
        //System.out.println(Arrays.toString(array1));
        //System.out.println();

        int[] array2 = makeArray(1000, 1, 9999);
        swapsComparisons = bubbleSort(array2);
        System.out.println("Bubble swaps on size 1000: "+swapsComparisons[0]);
        System.out.println("Bubble comparisons on size 1000: "+swapsComparisons[1]);

        int[] array3 = makeArray(10000, 1, 9999);
        swapsComparisons = bubbleSort(array3);
        System.out.println("Bubble swaps on size 10000: "+swapsComparisons[0]);
        System.out.println("Bubble comparisons on size 10000: "+swapsComparisons[1]);

        int[] array4 = makeArray(100000, 1, 9999);
        swapsComparisons = bubbleSort(array4);
        System.out.println("Bubble swaps on size 100000: "+swapsComparisons[0]);
        System.out.println("Bubble comparisons on size 100000: "+swapsComparisons[1]);

        int[] array5 = makeArray(1000000, 1, 9999);
        swapsComparisons = bubbleSort(array5);
        System.out.println("Bubble swaps on size 1000000: "+swapsComparisons[0]);
        System.out.println("Bubble comparisons on size 1000000: "+swapsComparisons[1]);

         
        int[] array = makeArray(100, 1, 9999);
        swapsComparisons = selectionSort(array,0,array.length);
        System.out.println("selection swaps on size 100: "+swapsComparisons[0]);
        System.out.println("selection comparisons on size 100: "+swapsComparisons[1]);
        //System.out.println(Arrays.toString(array));
        //System.out.println();

        array = makeArray(1000, 1, 9999);
        swapsComparisons = selectionSort(array,0,array.length);
        System.out.println("selection swaps on size 1000: "+swapsComparisons[0]);
        System.out.println("selection comparisons on size 1000: "+swapsComparisons[1]);

        array = makeArray(10000, 1, 9999);
        swapsComparisons = selectionSort(array,0,array.length);
        System.out.println("selection swaps on size 10000: "+swapsComparisons[0]);
        System.out.println("selection comparisons on size 10000: "+swapsComparisons[1]);

        array = makeArray(100000, 1, 9999);
        swapsComparisons = selectionSort(array,0,array.length);
        System.out.println("selection swaps on size 100000: "+swapsComparisons[0]);
        System.out.println("selection comparisons on size 100000: "+swapsComparisons[1]);

        array = makeArray(1000000, 1, 9999);
        swapsComparisons = selectionSort(array,0,array.length);
        System.out.println("selection swaps on size 1000000: "+swapsComparisons[0]);
        System.out.println("selection comparisons on size 1000000: "+swapsComparisons[1]);

        

        array = makeArray(100, 1, 9999);
        int[] temp = new int[100];
        swapsComparisons = mergeSort(array,temp,0,array.length);
        System.out.println("merge swaps on size 100: "+swapsComparisons[0]);
        System.out.println("merge comparisons on size 100: "+swapsComparisons[1]);
        //System.out.println(Arrays.toString(array));
        //System.out.println();

        array = makeArray(1000, 1, 9999);
        temp = new int[1000];
        swapsComparisons = mergeSort(array,temp,0,array.length);
        System.out.println("merge swaps on size 1000: "+swapsComparisons[0]);
        System.out.println("merge comparisons on size 1000: "+swapsComparisons[1]);

        array = makeArray(10000, 1, 9999);
        temp = new int[10000];
        swapsComparisons = mergeSort(array,temp,0,array.length);
        System.out.println("merge swaps on size 10000: "+swapsComparisons[0]);
        System.out.println("merge comparisons on size 10000: "+swapsComparisons[1]);

        array = makeArray(100000, 1, 9999);
        temp = new int[100000];
        swapsComparisons = mergeSort(array,temp,0,array.length);
        System.out.println("merge swaps on size 100000: "+swapsComparisons[0]);
        System.out.println("merge comparisons on size 100000: "+swapsComparisons[1]);

        array = makeArray(1000000, 1, 9999);
        temp = new int[1000000];
        swapsComparisons = mergeSort(array,temp,0,array.length);
        System.out.println("merge swaps on size 1000000: "+swapsComparisons[0]);
        System.out.println("merge comparisons on size 1000000: "+swapsComparisons[1]);

        array = makeArray(100, 1, 9999);
        swapsComparisons = shellSort(array);
        System.out.println("insertion optimized/shell swaps on size 100: "+swapsComparisons[0]);
        System.out.println("insertion optimized/shell comparisons on size 100: "+swapsComparisons[1]);
        //System.out.println(Arrays.toString(array));
        //System.out.println();

        array = makeArray(1000, 1, 9999);
        swapsComparisons = shellSort(array);
        System.out.println("insertion optimized/shell swaps on size 1000: "+swapsComparisons[0]);
        System.out.println("insertion optimized/shell comparisons on size 1000: "+swapsComparisons[1]);

        array = makeArray(10000, 1, 9999);
        swapsComparisons = shellSort(array);
        System.out.println("insertion optimized/shell swaps on size 10000: "+swapsComparisons[0]);
        System.out.println("insertion optimized/shell comparisons on size 10000: "+swapsComparisons[1]);

        array = makeArray(100000, 1, 9999);
        swapsComparisons = shellSort(array);
        System.out.println("insertion optimized/shell swaps on size 100000: "+swapsComparisons[0]);
        System.out.println("insertion optimized/shell comparisons on size 100000: "+swapsComparisons[1]);

        array = makeArray(1000000, 1, 9999);
        swapsComparisons = shellSort(array);
        System.out.println("insertion optimized/shell swaps on size 1000000: "+swapsComparisons[0]);
        System.out.println("insertion optimized/shell comparisons on size 1000000: "+swapsComparisons[1]);

        array = makeArray(100, 1, 9999);
        swapsComparisons = quickSort(array,0,array.length);
        System.out.println("quick swaps on size 100: "+swapsComparisons[0]);
        System.out.println("quick comparisons on size 100: "+swapsComparisons[1]);
        //System.out.println(Arrays.toString(array));
        //System.out.println();

        array = makeArray(1000, 1, 9999);
        swapsComparisons = quickSort(array,0,array.length);
        System.out.println("quick swaps on size 1000: "+swapsComparisons[0]);
        System.out.println("quick comparisons on size 1000: "+swapsComparisons[1]);

        array = makeArray(10000, 1, 9999);
        swapsComparisons = quickSort(array,0,array.length);
        System.out.println("quick swaps on size 10000: "+swapsComparisons[0]);
        System.out.println("quick comparisons on size 10000: "+swapsComparisons[1]);

        array = makeArray(100000, 1, 9999);
        swapsComparisons = quickSort(array,0,array.length);
        System.out.println("quick swaps on size 100000: "+swapsComparisons[0]);
        System.out.println("quick comparisons on size 100000: "+swapsComparisons[1]);

        array = makeArray(1000000, 1, 9999);
        swapsComparisons = quickSort(array,0,array.length);
        System.out.println("quick swaps on size 1000000: "+swapsComparisons[0]);
        System.out.println("quick comparisons on size 1000000: "+swapsComparisons[1]);

        

        


        //System.out.println(Arrays.toString(array));
    }

}