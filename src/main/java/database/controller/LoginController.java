package database.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.conn.databaseConn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

  @FXML
  private TextField emailField;
  @FXML
  private PasswordField senhaField;
  @FXML
  private Button botaoLogin;

  private boolean login() {
    String emailDigitado = emailField.getText();
    String senhaDigitada = senhaField.getText();
    try {
      String senhaRecebida = getSenhaEmail(emailDigitado);
      if (senhaRecebida == null) {
        return false;
      }
      return senhaDigitada.equals(senhaRecebida);
    } catch (SQLException e) {
      // TODO: Colocar algo aqui, provavelvemente em um botão ou algo assim...
      return false;
    }

  }

  @FXML
  private void irParaCadastro() throws Exception {
    Parent root = FXMLLoader.load(
        getClass().getResource("/fxml/Cadastro.fxml"));
    botaoLogin.getScene().setRoot(root);
  }

  public String getSenhaEmail(String email) throws SQLException {
    String query = "SELECT usuario_senha FROM usuario WHERE usuario_email = ?";
    try (Connection conn = databaseConn.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, email);
      ResultSet rs = stmt.executeQuery();
      return rs.next() ? rs.getString("usuario_senha") : null;
    }

  }
}
