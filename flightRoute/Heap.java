import java.util.*;

public class Heap<T> {

    private final ArrayList<T> heap;
    private final Comparator<T> compare;

    public Heap(Comparator<T> compare, List<T> data) {
        this.compare = compare;
        heap = new ArrayList<>(data);
        heapify();
    }

    public Heap(Comparator<T> compare) {
        this.compare = compare;
        heap = new ArrayList<>();
    }

    private void heapify() {
        for (int i = heap.size() / 2 - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    private void siftDown(int i) {
        int smallest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < heap.size() && compare.compare(heap.get(left), heap.get(smallest)) < 0)
            smallest = left;

        if (right < heap.size() && compare.compare(heap.get(right), heap.get(smallest)) < 0)
            smallest = right;

        if (smallest != i) {
            swap(i, smallest);
            siftDown(smallest);
        }
    }

    private void upHeap(int j) {
        if (j == 0) return;
        int p = (j - 1) / 2;
        if (compare.compare(heap.get(j), heap.get(p)) < 0) {
            swap(j, p);
            upHeap(p);
        }
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public void insert(T val) {
        heap.add(val);
        upHeap(heap.size() - 1);
    }

    public T extract() {
        if (heap.isEmpty()) return null;
        swap(0, heap.size() - 1);
        T res = heap.remove(heap.size() - 1);
        siftDown(0);
        return res;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public T top() {
        return heap.get(0);
    }
}
