import java.util.LinkedList;
import java.util.Random;

public class LinkSortAlgo {

    // Merge Sort for linked list
    public static LinkedList<Integer> mergeSort(LinkedList<Integer> list) {
        if (list.size() <= 1) {
            return list;
        }

        int mid = list.size() / 2;
        LinkedList<Integer> left = new LinkedList<>(list.subList(0, mid));
        LinkedList<Integer> right = new LinkedList<>(list.subList(mid, list.size()));

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    private static LinkedList<Integer> merge(LinkedList<Integer> left, LinkedList<Integer> right) {
        LinkedList<Integer> result = new LinkedList<>();
        while (!left.isEmpty() && !right.isEmpty()) {
            if (left.getFirst() <= right.getFirst()) {
                result.add(left.pollFirst());
            } else {
                result.add(right.pollFirst());
            }
        }
        result.addAll(left);
        result.addAll(right);
        return result;
    }

    // Radix Sort for linked list
    public static void radixSort(LinkedList<Integer> list) {
        int max = list.stream().mapToInt(Integer::intValue).max().orElse(0);
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(list, exp);
        }
    }

    private static void countSort(LinkedList<Integer> list, int exp) {
        int[] count = new int[10];
        LinkedList<Integer> output = new LinkedList<>();
        for (int num : list) {
            count[(num / exp) % 10]++;
        }
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            output.addFirst(list.get(i));
        }
        list.clear();
        for (int i = 0; i < output.size(); i++) {
            list.addLast(output.get(i));
        }
    }

    // Quick Sort for linked list using nodes
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    Node head;

    void addNode(int data) {
        if (head == null) {
            head = new Node(data);
            return;
        }

        Node curr = head;
        while (curr.next != null)
            curr = curr.next;

        Node newNode = new Node(data);
        curr.next = newNode;
    }

    Node getLastNode(Node node) {
        while (node != null && node.next != null) {
            node = node.next;
        }
        return node;
    }

    Node partitionLast(Node start, Node end) {
        if (start == end || start == null || end == null)
            return start;

        Node pivot_prev = start;
        Node curr = start;
        int pivot = end.data;

        while (start != end) {
            if (start.data < pivot) {
                pivot_prev = curr;
                int temp = curr.data;
                curr.data = start.data;
                start.data = temp;
                curr = curr.next;
            }
            start = start.next;
        }

        int temp = curr.data;
        curr.data = pivot;
        end.data = temp;

        return pivot_prev;
    }

    void quickSort(Node start, Node end) {
        if (start == null || start == end || start == end.next)
            return;

        Node pivot_prev = partitionLast(start, end);
        quickSort(start, pivot_prev);

        if (pivot_prev != null && pivot_prev == start)
            quickSort(pivot_prev.next, end);
        else if (pivot_prev != null && pivot_prev.next != null)
            quickSort(pivot_prev.next.next, end);
    }

    public static void main(String[] args) {
        int[] sizes = {500, 10000, 100000};
        Random random = new Random();

        for (int size : sizes) {
            System.out.println("Linked List size = " + size);

            // Merge Sort
            System.out.println("\nMerge Sort");
            for (int run = 1; run <= 10; run++) {
                LinkedList<Integer> list = new LinkedList<>();
                for (int i = 0; i < size; i++) {
                    list.add(random.nextInt(size));
                }
                long startTime = System.nanoTime();
                mergeSort(list);
                long endTime = System.nanoTime();
                System.out.printf("Run %-2d: %.6f seconds%n", run, (endTime - startTime) / 1e9);
            }

            // Radix Sort
            System.out.println("\nRadix Sort");
            for (int run = 1; run <= 10; run++) {
                LinkedList<Integer> list = new LinkedList<>();
                for (int i = 0; i < size; i++) {
                    list.add(random.nextInt(size));
                }
                long startTime = System.nanoTime();
                radixSort(list);
                long endTime = System.nanoTime();
                System.out.printf("Run %-2d: %.6f seconds%n", run, (endTime - startTime) / 1e9);
            }

            // Quick Sort
            System.out.println("\nQuick Sort");
            for (int run = 1; run <= 10; run++) {
                LinkSortAlgo list = new LinkSortAlgo();
                for (int i = 0; i < size; i++) {
                    list.addNode(random.nextInt(size));
                }
                Node end = list.getLastNode(list.head);
                long startTime = System.nanoTime();
                list.quickSort(list.head, end);
                long endTime = System.nanoTime();
                System.out.printf("Run %-2d: %.6f seconds%n", run, (endTime - startTime) / 1e9);
            }

            System.out.println();
        }
    }
}
