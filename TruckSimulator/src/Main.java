import java.io.*;


public class Main {
    public static void main(String[] args) {
        // Store parking lots in a large-enough array, also store the capacity constraints in an AVL tree for easy traversals
        ParkingLot[] pLots = new ParkingLot[550000];
        AVLTree pLotsTree = new AVLTree();
        // Read the file line by line, parse the line and execute the correct method.

        try {
            FileReader inputFile = new FileReader(args[0]);
            BufferedReader reader = new BufferedReader(inputFile);
            FileWriter outputFile = new FileWriter(args[1]);
            BufferedWriter writer = new BufferedWriter(outputFile);
            String line;
            while ((line = reader.readLine()) != null) {
                String s = parseAndExecute(line, pLotsTree, pLots);
                // parse the line, execute the correct method with the correct parameters using parseAndExecute
                if (s !=null){
                    writer.write(s);
                    writer.newLine();
                }
            }
            reader.close();
            writer.close(); // avoid resource leaks
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // parses a string, takes in the correct keywords and parameters, executes the chosen method and returns the result as a string.
    public static String parseAndExecute(String line, AVLTree pLotsTree, ParkingLot[] pLots) {
        String[] parts = line.split("\\s+");
        String command = parts[0];
        try {
            // inelegant switch statement that addresses the methods for the word part of the text line
            switch (command) {
                // create a new parking lot
                    case "create_parking_lot":
                    if (parts.length == 3){
                        int number1 = Integer.parseInt(parts[1]);
                        int number2 = Integer.parseInt(parts[2]);
                        // insert the parking lot to pLots, also kep the capacity constraint in pLotsTree
                        pLots[number1] = new ParkingLot(number1, number2);
                        // create and insert the node in AVL tree
                        pLotsTree.insert(number1);
                    } else {
                        System.out.println("Invalid arguments for create_parking_lot command.");
                    }
                    return null;

                    // deletes an existing parking lot.
                    case "delete_parking_lot":
                    if(parts.length == 2){
                        int number = Integer.parseInt(parts[1]);
                        pLots[number] = null;
                        // delete the node in the AVL tree
                        pLotsTree.remove(number);
                    } else {
                        System.out.println("Invalid arguments for delete_parking lot command.");
                    }
                    return null;

                    // takes a truck from the waiting section with the given number as its capacity constraint to the ready section of a parking lot.
                    case "ready":
                    if (parts.length == 2){
                        int capacity = Integer.parseInt(parts[1]);
                        //String s = ready(number);
                        while (true){
                            if (capacity == -1) {
                                break;
                            }
                            else if (pLots[capacity] != null && pLots[capacity].waiting.peek() != null ){
                                Truck truck = pLots[capacity].waiting.peek();
                                pLots[capacity].moveToReady();
                                return truck.getID() + " " + capacity;
                            }
                            else {
                                capacity = pLotsTree.findSmallestLargerThan(capacity);
                            }
                        }
                        return "-1";
                    }
                     else {
                        System.out.println("Invalid arguments for ready command.");
                    }
                    break;

                     // performs the very rigorous "load" method, as described in the project 1 description file.
                    case "load":
                    if (parts.length == 3) {
                        int number1 = Integer.parseInt(parts[1]);
                        int number2 = Integer.parseInt(parts[2]);
                        String s = load(number1, number2, pLotsTree, pLots);
                        return s;
                    } else {
                        System.out.println("Invalid arguments for load command.");
                    }
                    break;

                    case "add_truck":
                    if (parts.length == 3) {
                        int ID = Integer.parseInt(parts[1]);
                        int capacity = Integer.parseInt(parts[2]);
                        String s = add_truck(ID, capacity,0,pLotsTree,pLots);
                        return s;

                    } else {
                        System.out.println("Invalid arguments for add command.");
                    }
                    break;


                    case "count":
                        if (parts.length == 2) {
                            int value = Integer.parseInt(parts[1]);
                            value = pLotsTree.findSmallestLargerThan(value);
                            int result = 0;
                            while (value!=-1){
                                //System.out.println("Current result: " + result + " Now adding lot with idx: " + value);
                                result = result + pLots[value].getCurrentSize();

                                value = pLotsTree.findSmallestLargerThan(value);
                            }
                            return Integer.toString(result);
                        } else {
                            System.out.println("Invalid arguments for count command.");
                        }
                        break;


                    default:
                        System.out.println("Unknown command: " + command);
                        return null;

            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in line: " + line);
        }
        return null;
    }



    public static String load(int capacityConstraint, int loadAmount, AVLTree pLotsTree, ParkingLot[] pLots){
        int remainingLoad = loadAmount;
        int currentLotIdx = capacityConstraint;
        StringBuilder output = new StringBuilder();
        // keep in the loop until the load is emptied or the parking lots are all traversed
        while (remainingLoad != 0 && currentLotIdx != -1){
            ParkingLot currentLot = pLots[currentLotIdx];
            // if the parking lot and the "ready" queue are not empty
            if (currentLot != null && !currentLot.ready.isEmpty()){
                Truck truck = currentLot.dequeue();
                int truckID = truck.getID();
                remainingLoad = truck.loadTruck(remainingLoad);
                String newCC;
                if (truck.getRemainingCapacity() == 0) {
                    newCC = add_truck(truckID, truck.getMaxCapacity(),0, pLotsTree, pLots);
                }
                else{
                    newCC = add_truck(truckID, truck.getMaxCapacity(), truck.getLoad(),pLotsTree, pLots);
                }
                // load truck, update the truck credentials, add it to the parking lot
                output.append(truckID + " " + newCC + " - ");
            }
            else{
                currentLotIdx = pLotsTree.findSmallestLargerThan(currentLotIdx);
            }
        }
        if (loadAmount == remainingLoad){
            return "-1";
        }
        int length = output.length();
        output.setLength(length-3);
        return output.toString();
    }
    public static String add_truck(int ID, int capacity, int load, AVLTree pLotsTree, ParkingLot[] pLots) {
        int remaining = capacity - load;
        while (true) {
            // no suitable lot found; stop the process.
            if (remaining == -1) {
                return "-1";
            }
            // check if the lot with this capacity exists and has space.
            if (pLots[remaining] != null && pLots[remaining].getCurrentSize() < pLots[remaining].getTruckLimit()) {
                Truck truck = new Truck(ID, capacity, load, remaining);
                pLots[remaining].enqueue(truck);  // add truck to the waiting section of the found lot.
                String s = Integer.toString(remaining);
                return s;

            } else {
                // try finding the largest parking lot with a smaller capacity.
                remaining = pLotsTree.findLargestSmallerThan(remaining);
            }
        }
    }
}