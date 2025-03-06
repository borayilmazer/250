import java.util.ArrayList;
// identical to MinHeap except for two inverted signs
public class MaxHeap<AnyType extends Comparable<? super AnyType>> {
    public MaxHeap( ){
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public MaxHeap( int capacity ){
        currentSize = 0;
        array = (AnyType[]) new Comparable[capacity + 1];
    }



    public void insert( AnyType x ){
        if (currentSize == array.length -1){
            enlargeArray(array.length * 2 + 1);
        }
        // Percolate up
        int hole = ++currentSize;
        for (array[0] = x; x.compareTo(array[hole/2]) > 0; hole /= 2){
            array[ hole ] = array[ hole / 2 ];
        }
        array[hole] = x;
    }
    public AnyType findMax( ){
        if (isEmpty()) {
            return null;
        }
        return array[1];

    }
    public AnyType deleteMax( ){
        if(isEmpty()){
            return null;
        }
        AnyType maxItem = findMax();
        array[1] = array[currentSize--];
        percolateDown(1);
        return maxItem;

    }
    public boolean isEmpty( ){
        return currentSize == 0;
    }

    private static final int DEFAULT_CAPACITY = 10;
    private int currentSize; // Number of elements in heap
    private AnyType [ ] array; // The heap array
    private void percolateDown( int hole ){
        int child;
        AnyType tmp = array[hole];
        for( ; hole*2 <= currentSize; hole = child){
            child = hole * 2;
            if (child != currentSize && array[child+1].compareTo(array[child]) > 0){
                child++;
            }
            if (array[child ].compareTo( tmp ) > 0 ){
                array[hole] = array[child];
            }
            else {
                break;
            }
        }
        array[hole] = tmp;
    }
    private void buildHeap( ){
        for( int i = currentSize / 2; i > 0; i-- )
            percolateDown( i );
    }

    @SuppressWarnings("unchecked")
    private void enlargeArray( int newSize ){
        AnyType[] oldArray = array;
        array = (AnyType[]) new Comparable[newSize];
        System.arraycopy(oldArray, 0, array, 0, oldArray.length);
    }
}


