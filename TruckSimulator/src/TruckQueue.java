

public class TruckQueue {
    private QueueNode front;
    private QueueNode rear;
    private int size;

    public TruckQueue() {
        this.front = this.rear = null;
        this.size = 0;
    }
    public void enqueue(Truck truck) {
        QueueNode newNode = new QueueNode(truck);
        if (rear == null) {
            front = rear = newNode;
        }
        else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }
    public Truck dequeue() {
        if (this.isEmpty()) {
            return null;
        }
        Truck truck = front.truck;
        front = front.next;

        if (front == null) {
            rear = null;
        }
        size--;
        return truck;
    }
    public boolean isEmpty() {
        return front == null;
    }

    public Truck peek() {
        if (this.isEmpty()) {
            return null;
        }
        return front.truck;
    }
    public String toString(){
        QueueNode node = this.front;
        String s = "";
        while (node != null) {
            s = s + node.truck.getID() + " , ";
            node = node.next;
        }
        return s;
    }
}
class QueueNode{
    QueueNode next;
    Truck truck;
    public QueueNode (Truck truck){
        this.truck = truck;
        this.next = null;
    }
}