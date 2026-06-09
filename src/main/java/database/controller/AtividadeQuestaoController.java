package database.controller;

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
import database.databaseModels.BancoQuestoes;

public class AtividadeQuestaoController {

  @FXML
  private Button botaoVoltar;
  @FXML
  private Label labelNumeroQuestao;
  @FXML
  private Label labelPergunta;
  @FXML
  private VBox boxAlternativas;
  @FXML
  private Button botaoResponder;
  @FXML
  private VBox areaFeedback;
  @FXML
  private Label labelIconeFeedback;
  @FXML
  private Label labelTituloFeedback;
  @FXML
  private Label labelSubtituloFeedback;
  @FXML
  private Label labelMascote;
  @FXML
  private ImageView imgMascote;
  @FXML
  private Label labelExplicacao;
  @FXML
  private Button botaoProxima;

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
      salvarProgressoAtividade();
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VideoAula.fxml"));
        Parent root = loader.load();

        VideoAulaController controller = loader.getController();
        controller.setUsuarioEmail(usuarioEmail);
        // Eu retorno passando o título correto mapeado para o módulo de onde ele partiu
        controller.setTituloAula(obterTituloAulaPorModulo(moduloAtivo));

        ((Node) event.getSource()).getScene().setRoot(root);
      } catch (Exception e) {
        System.out.println("Erro ao finalizar atividade: " + e.getMessage());
      }
    }
  }

  private void salvarProgressoAtividade() {
    if (usuarioEmail == null)
      return;

    try {
      int idAula = obterIdModuloPorNome(moduloAtivo);

      // Determina se é a estrela de avaliação final ou uma lição comum
      boolean ehAvaliacaoFinal = (idAtividadeAtiva == 6);
      String tipoConclusao = ehAvaliacaoFinal ? "AVALIACAO_CONCLUIDA" : "ATIVIDADE_CONCLUIDA";

      if (!checarProgressoExiste(usuarioEmail, idAula, idAtividadeAtiva, tipoConclusao)) {

        String queryBuscaId = "SELECT PK_id_usuario FROM usuario WHERE usuario_email = ?";

        // Se for a avaliação final, passamos NULL no id_atividade para não quebrar a
        // Foreign Key
        String queryInsert = ehAvaliacaoFinal
            ? "INSERT INTO usuario_progresso (id_usuario, id_aula, id_atividade, tipo_conclusao) VALUES (?, ?, NULL, ?)"
            : "INSERT INTO usuario_progresso (id_usuario, id_aula, id_atividade, tipo_conclusao) VALUES (?, ?, ?, ?)";

        try (Connection conn = databaseConn.connect()) {
          int idUsuario = -1;

          // Passo 1: Busca o ID do usuário baseado no email
          try (PreparedStatement stmtBusca = conn.prepareStatement(queryBuscaId)) {
            stmtBusca.setString(1, usuarioEmail);
            try (ResultSet rs = stmtBusca.executeQuery()) {
              if (rs.next()) {
                idUsuario = rs.getInt("PK_id_usuario");
              }
            }
          }

          // Passo 2: Se achou o usuário, faz o INSERT
          if (idUsuario != -1) {
            try (PreparedStatement stmtInsert = conn.prepareStatement(queryInsert)) {
              stmtInsert.setInt(1, idUsuario);
              stmtInsert.setInt(2, idAula);

              if (ehAvaliacaoFinal) {
                // Se for avaliação, a query acima já tem o NULL fixo, então passamos o
                // tipoConclusao no parâmetro 3
                stmtInsert.setString(3, tipoConclusao);
              } else {
                // Se for lição normal, passamos o ID da atividade no 3 e o tipo no 4
                stmtInsert.setInt(3, idAtividadeAtiva);
                stmtInsert.setString(4, tipoConclusao);
              }

              stmtInsert.executeUpdate();
              System.out.println("Progresso (" + tipoConclusao + ") salvo com sucesso para o usuário ID: " + idUsuario);
            }
          } else {
            System.out.println("Aviso: Usuário com o e-mail " + usuarioEmail + " não foi encontrado no banco.");
          }
        }
      }
    } catch (SQLException e) {
      System.out.println("Erro ao salvar progresso de atividade: " + e.getMessage());
    }
  }

  private int obterIdModuloPorNome(String nomeModulo) {
    if ("Atenção".equalsIgnoreCase(nomeModulo))
      return 2;
    if ("Lógica".equalsIgnoreCase(nomeModulo))
      return 3;
    if ("Linguagem".equalsIgnoreCase(nomeModulo))
      return 4;
    return 1;
  }

  // Eu fiz esse retorno dinâmico para garantir que ao voltar do quiz ele caia na
  // vídeo aula certa
  private String obterTituloAulaPorModulo(String modulo) {
    if ("Atenção".equalsIgnoreCase(modulo)) {
      return "Atuação prática real com o uso de IA";
    } else if ("Lógica".equalsIgnoreCase(modulo)) {
      return "Uso de IA e tecnologia vestível";
    }
    return "Introdução ao uso de IA e suas aplicações";
  }

  private boolean checarProgressoExiste(String email, int idAula, int idAtividade, String tipoConclusao)
      throws SQLException {

    // Usamos JOIN para conectar a tabela de progresso à tabela de usuário pelo ID
    String query = """
            SELECT COUNT(*)
            FROM usuario_progresso up
            JOIN usuario u ON up.id_usuario = u.PK_id_usuario
            WHERE u.usuario_email = ?
              AND up.id_aula = ?
              AND up.id_atividade = ?
              AND up.tipo_conclusao = ?
        """;

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
      controller.setUsuarioEmail(usuarioEmail);
      // Retorna carregando dinamicamente o título do módulo correspondente
      controller.setTituloAula(obterTituloAulaPorModulo(moduloAtivo));

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
