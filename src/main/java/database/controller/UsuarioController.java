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

        Timestamp dataConvertida = Timestamp.valueOf(data.atStartOfDay());

        Usuario novoUsuario = new Usuario(null, nome, email, senha, dataConvertida);
        boolean sucesso = novoUsuario.criar(novoUsuario);

        if (sucesso) {
            labelAviso.setText("Usuário cadastrado com sucesso!");
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
    return null;
  }

    @FXML
    private void irParaLogin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            // Obtém a Scene de forma segura usando a origem do clique do próprio evento
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Erro ao voltar para o login: " + e.getMessage());
        }
    }
  }

  @FXML
  private void irParaLogin() throws Exception {
    Parent root = FXMLLoader.load(
        getClass().getResource("/fxml/Login.fxml"));
    botaoCadastro.getScene().setRoot(root);
  }
}
