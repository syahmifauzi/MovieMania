package movie_mania;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieMania {
  private ArrayList<Movie> movies;
  private ArrayList<Ticket> tickets;
  private double total_price;
  private String movies_file;
  
  public MovieMania() {
    this.movies = new ArrayList<Movie>();
    this.tickets = new ArrayList<Ticket>();
    this.movies_file = "movies-data";
    try {
      readFile();
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
    this.total_price = 0.0D;
    for (Ticket localTicket : this.tickets) {
      this.total_price += localTicket.getPrice();
    }
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
    ArrayList<String> nameType = new ArrayList<String>();
    for (Movie movie : getMovies())
      if (!nameType.contains(movie.getNameType()))
        nameType.add(movie.getNameType());
    return nameType;
  }
  public ArrayList<String> getSessionByNameType(String nameType) {
    ArrayList<String> sessions = new ArrayList<String>();
    for (Movie movie : this.movies)
      if (movie.getNameType().equals(nameType))
        sessions.add(movie.getSession());
    return sessions;
  }
  
  // Other methods
  public void addTicket(Ticket ticket) {
    this.tickets.add(ticket);
  }
  private void readFile() throws Exception {
    File file = new File(this.movies_file).getAbsoluteFile();
    Scanner input = new Scanner(file);
    input.useDelimiter(";");
    while (input.hasNext()) {
      Movie movie = new Movie();
      movie.setName(input.next().trim());
      movie.setType(input.next().trim());
      movie.setSession(input.next().trim());
      this.movies.add(movie);
    }
    input.close();
  }
}
