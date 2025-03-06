import java.util.Iterator;
public class QuadraticProbingHashTable<AnyType> implements Iterable<AnyType> {
    private static final int DEFAULT_TABLE_SIZE = 13;
    private HashEntry<AnyType>[] array; // the array of elements
    int currentSize; // the number of occupied cells

    // constructors
    public QuadraticProbingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }
    public QuadraticProbingHashTable(int size) {
        allocateArray(size);
        currentSize = 0;
        makeEmpty();
    }

    // check whether an object exists in the hash table by finding its hash value and position
    public boolean contains(AnyType x) {
        int currentPos = findPos(x);
        return isActive(currentPos);
    }
    // insert an object to the hash table, rehash if necessary
    public void insert(AnyType x) {
        int currentPos = findPos(x);
        if (isActive(currentPos)) {
            return;
        }
        array[currentPos] = new HashEntry<>(x, true);
        if (++currentSize > array.length / 2) {
            rehash();
        }
    }
    // "lazy deletion" of an hash entry
    public void remove(AnyType x) {
        int currentPos = findPos(x);
        if (isActive(currentPos)) {
            array[currentPos].isActive = false;
            currentSize--;
        }
    }
    // fetch a given object from the hash table
    public AnyType get(AnyType x) {
        int currentPos = findPos(x);
        if (isActive(currentPos)) {
            return array[currentPos].element;
        }
        return null; // Return null if the object is not found
    }
    // the hash entry, along with its constructors
    private static class HashEntry<AnyType> {
        public AnyType element; // The element
        public boolean isActive; // False if marked deleted



        public HashEntry(AnyType e, boolean i) {
            element = e;
            isActive = i;
        }
    }
    @SuppressWarnings("unchecked")
    // create a new array that is the size of the smallest prime number that is larger than the size of the previous array
    private void allocateArray(int arraySize) {
        array = new HashEntry[nextPrime(arraySize)];
    }

    public void makeEmpty() {
        currentSize = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
    }
    // find whether an array cell is active or deleted.
    private boolean isActive(int currentPosition) {
        return array[currentPosition] != null && array[currentPosition].isActive;
    }

    // find a position for an object using the hash function, utilizing quadratic probing
    private int findPos(AnyType x) {
        int offset = 1;
        int currentPos = myhash(x);
        while (array[currentPos] != null && !array[currentPos].element.equals(x)) {
            currentPos += offset;
            offset += 2;
            if (currentPos >= array.length) {
                currentPos -= array.length;
            }
        }
        return currentPos;
    }

    // rehash
    private void rehash() {
        HashEntry<AnyType>[] oldArray = array;

        allocateArray(nextPrime(2 * oldArray.length));
        currentSize = 0;
        for (HashEntry<AnyType> entry : oldArray) {
            if (entry != null && entry.isActive) {
                insert(entry.element);
            }
        }
    }
    // returns the index the item is going to be sorted based on hashCode
    private int myhash(AnyType x) {
        int hashVal = x.hashCode();
        hashVal %= array.length;
        if (hashVal < 0)
            hashVal += array.length;
        return hashVal;
    }
    // isPrime and nextPrime methods are used to determine the size of the next array during rehashing
    private static int nextPrime(int n) {
        int candidate = n + 1;
        while (!isPrime(candidate)) {
            candidate++;
        }
        return candidate;
    }
    private static boolean isPrime(int n) {
        if (n <= 1)
            return false;
        if (n <= 3)
            return true;
        if (n % 2 == 0 || n % 3 == 0)
            return false;
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0)
                return false;
        }
        return true;
    }

    // iterator
    @Override
    public Iterator<AnyType> iterator() {
        return new HashTableIterator();
    }

    private class HashTableIterator implements Iterator<AnyType> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            while (currentIndex < array.length) {
                if (array[currentIndex] != null && array[currentIndex].isActive) {
                    return true;
                }
                currentIndex++;
            }
            return false;
        }

        @Override
        public AnyType next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return array[currentIndex++].element;
        }
    }
}
