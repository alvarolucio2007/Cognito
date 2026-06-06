// package database.controller;

// import javafx.fxml.FXML;
// import javafx.scene.control.CheckBox;
// import javafx.scene.control.Label;
// import javafx.scene.control.TextField;

// public class ConfiguracoesController {
//   @FXML
//   private CheckBox altoContrasteField;
//   @FXML
//   private CheckBox textoAmpliadoField;
//   @FXML
//   private TextField sensibilidadeToqueField; // Aliás, como que faríamos esse sensibilidadeToque? Literamente n tem como
//                                              // controlar a sensibiliade no mouse kkkkkkkkkkkkkkkkkkkk
//   @FXML
//   private CheckBox modoVozField;
//   @FXML
//   private Label labelAviso;

//   public void initialize() { // essa bomba (provavelmente) deve prevenir que o usuário coloque texto aonde só
//                              // pode ter
//                              // número. Eu não confio. Preciso testar.

                             
//     sensibilidadeToqueField.textProperty().addListener((observable, oldValue, newValue) -> {
//       if (!newValue.matches("\\d*(\\.\\d*)?")) {
//         sensibilidadeToqueField.setText(oldValue);
//       }
//     });
//   }

//   @FXML
//   private void salvarConfiguracoes() {
//     boolean altoContraste = altoContrasteField.isSelected();
//     boolean textoAmpliado = textoAmpliadoField.isSelected();
//     float sensibilidadeToque = verificarFloat(sensibilidadeToqueField.getText().trim());
//     boolean modoVoz = modoVozField.isSelected();
//   } // n vai ter muito uso isso...

//   private float verificarFloat(String string) {
//     try {
//       return Float.parseFloat(string);
//     } catch (NumberFormatException e) {
//       labelAviso.setText("Por favor, insira um número!");
//       return -1;
//     }
//   }

// }



// essa bomba vai funcionar só na UI kkkkkk
// mas eu pensei na ideia de invés de controlar a sensibilidade(impossível), fizesse um filtro
// dw toque acidental, um filtro que vesse se o idoso clica duas vezes num espaço de tempo muito curto
// ou clique muito rápido, fazendo tipo um delay tlgd?

// Modificações \/

package database.controller;

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

    public void setUsuarioEmail(String email) {
        this.usuarioEmail = email;
    }

    public void initialize() {
        if (comboIdioma != null) {
            comboIdioma.getItems().addAll("Português - Brasil", "English", "Español");
        }

        // Validação de Regex do Álvaro para aceitar apenas floats/decimais:
        sensibilidadeToqueField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                sensibilidadeToqueField.setText(oldValue);
            }
        });
    }

    @FXML
    private void salvarConfiguracoes() {
        boolean altoContraste = altoContrasteField.isSelected();
        boolean textoAmpliado = textoAmpliadoField.isSelected();
        float sensibilidadeToque = verificarFloat(sensibilidadeToqueField.getText().trim());
        boolean modoVoz = modoVozField.isSelected();

        if (sensibilidadeToque != -1) {
            labelAviso.setText("Configurações salvas localmente!");
            labelAviso.setStyle("-fx-text-fill: #16A34A;"); // Feedback verde de sucesso
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
            
            // Retorna passando as credenciais do usuário de volta para a principal
            PrincipalController controller = loader.getController();
            controller.setUsuarioEmail(usuarioEmail);

            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Erro ao retornar para a tela principal: " + e.getMessage());
        }
    }

    @FXML
    private void abrirSuporteVideo() {
        System.out.println("Abrindo suporte por vídeo chamada...");
    }

    @FXML
    private void abrirSuporteChat() {
        System.out.println("Abrindo suporte por chat...");
    }
}