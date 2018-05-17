// import java.io.*;
import java.util.Date;

public class Main {
  public static void main(String[] args) throws Exception {
    // Create mm object
    MovieMania mm = new MovieMania();

    // Display All Available Movies
    System.out.println(mm.getMovies());
    System.out.println(mm.getMovies().size());
    System.out.println();
    for (Movie movie: mm.getMovies()) {
      System.out.println(movie.getName());
      System.out.println(movie.getType());
      System.out.println(movie.getSession());
      System.out.println(movie.getHall());
    }

    // Add Ticket
    for (int i = 1; i <= 3; ++i) {
      Ticket ticket = new Ticket();
      Movie movie = new Movie();
      movie.setName("Movie " + i);
      movie.setType("Type " + i);
      movie.setSession(new Date());
      movie.setHall("Hall " + i);
      ticket.setMovie(movie);
      ticket.setSeat("Seat " + i);
      ticket.setIsStudent(true);
      mm.addTicket(ticket);
    }
    System.out.println();

    // Get Ticket's Data
    for (Ticket ticket: mm.getTickets()) {
      System.out.println(ticket.getMovie().getName());
      System.out.println(ticket.getMovie().getType());
      System.out.println(ticket.getMovie().getSession());
      System.out.println(ticket.getMovie().getHall());
      System.out.println(ticket.getSeat());
      System.out.println(ticket.getPrice());
      System.out.println();
    }
    System.out.println(mm.getTotalPrice());
    System.out.println(mm.getTickets());

  }
}
