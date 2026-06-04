package database.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class PrincipalController {

    @FXML private Button botaoPerfil;
    @FXML private ImageView imgAula1;
    @FXML private ImageView imgAula2;
    @FXML private ImageView imgAula3;
    @FXML private ImageView imgAula4;
    @FXML private ImageView imgAula5;
    @FXML private ImageView imgAula6;

    @FXML
    public void initialize() {
        // Espaço para carregar os PNGs exportados do Figma futuramente
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
    private void irParaConfiguracoes() {
        System.out.println("Navegando para as configurações...");
    }
}