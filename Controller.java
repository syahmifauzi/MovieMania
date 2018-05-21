import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


public class Controller implements Initializable {
  private int width;
  private int height;
  @FXML private MovieMania movieMania;
  @FXML private StackPane content;
  @FXML private ChoiceBox<String> moviesInput;
  @FXML private ChoiceBox<String> sessionsInput;
  @FXML private TextField seatInput;
  @FXML private Button selectSeatButton;
  @FXML private ToggleGroup studentTG;
  @FXML private ToggleGroup ticketTG;
  @FXML private RadioButton yesRB;
  @FXML private RadioButton standardRB;
  @FXML private TextField updatePrice;
  @FXML private TextField updateQuantity;
  @FXML private TextField updateTotal;
  @FXML private Button backButton;
  @FXML private Button addButton;
  @FXML private Button purchaseButton;

  public void initialize(URL uRL, ResourceBundle resourceBundle) {
    this.width = 640;
    this.height = 480;
  }

  /* CHANGING SCENES */
  /* ================================================================================ */
  @FXML
  private void goToScene1() throws IOException {
    this.goToScene("scene1.fxml");
  }
  @FXML
  private void goToScene2() throws IOException {
    this.goToScene("scene2.fxml");
    this.scene2Logic();
  }
  private void goToScene(String string) throws IOException {
    Stage stage = (Stage)this.content.getScene().getWindow();
    FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource(string));
    fXMLLoader.setController(this);
    Parent parent = fXMLLoader.load();
    stage.setScene(new Scene(parent, this.width, this.height));
  }


  /* SCENES 2 LOGIC */
  /* ================================================================================ */
  private void scene2Logic() {
    this.movieMania = new MovieMania();
    String[] movies = this.movieMania.getMoviesNameType().toArray(new String[0]);
    this.moviesInput.getItems().addAll(movies);
    this.moviesInput.setValue(movies[0]);
    String[] sessions = this.movieMania.getSessionByNameType(movies[0]).toArray(new String[0]);
    this.sessionsInput.getItems().addAll(sessions);
    this.sessionsInput.setValue(sessions[0]);
    this.selectSeatButton.setOnAction(e -> {
      String string = SeatsLayout.display(this.movieMania.getSeatsBySession(this.sessionsInput.getValue()));
      this.seatInput.setText(string);
    });
    this.seatInput.textProperty().addListener((observableValue, string, string2) -> {
      this.seatInput.setText(string2.toUpperCase());
    });
    this.calcNewPrice();
    this.moviesInput.getSelectionModel().selectedItemProperty().addListener((observableValue, string, string2) -> {
      String[] newSessions = this.movieMania.getSessionByNameType(this.moviesInput.getValue()).toArray(new String[0]);
      this.sessionsInput.getItems().clear();
      this.sessionsInput.getItems().addAll(newSessions);
      this.sessionsInput.setValue(newSessions[0]);
      this.calcNewPrice();
    });
    this.studentTG.selectedToggleProperty().addListener((observableValue, toggle, toggle2) -> {
      this.calcNewPrice();
    });
    this.ticketTG.selectedToggleProperty().addListener((observableValue, toggle, toggle2) -> {
      this.calcNewPrice();
    });
    this.backButton.setOnAction(e -> {
      boolean bl = ConfirmBox.display("Back to Home Page", "Do you want to cancel the booking?");
      if (bl) {
        try {
          this.goToScene1();
        } catch (IOException iOException) {
          System.out.println(iOException.getMessage());
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
        PurchaseTicket.display(this.movieMania.getTickets());
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


class PurchaseTicket {
  public static void display(ArrayList<Ticket> paramArrayList) {
    Stage localStage = new Stage();
    localStage.initModality(Modality.APPLICATION_MODAL);
    localStage.setTitle("Movie Mania Ticket Purchase");

    ScrollPane localScrollPane = new ScrollPane();
    localScrollPane.setId("pt-scroll");
    localScrollPane.setPadding(new Insets(10));
    localScrollPane.setFitToWidth(true);
    VBox localVBox1 = new VBox(10);

    int i = 1;
    for (Ticket ticket: paramArrayList) {
      if (i == 1) {
        Label text = new Label("Receipt");
        text.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        localVBox1.setAlignment(Pos.CENTER);
        localVBox1.getChildren().add(text);
      }
      Label localLabel1 = new Label("Ticket #" + i++);
      Label localLabel2 = new Label(ticket.getMovie().getNameType());
      Label localLabel3 = new Label(ticket.getMovie().getSession());
      boolean bool = ticket instanceof Premium;
      Label localLabel4 = new Label(bool ? "Premium" : "Standard");
      // System.out.println(localLabel4);
      // if (bool) {
      // ticket = new Premium();
      // }
      Label localLabel5 = new Label(ticket.getSeat());
      Label localLabel6 = new Label(String.format("RM%.2f", ticket.getPrice()));

      localLabel1.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

      VBox localVBox2 = new VBox(5);
      localVBox2.setAlignment(Pos.CENTER);
      localVBox2.setPadding(new Insets(10));
      localVBox2.getChildren().addAll(localLabel1, localLabel2, localLabel3, localLabel5, localLabel4, localLabel6);

      Rectangle localRectangle = new Rectangle(250, 150);
      localRectangle.setFill(Color.TRANSPARENT);
      localRectangle.setStroke(Color.BLACK);
      StackPane localStackPane = new StackPane();
      localStackPane.getChildren().addAll(localRectangle, localVBox2);

      localVBox1.getChildren().add(localStackPane);
    }
    localScrollPane.setContent(localVBox1);

    Scene scene = new Scene(localScrollPane, 500, 400);
    scene.getStylesheets().add("style.css");
    localStage.setResizable(false);
    localStage.setScene(scene);
    localStage.showAndWait();
  }
}


class SeatsLayout {

  static String selected;

  public static String display(ArrayList<String> arrayList) {
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("Movie Mania Seating Arrangement");
    GridPane gridPane = new GridPane();
    gridPane.setId("seats-layout");
    gridPane.setPadding(new Insets(10));
    gridPane.setAlignment(Pos.CENTER);
    for (int i = 1; i < 11; ++i) {
      for (int j = 0; j < 10; ++j) {
        String seat = "" + (char) (65 + i - 1) + (j != 9 ? "0" : "") + Integer.toString(j + 1);
        Button btn = new Button(seat);
        if (arrayList.contains(seat)) btn.setDisable(true);
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
    TextField textField = new TextField("SCREEN");
    textField.setDisable(true);
    textField.setId("seats-screen");
    VBox vBox2 = new VBox(10);
    vBox2.setPadding(new Insets(10, 0, 10, 0));
    vBox2.setAlignment(Pos.CENTER);
    vBox2.getChildren().addAll(text, textField);
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