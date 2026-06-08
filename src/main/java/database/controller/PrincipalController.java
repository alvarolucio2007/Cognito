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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class PrincipalController {

  @FXML
  private Button botaoPerfil;
  @FXML
  private TextField campoPesquisa;

  // Controles de Abas FXML
  @FXML
  private ScrollPane scrollVideoAulas;
  @FXML
  private ScrollPane scrollAtividades;
  @FXML
  private Button abaVideoBtn;
  @FXML
  private Button abaAtividadesBtn;
  @FXML
  private ComboBox<String> comboModulos;
  @FXML
  private Label labelAvisoTrilha;

  // Botões dos Livros e Estrela para controle de liberação
  @FXML
  private Button btnLivro1;
  @FXML
  private Button btnLivro2;
  @FXML
  private Button btnLivro3;
  @FXML
  private Button btnLivro4;
  @FXML
  private Button btnLivro5;
  @FXML
  private Button btnEstrelaAvaliacao;

  // Cards de Vídeo Aulas
  @FXML
  private VBox cardAula1;
  @FXML
  private VBox cardAula2;
  @FXML
  private VBox cardAula3;

  // Rótulos de Estatísticas
  @FXML
  private Label labelAulas1;
  @FXML
  private Label labelAvaliacoes1;
  @FXML
  private Label labelAulas2;
  @FXML
  private Label labelAvaliacoes2;
  @FXML
  private Label labelAulas3;
  @FXML
  private Label labelAvaliacoes3;

  private String usuarioEmail;
  private String moduloSelecionado = "Introdução ao uso de IA e suas aplicações";

  public void setUsuarioEmail(String email) {
    this.usuarioEmail = email;
    carregarEstatisticasUsuario();
    atualizarVisualTrilha();
  }

  @FXML
  public void initialize() {
    // Inicializa o ComboBox de Atividades com os nomes exatos das nossas trilhas
    if (comboModulos != null) {
      comboModulos.getItems().addAll(
          "Introdução ao uso de IA e suas aplicações",
          "Atuação prática real com o uso de IA",
          "Uso de IA e tecnologia vestível");
      comboModulos.getSelectionModel().select("Introdução ao uso de IA e suas aplicações");
    }

    // Eu configurei a barra de pesquisa para filtrar os cards dinamicamente em
    // tempo real
    if (campoPesquisa != null) {
      campoPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
        filtrarCardsAulas(newValue);
      });
    }
  }

  private void filtrarCardsAulas(String busca) {
    if (busca == null || busca.trim().isEmpty()) {
      mostrarCard(cardAula1, true);
      mostrarCard(cardAula2, true);
      mostrarCard(cardAula3, true);
      return;
    }

    String lowerBusca = busca.toLowerCase().trim();
    mostrarCard(cardAula1, "introdução ao uso de ia e suas aplicações".contains(lowerBusca));
    mostrarCard(cardAula2, "atuação prática real com o uso de ia".contains(lowerBusca));
    mostrarCard(cardAula3, "uso de ia e tecnologia vestível".contains(lowerBusca));
  }

  private void mostrarCard(Node card, boolean visivel) {
    if (card != null) {
      card.setVisible(visivel);
      card.setManaged(visivel);
    }
  }

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

      int atividadesConcluidas = obterContagemProgresso(usuarioEmail, idAula, "ATIVIDADE_CONCLUIDA");
      int avaliacoesConcluidas = obterContagemProgresso(usuarioEmail, idAula, "AVALIACAO_CONCLUIDA");

      labelAulas1.setText("Atividades feitas: " + atividadesConcluidas + "/5");
      labelAvaliacoes1.setText("Avaliações feitas: " + avaliacoesConcluidas + "/2");

    } catch (SQLException e) {
      System.out.println("Erro ao carregar estatísticas exclusivas: " + e.getMessage());
    }
  }

  private int obterIdModuloPorNome(String nomeModulo) {
    if ("Atuação prática real com o uso de IA".equalsIgnoreCase(nomeModulo))
      return 2;
    if ("Uso de IA e tecnologia vestível".equalsIgnoreCase(nomeModulo))
      return 3;
    return 1; // "Introdução ao uso de IA e suas aplicações"
  }

  private void atualizarVisualTrilha() {
    try {
      int idAula = obterIdModuloPorNome(moduloSelecionado);

      boolean livro1Concluido = checarProgressoAtividade(usuarioEmail, idAula, 1);
      boolean livro2Concluido = checarProgressoAtividade(usuarioEmail, idAula, 2);
      boolean livro3Concluido = checarProgressoAtividade(usuarioEmail, idAula, 3);
      boolean livro4Concluido = checarProgressoAtividade(usuarioEmail, idAula, 4);
      boolean livro5Concluido = checarProgressoAtividade(usuarioEmail, idAula, 5);

      desbloquearBotaoTrilha(btnLivro1);
      atualizarEstadoBotao(btnLivro2, livro1Concluido);
      atualizarEstadoBotao(btnLivro3, livro2Concluido);
      atualizarEstadoBotao(btnLivro4, livro3Concluido);
      atualizarEstadoBotao(btnLivro5, livro4Concluido);
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
    botao.setOpacity(0.4);
  }

  private void desbloquearBotaoTrilha(Button botao) {
    botao.setOpacity(1.0);
  }

  // Cliques nos livros (Mapeados de baixo para cima perfeitamente de acordo com o
  // Y físico do figma)
  @FXML
  public void abrirLivro1(ActionEvent event) {
    iniciarAtividadeSeLiberada(event, 1, true);
  }

  @FXML
  public void abrirLivro2(ActionEvent event) {
    iniciarAtividadeSeLiberada(event, 2, checarLivroAnteriorLiberado(1));
  }

  @FXML
  public void abrirLivro3(ActionEvent event) {
    iniciarAtividadeSeLiberada(event, 3, checarLivroAnteriorLiberado(2));
  }

  @FXML
  public void abrirLivro4(ActionEvent event) {
    iniciarAtividadeSeLiberada(event, 4, checarLivroAnteriorLiberado(3));
  }

  @FXML
  public void abrirLivro5(ActionEvent event) {
    iniciarAtividadeSeLiberada(event, 5, checarLivroAnteriorLiberado(4));
  }

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

    labelAvisoTrilha.setText("");

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AtividadeQuestao.fxml"));
      Parent root = loader.load();

      AtividadeQuestaoController controller = loader.getController();
      controller.setUsuarioEmail(usuarioEmail);
      controller.configurarAtividade(moduloSelecionado, idAtividade);

      ((Node) event.getSource()).getScene().setRoot(root);
    } catch (Exception e) {
      System.out.println("Erro ao iniciar atividade: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private int obterContagemProgresso(String email, int idAula, String tipoConclusao) throws SQLException {
    // Aplicando o JOIN para buscar o email na tabela correta (usuario)
    String query = """
            SELECT COUNT(*)
            FROM usuario_progresso up
            JOIN usuario u ON up.id_usuario = u.PK_id_usuario
            WHERE u.usuario_email = ?
              AND up.id_aula = ?
              AND up.tipo_conclusao = ?
        """;

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
    String query = """
            SELECT COUNT(*)
            FROM usuario_progresso up
            JOIN usuario u ON up.id_usuario = u.pk_id_usuario
            WHERE u.usuario_email = ?
              AND up.id_aula = ?
              AND up.id_atividade = ?
              AND up.tipo_conclusao = 'ATIVIDADE_CONCLUIDA'
        """;
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

    abaVideoBtn.setStyle(
        "-fx-background-color: #a0a6ff; -fx-text-fill: #000dc9; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10;");
    abaAtividadesBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #1E293B; -fx-font-size: 16px;");
  }

  @FXML
  public void mostrarAbaAtividades() {
    scrollVideoAulas.setVisible(false);
    scrollVideoAulas.setManaged(false);
    scrollAtividades.setVisible(true);
    scrollAtividades.setManaged(true);

    abaVideoBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #1E293B; -fx-font-size: 16px;");
    abaAtividadesBtn.setStyle(
        "-fx-background-color: #a0a6ff; -fx-text-fill: #000dc9; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10;");
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
