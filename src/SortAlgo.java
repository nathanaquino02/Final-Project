import java.util.Arrays;
import java.util.Random;

public class SortAlgo {

    // BubbleSort
    public static void bubbleSort(int arr[], int n) {
        int i, j, temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap arr[j] and arr[j+1]
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            // If no two elements were swapped by inner loop, then break
            if (!swapped)
                break;
        }
    }

    // InsertionSort
    public static void insertionSort(int arr[]) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;
            /* Move elements of arr[0..i-1], that are greater than key, to one position ahead
               of their current position */
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    // Selection Sort
    public static void selectionSort(int arr[]) {
        int n = arr.length;
        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i + 1; j < n; j++)
                if (arr[j] < min_idx)
                    min_idx = j;
            // Swap the found minimum element with the first element
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
    }

    // MergeSort
    public static void merge(int arr[], int l, int m, int r) {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;
        // Create temp arrays
        int L[] = new int[n1];
        int R[] = new int[n2];
        // Copy data to temp arrays
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
        // Merge the temp arrays
        // Initial indices of first and second subarrays
        int i = 0, j = 0;
        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        // Copy remaining elements of L[] if any
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        // Copy remaining elements of R[] if any
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using merge()
    public static void mergeSort(int arr[], int l, int r) {
        if (l < r) {
            // Find the middle point
            int m = l + (r - l) / 2;
            // Sort first and second halves
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

    // HeapSort
    // To heapify a subtree rooted with node i which is an index in arr[]. n is size of heap
    public static void heapify(int arr[], int N, int i) {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2
        // If left child is larger than root
        if (l < N && arr[l] > arr[largest])
            largest = l;
        // If right child is larger than largest so far
        if (r < N && arr[r] > arr[largest])
            largest = r;
        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            // Recursively heapify the affected sub-tree
            heapify(arr, N, largest);
        }
    }

    public static void heapSort(int arr[]) {
        int N = arr.length;
        // Build heap (rearrange array)
        for (int i = N / 2 - 1; i >= 0; i--)
            heapify(arr, N, i);
        // One by one extract an element from heap
        for (int i = N - 1; i > 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }

    // ShellSort
    public static void shellSort(int arr[]) {
        int n = arr.length;
        // Start with a big gap, then reduce the gap
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // Do a gapped insertion sort for this gap size.
            // The first gap elements a[0..gap-1] are already
            // in gapped order keep adding one more element
            // until the entire array is gap sorted
            for (int i = gap; i < n; i += 1) {
                // add a[i] to the elements that have been gap
                // sorted save a[i] in temp and make a hole at
                // position i
                int temp = arr[i];
                // shift earlier gap-sorted elements up until
                // the correct location for a[i] is found
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap)
                    arr[j] = arr[j - gap];
                // put temp (the original a[i]) in its correct
                // location
                arr[j] = temp;
            }
        }
    }

    // Radix Sort
    // A utility function to get maximum value in arr[]
    static int getMax(int arr[], int n) {
        int mx = arr[0];
        for (int i = 1; i < n; i++)
            if (arr[i] > mx)
                mx = arr[i];
        return mx;
    }

    // A function to do counting sort of arr[] according to the digit represented by exp.
    static void countSort(int arr[], int n, int exp) {
        int output[] = new int[n]; // output array
        int i;
        int count[] = new int[10];
        Arrays.fill(count, 0);
        // Store count of occurrences in count[]
        for (i = 0; i < n; i++)
            count[(arr[i] / exp) % 10]++;
        // Change count[i] so that count[i] now contains actual position of this digit in output[]
        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];
        // Build the output array
        for (i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }
        // Copy the output array to arr[], so that arr[] now contains sorted numbers according to
        // current digit
        for (i = 0; i < n; i++)
            arr[i] = output[i];
    }

    // The main function to that sorts arr[] of size n using Radix Sort
    static void radixSort(int arr[], int n) {
        // Find the maximum number to know number of digits
        int m = getMax(arr, n);
        // Do counting sort for every digit. Note that instead of passing digit number, exp is
        // passed. exp is 10^i where i is current digit number
        for (int exp = 1; m / exp > 0; exp *= 10)
            countSort(arr, n, exp);
    }

    // QuickSort
    // A utility function to swap two elements
    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // This function takes last element as pivot, places the pivot element at its correct
    // position in sorted array, and places all smaller to left of pivot and all greater elements
    // to right of pivot
    static int partition(int[] arr, int low, int high) {
        // Choosing the pivot
        int pivot = arr[high];
        // Index of smaller element and indicates the right position of pivot found so far
        int i = (low - 1);
        for (int j = low; j <= high - 1; j++) {
            // If current element is smaller than the pivot
            if (arr[j] < pivot) {
                // Increment index of smaller element
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    // The main function that implements QuickSort
    // arr[] --> Array to be sorted,
    // low --> Starting index,
    // high --> Ending index
    static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // pi is partitioning index, arr[p] is now at right place
            int pi = partition(arr, low, high);
            // Separately sort elements before partition and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    public static void main(String[] args) {
        int[] sizes = {500, 10000, 100000};
        Random random = new Random();

        for (int size : sizes) {
            int[] array = new int[size];
            for (int i = 0; i < size; i++) {
                array[i] = random.nextInt(size);
            }

            System.out.println("Array size = " + size);

            for (int run = 1; run <= 10; run++) {
                int[] bubbleArray = array.clone();
                int[] insertionArray = array.clone();
                int[] selectionArray = array.clone();
                int[] mergeArray = array.clone();
                int[] heapArray = array.clone();
                int[] shellArray = array.clone();
                int[] radixArray = array.clone();
                int[] quickArray = array.clone();

                long startTime, endTime;

                System.out.println("Run #" + run);

                startTime = System.nanoTime();
                bubbleSort(bubbleArray, bubbleArray.length);
                endTime = System.nanoTime();
                System.out.printf("Bubble Sort : %.9f seconds%n", (endTime - startTime) / 1e9);

                startTime = System.nanoTime();
                insertionSort(insertionArray);
                endTime = System.nanoTime();
                System.out.printf("Insertion Sort : %.9f seconds%n", (endTime - startTime) / 1e9);

                startTime = System.nanoTime();
                selectionSort(selectionArray);
                endTime = System.nanoTime();
                System.out.printf("Selection Sort : %.9f seconds%n", (endTime - startTime) / 1e9);

                startTime = System.nanoTime();
                mergeSort(mergeArray, 0, mergeArray.length - 1);
                endTime = System.nanoTime();
                System.out.printf("Merge Sort : %.9f seconds%n", (endTime - startTime) / 1e9);

                startTime = System.nanoTime();
                heapSort(heapArray);
                endTime = System.nanoTime();
                System.out.printf("Heap Sort : %.9f seconds%n", (endTime - startTime) / 1e9);

                startTime = System.nanoTime();
                shellSort(shellArray);
                endTime = System.nanoTime();
                System.out.printf("Shell Sort : %.9f seconds%n", (endTime - startTime) / 1e9);

                startTime = System.nanoTime();
                radixSort(radixArray, radixArray.length);
                endTime = System.nanoTime();
                System.out.printf("Radix Sort : %.9f seconds%n", (endTime - startTime) / 1e9);

                startTime = System.nanoTime();
                quickSort(quickArray, 0, quickArray.length - 1);
                endTime = System.nanoTime();
                System.out.printf("Quick Sort : %.9f seconds%n", (endTime - startTime) / 1e9);

                System.out.println();
            }
        }
    }
}
