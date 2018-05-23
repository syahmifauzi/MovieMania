import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  private String title;
  private int width;
  private int height;
  
  public static void main(String[] paramArrayOfString) {
    launch(paramArrayOfString);
  }
  
  public void initialize() {
    this.title = "Movie Mania Ticketing System";
    this.width = 640;
    this.height = 480;
  }
  
  public void start(Stage paramStage) throws Exception {
    initialize();
    FXMLLoader localFXMLLoader = new FXMLLoader(getClass().getResource("views/scene1.fxml"));
    localFXMLLoader.setController(new Controller());
    Parent localParent = (Parent)localFXMLLoader.load();
    paramStage.setTitle(this.title);
    paramStage.setResizable(false);
    paramStage.setScene(new Scene(localParent, this.width, this.height));
    paramStage.show();
  }
}
