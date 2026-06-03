package database.controller;

import database.databaseModels.TesteNivelamento;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;

import database.conn.databaseConn;

public class TesteNivelamentoController {

    @FXML private TextField diaField;
    @FXML private TextField mesField;
    @FXML private TextField anoField;
    @FXML private DatePicker tempoCelularField;
    @FXML private ToggleButton btnSimVoz;
    @FXML private ToggleButton btnNaoVoz;
    @FXML private Label labelAviso;

    // ID do usuário recém-cadastrado — setado pelo UsuarioController após cadastro
    private Long idUsuario;

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    // ── Seleção dos botões Sim/Não ───────────────────────────────────
    @FXML private void selecionarSimVoz() {
        btnSimVoz.setStyle(
            "-fx-background-color: #1A237E; -fx-text-fill: white;" +
            "-fx-font-size: 15px; -fx-background-radius: 12;" +
            "-fx-pref-height: 48px; -fx-cursor: hand;"
        );
        btnNaoVoz.setStyle(
            "-fx-background-color: white; -fx-text-fill: #3F51B5;" +
            "-fx-font-size: 15px; -fx-background-radius: 12;" +
            "-fx-pref-height: 48px; -fx-border-color: #3F51B5;" +
            "-fx-border-radius: 12; -fx-cursor: hand;"
        );
    }

    @FXML private void selecionarNaoVoz() {
        btnNaoVoz.setStyle(
            "-fx-background-color: #1A237E; -fx-text-fill: white;" +
            "-fx-font-size: 15px; -fx-background-radius: 12;" +
            "-fx-pref-height: 48px; -fx-cursor: hand;"
        );
        btnSimVoz.setStyle(
            "-fx-background-color: white; -fx-text-fill: #3F51B5;" +
            "-fx-font-size: 15px; -fx-background-radius: 12;" +
            "-fx-pref-height: 48px; -fx-border-color: #3F51B5;" +
            "-fx-border-radius: 12; -fx-cursor: hand;"
        );
    }

    // ── Lógica principal ─────────────────────────────────────────────
    @FXML private void continuar() {
        String dia  = diaField.getText().trim();
        String mes  = mesField.getText().trim();
        String ano  = anoField.getText().trim();
        LocalDate tempoCelular = tempoCelularField.getValue();
        boolean usouVoz = btnSimVoz.isSelected();
        boolean naoUsouVoz = btnNaoVoz.isSelected();

        // Validação
        if (dia.isEmpty() || mes.isEmpty() || ano.isEmpty()) {
            labelAviso.setText("Por favor, preencha sua data de nascimento.");
            return;
        }
        if (tempoCelular == null) {
            labelAviso.setText("Por favor, informe desde quando tem um celular.");
            return;
        }
        if (!usouVoz && !naoUsouVoz) {
            labelAviso.setText("Por favor, responda se já usou assistente de voz.");
            return;
        }

        // Calcular nível
        String nivel = calcularNivel(ano, tempoCelular, usouVoz);

        // Salvar no banco
        boolean salvo = salvarTeste(nivel);

        if (salvo) {
            irParaPrincipal();
        } else {
            labelAviso.setText("Erro ao salvar o teste. Tente novamente.");
        }
    }

    // ── Cálculo do nível ─────────────────────────────────────────────
    private String calcularNivel(String anoNasc, LocalDate tempoCelular, boolean usouVoz) {
        int pontos = 0;

        // Critério 1: idade (acima de 70 = menos experiente)
        try {
            int idade = LocalDate.now().getYear() - Integer.parseInt(anoNasc);
            if (idade < 60) pontos += 2;
            else if (idade < 70) pontos += 1;
        } catch (NumberFormatException ignored) {}

        // Critério 2: tempo com celular (antes de 2015 = mais experiente)
        if (tempoCelular.getYear() <= 2010) pontos += 2;
        else if (tempoCelular.getYear() <= 2015) pontos += 1;

        // Critério 3: já usou assistente de voz
        if (usouVoz) pontos += 2;

        if (pontos >= 4) return "Avançado";
        if (pontos >= 2) return "Intermediário";
        return "Iniciante";
    }

    // ── Salvar resultado no banco ────────────────────────────────────
    private boolean salvarTeste(String nivel) {
        String query = "INSERT INTO teste_nivelamento " +
                       "(teste_nivelamento_nivel_detectado, teste_nivelamento_data_realizacao, FK_id_usuario) " +
                       "VALUES (?, ?, ?)";
        try (Connection conn = databaseConn.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nivel);
            stmt.setTimestamp(2, Timestamp.valueOf(java.time.LocalDateTime.now()));
            stmt.setLong(3, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Erro ao salvar teste: " + e.getMessage());
            return false;
        }
    }

    // ── Navegar para tela principal ──────────────────────────────────
    private void irParaPrincipal() {
        try {
            Parent root = FXMLLoader.load(
                getClass().getResource("/fxml/Principal.fxml")
            );
            labelAviso.getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Erro ao carregar tela principal: " + e.getMessage());
        }
    }
}