import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class MovieMania {
  private ArrayList<Movie> movies;
  private ArrayList<Ticket> tickets;
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
  public int getQuantity() {
    return this.tickets.size();
  }
  public ArrayList<String> getSeatsBySession(String session) {
    ArrayList<String> seats = new ArrayList<String>();
    for (Ticket ticket: this.tickets)
      if (ticket.getMovie().getSession().equals(session))
        seats.add(ticket.getSeat());
    return seats;
  }
  public ArrayList<String> getMoviesNameType() {
    ArrayList<String> movies = new ArrayList<String>();
    for (Movie movie: this.getMovies())
      if (!movies.contains(movie.getNameType()))
        movies.add(movie.getNameType());
    return movies;
  }
  public ArrayList<String> getSessionByNameType(String nameType) {
    ArrayList<String> sessions = new ArrayList<String>();
    for (Movie movie: this.movies)
      if (movie.getNameType().equals(nameType))
        sessions.add(movie.getSession());
    return sessions; // .toArray(new String[sessions.size()]);
  }

  // Other Methods
  public void addTicket(Ticket ticket) {
    this.tickets.add(ticket);
  }
  private void readFile() throws Exception {
    File file = new File("movie-list.dat").getAbsoluteFile();
    Scanner scan = new Scanner(file);
    scan.useDelimiter(";");
    while (scan.hasNext()) {
      Movie movie = new Movie();
      movie.setName(scan.next().trim());
      movie.setType(scan.next().trim());
      movie.setSession(scan.next().trim());
      movies.add(movie); // Push current movie to movies array
    }
    scan.close();
  }
}
