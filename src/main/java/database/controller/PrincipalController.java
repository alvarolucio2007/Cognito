package database.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.Node;

public class PrincipalController {

  @FXML
  private Button botaoPerfil;
  @FXML
  private VBox cardAula1;
  @FXML
  private VBox cardAula2;
  @FXML
  private VBox cardAula3;
  @FXML
  private VBox cardAula4;
  @FXML
  private VBox cardAula5;
  @FXML
  private VBox cardAula6;

  private String usuarioEmail;

  public void setUsuarioEmail(String email) {
    this.usuarioEmail = email;
  }

  @FXML
  public void initialize() {
  }

  // EU ALTEREI ESTA LINHA PARA PUBLIC:
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
      } else if ("cardAula4".equals(idCard)) {
        tituloSelecionado = "Como se sobressair sobre as gerações mais jovens";
      } else if ("cardAula5".equals(idCard)) {
        tituloSelecionado = "Aprendizado colaborativo com robótica";
      } else if ("cardAula6".equals(idCard)) {
        tituloSelecionado = "Visão geral das novas tecnologias do mercado de trabalho";
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
  private void irParaConfiguracoes() {
    System.out.println("Navegando para as configurações...");
  }
}
