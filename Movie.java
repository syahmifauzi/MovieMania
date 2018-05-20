
public class Movie {
  private String name;
  private String type;
  private String session;

  // Constructors
  Movie() {}
  Movie(String name, String type, String session) {
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
  protected void setSession(String session) {
    this.session = session;
  }

  // Getters
  public String getName() {
    return this.name;
  }
  public String getType() {
    return this.type;
  }
  public String getSession() {
    return this.session;
  }
  public String getNameType() {
    return this.getName() + " - " + this.getType();
  }
}
