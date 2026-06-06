package database.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import database.conn.databaseConn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class AtividadeQuestaoController {

    @FXML private Button botaoVoltar;
    @FXML private Label labelNumeroQuestao;
    @FXML private Label labelPergunta;
    @FXML private VBox boxAlternativas;
    @FXML private Button botaoResponder;
    @FXML private VBox areaFeedback;
    @FXML private Label labelIconeFeedback;
    @FXML private Label labelTituloFeedback;
    @FXML private Label labelSubtituloFeedback;
    @FXML private Label labelMascote; // Emoji provisório
    @FXML private ImageView imgMascote; // Mascote do Figma
    @FXML private Label labelExplicacao;
    @FXML private Button botaoProxima;

    private String usuarioEmail;
    private int questaoAtual = 0;
    private Button alternativaSelecionada = null;

    private static class Questao {
        String pergunta;
        List<String> alternativas;
        int indiceCorreto;
        String explicacaoAcerto;
        String explicacaoErro;

        Questao(String pergunta, List<String> alternativas, int indiceCorreto,
                String explicacaoAcerto, String explicacaoErro) {
            this.pergunta = pergunta;
            this.alternativas = alternativas;
            this.indiceCorreto = indiceCorreto;
            this.explicacaoAcerto = explicacaoAcerto;
            this.explicacaoErro = explicacaoErro;
        }
    }

    private final List<Questao> questoes = new ArrayList<>(Arrays.asList(
        new Questao(
            "Qual destas opções melhor descreve a Inteligência Artificial no seu cotidiano?",
            Arrays.asList(
                "Um robô que substitui os humanos no trabalho",
                "Um assistente que ajuda a realizar tarefas e encontrar informações mais rápido",
                "Um vírus de computador que espiona as pessoas",
                "Um canal de TV interativo"
            ),
            1,
            "Exatamente! A IA é uma ferramenta que nos ajuda no dia a dia, como quando pedimos receitas ao Google ou usamos o GPS para nos guiar.",
            "Não se preocupe! A IA não é um robô ou vírus. Ela é como um assistente digital que nos ajuda a fazer coisas mais rápido, como encontrar receitas ou calcular rotas."
        ),
        new Questao(
            "Para que serve o campo de digitação (prompt) no ChatGPT?",
            Arrays.asList(
                "Para fazer ligações telefônicas",
                "Para assistir vídeos na internet",
                "Para escrever perguntas ou comandos que você quer que a IA responda",
                "Para comprar produtos online"
            ),
            2,
            "Perfeito! O prompt é o local onde você escreve o que quer da IA, como se fosse mandar uma mensagem para ela.",
            "O prompt é onde você escreve suas perguntas para a IA, como se fosse um chat. Você pode pedir receitas, tirar dúvidas ou pedir sugestões!"
        )
    ));

    public void setUsuarioEmail(String email) {
        this.usuarioEmail = email;
    }

    @FXML
    public void initialize() {
        carregarQuestao(0);
    }

    private void carregarQuestao(int indice) {
        questaoAtual = indice;
        alternativaSelecionada = null;
        areaFeedback.setVisible(false);
        areaFeedback.setManaged(false);
        botaoResponder.setVisible(true);
        botaoResponder.setManaged(true);

        Questao q = questoes.get(indice);
        labelPergunta.setText(q.pergunta);
        labelNumeroQuestao.setText("Questão " + (indice + 1) + "/" + questoes.size());

        boxAlternativas.getChildren().clear();
        for (int i = 0; i < q.alternativas.size(); i++) {
            final int idx = i;
            Button btn = new Button(q.alternativas.get(i));
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setWrapText(true);
            btn.setPrefHeight(60);
            btn.setStyle(estiloAlternativaNormal());
            btn.setOnAction(e -> selecionarAlternativa(btn, idx));
            boxAlternativas.getChildren().add(btn);
        }
    }

    private void selecionarAlternativa(Button btn, int indice) {
        for (Node node : boxAlternativas.getChildren()) {
            if (node instanceof Button) {
                ((Button) node).setStyle(estiloAlternativaNormal());
            }
        }
        btn.setStyle(estiloAlternativaSelecionada());
        alternativaSelecionada = btn;
    }

    @FXML
    public void responder() {
        if (alternativaSelecionada == null) {
            labelPergunta.setText("⚠ Selecione uma alternativa antes de responder!");
            return;
        }

        Questao q = questoes.get(questaoAtual);
        int indiceSelecionado = boxAlternativas.getChildren().indexOf(alternativaSelecionada);
        boolean acertou = indiceSelecionado == q.indiceCorreto;

        for (int i = 0; i < boxAlternativas.getChildren().size(); i++) {
            Button btn = (Button) boxAlternativas.getChildren().get(i);
            if (i == q.indiceCorreto) {
                btn.setStyle(estiloAlternativaCorreta());
            } else if (btn == alternativaSelecionada && !acertou) {
                btn.setStyle(estiloAlternativaErrada());
            }
            btn.setDisable(true);
        }

        // Esconde o emoji provisório de texto para usar as imagens reais do figma
        labelMascote.setVisible(false);

        try {
            if (acertou) {
                labelIconeFeedback.setText("✅");
                labelTituloFeedback.setText("Correto!");
                labelTituloFeedback.setStyle("-fx-text-fill: #16A34A; -fx-font-size: 22px; -fx-font-weight: bold;");
                labelSubtituloFeedback.setText("Muito bem! Continue assim.");
                
                // Carrega a imagem real do mascote feliz do Figma
                imgMascote.setImage(new Image(getClass().getResourceAsStream("/images/Mascote_correct.png")));
                labelExplicacao.setText(q.explicacaoAcerto);
            } else {
                labelIconeFeedback.setText("❌");
                labelTituloFeedback.setText("Errado!");
                labelTituloFeedback.setStyle("-fx-text-fill: #DC2626; -fx-font-size: 22px; -fx-font-weight: bold;");
                labelSubtituloFeedback.setText("Não desanime, você vai aprender!");
                
                // Carrega a imagem real do mascote triste do Figma
                imgMascote.setImage(new Image(getClass().getResourceAsStream("/images/Mascote_wrong.png")));
                labelExplicacao.setText(q.explicacaoErro);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem de feedback do mascote: " + e.getMessage());
        }

        boolean ultima = questaoAtual == questoes.size() - 1;
        botaoProxima.setText(ultima ? "Finalizar Atividade ✓" : "Próxima Questão →");

        botaoResponder.setVisible(false);
        botaoResponder.setManaged(false);
        areaFeedback.setVisible(true);
        areaFeedback.setManaged(true);
    }

    @FXML
    public void proximaQuestaoAction(ActionEvent event) {
        if (questaoAtual < questoes.size() - 1) {
            carregarQuestao(questaoAtual + 1);
        } else {
            salvarProgressoAtividade(); 
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VideoAula.fxml"));
                Parent root = loader.load();
                
                VideoAulaController controller = loader.getController();
                controller.setUsuarioEmail(usuarioEmail);

                ((Node) event.getSource()).getScene().setRoot(root);
            } catch (Exception e) {
                System.out.println("Erro ao finalizar atividade: " + e.getMessage());
            }
        }
    }

    private void salvarProgressoAtividade() {
        try {
            long idUsuario = obterIdUsuario(usuarioEmail);
            if (idUsuario == -1) return;

            int idAula = 1; // Módulo 1 (Aula 1)
            int idAtividade = 1; // Livro 1 da trilha de atividades

            if (!checarProgressoExiste(idUsuario, idAula, idAtividade, "ATIVIDADE_CONCLUIDA")) {
                String query = "INSERT INTO usuario_progresso (id_usuario, id_aula, id_atividade, tipo_conclusao) VALUES (?, ?, ?, 'ATIVIDADE_CONCLUIDA')";
                try (Connection conn = databaseConn.connect();
                     PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setLong(1, idUsuario);
                    stmt.setInt(2, idAula);
                    stmt.setInt(3, idAtividade);
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar progresso de atividade: " + e.getMessage());
        }
    }

    private boolean checarProgressoExiste(long idUsuario, int idAula, int idAtividade, String tipoConclusao) throws SQLException {
        String query = "SELECT COUNT(*) FROM usuario_progresso WHERE id_usuario = ? AND id_aula = ? AND id_atividade = ? AND tipo_conclusao = ?";
        try (Connection conn = databaseConn.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, idUsuario);
            stmt.setInt(2, idAula);
            stmt.setInt(3, idAtividade);
            stmt.setString(4, tipoConclusao);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
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

    private boolean checarProgressoExiste(long idUsuario, int idAula, String tipoConclusao) throws SQLException {
        String query = "SELECT COUNT(*) FROM usuario_progresso WHERE id_usuario = ? AND id_aula = ? AND tipo_conclusao = ?";
        try (Connection conn = databaseConn.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, idUsuario);
            stmt.setInt(2, idAula);
            stmt.setString(3, tipoConclusao);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    @FXML
    public void voltarAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VideoAula.fxml"));
            Parent root = loader.load();
            
            VideoAulaController controller = loader.getController();
            controller.setUsuarioEmail(usuarioEmail);

            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Erro ao voltar: " + e.getMessage());
        }
    }

    private String estiloAlternativaNormal() {
        return "-fx-background-color: #F1F5F9;-fx-text-fill: #0D1B54;-fx-font-size: 15px;-fx-font-weight: bold;-fx-background-radius: 14;-fx-border-color: #CBD5E1;-fx-border-radius: 14;-fx-cursor: hand;-fx-padding: 14 16 14 16;-fx-alignment: CENTER_LEFT;";
    }
    private String estiloAlternativaSelecionada() {
        return "-fx-background-color: #0D1B54;-fx-text-fill: white;-fx-font-size: 15px;-fx-font-weight: bold;-fx-background-radius: 14;-fx-border-color: #0D1B54;-fx-border-radius: 14;-fx-cursor: hand;-fx-padding: 14 16 14 16;-fx-alignment: CENTER_LEFT;";
    }
    private String estiloAlternativaCorreta() {
        return "-fx-background-color: #16A34A;-fx-text-fill: white;-fx-font-size: 15px;-fx-font-weight: bold;-fx-background-radius: 14;-fx-border-color: #16A34A;-fx-border-radius: 14;-fx-padding: 14 16 14 16;-fx-alignment: CENTER_LEFT;";
    }
    private String estiloAlternativaErrada() {
        return "-fx-background-color: #DC2626;-fx-text-fill: white;-fx-font-size: 15px;-fx-font-weight: bold;-fx-background-radius: 14;-fx-border-color: #DC2626;-fx-border-radius: 14;-fx-padding: 14 16 14 16;-fx-alignment: CENTER_LEFT;";
    }
}