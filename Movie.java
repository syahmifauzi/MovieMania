public class Movie {
  private String name;
  private String type;
  private String session;
  
  // Constructors
  Movie() {}
  Movie(String paramString1, String paramString2, String paramString3) {
    this.name = paramString1;
    this.type = paramString2;
    this.session = paramString3;
  }
  Movie(String paramString1, String paramString2) {
    setNameType(paramString1);
    this.session = paramString2;
  }
  Movie(String paramString) {
    setNameType(paramString);
  }
  
  // Setters
  public void setName(String paramString) {
    this.name = paramString;
  }
  public void setType(String paramString) {
    this.type = paramString;
  }
  public void setSession(String paramString) {
    this.session = paramString;
  }
  public void setNameType(String paramString) {
    this.name = paramString.split("-")[0].trim();
    this.type = paramString.split("-")[1].trim();
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
