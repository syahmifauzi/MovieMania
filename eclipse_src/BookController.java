import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class BookController implements Initializable {

  @FXML private MovieMania movieMania;
  @FXML private ChoiceBox<String> moviesInput, sessionsInput;
  @FXML private TextField seatInput;
  @FXML private Button selectSeatButton;
  @FXML private ToggleGroup studentTG, ticketTG;
  @FXML private RadioButton yesRB, standardRB;
  @FXML private TextField updatePrice, updateQuantity, updateTotal;
  @FXML private Button backButton, addButton, purchaseButton;

  @FXML
  private void viewHome() throws Exception {
    // System.out.println("viewHome()");
    // System.out.println(Controller.root.getChildren());
    FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
    Controller.root.getChildren().clear();
    Controller.root.getChildren().add(loader.load());
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.movieMania = new MovieMania();
    String[] movies = this.movieMania.getMoviesNameType().toArray(new String[0]);
    this.moviesInput.getItems().addAll(movies);
    this.moviesInput.setValue(movies[0]);
    String[] sessions = this.movieMania.getSessionByNameType(movies[0]).toArray(new String[0]);
    this.sessionsInput.getItems().addAll(sessions);
    this.sessionsInput.setValue(sessions[0]);
    if (movies.length == 1 && sessions.length == 1 &&
    	movies[0].equals("NA - NA") && sessions[0].equals("NA")) {
    	Alert.display();
    }
    this.selectSeatButton.setOnAction(e -> {
      try {
        String string = SeatsLayout.display(this.movieMania.getSeatsBySession(this.sessionsInput.getValue()));
        this.seatInput.setText(string);
      } catch (Exception e_) {
        e_.getStackTrace();
      }
    });
    this.seatInput.textProperty().addListener((v, oldValue, newValue) -> {
      this.seatInput.setText(newValue.toUpperCase());
    });
    this.calcNewPrice();
    this.moviesInput.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
      String[] newSessions = this.movieMania.getSessionByNameType(this.moviesInput.getValue()).toArray(new String[0]);
      this.sessionsInput.getItems().clear();
      this.sessionsInput.getItems().addAll(newSessions);
      this.sessionsInput.setValue(newSessions[0]);
      this.calcNewPrice();
    });
    this.studentTG.selectedToggleProperty().addListener((v, toggle, toggle2) -> {
      this.calcNewPrice();
    });
    this.ticketTG.selectedToggleProperty().addListener((v, toggle, toggle2) -> {
      this.calcNewPrice();
    });
    this.backButton.setOnAction(e -> {
      boolean bl = ConfirmBox.display("Back to Home Page", "Do you want to cancel the booking?");
      if (bl) {
        try {
          this.viewHome();
        } catch (Exception e_) {
          e_.getStackTrace();
        }
      }
    });
    this.addButton.setOnAction(e -> {
      Movie movie = new Movie(this.moviesInput.getValue(), this.sessionsInput.getValue());
      Ticket ticket = this.ticketTG.getSelectedToggle().equals(this.standardRB) ? new Ticket() : new Premium();
      ticket.setMovie(movie);
      ticket.setSeat(this.seatInput.getText());
      ticket.setStudent(this.studentTG.getSelectedToggle().equals(this.yesRB));
      boolean bl = ConfirmBox.display("Add New Ticket", "Do you want to add this ticket?");
      if (bl && !ticket.getSeat().equals("") && this.isValidSeat(ticket.getSeat())
          && !this.movieMania.getSeatsBySession(this.sessionsInput.getValue()).contains(ticket.getSeat())) {
        this.movieMania.addTicket(ticket);
        this.updateQuantity.setPromptText("Quantity: " + this.movieMania.getQuantity());
        this.updateTotal.setPromptText("Total (RM): " + this.movieMania.getTotalPrice() + "0");
      }
    });
    this.purchaseButton.setOnAction(e -> {
      boolean bl = ConfirmBox.display("Purchase Ticket(s)", "Do you want to buy the added ticket(s)?");
      if (bl) {
        PurchaseTicket.display(this.movieMania);
        try {
          this.viewHome();
        } catch (Exception e_) {
          e_.getStackTrace();
        }
      }
    });
  }

  private void calcNewPrice() {
    Movie movie = new Movie(this.moviesInput.getValue());
    Ticket ticket = this.ticketTG.getSelectedToggle().equals(this.standardRB)
        ? new Ticket(movie, this.studentTG.getSelectedToggle().equals(this.yesRB))
        : new Premium(movie, this.studentTG.getSelectedToggle().equals(this.yesRB));
    this.updatePrice.setPromptText("Price (RM): " + ticket.getPrice() + "0");
  }

  private boolean isValidSeat(String string) {
    if (string.length() != 3) return false;
    int n = -1;
    try {
      n = Integer.parseInt(string.substring(1, 3));
    } catch (NumberFormatException numberFormatException) {
      System.out.println(numberFormatException.getMessage());
      return false;
    }
    if (n < 0 || n > 10) return false;
    if (string.charAt(0) < 'A' || string.charAt(0) > 'K') return false;
    return true;
  }


}


