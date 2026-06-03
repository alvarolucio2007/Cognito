// package database.controller;

// import database.databaseModels.Usuario;
// import javafx.scene.control.DatePicker;
// import javafx.scene.control.Label;
// import javafx.scene.control.PasswordField;
// import javafx.scene.control.TextField;
// import javafx.fxml.FXML;

// import java.awt.*;
// import java.sql.Timestamp;
// import java.time.LocalDate;

// public class UsuarioController {

//     @FXML private TextField nomeField;
//     @FXML private TextField emailField;
//     @FXML private PasswordField senhaField;
//     @FXML private DatePicker dataField;
//     @FXML private Label labelAviso;
//     @FXML private Button botaoCadastro;

//     @FXML private void cadastrar() {
//         String nome = nomeField.getText().trim();
//         String email = emailField.getText().trim();
//         String senha = senhaField.getText().trim();
//         LocalDate data = dataField.getValue();

//         if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() ||
//         data == null) {
//             labelAviso.setText("Preencha todos os campos obrigatorios!");
//             return;
//         }

//         Timestamp dataConvertida = Timestamp.valueOf(data.atStartOfDay());

//         Usuario novoUsuario = new Usuario(null, nome, email, senha, dataConvertida);
//         boolean sucesso = novoUsuario.criar(novoUsuario);

//         if (sucesso) {
//             labelAviso.setText("Usuário cadastrado com sucesso!");
//         } else {
//             labelAviso.setText("Erro! Não foi possivel realizar o cadastro");
//         }
//     }
// }


package database.controller;

import database.databaseModels.Usuario;
import javafx.scene.control.Button;        // corrigido, tav dando problema (era java.awt.Button)
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Timestamp;
import java.time.LocalDate;

public class UsuarioController {

    // Sem @FXML — campos são injetados via setters pelo HelloFX
    private TextField nomeField;
    private TextField emailField;
    private PasswordField senhaField;
    private DatePicker dataField;
    private Label labelAviso;
    private Button botaoCadastro;

    // ── Setters chamados pelo HelloFX ────────────────────────────────
    public void setNomeField(TextField nomeField)         { this.nomeField = nomeField; }
    public void setEmailField(TextField emailField)       { this.emailField = emailField; }
    public void setSenhaField(PasswordField senhaField)   { this.senhaField = senhaField; }
    public void setDataField(DatePicker dataField)        { this.dataField = dataField; }
    public void setLabelAviso(Label labelAviso)           { this.labelAviso = labelAviso; }
    public void setBotaoCadastro(Button botaoCadastro)    { this.botaoCadastro = botaoCadastro; }

    // ── Lógica de cadastro (antes era @FXML private)
    public void cadastrar() {
        String nome  = nomeField.getText().trim();
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
            labelAviso.setStyle("-fx-text-fill: #2E7D32;"); // verde no sucesso
            labelAviso.setText("Usuário cadastrado com sucesso!");
        } else {
            labelAviso.setStyle("-fx-text-fill: #C62828;"); // vermelho no erro
            labelAviso.setText("Erro! Não foi possível realizar o cadastro.");
        }
    }
}