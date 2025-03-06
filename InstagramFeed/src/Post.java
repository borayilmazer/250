public class Post implements Comparable <Post> {
    // data fields
    public User author;
    public String postID;
    public String content;
    public int likeCount;

    // "proper" constructor to create post objects that will actually go into "Posts"
    public Post(User user, String postId, String content){
        this.author = user;
        this.postID = postId;
        this.content = content;
        this.likeCount = 0;
    }
    // constructor to create "temporary" posts to check their existence in "Posts"
    public Post(String postId){
        this.author = null;
        this.postID = postId;
        this.content = null;
        this.likeCount = 0;
    }
    // custom hashCode method that hashes a post based on their ID
    @Override
    public int hashCode() {
        return  postID.hashCode();
    }
    // the equals method only checks whether the IDs of two posts are equal
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Post that = (Post) obj;
        return postID.equals(that.postID);
    }
    // compare two posts, first based on their like count, then their IDs
    @Override
    public int compareTo(Post p) {
        if (this.likeCount > p.likeCount) {
            return 1;
        }
        else if (this.likeCount < p.likeCount) // if the current object is less than o, return negative
            return -1;
        else  if( this.likeCount == p.likeCount){
            if (this.postID.compareTo(p.postID) > 0){
                return 1;
            }
            else if (this.postID.compareTo(p.postID) < 0){
                return -1;
            }
            else {
                return 0;
            }
        }
        else{
            return 0;
        }
    }
}
