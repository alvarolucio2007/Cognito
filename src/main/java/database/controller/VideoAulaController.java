package database.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.conn.databaseConn;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VideoAulaController {

    @FXML private Button botaoVoltar;
    @FXML private Label tituloAulaLabel;
    @FXML private ImageView videoPlaceholder;
    @FXML private Label statusVideoLabel;
    @FXML private Button botaoPlayPause;
    @FXML private Slider sliderVideo;
    @FXML private Label tempoLabel;
    @FXML private Button botaoCertificado;
    @FXML private Label labelStatusModulo;
    @FXML private MediaView videoPlayer;

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

    private void inicializarPlayerReal() {
        String nomeVideo = "aula_1.mp4";
        if (tituloAula.contains("Atuação prática")) {
            nomeVideo = "aula_2.mp4";
        } else if (tituloAula.contains("tecnologia vestível")) {
            nomeVideo = "aula_3.mp4";
        }

        try {
            // Eu restaurei o caminho para a pasta "video" no singular
            var URLVideo = getClass().getResource("/video/" + nomeVideo);
            if (URLVideo == null) {
                System.out.println("ERRO: " + nomeVideo + " não encontrado em resources/video/");
                inicializarSimuladorPlayer(); // Reverte para o simulador se o arquivo faltar
                return;
            }

            Media media = new Media(URLVideo.toExternalForm());
            mediaPlayer = new MediaPlayer(media); 

            videoPlayer.setMediaPlayer(mediaPlayer);

            mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                if (!sliderVideo.isValueChanging()) {
                    sliderVideo.setValue(newTime.toSeconds());
                    tempoLabel.setText(formatarTempo((int) newTime.toSeconds()));
                }
            });

            mediaPlayer.setOnReady(() -> {
                sliderVideo.setMax(mediaPlayer.getTotalDuration().toSeconds());
            });

            sliderVideo.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (sliderVideo.isValueChanging()) {
                    mediaPlayer.seek(Duration.seconds(newVal.doubleValue()));
                }
            });

            statusVideoLabel.setVisible(false);
            System.out.println("Vídeo carregado com sucesso no player real: " + nomeVideo);

        } catch (Exception e) {
            // SE HOUVER FALHA DE CODECS NO LINUX (MediaException), REVERTE AUTOMATICAMENTE PARA O SIMULADOR! (tive esse problema)
            System.out.println("Aviso: Falha ao carregar player nativo (falta de codecs GStreamer no Linux). Revertendo para o simulador...");
            statusVideoLabel.setText("Vídeo Simulável (Sem Codecs de Mídia)");
            statusVideoLabel.setVisible(true);
            inicializarSimuladorPlayer();
        }
    }

    private void inicializarSimuladorPlayer() {
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
        // se o player real foi inicializado com sucesso, controla ele
        if (mediaPlayer != null) {
            if (MediaPlayer.Status.PLAYING == mediaPlayer.getStatus()) {
                mediaPlayer.pause();
                botaoPlayPause.setText("▶ Play");
                statusVideoLabel.setText("Vídeo Pausado");
                statusVideoLabel.setVisible(true);
            } else {
                mediaPlayer.play();
                botaoPlayPause.setText("⏸ Pause");
                statusVideoLabel.setVisible(false);
            }
        } 
        // Ss o player real falhou e o simulador está ativo, controla a Timeline
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

    @FXML
    private void voltar(ActionEvent event) {
        pararSimulacao();
        retornarParaPrincipal(event);
    }

    @FXML
    private void salvarEVoltar(ActionEvent event) {
        pararSimulacao();
        salvarProgressoVideo();
        System.out.println("Progresso salvo para o usuário: " + usuarioEmail);
        retornarParaPrincipal(event);
    }

    private void salvarProgressoVideo() {
        if (usuarioEmail == null) return;
        try {
            int idAula = 1;
            if (tituloAulaLabel.getText().contains("Atuação prática")) {
                idAula = 2;
            } else if (tituloAulaLabel.getText().contains("tecnologia vestível")) {
                idAula = 3;
            }

            if (!checarProgressoExiste(usuarioEmail, idAula, "VIDEO_ASSISTIDO")) {
                String query = "INSERT INTO usuario_progresso (usuario_email, id_aula, tipo_conclusao) VALUES (?, ?, 'VIDEO_ASSISTIDO')";
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
    private void abrirAtividade1(ActionEvent event) {
        pararSimulacao();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AtividadeQuestao.fxml"));
            Parent root = loader.load();

            AtividadeQuestaoController controller = loader.getController();
            controller.setUsuarioEmail(usuarioEmail);
            controller.configurarAtividade("Geral", 1);

            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Erro ao carregar Atividade 1: " + e.getMessage());
        }
    }

    @FXML
    private void abrirTelaCheia() {
        if (mediaPlayer == null) return;

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

    @FXML private void abrirAtividade2() {}
    @FXML private void abrirAvaliacao1() {}
    @FXML private void abrirAvaliacao2() {}
    @FXML private void emitirCertificado() {}
}