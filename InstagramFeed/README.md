# Instagram Feed Manager

## Overview
This project implements an Instagram Feed Manager in Java, simulating the functionalities of a basic social media feed system. The application keeps track of users, their posts, interactions such as likes and follows, and generates user feeds based on specific rules.

## Features
- **User Management**: Create users with unique IDs, follow/unfollow users.
- **Post Management**: Users can create posts, view posts, and interact with them.
- **Feed Generation**: Generates a personalized feed for each user based on post likes and follow relationships.
- **Sorting**: Allows sorting of posts based on popularity.
- **Efficient Data Structures**: Implements a custom quadratic probing hash table and min/max heaps for optimized performance.

## Project Structure
The project consists of the following Java classes:
- **Main.java**: Handles file input/output and executes commands.
- **User.java**: Represents a user with follow, post, and interaction data.
- **Post.java**: Represents a social media post with an ID, author, content, and like count.
- **QuadraticProbingHashTable.java**: Implements a custom hash table for efficient storage and retrieval.
- **MaxHeap.java**: Implements a max-heap to efficiently find the most liked posts.
- **MinHeap.java**: Implements a min-heap for efficient feed generation.

## Installation
1. Clone the repository or download the source files.
2. Compile the Java files using:
   ```sh
   javac *.java
   ```
3. Run the program with:
   ```sh
   java Main <input_file> <output_file>
   ```

## Usage
The program takes an input file containing commands and processes them to manage users and their interactions. The output file records the results of these operations.

### Example Commands
```
create_user user1
follow_user user1 user2
create_post user1 post1 Hello
see_post user2 post1
generate_feed user2 5
sort_posts user1
```

## Warning
Due to GitHub's file size restrictions, large example input and output files are not included in this repository. To test large cases, generate input files with high user and post counts.



