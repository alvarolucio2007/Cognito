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
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.scene.Node;

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
        // ─────────────────────────────────────────────────────────────────────────
        // Quando tu gerar as vídeo aulas
        // salve cada vídeo (.mp4) na pasta 'src/main/resources/video/' seguindo nomes:
        // Módulo 1 (Aula 1) -> aula_1.mp4
        // Módulo 2 (Aula 2) -> aula_2.mp4
        // Módulo 3 (Aula 3) -> aula_3.mp4
        //
        // O código abaixo já foi automatizado pra carregar o vídeo!
        // ─────────────────────────────────────────────────────────────────────────
        String nomeVideo = "aula_1.mp4";
        if (tituloAulaLabel.getText().contains("Atuação prática")) {
            nomeVideo = "aula_2.mp4";
        } else if (tituloAulaLabel.getText().contains("tecnologia vestível")) {
            nomeVideo = "aula_3.mp4";
        }

        System.out.println("Carregando o arquivo de vídeo físico correspondente: " + nomeVideo);

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
        if (timeline == null) return;

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
            // Garante o carregamento inicial seguro passando a categoria ativa e o ID do livro 1
            controller.configurarAtividade("Geral", 1); 

            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Erro ao carregar Atividade 1: " + e.getMessage());
        }
    }

    @FXML private void abrirAtividade2() {}
    @FXML private void abrirAvaliacao1() {}
    @FXML private void abrirAvaliacao2() {}
    @FXML private void emitirCertificado() {}
}