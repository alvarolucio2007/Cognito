// Eu atualizei esse controlador usando as funções que o carlos de tinha criado no outro controle
// que eu achei interessante, que de tipo salvar as configurações de cada usuário
// e eu tinha esquecido desse detalhe

package database.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.conn.databaseConn;
import database.databaseModels.Configuracao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class ConfiguracoesController {

    @FXML private Button botaoVoltar;
    @FXML private CheckBox altoContrasteField;
    @FXML private CheckBox textoAmpliadoField;
    @FXML private TextField sensibilidadeToqueField;
    @FXML private CheckBox modoVozField;
    @FXML private Label labelAviso;
    @FXML private ComboBox<String> comboIdioma;
    @FXML private Slider sliderFonte;

    private String usuarioEmail;
    private long idUsuarioAtivo = -1;

    public void setUsuarioEmail(String email) {
        this.usuarioEmail = email;
        carregarConfiguracoesAnteriores(); // Carrega os dados reais usando a classe do Álvaro
    }

    @FXML
    public void initialize() {
        if (comboIdioma != null) {
            comboIdioma.getItems().addAll("Português - Brasil", "English", "Español");
        }

        // validação de Regex do carlos para aceitar apenas números decimais
        sensibilidadeToqueField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                sensibilidadeToqueField.setText(oldValue);
            }
        });
    }

    private void carregarConfiguracoesAnteriores() {
        try {
            idUsuarioAtivo = obterIdUsuario(usuarioEmail);
            if (idUsuarioAtivo == -1) return;

            // Eu consumo o método de busca da classe do Álvaro para ler as configurações do banco
            Configuracao configSalva = Configuracao.buscarPorUsuario(idUsuarioAtivo);
            
            if (configSalva != null) {
                // Preenche a interface com os dados reais salvos no Postgres
                altoContrasteField.setSelected(configSalva.isConfiguracaoContraste());
                textoAmpliadoField.setSelected(configSalva.isConfiguracaoTextoAmpliado());
                sensibilidadeToqueField.setText(String.valueOf(configSalva.getConfiguracaoSensibilidade()));
                modoVozField.setSelected(configSalva.isConfiguracaoModoVoz());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inicializar configurações do banco: " + e.getMessage());
        }
    }

    @FXML
    private void salvarConfiguracoes() {
        boolean altoContraste = altoContrasteField.isSelected();
        boolean textoAmpliado = textoAmpliadoField.isSelected();
        float sensibilidadeToque = verificarFloat(sensibilidadeToqueField.getText().trim());
        boolean modoVoz = modoVozField.isSelected();

        if (sensibilidadeToque == -1 || idUsuarioAtivo == -1) {
            return;
        }

        // eu instancio a classe de modelo do carlos com os novos dados da tela
        Configuracao novaConfig = new Configuracao(null, altoContraste, textoAmpliado, sensibilidadeToque, modoVoz, idUsuarioAtivo);
        
        // Eu chamo o método do carlos para persistir os dados no PostgreSQL
        boolean sucesso = novaConfig.criar(novaConfig);

        if (sucesso) {
            labelAviso.setText("Configurações salvas localmente!");
            labelAviso.setStyle("-fx-text-fill: #16A34A; -fx-font-weight: bold;");
        } else {
            labelAviso.setText("Erro ao salvar configurações no banco.");
            labelAviso.setStyle("-fx-text-fill: #DC2626;");
        }
    }

    private float verificarFloat(String string) {
        try {
            return Float.parseFloat(string);
        } catch (NumberFormatException e) {
            labelAviso.setText("Por favor, insira um número!");
            labelAviso.setStyle("-fx-text-fill: #DC2626;");
            return -1;
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

    @FXML
    private void fazerLogout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Erro ao deslogar: " + e.getMessage());
        }
    }

    @FXML
    private void voltar(ActionEvent event) {
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

    @FXML private void abrirSuporteVideo() {}
    @FXML private void abrirSuporteChat() {}
}