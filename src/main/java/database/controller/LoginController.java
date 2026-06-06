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

    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private Button botaoLogin;

    @FXML
    public void login() {
        String emailDigitado = emailField.getText().trim();
        String senhaDigitada = senhaField.getText().trim();

        if (emailDigitado.isEmpty() || senhaDigitada.isEmpty()) {
            System.out.println("Por favor, preencha todos os campos.");
            return;
        }

        try {
            String senhaRecebida = getSenhaEmail(emailDigitado);
            if (senhaRecebida == null) {
                System.out.println("Email não encontrado.");
                return;
            }

            if (senhaDigitada.equals(senhaRecebida)) {
                // Eu passo o e-mail digitado para que o método propage o login na próxima tela
                irParaPrincipal(emailDigitado); 
            } else {
                System.out.println("Senha incorreta.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao tentar fazer login: " + e.getMessage());
        }
    }

    // Eu atualizei a assinatura deste método para receber a String do e-mail de login,
    // garantindo que as informações do usuário logado cheguem corretamente à tela principal.
    private void irParaPrincipal(String email) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Principal.fxml"));
            Parent root = loader.load();

            PrincipalController controller = loader.getController();
            controller.setUsuarioEmail(email); // Injeta o e-mail na tela principal

            botaoLogin.getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Erro ao carregar a tela principal: " + e.getMessage());
        }
    }

    private void irParaTesteNivelamento(String email) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TesteNivelamento.fxml"));
            Parent root = loader.load();
            
            TesteNivelamentoController controller = loader.getController();
            controller.setUsuarioEmail(email);

            botaoLogin.getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Erro ao carregar teste de nivelamento: " + e.getMessage());
        }
    }

    @FXML
    private void irParaCadastro() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Cadastro.fxml"));
        botaoLogin.getScene().setRoot(root);
    }

    public String getSenhaEmail(String email) throws SQLException {
        String query = "SELECT usuario_senha FROM usuario WHERE usuario_email = ?";
        try (Connection conn = databaseConn.connect(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getString("usuario_senha") : null;
            }
        }
    }
}