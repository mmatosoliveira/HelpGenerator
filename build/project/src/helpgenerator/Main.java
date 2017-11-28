package helpgenerator;
	
import helpgenerator.utils.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	private static Alertas alerta = new Alertas();
	
	private static void showError(Thread t, Throwable e) {
        if (Platform.isFxApplicationThread()) {
        	alerta.showErrorDialog(e);
        } else {
        	alerta.showErrorDialog(e);
        }
    }
	
	@Override
	public void start(Stage primaryStage) {
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> Platform.runLater(() -> showError(t, e)));
        Thread.currentThread().setUncaughtExceptionHandler(Main::showError);
        
        primaryStage.setTitle("Exportador de helps cadastrados para o Blue");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("logo-agili.png")));
		
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/GeradorHelpsBlue.fxml"));
			
			AnchorPane painel = (AnchorPane) loader.load();
			
			Scene scene = new Scene(painel);
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
            primaryStage.show();
            
            /*LoginController controller = loader.getController();
            controller.setPrincipal(this);*/
            
		} catch(Exception e){Main.showError(Thread.currentThread(), e);}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}