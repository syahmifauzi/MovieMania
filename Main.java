import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
  private String title;
  private int width;
  private int height;
  
  public static void main(String[] args) {
    launch(args);
  }
  
  public void initialize() {
    this.title = "Movie Mania Ticketing System";
    this.width = 640;
    this.height = 480;
  }
  
  public void start(Stage stage) throws Exception {
    initialize();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("views/root.fxml"));
    Parent parent = (Parent)loader.load();
    Controller.root = (StackPane)parent;
    stage.setTitle(this.title);
    stage.setResizable(false);
    stage.setScene(new Scene(parent, this.width, this.height));
    stage.show();
  }
}
