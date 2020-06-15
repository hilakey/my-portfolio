package usercomments;

public class Comment{
  private String comment;
  private String email;
  private String displayName;

  //Constructor
  public Comment(String comment, String email){
      this.comment = comment;
      this.email = email;
  }

  //Getters
  public String getComment(){
      return this.comment;
  }

  public String getEmail(){
      return this.email;
  }

  public String getDisplayName(){
      return this.displayName;
  }
}