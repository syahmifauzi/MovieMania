import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MovieMania {
  private ArrayList<Movie> movies;
  private ArrayList<Ticket> tickets; // All bought tickets - each ticket has all information
  private double total_price;
  // Constructor
  MovieMania() {
    this.movies = new ArrayList<Movie>();
    this.tickets = new ArrayList<Ticket>();
    try {
      this.readFile();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  // Getters
  public ArrayList<Movie> getMovies() {
    return this.movies;
  }
  public ArrayList<Ticket> getTickets() {
    return this.tickets;
  }
  public double getTotalPrice() {
    this.total_price = 0;
    for (Ticket ticket: tickets)
      this.total_price += ticket.getPrice();
    return this.total_price;
  }
  public ArrayList<String> getSeats() {
    ArrayList<String> seats = new ArrayList<String>();
    for (Ticket ticket: this.tickets)
      seats.add(ticket.getSeat());
    return seats;
  }
  public int getQuantity() {
    return this.tickets.size();
  }

  // Other Methods
  private void readFile() throws Exception {
    File file = new File("movie-list.dat").getAbsoluteFile();
    Scanner scan = new Scanner(file);
    scan.useDelimiter(";");
    while (scan.hasNext()) {
      Movie movie = new Movie();
      movie.setName(scan.next().trim());
      movie.setType(scan.next().trim());
      String dateStr = scan.next().trim();
      try {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-HH-dd HH:mm");
        movie.setSession(dateFormat.parse(dateStr));
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }
      movie.setHall(scan.next().trim());
      movies.add(movie); // Push current movie to movies array
    }
    scan.close();
  }
  public void addTicket(Ticket ticket) {
    this.tickets.add(ticket);
  }
}
