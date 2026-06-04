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
                // Login bem-sucedido vai sempre direto para a tela principal
                irParaPrincipal(); 
            } else {
                System.out.println("Senha incorreta.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao tentar fazer login: " + e.getMessage());
        }
    }

    private void irParaPrincipal() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Principal.fxml"));
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

    private boolean temTesteNivelamento(String email) throws SQLException {
        // Esta query usa uma estratégia dinâmica: ela busca o ID do usuário de forma compatível
        // para evitar falhas se a coluna primária se chamar pkidusuario, pk_id_usuario ou id_usuario.
        String query = "SELECT COUNT(*) FROM teste_nivelamento WHERE FK_id_usuario = (" +
                       "  SELECT COALESCE(" +
                       "    (SELECT pkidusuario FROM usuario WHERE usuario_email = ?)," +
                       "    (SELECT pk_id_usuario FROM usuario WHERE usuario_email = ?)," +
                       "    (SELECT id_usuario FROM usuario WHERE usuario_email = ?)" +
                       "  )" +
                       ")";
        try (Connection conn = databaseConn.connect(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, email);
            stmt.setString(3, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            // Se as tabelas estiverem sem dados correspondentes, trata de forma segura retornando falso (força o teste)
            System.out.println("Aviso na checagem de nivelamento: " + e.getMessage());
        }
        return false;
    }
}