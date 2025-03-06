# CMPE Truck Simulator

## Introduction
The CMPE Truck Simulator is a Java-based simulation designed to manage a fleet of trucks and their allocation to parking lots with varying capacity constraints. The system processes commands from an input file and generates appropriate outputs, handling truck movements, parking lot constraints, and load distributions efficiently.

## Features
- **Truck Management**: Each truck has a unique ID, a maximum capacity, and a load.
- **Parking Lot System**: Parking lots have capacity constraints and a truck limit.
- **Queue System**: Each parking lot has two sections (waiting and ready) implemented using a queue-based system.
- **AVL Tree for Capacity Constraint Management**: Efficiently handles parking lot searches and constraints.
- **Command Processing**: Supports commands to create/delete parking lots, add trucks, move trucks, distribute loads, and count trucks.

## Project Structure
- `Main.java`: The main class that reads input commands, processes them, and writes outputs.
- `Truck.java`: Defines the `Truck` class with attributes like ID, maximum capacity, and load handling.
- `ParkingLot.java`: Defines the `ParkingLot` class, managing the ready and waiting queues for trucks.
- `TruckQueue.java`: Implements a queue-based system to manage truck movement within parking lots.
- `AVLTree.java`: Implements an AVL tree to efficiently manage parking lot capacity constraints.

## How to Run the Program
1. **Compile the Java Files:**
   ```sh
   javac *.java
   ```
2. **Run the Simulation:**
   ```sh
   java Main <input_file> <output_file>
   ```
   - `<input_file>`: Path to the input file containing simulation commands.
   - `<output_file>`: Path where the simulation results will be written.

## Command Structure
The system processes commands from the input file in the following format:

- **Creating a Parking Lot:**
  ```
  create_parking_lot <capacity_constraint> <truck_limit>
  ```
  Example:
  ```
  create_parking_lot 50 15
  ```

- **Deleting a Parking Lot:**
  ```
  delete_parking_lot <capacity_constraint>
  ```
  Example:
  ```
  delete_parking_lot 50
  ```

- **Adding a Truck:**
  ```
  add_truck <truck_id> <capacity>
  ```
  Example:
  ```
  add_truck 1 100
  ```

- **Moving a Truck to Ready Section:**
  ```
  ready <capacity>
  ```
  Example:
  ```
  ready 50
  ```

- **Loading Trucks:**
  ```
  load <capacity> <load_amount>
  ```
  Example:
  ```
  load 50 200
  ```

- **Counting Trucks in Larger Parking Lots:**
  ```
  count <capacity>
  ```
  Example:
  ```
  count 50
  ```

## Example Input and Output
### Input:
```
create_parking_lot 50 15
add_truck 1 50
ready 50
load 50 30
count 50
delete_parking_lot 50
```
### Output:
```
1 50
1 50 -
1
```

## Constraints & Performance
- The system must handle up to 500,000 parking lots and trucks efficiently.
- Operations should run within a 25-second limit for large test cases.
- An AVL Tree ensures logarithmic performance for parking lot lookups.

## Author
Developed as part of **CMPE 250: Data Structures and Algorithms** coursework.

## License
This project is for educational purposes and follows university guidelines on plagiarism and academic integrity.

