public class ArrayQueue<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private Object[] data;
    private int size;
    private int front;

    public ArrayQueue() {
        data = new Object[DEFAULT_CAPACITY];
        // type erasure happens at runtime so we can;t use private T[] data; because when new T[10] is executed at run time jvm don't no more know about T 
        size = 0;
        front = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    public T first() {
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        return (T) data[front];
    }

    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (isEmpty()) return null;
        T ans = (T) data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        return ans;
    }

    public void enqueue(T e) {
        if (size == data.length) resize(2 * data.length);
        int avail = (front + size) % data.length;
        data[avail] = e;
        size++;
    }

    private void resize(int cap) {
        Object[] old = data;
        data = new Object[cap];
        int walk = front;
        for (int i = 0; i < size; i++) {
            data[i] = old[walk];
            walk = (walk + 1) % old.length;
        }
        front = 0;
    }
}