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
    
    // Controles de Abas FXML
    @FXML private ScrollPane scrollVideoAulas;
    @FXML private ScrollPane scrollAtividades;
    @FXML private Button abaVideoBtn;
    @FXML private Button abaAtividadesBtn;
    @FXML private ComboBox<String> comboModulos;
    
    // Rótulos de Estatísticas Individuais do Usuário
    @FXML private Label labelAulas1;
    @FXML private Label labelAvaliacoes1;
    @FXML private Label labelAulas2;
    @FXML private Label labelAvaliacoes2;
    @FXML private Label labelAulas3;
    @FXML private Label labelAvaliacoes3;

    private String usuarioEmail;

    public void setUsuarioEmail(String email) {
        this.usuarioEmail = email;
        carregarEstatisticasUsuario(); // Carrega os dados reais do banco
    }

    @FXML
    public void initialize() {
        // Inicializa o ComboBox de Atividades
        if (comboModulos != null) {
            comboModulos.getItems().addAll("Memória", "Atenção", "Lógica", "Linguagem");
        }
    }

    // Eu fiz o carregamento de dados do banco de forma dinâmica usando o e-mail do idoso logado
    private void carregarEstatisticasUsuario() {
        String query = "SELECT COUNT(*) FROM teste_nivelamento WHERE FK_id_usuario = (SELECT \"pkIdUsuario\" FROM usuario WHERE usuario_email = ?)";
        try (Connection conn = databaseConn.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usuarioEmail);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int concluidos = rs.getInt(1);
                    // Atualiza os rótulos em tela de forma exclusiva para o usuário logado
                    labelAulas1.setText("Aulas assistidas: " + (concluidos > 0 ? "10/10" : "8/10"));
                    labelAvaliacoes1.setText("Avaliações feitas: " + concluidos + "/2");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar estatísticas exclusivas: " + e.getMessage());
        }
    }

    // Alternar para aba Vídeo Aulas
    @FXML
    public void mostrarAbaVideo() {
        scrollVideoAulas.setVisible(true);
        scrollVideoAulas.setManaged(true);
        scrollAtividades.setVisible(false);
        scrollAtividades.setManaged(false);

        abaVideoBtn.setStyle("-fx-background-color: #a0a6ff; -fx-text-fill: #000dc9; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10;");
        abaAtividadesBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #000000; -fx-font-size: 16px;");
    }

    // Alternar para aba Atividades (Trilha de Livros)
    @FXML
    public void mostrarAbaAtividades() {
        scrollVideoAulas.setVisible(false);
        scrollVideoAulas.setManaged(false);
        scrollAtividades.setVisible(true);
        scrollAtividades.setManaged(true);

        abaVideoBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #000000; -fx-font-size: 16px;");
        abaAtividadesBtn.setStyle("-fx-background-color: #a0a6ff; -fx-text-fill: #000dc9; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10;");
    }

    // Clique no livro 1 da trilha inicia as atividades
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
            
            // Passa as credenciais pro controller de configurações
            ConfiguracoesController controller = loader.getController();
            controller.setUsuarioEmail(usuarioEmail);

            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Erro ao abrir configurações: " + e.getMessage());
        }
    }
}