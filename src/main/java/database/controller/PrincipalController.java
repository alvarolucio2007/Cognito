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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class PrincipalController {

    @FXML private Button botaoPerfil;
    
    @FXML private ScrollPane scrollVideoAulas;
    @FXML private ScrollPane scrollAtividades;
    @FXML private Button abaVideoBtn;
    @FXML private Button abaAtividadesBtn;
    @FXML private ComboBox<String> comboModulos;
    
    @FXML private Label labelAulas1;
    @FXML private Label labelAvaliacoes1;
    @FXML private Label labelAulas2;
    @FXML private Label labelAvaliacoes2;
    @FXML private Label labelAulas3;
    @FXML private Label labelAvaliacoes3;

    private String usuarioEmail;

    public void setUsuarioEmail(String email) {
        this.usuarioEmail = email;
        carregarEstatisticasUsuario(); 
    }

    @FXML
    public void initialize() {
        if (comboModulos != null) {
            comboModulos.getItems().addAll("Memória", "Atenção", "Lógica", "Linguagem");
        }
    }

    // Eu fiz o carregamento de dados do progresso dinamicamente usando as colunas da nova tabela
    private void carregarEstatisticasUsuario() {
        try {
            long idUsuario = obterIdUsuario(usuarioEmail);
            if (idUsuario == -1) return;

            // Busca do banco quantas atividades (livros) o idoso concluiu no Módulo 1 (Aula 1)
            int atividadesAula1 = obterContagemProgresso(idUsuario, 1, "ATIVIDADE_CONCLUIDA");
            int avaliacoesAula1 = obterContagemProgresso(idUsuario, 1, "AVALIACAO_CONCLUIDA");
            
            // Exibo as atividades concluídas de forma cumulativa baseada nas interações
            labelAulas1.setText("Atividades feitas: " + atividadesAula1 + "/5");
            labelAvaliacoes1.setText("Avaliações feitas: " + avaliacoesAula1 + "/2");

            // Módulo 2
            int atividadesAula2 = obterContagemProgresso(idUsuario, 2, "ATIVIDADE_CONCLUIDA");
            int avaliacoesAula2 = obterContagemProgresso(idUsuario, 2, "AVALIACAO_CONCLUIDA");
            labelAulas2.setText("Atividades feitas: " + atividadesAula2 + "/5");
            labelAvaliacoes2.setText("Avaliações feitas: " + avaliacoesAula2 + "/1");

        } catch (SQLException e) {
            System.out.println("Erro ao carregar estatísticas exclusivas: " + e.getMessage());
        }
    }

    private long obterIdUsuario(String email) throws SQLException {
        String query = "SELECT \"pkIdUsuario\" FROM usuario WHERE usuario_email = ?";
        try (Connection conn = databaseConn.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("pkIdUsuario");
                }
            }
        }
        return -1;
    }

    private int obterContagemProgresso(long idUsuario, int idAula, String tipoConclusao) throws SQLException {
        String query = "SELECT COUNT(*) FROM usuario_progresso WHERE id_usuario = ? AND id_aula = ? AND tipo_conclusao = ?";
        try (Connection conn = databaseConn.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, idUsuario);
            stmt.setInt(2, idAula);
            stmt.setString(3, tipoConclusao);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    @FXML
    public void mostrarAbaVideo() {
        scrollVideoAulas.setVisible(true);
        scrollVideoAulas.setManaged(true);
        scrollAtividades.setVisible(false);
        scrollAtividades.setManaged(false);

        abaVideoBtn.setStyle("-fx-background-color: #a0a6ff; -fx-text-fill: #000dc9; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10;");
        abaAtividadesBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #1E293B; -fx-font-size: 16px;");
    }

    @FXML
    public void mostrarAbaAtividades() {
        scrollVideoAulas.setVisible(false);
        scrollVideoAulas.setManaged(false);
        scrollAtividades.setVisible(true);
        scrollAtividades.setManaged(true);

        abaVideoBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #1E293B; -fx-font-size: 16px;");
        abaAtividadesBtn.setStyle("-fx-background-color: #a0a6ff; -fx-text-fill: #000dc9; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10;");
    }

    @FXML
    public void abrirTrilhaAtividade(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AtividadeQuestao.fxml"));
            Parent root = loader.load();
            
            AtividadeQuestaoController controller = loader.getController();
            controller.setUsuarioEmail(usuarioEmail);

            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Erro ao iniciar trilha de atividades: " + e.getMessage());
        }
    }

    @FXML
    public void abrirAula(MouseEvent event) {
        try {
            Node origem = (Node) event.getSource();
            String idCard = origem.getId(); 

            String tituloSelecionado = "Introdução ao uso de IA e suas aplicações";
            
            if ("cardAula2".equals(idCard)) {
                tituloSelecionado = "Atuação prática real com o uso de IA";
            } else if ("cardAula3".equals(idCard)) {
                tituloSelecionado = "Uso de IA e tecnologia vestível";
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VideoAula.fxml"));
            Parent root = loader.load();

            VideoAulaController controller = loader.getController();
            controller.setUsuarioEmail(usuarioEmail);
            controller.setTituloAula(tituloSelecionado); 

            origem.getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Erro ao abrir a vídeo aula selecionada: " + e.getMessage());
        }
    }

    @FXML
    private void sair() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            botaoPerfil.getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Erro ao deslogar: " + e.getMessage());
        }
    }

    @FXML
    private void irParaConfiguracoes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Configuracoes.fxml"));
            Parent root = loader.load();
            
            ConfiguracoesController controller = loader.getController();
            controller.setUsuarioEmail(usuarioEmail);

            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Erro ao abrir configurações: " + e.getMessage());
        }
    }

    @FXML
    private void irParaPerfil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Perfil.fxml"));
            Parent root = loader.load();
            
            PerfilController controller = loader.getController();
            controller.setUsuarioEmail(usuarioEmail);

            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Erro ao abrir tela de Perfil: " + e.getMessage());
        }
    }
}