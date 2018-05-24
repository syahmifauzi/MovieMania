public class Movie {
  private String name;
  private String type;
  private String session;
  
  // Constructors
  public Movie() {}
  public Movie(String name, String type, String session) {
    this.name = name;
    this.type = type;
    this.session = session;
  }
  public Movie(String nameType, String session) {
    setNameType(nameType);
    this.session = session;
  }
  public Movie(String nameType) {
    setNameType(nameType);
  }
  
  // Setters
  public void setName(String name) {
    this.name = name;
  }
  public void setType(String type) {
    this.type = type;
  }
  public void setSession(String session) {
    this.session = session;
  }
  public void setNameType(String nameType) {
    this.name = nameType.split("-")[0].trim();
    this.type = nameType.split("-")[1].trim();
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
    return getName() + " - " + getType();
  }
}
