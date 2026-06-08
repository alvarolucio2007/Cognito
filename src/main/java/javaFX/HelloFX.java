package javaFX;

import java.sql.Connection;

import database.conn.databaseConn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloFX extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    try {
      Connection conn = databaseConn.connect();
      databaseConn.migrate(conn);
      stage.setResizable(false);
      stage.setTitle("Cognito");
      stage.setWidth(440);
      stage.setHeight(950);
      // stage.initStyle(StageStyle.TRANSPARENT);

      Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
      Scene scene = new Scene(root);

      stage.setResizable(false);
      stage.setWidth(440);
      stage.setHeight(950);

      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      System.out.println("Erro ao inicializar a aplicação: " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
