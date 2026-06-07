package database.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.scene.Node;

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

  private Timeline timeline;
  private int tempoSegundos = 0;
  private String usuarioEmail;

  public void setUsuarioEmail(String email) {
    this.usuarioEmail = email;
  }

  public void setTituloAula(String titulo) {
    if (tituloAulaLabel != null) {
      tituloAulaLabel.setText(titulo);
    }
    inicializarSimuladorPlayer();
  }

  private void inicializarSimuladorPlayer() {
    // Eu criei um simulador de tempo que roda a cada 1 segundo usando a classe de
    // animação padrão do JavaFX
    timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
      if (tempoSegundos < 120) {
        tempoSegundos++;
        sliderVideo.setValue(tempoSegundos);
        tempoLabel.setText(formatarTempo(tempoSegundos));
      } else {
        pararSimulacao();
      }
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);

    // Permite ao idoso arrastar a barra de progresso manualmente se desejar
    sliderVideo.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (sliderVideo.isValueChanging()) {
        tempoSegundos = newValue.intValue();
        tempoLabel.setText(formatarTempo(tempoSegundos));
      }
    });
  }

  private String formatarTempo(int segundosTotais) {
    int minutos = segundosTotais / 60;
    int segundos = segundosTotais % 60;
    return String.format("%02d:%02d", minutos, segundos);
  }

  @FXML
  private void alternarPlayPause() {
    if (timeline == null)
      return;

    if (Timeline.Status.RUNNING == timeline.getStatus()) {
      timeline.pause();
      botaoPlayPause.setText("▶ Play");
      statusVideoLabel.setText("Vídeo Pausado");
      statusVideoLabel.setVisible(true);
    } else {
      timeline.play();
      botaoPlayPause.setText("Paragraph Pause");
      botaoPlayPause.setText("⏸ Pause");
      statusVideoLabel.setVisible(false);
    }
  }

  @FXML
  private void voltar(ActionEvent event) {
    pararSimulacao();
    retornarParaPrincipal(event);
  }

  @FXML
  private void salvarEVoltar(ActionEvent event) {
    pararSimulacao();
    System.out.println("Progresso salvo para o usuário: " + usuarioEmail);
    retornarParaPrincipal(event);
  }

  private void pararSimulacao() {
    if (timeline != null) {
      timeline.stop();
    }
  }

  private void retornarParaPrincipal(ActionEvent event) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/fxml/Principal.fxml"));
      ((Node) event.getSource()).getScene().setRoot(root);
    } catch (Exception e) {
      System.out.println("Erro ao retornar para a tela principal: " + e.getMessage());
    }
  }

  @FXML
  private void abrirAtividade1(ActionEvent event) {
    pararSimulacao();
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AtividadeQuestao.fxml"));
      Parent root = loader.load();

      AtividadeQuestaoController controller = loader.getController();
      controller.setUsuarioEmail(usuarioEmail);

      ((Node) event.getSource()).getScene().setRoot(root);
    } catch (Exception e) {
      System.out.println("Erro ao carregar Atividade 1: " + e.getMessage());
    }
  }

  @FXML
  private void abrirAtividade2() {
    System.out.println("Carregando Atividade 2...");
  }

  @FXML
  private void abrirAvaliacao1() {
    System.out.println("Carregando Avaliação 1...");
  }

  @FXML
  private void abrirAvaliacao2() {
    System.out.println("Carregando Avaliação 2...");
  }

  @FXML
  private void emitirCertificado() {
    System.out.println("Certificado bloqueado.");
  }
}
