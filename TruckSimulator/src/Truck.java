public class Truck {
    private final int ID;
    private final int maxCapacity;
    private int load;
    private int remainingCapacity;
    private int currentCapacity;

    // Constructors
    public Truck(int ID, int maxCapacity){
        this.ID = ID;
        this.maxCapacity = maxCapacity;
        this.load = 0;
        this.remainingCapacity = this.maxCapacity - this.load;
    }
    public Truck(int ID, int maxCapacity, int load){
        this(ID, maxCapacity, load, maxCapacity);
    }

    public Truck(int ID, int maxCapacity, int load, int currentCapacity){
        this.ID = ID;
        this.maxCapacity = maxCapacity;
        this.load = load;
        this.remainingCapacity = maxCapacity - load;
        this.currentCapacity = currentCapacity;
    }

    // Class Methods
    public void updateRemainingCapacity(){
        this.remainingCapacity = maxCapacity - load;
    }
    public int getID(){
        return this.ID;
    }


    public int getLoad() {
        return load;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
    public int getRemainingCapacity(){
        return remainingCapacity;
    }
    // returns the remainder of load after the truck takes the amount it has space for.
    public int loadTruck(int loadAmount){
        if (loadAmount <= currentCapacity){
            this.load = this.load + loadAmount;
            this.remainingCapacity = maxCapacity - load;
            return 0;
        }
        else{
            loadAmount = loadAmount - currentCapacity;
            load += currentCapacity;
            this.remainingCapacity = maxCapacity - load;
            return loadAmount;
        }
    }
}
