import java.io.*;
import java.util.ArrayList;

public class Main {
    // create the hash tables that will store all the users and posts
    public static QuadraticProbingHashTable<User> Users = new QuadraticProbingHashTable<>();
    public static QuadraticProbingHashTable<Post> Posts = new QuadraticProbingHashTable<>();

    public static void main(String[] args) {
        try {
            FileReader inputFile = new FileReader(args[0]);
            BufferedReader reader = new BufferedReader(inputFile);
            FileWriter outputFile = new FileWriter(args[1]);
            BufferedWriter writer = new BufferedWriter(outputFile);
            String line;
            while ((line = reader.readLine()) != null) {
                // parse the line, execute the correct method with the correct parameters using parseAndExecute
                String s = parseAndExecute(line, Users);
                if (s != null) {
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

    public static String parseAndExecute(String line, QuadraticProbingHashTable<User> Users) {
        // parse the line, use the first String to implement the correct "command"
        String[] parts = line.split("\\s+");
        String command = parts[0];
        try {
            switch (command) {
                case "create_user":
                    if (parts.length == 2){
                        String id = parts[1];
                        // creates a new user using on the id provided as input.
                        User user = new User(id);
                        // checks whether the user exists in Users solely based on the id.
                        if (Users.contains(user)){
                            return "Some error occurred in create_user.";
                        }
                        else{
                            // insert the user to Users
                            Users.insert(user);
                            return "Created user with Id " + id + ".";
                        }
                    } else {
                        System.out.println("Invalid arguments for create_user command.");
                    }
                    break;

                    case "follow_user":
                    if (parts.length == 3) {
                        String id1 = parts[1];
                        String id2 = parts[2];
                        User user1 = new User(id1);
                        User user2 = new User(id2);

                        // check whether the users exist and are different
                        if (!Users.contains(user1) || !Users.contains(user2)){
                            return "Some error occurred in follow_user.";
                        }
                        else if (user1.equals(user2)){
                            return "Some error occurred in follow_user.";
                        }

                        // retrieve the actual users that were already in Users so that we can access the true user credentials
                        User actualUser1 = Users.get(user1);
                        User actualUser2 = Users.get(user2);

                        // insert user2 to the following list of user1 if user2 is not in there.
                        if (actualUser1.following.contains(actualUser2)){
                            return "Some error occurred in follow_user.";
                        }
                        else {
                            actualUser1.following.insert(actualUser2);
                            return id1 + " followed " + id2 + ".";
                        }


                    } else {
                        System.out.println("Invalid arguments for follow_user command.");
                    }
                    break;

                // performs symmetrically to follow_user once it ensures the existence of the actual users and retrieves them.
                case "unfollow_user":
                    if (parts.length == 3) {
                        String id1 = parts[1];
                        String id2 = parts[2];
                        User user1 = new User(id1);
                        User user2 = new User(id2);

                        if (!Users.contains(user1) || !Users.contains(user2)){
                            return "Some error occurred in unfollow_user.";
                        }

                        else if (user1.equals(user2)){
                            return "Some error occurred in unfollow_user.";
                        }

                        User actualUser1 = Users.get(user1);
                        User actualUser2 = Users.get(user2);


                        if (!actualUser1.following.contains(actualUser2)){
                            return "Some error occurred in unfollow_user.";
                        }
                        else {
                            actualUser1.following.remove(actualUser2);
                            return id1 + " unfollowed " + id2 + ".";
                        }


                    } else {
                        System.out.println("Invalid arguments for unfollow_user command.");
                    }
                    break;

                case "create_post":
                    if (parts.length == 4) {
                        String userid = parts[1];
                        String postid = parts[2];
                        String content = parts[3];
                        User user = new User(userid);
                        Post post = new Post(user, postid, content);
                        if (!Users.contains(user)){
                            return "Some error occurred in create_post.";
                        }
                        else if (Posts.contains(post)){
                            return "Some error occurred in create_post.";
                        }
                        // after checking for the validity of user and post, add the post into the necessary places
                        else {
                            Posts.insert(post);
                            Users.get(user).posts.insert(Posts.get(post));
                            return userid + " created a post with Id " + postid + ".";
                        }

                    } else {
                        System.out.println("Invalid arguments for create_post command.");
                    }
                    break;

                case "see_post":
                    if (parts.length == 3) {
                        String userid = parts[1];
                        String postid = parts[2];
                        User user = new User(userid);
                        Post post = new Post(postid);
                        // check whether the user and post already exist
                        if (!Users.contains(user) || !Posts.contains(post)){
                            return "Some error occurred in see_post.";
                        }
                        else {
                            // refer to the actual user and post within Users and Posts, make the necessary operatipns
                            User userRef = Users.get(user);
                            Post postRef = Posts.get(post);
                            if (!userRef.seenPosts.contains(postRef)){
                                userRef.seenPosts.insert(postRef);
                            }
                            return userid + " saw " + postid + ".";
                        }

                    } else {
                        System.out.println("Invalid arguments for see_post command.");
                    }
                    break;

                case "see_all_posts_from_user":
                    if (parts.length == 3) {
                        String viewerid = parts[1];
                        String viewedid = parts[2];
                        User viewer = new User(viewerid);
                        User viewed = new User(viewedid);
                        // ensure the users with the correct ids already exist in Users
                        if (!Users.contains(viewer) || !Users.contains(viewed)){
                            return "Some error occurred in see_all_posts_from_user.";
                        }

                        else {
                            // refer to the hash table that stores the posts of the user that is going to be viewed
                            QuadraticProbingHashTable<Post> postsOfViewed = Users.get(viewed).posts;
                            for (Post p : postsOfViewed){
                                Users.get(viewer).seenPosts.insert(p);
                            }
                            return viewerid + " saw all posts of " + viewedid + ".";
                        }
                    } else {
                        System.out.println("Invalid arguments for see_all_posts_from_user command.");
                    }
                    break;

                case "toggle_like":
                    if (parts.length == 3) {
                        String userid = parts[1];
                        String postid = parts[2];
                        User user = new User(userid);
                        Post post = new Post(postid);
                        // check whether the post and the user exist
                        if (!Users.contains(user) || !Posts.contains(post)){
                            return "Some error occurred in toggle_like.";
                        }
                        User userRef = Users.get(user);
                        Post postRef = Posts.get(post);
                        // if the user already liked the post, "unlike" it
                        if (userRef.likedPosts.contains(postRef)){
                            userRef.likedPosts.remove(postRef);
                            postRef.likeCount -= 1;
                            return userid + " unliked " + postid + ".";
                        }
                        // if the user has not liked the post before, do it
                        else if (!userRef.likedPosts.contains(postRef)){
                            userRef.seenPosts.insert(postRef);
                            userRef.likedPosts.insert(postRef);
                            postRef.likeCount += 1;
                            return userid + " liked " + postid + ".";
                        }
                        else{
                            return "Some error occurred in toggle_like.";
                        }
                    } else {
                        System.out.println("Invalid arguments for toggle_like command.");
                    }
                    break;

                    case "generate_feed":
                    if (parts.length == 3) {
                        String userid = parts[1];
                        int numberOfPosts = Integer.parseInt(parts[2]);
                        User user = new User(userid);
                        if (!Users.contains(user)) {
                            return "Some error occurred in generate_feed.";
                        }
                        else {
                            // refer to the actual user, create a minHeap for posts
                            User userRef = Users.get(user);
                            MinHeap<Post> postHeap = new MinHeap<>(numberOfPosts);
                            // check all posts and compare the posts that fit the criteria with the minimum one in the heap. If the post is "bigger" than the minimum, delete the minimum and insert the post.
                            // not the most efficient way to perform the desired task
                            for (Post p : Posts) {
                                if (!userRef.seenPosts.contains(p) && !userRef.posts.contains(p) && userRef.following.contains(p.author)) {
                                    if (postHeap.getCurrentSize() < numberOfPosts) {
                                        postHeap.insert(p);
                                    }
                                    // if the minHeap is not at the desired size, insert without comparisons
                                    else if (postHeap.findMin().compareTo(p) < 0) {
                                        postHeap.deleteMin();
                                        postHeap.insert(p);
                                    }
                                }
                            }
                            // dump the contents of the minHeap to an ArrayList
                            ArrayList<Post> feed = new ArrayList<>();
                            while (postHeap.getCurrentSize() > 0) {
                                feed.add(postHeap.findMin());
                                postHeap.deleteMin();
                            }
                            reverse(feed);

                            StringBuilder result = new StringBuilder();
                            result.append("Feed for ").append(userid).append(":");
                            if (!feed.isEmpty()) {
                                int i = 0;
                                for (Post p : feed) {
                                    result.append("\nPost ID: ").append(p.postID).append(", Author: ").append(p.author.ID).append(", Likes: ").append(p.likeCount);
                                    i +=1;
                                }
                                if (i<numberOfPosts){
                                    result.append("\nNo more posts available for ").append(userid).append(".");
                                }
                            } else {
                                result.append("\nNo more posts available for ").append(userid).append(".");
                            }

                            return result.toString();
                        }
                    } else {
                        System.out.println( "Invalid arguments for generate_feed command.");
                    }
                    break;

                    case "scroll_through_feed":
                        String userid = parts[1];
                        // find the number of posts that are going to be scrolled. create an integer array and store the 0's and 1's.
                    int numberOfPosts = Integer.parseInt(parts[2]);
                    int[] likes = new int[numberOfPosts];
                    int j = 0;
                    for (int i = 3; i < parts.length; i ++){
                        likes[j] = Integer.parseInt(parts[i]);
                        j = j+1;
                    }
                    User user = new User(userid);
                    // check whether the user exists.
                    if (!Users.contains(user)) {
                        return "Some error occurred in scroll_through_feed.";
                    } else {
                        // similar algorithm to "generate_feed"
                        User userRef = Users.get(user);
                        MinHeap<Post> postHeap = new MinHeap<>(numberOfPosts);
                        // improved by using two foreach loops that only iterate through the posts of the users that are followed
                        for (User u: userRef.following) {
                            for (Post p : u.posts) {
                                if (!userRef.seenPosts.contains(p) && !userRef.posts.contains(p)){
                                    if (postHeap.getCurrentSize() < numberOfPosts) {
                                        postHeap.insert(p);
                                    }
                                    else if (postHeap.findMin().compareTo(p) < 0) {
                                        postHeap.deleteMin();
                                        postHeap.insert(p);
                                    }
                                }
                            }
                        }

                        // dump the contents of the minHeap to an ArrayList
                        ArrayList<Post> feed = new ArrayList<>();
                        while (postHeap.getCurrentSize() > 0) {
                            feed.add(postHeap.findMin());
                            postHeap.deleteMin();
                        }
                        reverse(feed);

                        StringBuilder result = new StringBuilder(userid + " is scrolling through feed:");
                        if (!feed.isEmpty()) {
                            int i = 0;
                            for (Post p : feed) {
                                if (likes[i] == 0){
                                    userRef.seenPosts.insert(p);
                                    result.append("\n" + userid + " saw " + p.postID + " while scrolling.");
                                }
                                else if (likes[i] == 1){
                                    if (userRef.likedPosts.contains(p)){
                                        userRef.likedPosts.remove(p);

                                        p.likeCount -= 1;
                                        result.append("\n" + userid + " saw " + p.postID + " while scrolling and clicked the like button.");
                                    }
                                    else if (!userRef.likedPosts.contains(p)){
                                        userRef.seenPosts.insert(p);
                                        userRef.likedPosts.insert(p);

                                        p.likeCount += 1;
                                        result.append("\n" + userid + " saw " + p.postID + " while scrolling and clicked the like button.");
                                    }
                                }

                                i +=1;
                            }
                            if (i<numberOfPosts){
                                result.append("\nNo more posts in feed.");
                            }
                        } else {
                            result.append("\nNo more posts in feed.");
                        }
                        return result.toString();
                    }

                    case "sort_posts":
                    if (parts.length == 2){
                        String id = parts[1];
                        // check if the user with id exists.
                        User user1 = new User(id);
                        if (!Users.contains(user1)){
                            return "Some error occurred in sort_posts.";
                        }
                        // alter the reference and create a maxHeap that consists of the posts of the user.
                        user1 = Users.get(user1);
                        StringBuilder result = new StringBuilder("Sorting " + id + "'s posts:");
                        MaxHeap<Post> postMaxHeap = new MaxHeap<>();
                        if (user1.posts.currentSize == 0){
                            return "No posts from " + id;
                        }
                        else {
                            // if the maxHeap stores any posts, take them out one by one and append the StringBuilder.
                            for (Post userPost : user1.posts) {
                                postMaxHeap.insert(userPost);
                            }
                            while (!postMaxHeap.isEmpty()) {
                                Post maxPost = postMaxHeap.findMax();
                                result.append("\n" + maxPost.postID + ", Likes: " + maxPost.likeCount);
                                postMaxHeap.deleteMax();
                            }
                            return result.toString();
                        }
                    } else {
                        System.out.println("Invalid arguments for create_user command.");
                        break;
                    }

                default:
                    System.out.println("Unknown command: " + command);
                    return null;

            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in line: " + line);
        }
        return null;
    }

    // method to reverse an ArrayList
    public static <AnyType> void reverse(ArrayList<AnyType> arraylist) {
        int left = 0;
        int right = arraylist.size() - 1;

        while (left < right) {
            AnyType temp = arraylist.get(left);
            arraylist.set(left, arraylist.get(right));
            arraylist.set(right, temp);
            left++;
            right--;
        }
    }
}