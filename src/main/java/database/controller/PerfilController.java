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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class PerfilController {

    @FXML private Button botaoVoltar;
    @FXML private TextField nomeField;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private TextField senhaMostradaField;
    @FXML private Button botaoRevelarSenha;
    @FXML private Label labelAviso;

    private String usuarioEmailOriginal;
    private boolean senhaVisivel = false;

    // Eu fiz o carregamento de dados do banco de forma dinâmica usando o e-mail do idoso logado ao abrir a tela
    public void setUsuarioEmail(String email) {
        this.usuarioEmailOriginal = email;
        this.emailField.setText(email);
        // Exibo o username mapeado localmente no campo do seu design
        this.usernameField.setText(email.split("@")[0] + "123"); 
        buscarDadosNoBanco();
    }

    private void buscarDadosNoBanco() {
        String query = "SELECT usuario_nome, usuario_senha FROM usuario WHERE usuario_email = ?";
        try (Connection conn = databaseConn.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usuarioEmailOriginal);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nomeField.setText(rs.getString("usuario_nome"));
                    senhaField.setText(rs.getString("usuario_senha"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar dados do usuário: " + e.getMessage());
        }
    }

    // Eu implementei a lógica do botão de olho para alternar a exibição da senha
    @FXML
    private void toggleSenha() {
        if (senhaVisivel) {
            senhaVisivel = false;
            senhaField.setText(senhaMostradaField.getText());
            senhaField.setVisible(true);
            senhaField.setManaged(true);
            senhaMostradaField.setVisible(false);
            senhaMostradaField.setManaged(false);
            botaoRevelarSenha.setText("👁");
        } else {
            senhaVisivel = true;
            senhaMostradaField.setText(senhaField.getText());
            senhaMostradaField.setVisible(true);
            senhaMostradaField.setManaged(true);
            senhaField.setVisible(false);
            senhaField.setManaged(false);
            botaoRevelarSenha.setText("🙈");
        }
    }

    // Eu adicionei o método de salvar que executa um UPDATE real no banco de dados local
    @FXML
    private void salvar() {
        String nome = nomeField.getText().trim();
        String email = emailField.getText().trim();
        // Sincroniza a senha dependendo de qual campo está sendo exibido no momento
        String senha = senhaVisivel ? senhaMostradaField.getText() : senhaField.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            labelAviso.setText("Preencha todos os campos!");
            labelAviso.setStyle("-fx-text-fill: #DC2626;");
            return;
        }

        String query = "UPDATE usuario SET usuario_nome = ?, usuario_email = ?, usuario_senha = ? WHERE usuario_email = ?";
        try (Connection conn = databaseConn.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setString(4, usuarioEmailOriginal);
            
            int atualizados = stmt.executeUpdate();
            if (atualizados > 0) {
                this.usuarioEmailOriginal = email; // Atualiza a credencial ativa
                labelAviso.setText("Perfil atualizado com sucesso!");
                labelAviso.setStyle("-fx-text-fill: #16A34A;");
            }
        } catch (SQLException e) {
            labelAviso.setText("Erro ao salvar no banco: " + e.getMessage());
            labelAviso.setStyle("-fx-text-fill: #DC2626;");
        }
    }

    @FXML
    private void voltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Principal.fxml"));
            Parent root = loader.load();
            
            // Retorna passando as credenciais do usuário de volta para a principal
            PrincipalController controller = loader.getController();
            controller.setUsuarioEmail(usuarioEmailOriginal);

            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Erro ao retornar para a tela principal: " + e.getMessage());
        }
    }
}