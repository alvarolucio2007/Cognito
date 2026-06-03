package database.controller;

import database.databaseModels.Usuario;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;

import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDate;

public class UsuarioController {

    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private DatePicker dataField;
    @FXML private Label labelAviso;
    @FXML private Button botaoCadastro;

    @FXML private void cadastrar() {
        String nome = nomeField.getText().trim();
        String email = emailField.getText().trim();
        String senha = senhaField.getText().trim();
        LocalDate data = dataField.getValue();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() ||
        data == null) {
            labelAviso.setText("Preencha todos os campos obrigatorios!");
            return;
        }

        Timestamp dataConvertida = Timestamp.valueOf(data.atStartOfDay());

        Usuario novoUsuario = new Usuario(null, nome, email, senha, dataConvertida);
        boolean sucesso = novoUsuario.criar(novoUsuario);

        if (sucesso) {
            labelAviso.setText("Usuário cadastrado com sucesso!");
        } else {
            labelAviso.setText("Erro! Não foi possivel realizar o cadastro");
        }
    }
}
