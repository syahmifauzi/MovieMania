import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieMania {
  private ArrayList<Movie> movies;
  private ArrayList<Ticket> tickets;
  private double total_price;
  
  MovieMania() {
    this.movies = new ArrayList<Movie>();
    this.tickets = new ArrayList<Ticket>();
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
  public ArrayList<String> getSeatsBySession(String paramString) {
    ArrayList<String> localArrayList = new ArrayList<String>();
    for (Ticket localTicket: this.tickets)
      if (localTicket.getMovie().getSession().equals(paramString))
        localArrayList.add(localTicket.getSeat());
    return localArrayList;
  }
  public ArrayList<String> getMoviesNameType() {
    ArrayList<String> localArrayList = new ArrayList<String>();
    for (Movie localMovie : getMovies())
      if (!localArrayList.contains(localMovie.getNameType()))
        localArrayList.add(localMovie.getNameType());
    return localArrayList;
  }
  public ArrayList<String> getSessionByNameType(String paramString) {
    ArrayList<String> localArrayList = new ArrayList<String>();
    for (Movie localMovie : this.movies)
      if (localMovie.getNameType().equals(paramString))
        localArrayList.add(localMovie.getSession());
    return localArrayList;
  }
  
  // Other methods
  public void addTicket(Ticket paramTicket) {
    this.tickets.add(paramTicket);
  }
  private void readFile() throws Exception {
    File localFile = new File("movie-list.dat").getAbsoluteFile();
    Scanner localScanner = new Scanner(localFile);
    localScanner.useDelimiter(";");
    while (localScanner.hasNext()) {
      Movie localMovie = new Movie();
      localMovie.setName(localScanner.next().trim());
      localMovie.setType(localScanner.next().trim());
      localMovie.setSession(localScanner.next().trim());
      this.movies.add(localMovie);
    }
    localScanner.close();
  }
}
