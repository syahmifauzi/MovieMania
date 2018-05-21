import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Ticket {
  private Movie movie;
  private String seat;
  private double price;
  private boolean isStudent;
  
  // Constructors
  Ticket() {
    this.isStudent = false;
  }
  Ticket(Movie paramMovie, boolean paramBoolean) {
    this.movie = paramMovie;
    this.isStudent = paramBoolean;
  }
  
  // Setters
  public void setMovie(Movie paramMovie) {
    this.movie = paramMovie;
  }
  public void setSeat(String paramString) {
    this.seat = paramString;
  }
  public void setStudent(boolean paramBoolean) {
    this.isStudent = paramBoolean;
  }

  // Getters
  public Movie getMovie() {
    return this.movie;
  }
  public String getSeat() {
    return this.seat;
  }
  public boolean isStudent() {
    return this.isStudent;
  }
  public double getPrice() {
    calcPrice();
    return this.price;
  }
  
  // Other methods
  protected boolean isWeekDay() {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(new Date());
    int i = localCalendar.get(7);
    return (i > 1) && (i < 7);
  }
  private void calcPrice() {
    this.price = 8.0D;
    if (this.isStudent) this.price -= 1.0D;
    if (isWeekDay()) this.price -= 1.0D;
    if (this.movie.getType().equals("3D")) this.price += 3.0D;
    else if (this.movie.getType().equals("ATMOS")) this.price += 1.5D;
  }
}


class Premium extends Ticket {
  Premium() {}
  Premium(Movie paramMovie, boolean paramBoolean) {
    super(paramMovie, paramBoolean);
  }

  public double getPrice() {
    double d = 18.0D;
    if (isStudent()) d -= 0.5D;
    if (isWeekDay()) d -= 0.5D;
    if (getMovie().getType().equals("3D")) d += 2.5D;
    else if (getMovie().getType().equals("ATMOS")) d += 1.0D;
    return d;
  }

  public ArrayList<String> getPackages() {
    ArrayList<String> localArrayList = new ArrayList<String>();
    localArrayList.add("Soft drink");
    localArrayList.add("Popcorn");
    localArrayList.add("Ferrero rocher");
    return localArrayList;
  }
}
