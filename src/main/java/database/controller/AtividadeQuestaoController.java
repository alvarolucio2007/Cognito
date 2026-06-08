package database.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.conn.databaseConn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    @FXML private Label labelMascote; 
    @FXML private ImageView imgMascote; 
    @FXML private Label labelExplicacao;
    @FXML private Button botaoProxima;

    private String usuarioEmail;
    private int questaoAtual = 0;
    private Button alternativaSelecionada = null;

    private List<BancoQuestoes.Questao> questoes; 
    private int idAtividadeAtiva = 1; 
    private String moduloAtivo = "Geral"; 

    public void configurarAtividade(String modulo, int idAtividade) {
        this.moduloAtivo = modulo;
        this.idAtividadeAtiva = idAtividade;
        this.questoes = BancoQuestoes.obterQuestoes(modulo, idAtividade);
        carregarQuestao(0);
    }

    public void setUsuarioEmail(String email) {
        this.usuarioEmail = email;
    }

    @FXML
    public void initialize() {
    }

    private void carregarQuestao(int indice) {
        questaoAtual = indice;
        alternativaSelecionada = null;
        areaFeedback.setVisible(false);
        areaFeedback.setManaged(false);
        botaoResponder.setVisible(true);
        botaoResponder.setManaged(true);

        if (questoes == null || questoes.isEmpty()) {
            labelPergunta.setText("Aviso: Módulo de questões em desenvolvimento.");
            botaoResponder.setVisible(false);
            return;
        }

        BancoQuestoes.Questao q = questoes.get(indice);
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

        BancoQuestoes.Questao q = questoes.get(questaoAtual);
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

        labelMascote.setVisible(false);

        try {
            if (acertou) {
                labelIconeFeedback.setText("✅");
                labelTituloFeedback.setText("Correto!");
                labelTituloFeedback.setStyle("-fx-text-fill: #16A34A; -fx-font-size: 22px; -fx-font-weight: bold;");
                labelSubtituloFeedback.setText("Muito bem! Continue assim.");
                imgMascote.setImage(new Image(getClass().getResourceAsStream("/images/Mascote_correct.png")));
                labelExplicacao.setText(q.explicacaoAcerto);
            } else {
                labelIconeFeedback.setText("❌");
                labelTituloFeedback.setText("Errado!");
                labelTituloFeedback.setStyle("-fx-text-fill: #DC2626; -fx-font-size: 22px; -fx-font-weight: bold;");
                labelSubtituloFeedback.setText("Não desanime, você vai aprender!");
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
            // Eu gravo o progresso passando o ID dinâmico do livro que foi concluído!
            salvarProgressoAtividade(); 
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VideoAula.fxml"));
                Parent root = loader.load();
                
                VideoAulaController controller = loader.getController();
                controller.setUsuarioEmail(usuarioEmail); // PROPAGA O EMAIL DE VOLTA!
                controller.setTituloAula("Introdução ao uso de IA e suas aplicações");

                ((Node) event.getSource()).getScene().setRoot(root);
            } catch (Exception e) {
                System.out.println("Erro ao finalizar atividade: " + e.getMessage());
            }
        }
    }

    private void salvarProgressoAtividade() {
        if (usuarioEmail == null) return;
        try {
            // Eu mudei de estático para dinâmico: identifica qual o ID de módulo real (1, 2 ou 3)
            int idAula = obterIdModuloPorNome(moduloAtivo); 

            if (!checarProgressoExiste(usuarioEmail, idAula, idAtividadeAtiva, "ATIVIDADE_CONCLUIDA")) {
                String query = "INSERT INTO usuario_progresso (usuario_email, id_aula, id_atividade, tipo_conclusao) VALUES (?, ?, ?, 'ATIVIDADE_CONCLUIDA')";
                try (Connection conn = databaseConn.connect();
                     PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, usuarioEmail);
                    stmt.setInt(2, idAula);
                    stmt.setInt(3, idAtividadeAtiva); // Salva o ID correspondente do livro (1 a 5)
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar progresso de atividade: " + e.getMessage());
        }
    }

    private int obterIdModuloPorNome(String nomeModulo) {
        if ("Atenção".equalsIgnoreCase(nomeModulo)) return 2;
        if ("Lógica".equalsIgnoreCase(nomeModulo)) return 3;
        if ("Linguagem".equalsIgnoreCase(nomeModulo)) return 4;
        return 1; // "Memória" / "Geral"
    }

    private boolean checarProgressoExiste(String email, int idAula, int idAtividade, String tipoConclusao) throws SQLException {
        String query = "SELECT COUNT(*) FROM usuario_progresso WHERE usuario_email = ? AND id_aula = ? AND id_atividade = ? AND tipo_conclusao = ?";
        try (Connection conn = databaseConn.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setInt(2, idAula);
            stmt.setInt(3, idAtividade);
            stmt.setString(4, tipoConclusao);
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
            controller.setUsuarioEmail(usuarioEmail); // PROPAGA O EMAIL DE VOLTA!
            controller.setTituloAula("Introdução ao uso de IA e suas aplicações");

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