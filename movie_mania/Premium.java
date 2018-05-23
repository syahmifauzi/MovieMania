package movie_mania;

import java.util.ArrayList;

public class Premium extends Ticket {
  public Premium() {}

  public Premium(Movie movie, boolean isStudent) {
    super(movie, isStudent);
  }

  public double getPrice() {
    double d = 18.0D;
    if (isStudent())
      d -= 0.5D;
    if (isWeekDay())
      d -= 0.5D;
    if (getMovie().getType().equals("3D"))
      d += 2.5D;
    else if (getMovie().getType().equals("ATMOS"))
      d += 1.0D;
    return d;
  }

  public ArrayList<String> getPackages() {
    ArrayList<String> packages = new ArrayList<String>();
    packages.add("Soft drink");
    packages.add("Popcorn");
    packages.add("Ferrero rocher");
    return packages;
  }
}
