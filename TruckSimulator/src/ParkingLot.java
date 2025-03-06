public class ParkingLot {
    TruckQueue ready;
    TruckQueue waiting;
    int capacityConstraint;
    int truckLimit;
    private int currentSize;
    // Constructor
    public ParkingLot(int constraint, int truckLimit){
        this.capacityConstraint = constraint;
        this.truckLimit = truckLimit;
        this.ready = new TruckQueue();
        this.waiting = new TruckQueue();
        this.currentSize = 0;
    }
    // Class Methods
    public void enqueue(Truck truck) {
        if (this.currentSize < this.truckLimit) {
            this.currentSize++;
            this.waiting.enqueue(truck);

        }
    }
    public void moveToReady() {
        if (!this.waiting.isEmpty()) {  // Ensure there's a truck to move
            Truck movingTruck = this.waiting.dequeue();
            this.ready.enqueue(movingTruck);
        }
    }
    public Truck dequeue(){
        this.currentSize-- ;
        return this.ready.dequeue();

    }
    public int getTruckLimit() {
        return truckLimit;
    }
    public int getCurrentSize() {
        return this.currentSize;
    }
}
