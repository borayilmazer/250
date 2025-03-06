import java.util.ArrayList;

public class User {
    // data fields
    public String ID;
    QuadraticProbingHashTable<User> following;
    QuadraticProbingHashTable<Post> posts;
    QuadraticProbingHashTable<Post> seenPosts;
    QuadraticProbingHashTable<Post> likedPosts;


    // constructor
    public User (String id){
        this.ID = id;
        this.following = new QuadraticProbingHashTable<>();
        this.posts = new QuadraticProbingHashTable<>();
        this.seenPosts = new QuadraticProbingHashTable<>();
        this.likedPosts = new QuadraticProbingHashTable<>();
    }

    // custom hashCode method that hashes a user based on their ID
    @Override
    public int hashCode() {
        return  ID.hashCode();
    }

    // the equals method only checks whether the IDs of two users are equal
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User that = (User) obj;
        if (ID != null ){
            return ID.equals(that.ID);
        }
        else {
            return that.ID == null;
        }
    }
}
