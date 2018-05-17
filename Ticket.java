import java.util.Calendar;
import java.util.Date;

public class Ticket {
  private Movie movie;
  private String seat;
  private double price;
  private boolean isStudent;
  private boolean isWeekDay;

  // Constructor
  Ticket() {
    this.isStudent = false;
    this.isWeekDay = false;
  }
  Ticket(String seat, boolean isStudent) {
    this.seat = seat;
    this.isStudent = isStudent;
    this.isWeekDay = false;
  }

  // Setters
  public void setIsStudent(boolean isStudent) {
    this.isStudent = isStudent;
  }
  public void setMovie(Movie movie) {
    this.movie = movie;
  }
  public void setSeat(String seat) {
    this.seat = seat;
  }

  // Getters
  public Movie getMovie() {
    return this.movie;
  }
  public String getSeat() {
    return this.seat;
  }
  public boolean getIsStudent() {
    return this.isStudent;
  }
  public double getPrice() {
    this.calcPrice();
    return this.price;
  }

  // Other methods
  public void checkDay() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(this.movie.getSession());
    int dow = cal.get(Calendar.DAY_OF_WEEK);
    this.isWeekDay = dow > 1 && dow < 7; // false if SUNDAY & SATURDAY
  }
  public void calcPrice() {
    this.checkDay();
    this.price = 8.00; // RM8.00 is the base price
    if (isStudent) this.price -= 1.00;
    if (isWeekDay) this.price -= 1.00;
    if (this.movie.getType() == "3D") this.price += 3.00;
    else if (this.movie.getType() == "ATMOS") this.price += 1.00;
  }
}

class Primium extends Ticket {
  @Override
  public double getPrice() {
    double price = 18.00; // RM18.00 for Primium Ticket
    if (this.getIsStudent()) price -= 1.00;
    return price;
  }
}