class Alert {

  public static void display() {
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("Error!");
    
    String str1 = "Please provide data.txt file & place it in the " +
    			  "same directory as this executable file.";
    String str2 = "Movie Name  ;  Movie Type  ;  Session  ;\n" +
			  	  "Harry Potter  ;  3D  ;  2130  ;";
    Text title = new Text("Error!");
    Text desc = new Text(str1);
    Text eg = new Text("Example content of data.txt file:");
    Text egeg = new Text(str2);
    
    title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    title.setFill(Color.RED);
    eg.setStyle("-fx-font-weight: bold;");
    desc.setWrappingWidth(300);
    desc.setTextAlignment(TextAlignment.CENTER);
    egeg.setTextAlignment(TextAlignment.CENTER);
    
    Button btnHome = new Button("Home");
    btnHome.setOnAction(e -> stage.close());
    
    VBox vBox = new VBox(10);
    vBox.setId("alert-box");
    vBox.getChildren().addAll(title, desc, eg, egeg, btnHome);
    vBox.setAlignment(Pos.CENTER);
    Scene scene = new Scene(vBox, 350, 200);
    scene.getStylesheets().add("style.css");
    stage.setResizable(false);
    stage.setScene(scene);
    stage.showAndWait();
  }

}

class ConfirmBox {

  static boolean answer;

  public static boolean display(String string, String string2) {
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("Confirmation");
    Label label = new Label(string);
    Label label2 = new Label(string2);
    label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    label.setWrapText(true);
    label2.setWrapText(true);
    Button button = new Button("Yes");
    Button button2 = new Button("No");
    button.setOnAction(e -> {
      answer = true;
      stage.close();
    });
    button2.setOnAction(e -> {
      answer = false;
      stage.close();
    });
    VBox vBox = new VBox(15);
    HBox hBox = new HBox(10);
    vBox.setId("confirm-box");
    hBox.getChildren().addAll(button, button2);
    vBox.getChildren().addAll(label, label2, hBox);
    vBox.setAlignment(Pos.CENTER);
    hBox.setAlignment(Pos.CENTER);
    Scene scene = new Scene(vBox, 300, 150);
    scene.getStylesheets().add("style.css");
    stage.setResizable(false);
    stage.setScene(scene);
    stage.showAndWait();
    return answer;
  }

}



class SeatsLayout {

  static String selected;

