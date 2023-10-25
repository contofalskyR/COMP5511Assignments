import java.util.Random;

public class FinalSearchWithTabulation{

    private static final int LIST_SIZE = 1000;
    private static final int TEST_COUNT = 10;

    // Normal binary search
    public static int binarySearch(int[] arr, int target, int[] counter) {
        // points to the end of the section of the array which we are searching
        int end = arr.length - 1;

        // points to the beginning of the section of the array which we are searching
        int start = 0;

        // the halfway point in the section we are searching
        int mid = end / 2;

        // loops until we haves searched the entire list
        while (end >= start) {
            counter[0]++;  // Counting the comparison

            // target was found
            if (arr[mid] == target) {
                return mid;
            }

            if (arr[mid] > target) {  // if the variable in the current middle is bigger than the target element
                end = mid - 1;  // cut out the middle and everything after it
            } else {  // variable in middle is smaller than current target
                start = mid + 1;  // cut out middle and everything before it
            }

            // creates new middle between start and end
            mid = (start + end) / 2;
        }
        // if the target was not found
        return -1;
    }

    public static int modifiedBinarySearch(int[] arr, int target, int[] counter) {

        //the index of the end of the part of the list being searched
        int end = arr.length - 1;

        //the index of the start of the part of the list being searched
        int start = 0;

        //the index of the element to be compared to the target element. 
        //Here, the element is 1/5th of the way through the list
        int mid = end / 5;

        //Stores the size of 1/5th of the list
        int oneFifth;

        //Stores the corrent length of the list
        int currLen;

        //Keeps track of whether the most recent search was binary
        boolean wasJustBinary = false;

        //loops until the item is found or the entire list has been searched
        while (end >= start) {
            counter[0]++;  // Counting the comparison

            //sets variable to 1/5th of the list section being searched
            oneFifth = ((end - start) / 5) + 1;
            //If the element is found, return's its index
            if (arr[mid] == target) {
                return mid;
            }

            if(arr[mid]>target){//if the variable in the current middle is bigger than the target element
                end=mid-1; //cut out the middle and everything after it
            }else{//variable in middle is smaller than current target
                start = mid+1;//cut out middle and everything before it
            }

            //set current length now that the section to be searched has changed in size
            currLen = (end - start) + 1;

            /*If the next section to be searched (currLen) is the 4/5ths section (i.e. is bigger than 1/5th)
            and the most recent search was NOT binary, this if statement is executed.
            */
            if (currLen > oneFifth && !wasJustBinary) {
                //Sets mid to halfway between start and end (i.e. binary search)
                mid = (start + end) / 2;
                //sets variable to true so that binary search is not performed 2 times in  a row
                wasJustBinary = true;
            } else {
                //sets mid to 1/5th
                mid = start + (end - start) / 5;
                //sets this variable to false so that binary search is possible in the next iteration
                wasJustBinary = false;
            }
        }
        return -1;
    }

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

    public static void main(String[] args) {
        int binarySuccess = 0;
        int modifiedSuccess = 0;
        int binaryFailure = 0;
        int modifiedFailure = 0;

        for (int i = 0; i < TEST_COUNT; i++) {
            int[] list = generateRandomSortedList(LIST_SIZE);
            // Ensuring the target is within the range of the list
            int target = list[0] + new Random().nextInt(list[list.length-1] - list[0]);

            int[] counter1 = {0};
            int[] counter2 = {0};

            int modifiedResult = modifiedBinarySearch(list, target, counter1);
            int binaryResult = binarySearch(list, target, counter2);

            // Counting successes and failures
            if (modifiedResult != -1) modifiedSuccess++;
            else modifiedFailure++;

            if (binaryResult != -1) binarySuccess++;
            else binaryFailure++;

            System.out.println("Experiment " + (i + 1));
            System.out.println("Target Number: " + target);
            System.out.println("Modified Search Comparisons: " + counter1[0]);
            System.out.println("Binary Search Comparisons: " + counter2[0]);
            System.out.println("-----------------------------");
        }

        System.out.println("Binary Search - Successes: " + binarySuccess + " Failures: " + binaryFailure);
        System.out.println("Modified Search - Successes: " + modifiedSuccess + " Failures: " + modifiedFailure);
    }
}
