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
    @FXML private Label labelAvisoTrilha;
    
    // Botões dos Livros e Estrela para controle de liberação
    @FXML private Button btnLivro1;
    @FXML private Button btnLivro2;
    @FXML private Button btnLivro3;
    @FXML private Button btnLivro4;
    @FXML private Button btnLivro5;
    @FXML private Button btnEstrelaAvaliacao;
    
    // Rótulos de Estatísticas
    @FXML private Label labelAulas1;
    @FXML private Label labelAvaliacoes1;
    @FXML private Label labelAulas2;
    @FXML private Label labelAvaliacoes2;
    @FXML private Label labelAulas3;
    @FXML private Label labelAvaliacoes3;

    private String usuarioEmail;
    private String moduloSelecionado = "Geral"; // Módulo selecionado no ComboBox (Memória por padrão)

    public void setUsuarioEmail(String email) {
        this.usuarioEmail = email;
        carregarEstatisticasUsuario(); 
        atualizarVisualTrilha(); // Carrega o bloqueio ou liberação das cores dos livros
    }

    @FXML
    public void initialize() {
        if (comboModulos != null) {
            comboModulos.getItems().addAll("Memória", "Atenção", "Lógica", "Linguagem");
            comboModulos.getSelectionModel().select("Memória"); // Define Memória como padrão de partida
        }
    }

    // Ação acionada quando o idoso muda a categoria do ComboBox
    @FXML
    public void selecionarModuloAction() {
        String selecionado = comboModulos.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            this.moduloSelecionado = selecionado;
            System.out.println("Módulo de estudo alterado para: " + moduloSelecionado);
            carregarEstatisticasUsuario();
            atualizarVisualTrilha();
        }
    }

    private void carregarEstatisticasUsuario() {
        try {
            int idAula = obterIdModuloPorNome(moduloSelecionado);
            
            // Busca do banco quantas atividades (livros) o idoso concluiu no módulo ativo
            int atividadesConcluidas = obterContagemProgresso(usuarioEmail, idAula, "ATIVIDADE_CONCLUIDA");
            int avaliacoesConcluidas = obterContagemProgresso(usuarioEmail, idAula, "AVALIACAO_CONCLUIDA");
            
            // Atualiza dinamicamente as estatísticas baseada no módulo ativo
            labelAulas1.setText("Atividades feitas: " + atividadesConcluidas + "/5");
            labelAvaliacoes1.setText("Avaliações feitas: " + avaliacoesConcluidas + "/2");

        } catch (SQLException e) {
            System.out.println("Erro ao carregar estatísticas exclusivas: " + e.getMessage());
        }
    }

    private int obterIdModuloPorNome(String nomeModulo) {
        if ("Atenção".equalsIgnoreCase(nomeModulo)) return 2;
        if ("Lógica".equalsIgnoreCase(nomeModulo)) return 3;
        if ("Linguagem".equalsIgnoreCase(nomeModulo)) return 4;
        return 1; // "Memória" / "Geral"
    }

    // Gerencia o bloqueio e a cor de cada livro de forma progressiva
    private void atualizarVisualTrilha() {
        try {
            int idAula = obterIdModuloPorNome(moduloSelecionado);
            
            // Verifica no banco quais livros o usuário já concluiu neste módulo
            boolean livro1Concluido = checarProgressoAtividade(usuarioEmail, idAula, 1);
            boolean livro2Concluido = checarProgressoAtividade(usuarioEmail, idAula, 2);
            boolean livro3Concluido = checarProgressoAtividade(usuarioEmail, idAula, 3);
            boolean livro4Concluido = checarProgressoAtividade(usuarioEmail, idAula, 4);
            boolean livro5Concluido = checarProgressoAtividade(usuarioEmail, idAula, 5);

            // Livro 1 sempre liberado por ser o ponto de partida
            desbloquearBotaoTrilha(btnLivro1);

            // Livro 2 só libera se o 1 estiver concluído
            atualizarEstadoBotao(btnLivro2, livro1Concluido);
            // Livro 3 só libera se o 2 estiver concluído
            atualizarEstadoBotao(btnLivro3, livro2Concluido);
            // Livro 4 só libera se o 3 estiver concluído
            atualizarEstadoBotao(btnLivro4, livro3Concluido);
            // Livro 5 só libera se o 4 estiver concluído
            atualizarEstadoBotao(btnLivro5, livro4Concluido);
            
            // Estrela (Avaliação) só libera se o Livro 5 estiver concluído
            atualizarEstadoBotao(btnEstrelaAvaliacao, livro5Concluido);

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar visual da trilha: " + e.getMessage());
        }
    }

    private void atualizarEstadoBotao(Button botao, boolean condicaoLiberacao) {
        if (condicaoLiberacao) {
            desbloquearBotaoTrilha(botao);
        } else {
            bloquearBotaoTrilha(botao);
        }
    }

    private void bloquearBotaoTrilha(Button botao) {
        botao.setOpacity(0.4); // Deixa o botão cinza/transparente para indicar bloqueio
    }

    private void desbloquearBotaoTrilha(Button botao) {
        botao.setOpacity(1.0); // Deixa o botão com as cores normais
    }

    // Cliques em cada um dos livros da trilha (Verificando progressão)
    @FXML public void abrirLivro1(ActionEvent event) { iniciarAtividadeSeLiberada(event, 1, true); }
    @FXML public void abrirLivro2(ActionEvent event) { iniciarAtividadeSeLiberada(event, 2, checarLivroAnteriorLiberado(1)); }
    @FXML public void abrirLivro3(ActionEvent event) { iniciarAtividadeSeLiberada(event, 3, checarLivroAnteriorLiberado(2)); }
    @FXML public void abrirLivro4(ActionEvent event) { iniciarAtividadeSeLiberada(event, 4, checarLivroAnteriorLiberado(3)); }
    @FXML public void abrirLivro5(ActionEvent event) { iniciarAtividadeSeLiberada(event, 5, checarLivroAnteriorLiberado(4)); }
    
    @FXML 
    public void abrirEstrelaAvaliacao(ActionEvent event) { 
        iniciarAtividadeSeLiberada(event, 6, checarLivroAnteriorLiberado(5)); 
    }

    private boolean checarLivroAnteriorLiberado(int idLivroAnterior) {
        try {
            int idAula = obterIdModuloPorNome(moduloSelecionado);
            return checarProgressoAtividade(usuarioEmail, idAula, idLivroAnterior);
        } catch (SQLException e) {
            return false;
        }
    }

    private void iniciarAtividadeSeLiberada(ActionEvent event, int idAtividade, boolean liberada) {
        if (!liberada) {
            labelAvisoTrilha.setText("🔒 Conclua a lição anterior para liberar este conteúdo!");
            return;
        }
        
        labelAvisoTrilha.setText(""); // Limpa o aviso

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AtividadeQuestao.fxml"));
            Parent root = loader.load();
            
            AtividadeQuestaoController controller = loader.getController();
            controller.setUsuarioEmail(usuarioEmail);
            // Configura dinamicamente qual módulo e qual livro carregar no questionário
            controller.configurarAtividade(moduloSelecionado, idAtividade);

            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Erro ao iniciar atividade: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int obterContagemProgresso(String email, int idAula, String tipoConclusao) throws SQLException {
        String query = "SELECT COUNT(*) FROM usuario_progresso WHERE usuario_email = ? AND id_aula = ? AND tipo_conclusao = ?";
        try (Connection conn = databaseConn.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
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

    private boolean checarProgressoAtividade(String email, int idAula, int idAtividade) throws SQLException {
        String query = "SELECT COUNT(*) FROM usuario_progresso WHERE usuario_email = ? AND id_aula = ? AND id_atividade = ? AND tipo_conclusao = 'ATIVIDADE_CONCLUIDA'";
        try (Connection conn = databaseConn.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setInt(2, idAula);
            stmt.setInt(3, idAtividade);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
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
    public void abrirAula(MouseEvent event) {
        try {
            Node origem = (Node) event.getSource();
            String idCard = origem.getId(); 

            String tituloSelecionado = "Introdução ao uso de IA e suas aplicações";
            int idModulo = 1;
            
            if ("cardAula2".equals(idCard)) {
                tituloSelecionado = "Atuação prática real com o uso de IA";
                idModulo = 2;
            } else if ("cardAula3".equals(idCard)) {
                tituloSelecionado = "Uso de IA e tecnologia vestível";
                idModulo = 3;
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
    public void irParaConfiguracoes(ActionEvent event) {
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
    public void irParaPerfil(ActionEvent event) {
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