  public static String display(ArrayList<String> arrayList) {
    selected = "A01";
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("MovieMania Seating Arrangement");
    GridPane gridPane = new GridPane();
    gridPane.setId("seats-layout");
    gridPane.setPadding(new Insets(10));
    gridPane.setAlignment(Pos.CENTER);
    for (int i = 1; i < 11; ++i) {
      for (int j = 0; j < 10; ++j) {
        String seat = "" + (char) (65 + i - 1) + (j != 9 ? "0" : "") + Integer.toString(j + 1);
        Button btn = new Button(seat);
        if (arrayList.contains(seat))
          btn.setDisable(true);
        btn.setPrefWidth(100);
        GridPane.setConstraints(btn, j, i);
        gridPane.getChildren().add(btn);
        btn.setOnAction(e -> {
          selected = btn.getText();
          stage.close();
        });
      }
    }
    Text text = new Text("Select Seat");
    text.setStyle("-fx-font-size: 25px;");
    TextField screen = new TextField("SCREEN");
    screen.setDisable(true);
    screen.setId("seats-screen");
    VBox vBox2 = new VBox(10);
    vBox2.setPadding(new Insets(10, 0, 10, 0));
    vBox2.setAlignment(Pos.CENTER);
    vBox2.getChildren().addAll(text, screen);
    GridPane.setConstraints(vBox2, 0, 0, 10, 1);
    gridPane.getChildren().add(vBox2);
    Scene scene = new Scene(gridPane, 500, 400);
    scene.getStylesheets().add("style.css");
    stage.setResizable(false);
    stage.setScene(scene);
    stage.showAndWait();
    return selected;
  }
}


class PurchaseTicket {

  public static void display(MovieMania movieMania) {
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("MovieMania Ticket Purchase");

    ScrollPane scroll = new ScrollPane();
    scroll.setFitToHeight(true);
    scroll.setFitToWidth(true);
    VBox box1 = new VBox(10);
    box1.setId("purchase");
    box1.setPadding(new Insets(10));
    box1.setAlignment(Pos.CENTER);

    Label text = new Label("Receipt");
    text.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
    box1.getChildren().add(text);

    int i = 1;
    for (Ticket ticket : movieMania.getTickets()) {
      Label locLab1 = new Label("Ticket #" + i++);
      Label locLab2 = new Label("Movie: " + ticket.getMovie().getNameType());
      Label locLab3 = new Label("Session: " + ticket.getMovie().getSession());
      Label locLab4 = new Label("Seat: " + ticket.getSeat());
      Label ticketLab = new Label("Standard - *no add on");

      if (ticket instanceof Premium) {
        Premium premium = new Premium();
        ticketLab.setText("Premium - " + premium.getPackages().toString());
      }

      Label locLab6 = new Label(String.format("RM%.2f", ticket.getPrice()));
      locLab1.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

      VBox box2 = new VBox(5);
      box2.setAlignment(Pos.CENTER);
      box2.setPadding(new Insets(10));
      box2.getChildren().addAll(locLab1, locLab2, locLab3, locLab4, ticketLab, locLab6);

      Rectangle rect = new Rectangle(350, 150);
      rect.setFill(Color.TRANSPARENT);
      rect.setStroke(Color.BLACK);
      StackPane localStackPane = new StackPane();
      localStackPane.getChildren().addAll(rect, box2);

      box1.getChildren().add(localStackPane);
    }
    Label title = new Label("MovieMania Ticketing System");
    Label quantity = new Label("Quantity: " + String.format("%d", movieMania.getQuantity()));
    Label price = new Label("Total Price (RM): " + String.format("%.2f", movieMania.getTotalPrice()));
    Label tyMsg = new Label("\"Thank you for using MovieMania Ticketing System\"");
    title.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");
    quantity.setStyle("-fx-font-weight: bold;");
    price.setStyle("-fx-font-weight: bold;");
    tyMsg.setStyle("-fx-font-style: italic;");
    VBox qtyPrice = new VBox(10);
    qtyPrice.setAlignment(Pos.CENTER);
    qtyPrice.setPadding(new Insets(10, 10, 20, 10));
    qtyPrice.getChildren().addAll(title, quantity, price, tyMsg);

    Button btnHome = new Button("HOME");
    btnHome.setOnAction(e -> {
      boolean bl = ConfirmBox.display("Thank You!", "Do you want to view Home Page?");
      if (bl) stage.close();
    });
    btnHome.setFocusTraversable(false);

    box1.getChildren().addAll(qtyPrice, btnHome);
    scroll.setContent(box1);

    Scene scene = new Scene(scroll, 500, 400);
    scene.getStylesheets().add("style.css");
    stage.setResizable(false);
    stage.setScene(scene);
    stage.showAndWait();
  }
}
