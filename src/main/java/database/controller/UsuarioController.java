package database.controller;

import database.databaseModels.Usuario;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.apache.commons.validator.EmailValidator;

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
      labelAviso.setText("Preencha todos os campos obrigatórios!");
      return;
    }
    if (!EmailValidator.getInstance().isValid(email)) {
      labelAviso.setText("Preencha o campo de e-mail com um e-mail válido!");
      return;
    }

    Timestamp dataConvertida = Timestamp.valueOf(data.atStartOfDay());

    // Eu mantive a implementação de modelo e chamada do banco do Carlos pra criar o
    // usuário
    Usuario novoUsuario = new Usuario(null, nome, email, senha, dataConvertida);
    boolean sucesso = novoUsuario.criar(novoUsuario);

    if (sucesso) {
      labelAviso.setText("Usuário cadastrado com sucesso!");
      // Eu adicionei essa chamada pra que logo após o cadastro, o fluxo redirecione o
      // usuário diretamente para as perguntas de nivelamento.
      irParaTesteNivelamento(email);
    } else {
      labelAviso.setText("Erro! Não foi possível realizar o cadastro.");
    }
  }

  private void irParaTesteNivelamento(String email) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TesteNivelamento.fxml"));
      Parent root = loader.load();

      TesteNivelamentoController controller = loader.getController();
      controller.setUsuarioEmail(email);

      botaoCadastro.getScene().setRoot(root);
    } catch (Exception e) {
      System.out.println("Erro ao carregar teste de nivelamento: " + e.getMessage());
    }
  }

  @FXML
  private void irParaLogin(ActionEvent event) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
      // Eu alterei esse método para usar ActionEvent. Desse jeito, eu busco a origem
      // do clique (o próprio link clicado) e mudo o root da Scene de forma dinâmica.
      // Isso previne quebra se por acaso o botão do cadastro estiver nulo ou não
      // inicializado.
      ((Node) event.getSource()).getScene().setRoot(root);
    } catch (Exception e) {
      System.out.println("Erro ao voltar para o login: " + e.getMessage());
    }
  }
}
