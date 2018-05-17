import java.util.Date;

public class Movie {
  private String name;
  private String type;
  private Date session;
  private String hall;

  // Constructors
  Movie() {}
  Movie(String name, String type, Date session) {
    this.name = name;
    this.type = type;
    this.session = session;
  }

  // Setters
  protected void setName(String name) {
    this.name = name;
  }
  protected void setType(String type) {
    this.type = type;
  }
  protected void setSession(Date session) {
    this.session = session;
  }
  protected void setHall(String hall) {
    this.hall = hall;
  }

  // Getters
  public String getName() {
    return this.name;
  }
  public String getType() {
    return this.type;
  }
  public Date getSession() {
    return this.session;
  }
  public String getHall() {
    return this.hall;
  }
}
