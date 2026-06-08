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
import javafx.scene.control.Slider;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class VideoAulaController {

  @FXML
  private Button botaoVoltar;
  @FXML
  private Label tituloAulaLabel;
  @FXML
  private ImageView videoPlaceholder;
  @FXML
  private Label statusVideoLabel;
  @FXML
  private Button botaoPlayPause;
  @FXML
  private Slider sliderVideo;
  @FXML
  private Label tempoLabel;
  @FXML
  private Button botaoCertificado;
  @FXML
  private Label labelStatusModulo;
  @FXML
  private MediaView videoPlayer;

  private Timeline timeline;
  private int tempoSegundos = 0;
  private String usuarioEmail;
  private MediaPlayer mediaPlayer;
  private String tituloAula;

  @FXML
  public void initialize() {
    if (tituloAula != null) {
      inicializarPlayerReal();
    }
  }

  public void setUsuarioEmail(String email) {
    this.usuarioEmail = email;
  }

  public void setTituloAula(String titulo) {
    this.tituloAula = titulo;
    if (tituloAulaLabel != null) {
      tituloAulaLabel.setText(titulo);
    }
    if (videoPlayer != null) {
      inicializarPlayerReal();
    }
  }

  // Variável de controle para evitar loops infinitos de listeners no Slider
  private boolean mudandoSliderManualmente = false;

  private void limparPlayers() {
    // Para e limpa o player real se ele existir
    if (mediaPlayer != null) {
      mediaPlayer.stop();
      mediaPlayer.dispose();
      mediaPlayer = null;
    }
    // Para e limpa o simulador se ele existir
    if (timeline != null) {
      timeline.stop();
      timeline = null;
    }

    // Reseta o estado do botão e labels
    botaoPlayPause.setText("▶ Play");
    tempoSegundos = 0;
    sliderVideo.setValue(0);
  }

  private void inicializarPlayerReal() {
    // 1. Limpa qualquer rastro de execuções anteriores antes de começar
    limparPlayers();

    String nomeVideo = "aula_1.mp4";
    String nomeImagem = "image-CardLesson01.png";

    if (tituloAula != null) {
      if (tituloAula.contains("Atuação prática")) {
        nomeVideo = "aula_2.mp4";
        nomeImagem = "image-CardLesson02.png";
      } else if (tituloAula.contains("tecnologia vestível")) {
        nomeVideo = "aula_3.mp4";
        nomeImagem = "image-CardLesson03.png";
      }
    }

    try {
      var urlImagem = getClass().getResource("/images/" + nomeImagem);
      if (urlImagem != null) {
        videoPlaceholder.setImage(new Image(urlImagem.toExternalForm()));
      }
    } catch (Exception imgEx) {
      System.err.println("Erro ao carregar imagem de capa: " + imgEx.getMessage());
    }

    try {
      var URLVideo = getClass().getResource("/video/" + nomeVideo);
      if (URLVideo == null) {
        System.out.println("ERRO: " + nomeVideo + " não encontrado. Revertendo para o simulador...");
        inicializarSimuladorPlayer();
        return;
      }

      Media media = new Media(URLVideo.toExternalForm());
      mediaPlayer = new MediaPlayer(media);
      videoPlayer.setMediaPlayer(mediaPlayer);

      // ATUALIZAÇÃO DO SLIDER (Apenas quando o vídeo roda e o usuário NÃO está
      // arrastando)
      mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
        if (!mudandoSliderManualmente && !sliderVideo.isValueChanging()) {
          sliderVideo.setValue(newTime.toSeconds());
          tempoLabel.setText(formatarTempo((int) newTime.toSeconds()));
        }
      });

      mediaPlayer.setOnReady(() -> {
        sliderVideo.setMax(mediaPlayer.getTotalDuration().toSeconds());
      });

      // Garante que se o vídeo acabar, o botão resete visualmente
      mediaPlayer.setOnEndOfMedia(() -> {
        botaoPlayPause.setText("▶ Play");
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.pause();
      });

      // CONTROLE DO USUÁRIO ARRSTANDO O SLIDER (Configurado de forma segura)
      sliderVideo.setOnMousePressed(e -> mudandoSliderManualmente = true);
      sliderVideo.setOnMouseReleased(e -> {
        if (mediaPlayer != null) {
          mediaPlayer.seek(Duration.seconds(sliderVideo.getValue()));
        }
        mudandoSliderManualmente = false;
      });

      videoPlayer.setVisible(true);
      videoPlaceholder.setVisible(false);
      statusVideoLabel.setVisible(false);
      System.out.println("Vídeo carregado com sucesso no player real: " + nomeVideo);

    } catch (Exception e) {
      System.err.println("Aviso: Falha ao carregar player nativo (Codecs). Revertendo para o simulador...");
      e.printStackTrace();

      statusVideoLabel.setText("Vídeo Simulável (Sem Codecs de Mídia)");
      statusVideoLabel.setVisible(true);

      try {
        videoPlaceholder.setImage(new Image(getClass().getResourceAsStream("/images/" + nomeImagem)));
      } catch (Exception ex) {
        /* fallback */ }

      videoPlaceholder.setVisible(true);
      videoPlayer.setVisible(false);

      inicializarSimuladorPlayer();
    }
  }

  private void inicializarSimuladorPlayer() {
    sliderVideo.setMax(120); // Define um tempo padrão máximo para o simulador (Ex: 2 minutos)

    timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
      if (tempoSegundos < 120) {
        tempoSegundos++;
        if (!mudandoSliderManualmente) {
          sliderVideo.setValue(tempoSegundos);
          tempoLabel.setText(formatarTempo(tempoSegundos));
        }
      } else {
        if (timeline != null)
          timeline.pause();
        botaoPlayPause.setText("▶ Play");
      }
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);

    // Controle do slider no simulador
    sliderVideo.setOnMousePressed(e -> mudandoSliderManualmente = true);
    sliderVideo.setOnMouseReleased(e -> {
      tempoSegundos = (int) sliderVideo.getValue();
      tempoLabel.setText(formatarTempo(tempoSegundos));
      mudandoSliderManualmente = false;
    });
  }

  private String formatarTempo(int segundosTotais) {
    int minutos = segundosTotais / 60;
    int segundos = segundosTotais % 60;
    return String.format("%02d:%02d", minutos, segundos);
  }

  @FXML
  public void alternarPlayPause() {
    // Prioridade para o Player Real
    if (mediaPlayer != null) {
      try {
        MediaPlayer.Status status = mediaPlayer.getStatus();

        // Só interage com o player se ele já tiver saído do estado UNKNOWN
        if (status == MediaPlayer.Status.PLAYING) {
          mediaPlayer.pause();
          botaoPlayPause.setText("▶ Play");
          statusVideoLabel.setText("Vídeo Pausado");
          statusVideoLabel.setVisible(true);
        } else if (status == MediaPlayer.Status.PAUSED ||
            status == MediaPlayer.Status.READY ||
            status == MediaPlayer.Status.STOPPED) {
          mediaPlayer.play();
          botaoPlayPause.setText("⏸ Pause");
          statusVideoLabel.setVisible(false);
        }
      } catch (NullPointerException e) {
        // Se o JavaFX estourar o NPE interno (jfxPlayer nulo), capturamos aqui
        System.err.println("Erro interno do JavaFX Media: Codecs ausentes ou falha de renderização.");
        reverterParaSimuladorImediato();
      }
    }
    // Fallback para o Simulador
    else if (timeline != null) {
      if (Timeline.Status.RUNNING == timeline.getStatus()) {
        timeline.pause();
        botaoPlayPause.setText("▶ Play");
        statusVideoLabel.setText("Vídeo Pausado");
        statusVideoLabel.setVisible(true);
      } else {
        timeline.play();
        botaoPlayPause.setText("⏸ Pause");
        statusVideoLabel.setVisible(false);
      }
    }
  }

  // Método auxiliar para fazer a transição de emergência se o player real
  // engasgar no meio do caminho
  private void reverterParaSimuladorImediato() {
    System.out.println("Forçando reversão para o simulador...");
    statusVideoLabel.setText("Vídeo Simulável (Erro de Inicialização Nativa)");
    statusVideoLabel.setVisible(true);

    if (mediaPlayer != null) {
      try {
        mediaPlayer.dispose();
      } catch (Exception ex) {
      }
      mediaPlayer = null;
    }

    videoPlayer.setVisible(false);
    videoPlaceholder.setVisible(true);

    inicializarSimuladorPlayer();
    // Inicia a linha do tempo do simulador automaticamente já que o usuário clicou
    // no Play
    if (timeline != null) {
      timeline.play();
      botaoPlayPause.setText("⏸ Pause");
    }
  }

  @FXML
  public void voltar(ActionEvent event) {
    pararSimulacao();
    retornarParaPrincipal(event);
  }

  @FXML
  public void salvarEVoltar(ActionEvent event) {
    pararSimulacao();
    salvarProgressoVideo();
    System.out.println("Progresso salvo para o usuário: " + usuarioEmail);
    retornarParaPrincipal(event);
  }

  private void salvarProgressoVideo() {
    if (usuarioEmail == null)
      return;
    try {
      int idAula = 1;
      if (tituloAulaLabel.getText().contains("Atuação prática")) {
        idAula = 2;
      } else if (tituloAulaLabel.getText().contains("tecnologia vestível")) {
        idAula = 3;
      }

      if (!checarProgressoExiste(usuarioEmail, idAula, "VIDEO_ASSISTIDO")) {
        String query = "INSERT INTO usuario_progresso (usuario_email, id_aula, tipo_conclusao) VALUES (?, ?, ?, 'VIDEO_ASSISTIDO')";
        try (Connection conn = databaseConn.connect();
            PreparedStatement stmt = conn.prepareStatement(query)) {
          stmt.setString(1, usuarioEmail);
          stmt.setInt(2, idAula);
          stmt.executeUpdate();
        }
      }
    } catch (SQLException e) {
      System.out.println("Erro ao salvar progresso de vídeo: " + e.getMessage());
    }
  }

  private boolean checarProgressoExiste(String email, int idAula, String tipoConclusao) throws SQLException {
    String query = "SELECT COUNT(*) FROM usuario_progresso WHERE usuario_email = ? AND id_aula = ? AND tipo_conclusao = ?";
    try (Connection conn = databaseConn.connect();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, email);
      stmt.setInt(2, idAula);
      stmt.setString(3, tipoConclusao);
      try (ResultSet rs = stmt.executeQuery()) {
        return rs.next() && rs.getInt(1) > 0;
      }
    }
  }

  private void pararSimulacao() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
      mediaPlayer.dispose();
    }
    if (timeline != null) {
      timeline.stop();
    }
  }

  private void retornarParaPrincipal(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Principal.fxml"));
      Parent root = loader.load();

      PrincipalController controller = loader.getController();
      controller.setUsuarioEmail(usuarioEmail);

      ((Node) event.getSource()).getScene().setRoot(root);
    } catch (Exception e) {
      System.out.println("Erro ao retornar para a tela principal: " + e.getMessage());
    }
  }

  @FXML
  public void abrirAtividade1(ActionEvent event) {
    abrirQuizDinamico(event, 1);
  }

  @FXML
  public void abrirAtividade2(ActionEvent event) {
    abrirQuizDinamico(event, 2);
  }

  @FXML
  public void abrirAtividade3(ActionEvent event) {
    abrirQuizDinamico(event, 3);
  }

  @FXML
  public void abrirAtividade4(ActionEvent event) {
    abrirQuizDinamico(event, 4);
  }

  @FXML
  public void abrirAtividade5(ActionEvent event) {
    abrirQuizDinamico(event, 5);
  }

  @FXML
  public void abrirAvaliacao(ActionEvent event) {
    abrirQuizDinamico(event, 6);
  }

  private void abrirQuizDinamico(ActionEvent event, int idAtividade) {
    pararSimulacao();

    int idAula = 1;
    String modulo = "Geral";
    if (tituloAulaLabel.getText().contains("Atuação prática")) {
      modulo = "Atenção";
      idAula = 2;
    } else if (tituloAulaLabel.getText().contains("tecnologia vestível")) {
      modulo = "Lógica";
      idAula = 3;
    }

    // Lógica de Bloqueio Progressivo no Card de Vídeo
    if (idAtividade > 1) {
      try {
        boolean anteriorConcluido = checarProgressoAtividadeExiste(usuarioEmail, idAula, idAtividade - 1);
        if (!anteriorConcluido) {
          // Eu criei um aviso de diálogo nativo elegante para orientar o idoso de forma
          // clara
          Alert alerta = new Alert(Alert.AlertType.WARNING);
          alerta.setTitle("Atividade Bloqueada");
          alerta.setHeaderText(null);
          alerta
              .setContentText("🔒 Conclua a atividade " + (idAtividade - 1) + " primeiro para liberar este conteúdo!");
          alerta.showAndWait();
          return;
        }
      } catch (SQLException e) {
        System.out.println("Erro ao verificar progresso: " + e.getMessage());
      }
    }

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AtividadeQuestao.fxml"));
      Parent root = loader.load();

      AtividadeQuestaoController controller = loader.getController();
      controller.setUsuarioEmail(usuarioEmail);
      controller.configurarAtividade(modulo, idAtividade);

      ((Node) event.getSource()).getScene().setRoot(root);
    } catch (Exception e) {
      System.out.println("Erro ao carregar Atividade dinamicamente: " + e.getMessage());
    }
  }

  private boolean checarProgressoAtividadeExiste(String email, int idAula, int idAtividade) throws SQLException {
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
  public void abrirTelaCheia() {
    if (mediaPlayer == null)
      return;

    mediaPlayer.pause();
    botaoPlayPause.setText("▶ Play");

    MediaView mediaViewFS = new MediaView(mediaPlayer);
    mediaViewFS.setPreserveRatio(true);

    StackPane root = new StackPane(mediaViewFS);
    root.setStyle("-fx-background-color: black;");

    mediaViewFS.fitWidthProperty().bind(root.widthProperty());
    mediaViewFS.fitHeightProperty().bind(root.heightProperty());

    Scene cenaFS = new Scene(root);

    Stage stageFS = new Stage();
    stageFS.setScene(cenaFS);
    stageFS.setFullScreen(true);
    stageFS.setFullScreenExitHint("Pressione ESC para sair da tela cheia");

    stageFS.setOnHidden(e -> {
      MediaView novoView = new MediaView(mediaPlayer);
      novoView.setFitWidth(360);
      novoView.setFitHeight(220);
      novoView.setPreserveRatio(false);
      videoPlayer.setMediaPlayer(mediaPlayer);
    });

    stageFS.show();
    mediaPlayer.play();
    botaoPlayPause.setText("⏸ Pause");
  }

  @FXML
  public void abrirAtividade2() {
  }

  @FXML
  public void abrirAvaliacao1() {
  }

  @FXML
  public void abrirAvaliacao2() {
  }

  @FXML
  public void emitirCertificado() {
  }
}
