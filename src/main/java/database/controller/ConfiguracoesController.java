package database.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ConfiguracoesController {
  @FXML
  private CheckBox altoContrasteField;
  @FXML
  private CheckBox textoAmpliadoField;
  @FXML
  private TextField sensibilidadeToqueField; // Aliás, como que faríamos esse sensibilidadeToque? Literamente n tem como
                                             // controlar a sensibiliade no mouse kkkkkkkkkkkkkkkkkkkk
  @FXML
  private CheckBox modoVozField;
  @FXML
  private Label labelAviso;

  public void initialize() { // essa bomba (provavelmente) deve prevenir que o usuário coloque texto aonde só
                             // pode ter
                             // número. Eu não confio. Preciso testar.

                             
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
  } // n vai ter muito uso isso...

  private float verificarFloat(String string) {
    try {
      return Float.parseFloat(string);
    } catch (NumberFormatException e) {
      labelAviso.setText("Por favor, insira um número!");
      return -1;
    }
  }

}



// essa bomba vai funcionar só na UI kkkkkk
// mas eu pensei na ideia de invés de controlar a sensibilidade(impossível), fizesse um filtro
// dw toque acidental, um filtro que vesse se o idoso clica duas vezes num espaço de tempo muito curto
// ou clique muito rápido, fazendo tipo um delay tlgd?