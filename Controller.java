import movie_mania.*;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;


public class Controller implements Initializable {

  public static StackPane root;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // System.out.println("initialize()");
    // System.out.println(root);
  }

  @FXML
  private void viewBook() throws Exception {
    // System.out.println("viewBook()");
    // System.err.println(root.getChildren());
    FXMLLoader loader = new FXMLLoader(getClass().getResource("views/book.fxml"));
    root.getChildren().clear();
    root.getChildren().add(loader.load());
  }

  @FXML
  private void viewAbout() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("views/about.fxml"));
    Parent parent = (Parent)loader.load();
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("About MovieMania");
    Button btnClose = new Button("Close");
    btnClose.setOnAction(e -> stage.close());
    VBox box = new VBox(10);
    box.setId("aboutPage");
    box.setAlignment(Pos.CENTER);
    box.getStylesheets().add("style.css");
    box.getChildren().addAll(parent, btnClose);
    stage.setScene(new Scene(box, 400, 300));
    stage.setResizable(false);
    stage.show();
  }

}