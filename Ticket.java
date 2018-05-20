import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Ticket {
  private Movie movie;
  private String seat;
  private double price;
  private boolean isStudent;

  // Constructor
  Ticket() {
    this.isStudent = false;
  }
  Ticket(String seat, boolean isStudent) {
    this.seat = seat;
    this.isStudent = isStudent;
  }

  // Setters
  public void setMovie(Movie movie) {
    this.movie = movie;
  }
  public void setSeat(String seat) {
    this.seat = seat;
  }
  public void setStudent(boolean isStudent) {
    this.isStudent = isStudent;
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
    this.calcPrice();
    return this.price;
  }

  // Other methods
  protected boolean isWeekDay() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    int dow = cal.get(Calendar.DAY_OF_WEEK);
    return dow > 1 && dow < 7; // return false if SUNDAY(1) & SATURDAY(7)
  }
  private void calcPrice() {
    this.price = 8.00; // RM8.00 is the base price
    if (this.isStudent) this.price -= 1.00;
    if (this.isWeekDay()) this.price -= 1.00;
    if (this.movie.getType() == "3D") this.price += 3.00;
    else if (this.movie.getType() == "ATMOS") this.price += 1.00;
  }
}


class Primium extends Ticket {
  @Override
  public double getPrice() {
    double price = 18.00; // RM18.00 for Primium Ticket
    if (this.isStudent()) price -= 0.50;
    if (this.isWeekDay()) price -= 0.50;
    if (this.getMovie().getType() == "3D") price += 2.50;
    else if (this.getMovie().getType() == "ATMOS") price += 0.50;
    return price;
  }
  // Premium ticket include these things
  public ArrayList<String> getPackages() {
    ArrayList<String> packages = new ArrayList<String>();
    packages.add("Soft drink");
    packages.add("Popcorn");
    packages.add("Ferrero rocher");
    return packages;
  }
}

