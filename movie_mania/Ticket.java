package movie_mania;

import java.util.Calendar;
import java.util.Date;

public class Ticket {
  private Movie movie;
  private String seat;
  private double price;
  private boolean isStudent;
  
  // Constructors
  public Ticket() {
    this.isStudent = false;
  }
  public Ticket(Movie movie, boolean isStudent) {
    this.movie = movie;
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
    calcPrice();
    return this.price;
  }
  
  // Other methods
  protected boolean isWeekDay() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    int i = cal.get(7);
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

