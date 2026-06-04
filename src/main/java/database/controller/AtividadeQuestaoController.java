package database.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    @FXML private Label labelMascote;
    @FXML private Label labelExplicacao;
    @FXML private Button botaoProxima;

    private String usuarioEmail;
    private int questaoAtual = 0;
    private Button alternativaSelecionada = null;

    // ── Estrutura de dados das questões ─────────────────────────────
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
        ),
        new Questao(
            "Se você receber um link suspeito no WhatsApp dizendo ser do banco, qual a primeira atitude?",
            Arrays.asList(
                "Clicar no link para ver do que se trata",
                "Compartilhar com amigos para avisar",
                "Não clicar e verificar a informação em um canal oficial ou usar uma IA de segurança",
                "Responder a mensagem pedindo mais informações"
            ),
            2,
            "Muito bem! Nunca clique em links suspeitos. Sempre confirme pelo número oficial do banco ou acesse o site digitando o endereço manualmente.",
            "Cuidado! Links suspeitos são uma forma comum de golpe. O correto é nunca clicar e sempre verificar ligando para o número oficial do banco."
        ),
        new Questao(
            "Verdadeiro ou Falso: A IA pode ser usada para praticar respostas de uma entrevista de emprego.",
            Arrays.asList(
                "Verdadeiro — a IA pode simular perguntas de entrevista",
                "Falso — a IA não consegue ajudar com entrevistas"
            ),
            0,
            "Verdade! Você pode pedir ao ChatGPT para simular uma entrevista de emprego e praticar suas respostas antes do dia real.",
            "Na verdade, a IA pode sim ajudar! Você pode pedir ao ChatGPT para fazer perguntas de entrevista e te dar dicas de como melhorar suas respostas."
        ),
        new Questao(
            "Qual o principal benefício de um idoso aprender a usar IA hoje?",
            Arrays.asList(
                "Substituir completamente o contato com outras pessoas",
                "Manter-se conectado com o mundo moderno e ter mais autonomia digital",
                "Usar o celular apenas para jogos",
                "Evitar sair de casa para sempre"
            ),
            1,
            "Excelente! Aprender IA ajuda você a se manter conectado, fazer videochamadas, pesquisar sobre saúde e muito mais, com mais independência.",
            "O maior benefício é a autonomia! Com a IA, você pode pesquisar informações, marcar consultas, falar com familiares e participar do mundo digital com mais facilidade."
        )
    ));

    // ────────────────────────────────────────────────────────────────

    public void setUsuarioEmail(String email) {
        this.usuarioEmail = email;
    }

    @FXML
    public void initialize() {
        carregarQuestao(0);
    }

    // ── Carrega a questão pelo índice ────────────────────────────────
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

    // ── Seleciona alternativa visualmente ───────────────────────────
    private void selecionarAlternativa(Button btn, int indice) {
        // Reseta todas
        for (Node node : boxAlternativas.getChildren()) {
            if (node instanceof Button) {
                ((Button) node).setStyle(estiloAlternativaNormal());
            }
        }
        // Marca a selecionada
        btn.setStyle(estiloAlternativaSelecionada());
        alternativaSelecionada = btn;
    }

    // ── Verifica resposta e exibe feedback ──────────────────────────
    @FXML
    public void responder() {
        if (alternativaSelecionada == null) {
            labelPergunta.setText("⚠ Selecione uma alternativa antes de responder!");
            return;
        }

        Questao q = questoes.get(questaoAtual);
        int indiceSelecionado = boxAlternativas.getChildren().indexOf(alternativaSelecionada);
        boolean acertou = indiceSelecionado == q.indiceCorreto;

        // Destaca correto e errado
        for (int i = 0; i < boxAlternativas.getChildren().size(); i++) {
            Button btn = (Button) boxAlternativas.getChildren().get(i);
            if (i == q.indiceCorreto) {
                btn.setStyle(estiloAlternativaCorreta());
            } else if (btn == alternativaSelecionada && !acertou) {
                btn.setStyle(estiloAlternativaErrada());
            }
            btn.setDisable(true);
        }

        // Feedback
        if (acertou) {
            labelIconeFeedback.setText("✅");
            labelTituloFeedback.setText("Correto!");
            labelTituloFeedback.setStyle("-fx-text-fill: #16A34A; -fx-font-size: 22px; -fx-font-weight: bold;");
            labelSubtituloFeedback.setText("Muito bem! Continue assim.");
            labelMascote.setText("🦉😄");
            labelExplicacao.setText(q.explicacaoAcerto);
        } else {
            labelIconeFeedback.setText("❌");
            labelTituloFeedback.setText("Errado!");
            labelTituloFeedback.setStyle("-fx-text-fill: #DC2626; -fx-font-size: 22px; -fx-font-weight: bold;");
            labelSubtituloFeedback.setText("Não desanime, você vai aprender!");
            labelMascote.setText("🦉😔");
            labelExplicacao.setText(q.explicacaoErro);
        }

        // Última questão: muda texto do botão
        boolean ultima = questaoAtual == questoes.size() - 1;
        botaoProxima.setText(ultima ? "Finalizar Atividade ✓" : "Próxima Questão →");

        botaoResponder.setVisible(false);
        botaoResponder.setManaged(false);
        areaFeedback.setVisible(true);
        areaFeedback.setManaged(true);
    }

    // ── Avança pra próxima ou finaliza (NOME CORRIGIDO AQUI) ─────────
    @FXML
    public void proximaQuestaoAction() {
        if (questaoAtual < questoes.size() - 1) {
            carregarQuestao(questaoAtual + 1);
        } else {
            // Finalizado — volta pra tela de vídeo aula buscando a scene direto do botaoProxima
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/VideoAula.fxml"));
                botaoProxima.getScene().setRoot(root);
            } catch (Exception e) {
                System.out.println("Erro ao finalizar atividade: " + e.getMessage());
            }
        }
    }

    // ── Voltar ───────────────────────────────────────────────────────
    @FXML
    public void voltarAction() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/VideoAula.fxml"));
            botaoVoltar.getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Erro ao voltar: " + e.getMessage());
        }
    }

    // ── Estilos ──────────────────────────────────────────────────────
    private String estiloAlternativaNormal() {
        return "-fx-background-color: #F1F5F9;" +
               "-fx-text-fill: #0D1B54;" +
               "-fx-font-size: 15px;" +
               "-fx-font-weight: bold;" +
               "-fx-background-radius: 14;" +
               "-fx-border-color: #CBD5E1;" +
               "-fx-border-radius: 14;" +
               "-fx-cursor: hand;" +
               "-fx-padding: 14 16 14 16;" +
               "-fx-alignment: CENTER_LEFT;";
    }

    private String estiloAlternativaSelecionada() {
        return "-fx-background-color: #0D1B54;" +
               "-fx-text-fill: white;" +
               "-fx-font-size: 15px;" +
               "-fx-font-weight: bold;" +
               "-fx-background-radius: 14;" +
               "-fx-border-color: #0D1B54;" +
               "-fx-border-radius: 14;" +
               "-fx-cursor: hand;" +
               "-fx-padding: 14 16 14 16;" +
               "-fx-alignment: CENTER_LEFT;";
    }

    private String estiloAlternativaCorreta() {
        return "-fx-background-color: #16A34A;" +
               "-fx-text-fill: white;" +
               "-fx-font-size: 15px;" +
               "-fx-font-weight: bold;" +
               "-fx-background-radius: 14;" +
               "-fx-border-color: #16A34A;" +
               "-fx-border-radius: 14;" +
               "-fx-padding: 14 16 14 16;" +
               "-fx-alignment: CENTER_LEFT;";
    }

    private String estiloAlternativaErrada() {
        return "-fx-background-color: #DC2626;" +
               "-fx-text-fill: white;" +
               "-fx-font-size: 15px;" +
               "-fx-font-weight: bold;" +
               "-fx-background-radius: 14;" +
               "-fx-border-color: #DC2626;" +
               "-fx-border-radius: 14;" +
               "-fx-padding: 14 16 14 16;" +
               "-fx-alignment: CENTER_LEFT;";
    }
}