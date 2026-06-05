package database.controller;

import database.databaseModels.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button; // alterei essa linha pq tava dando problema na UI, antes era: import java.awt.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;

import database.conn.databaseConn;

public class UsuarioController {

  @FXML
  private TextField nomeField;
  @FXML
  private TextField emailField;
  @FXML
  private PasswordField senhaField;
  @FXML
  private DatePicker dataField;
  @FXML
  private Label labelAviso;
  @FXML
  private Button botaoCadastro;

  @FXML
  private void cadastrar() {
    String nome = nomeField.getText().trim();
    String email = emailField.getText().trim();
    String senha = senhaField.getText().trim();
    LocalDate data = dataField.getValue();

    if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || data == null) {
      labelAviso.setText("Preencha todos os campos obrigatorios!");
      return;
    }

    Timestamp dataConvertida = Timestamp.valueOf(data.atStartOfDay());

    Usuario novoUsuario = new Usuario(null, nome, email, senha, dataConvertida);
    boolean sucesso = novoUsuario.criar(novoUsuario);

    if (sucesso) {
      labelAviso.setText("Usuário cadastrado com sucesso!");
      // Busca o ID gerado e redireciona pro teste de nivelamento
      Long idGerado = buscarIdPorEmail(email);
      if (idGerado != null) {
        irParaTeste(idGerado);
      }
    } else {
      labelAviso.setText("Erro! Não foi possivel realizar o cadastro");
    }
  }

  // ── Busca o ID do usuário recém-criado ───────────────────────────
  private Long buscarIdPorEmail(String email) {
    String query = "SELECT PK_id_usaurio FROM usuario WHERE usuario_email = ?";
    try (Connection conn = databaseConn.connect();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, email);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return rs.getLong("PK_id_usaurio");
      }
    } catch (Exception e) {
      System.out.println("Erro ao buscar id do usuário: " + e.getMessage());
    }
    return null;
  }

  // ── Navega pro teste passando o ID do usuário ────────────────────
  private void irParaTeste(Long idUsuario) {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/fxml/TesteNivelamento.fxml"));
      Parent root = loader.load();

      // Injeta o ID no controller do teste
      TesteNivelamentoController testeController = loader.getController();
      testeController.setIdUsuario(idUsuario);

      botaoCadastro.getScene().setRoot(root);
    } catch (Exception e) {
      System.out.println("Erro ao carregar teste de nivelamento: " + e.getMessage());
    }
  }

  @FXML
  private void irParaLogin() throws Exception {
    Parent root = FXMLLoader.load(
        getClass().getResource("/fxml/Login.fxml"));
    botaoCadastro.getScene().setRoot(root);
  }
}
