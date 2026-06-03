package javaFX;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.ParallelTransition;
import javafx.util.Duration;

import database.controller.UsuarioController;

public class HelloFX extends Application {

    private Stage primaryStage;

    // ── Paleta Cognito ──────────────────────────────────────────────
    private static final String NAVY       = "#1A237E";
    private static final String BLUE_BTN   = "#283593";
    private static final String BLUE_LINK  = "#3F51B5";
    private static final String FIELD_BG   = "#F0F0F0";
    private static final String TEXT_LABEL = "#444444";

    // ── CSS reutilizável ────────────────────────────────────────────
    private static final String FIELD_STYLE =
        "-fx-background-color: " + FIELD_BG + ";" +
        "-fx-background-radius: 10;" +
        "-fx-border-color: transparent;" +
        "-fx-font-size: 14px;" +
        "-fx-padding: 10 14 10 14;" +
        "-fx-pref-height: 44px;";

    private static final String BTN_STYLE =
        "-fx-background-color: " + BLUE_BTN + ";" +
        "-fx-text-fill: white;" +
        "-fx-font-size: 15px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 12;" +
        "-fx-cursor: hand;" +
        "-fx-pref-height: 48px;";

    private static final String BTN_HOVER =
        "-fx-background-color: " + NAVY + ";" +
        "-fx-text-fill: white;" +
        "-fx-font-size: 15px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 12;" +
        "-fx-cursor: hand;" +
        "-fx-pref-height: 48px;";

    private static final String BG_STYLE =
        "-fx-background-color: linear-gradient(to bottom, #E8EAF6, #C5CAE9);" +
        "-fx-background-radius: 20;" +
        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 30, 0, 0, 8);";

    // ────────────────────────────────────────────────────────────────

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setResizable(false);
        stage.setTitle("Cognito");
        stage.setWidth(440);
        stage.setHeight(950);
        // stage.initStyle(StageStyle.TRANSPARENT); --- Isso aqui era pra fazer o border radius, mas ainda tá dando problema
        showLogin();
        stage.show();
    }

    // ── TELA DE LOGIN ───────────────────────────────────────────────
    private void showLogin() {
        VBox root = new VBox(8);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(36, 32, 36, 32));
        root.setStyle(BG_STYLE);

        root.getChildren().add(buildLogo());

        Label title = new Label("Login");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 26));
        title.setTextFill(Color.web(BLUE_LINK));
        title.setPadding(new Insets(4, 0, 12, 0));
        root.getChildren().add(title);

        root.getChildren().add(buildLabel("E-Mail"));
        TextField emailField = new TextField();
        emailField.setPromptText("seu@email.com");
        emailField.setStyle(FIELD_STYLE);
        emailField.setMaxWidth(Double.MAX_VALUE);
        root.getChildren().add(emailField);

        root.getChildren().add(new Region() {{ setMinHeight(8); }});

        root.getChildren().add(buildLabel("Senha"));
        PasswordField senhaField = new PasswordField();
        senhaField.setPromptText("••••••••");
        senhaField.setStyle(FIELD_STYLE);
        senhaField.setMaxWidth(Double.MAX_VALUE);
        root.getChildren().add(senhaField);

        root.getChildren().add(new Region() {{ setMinHeight(24); }});

        Button btnLogin = new Button("Login");
        btnLogin.setStyle(BTN_STYLE);
        btnLogin.setMaxWidth(Double.MAX_VALUE);
        btnLogin.setOnMouseEntered(e -> btnLogin.setStyle(BTN_HOVER));
        btnLogin.setOnMouseExited(e  -> btnLogin.setStyle(BTN_STYLE));
        btnLogin.setEffect(new DropShadow(8, Color.web(NAVY, 0.4)));
        root.getChildren().add(btnLogin);

        root.getChildren().add(new Region() {{ setMinHeight(16); }});

        Label naoTem = new Label("Não possui uma conta?");
        naoTem.setFont(Font.font("SansSerif", 13));
        naoTem.setTextFill(Color.web(TEXT_LABEL));

        Hyperlink cadastroLink = new Hyperlink("Clique aqui e crie uma");
        cadastroLink.setFont(Font.font("SansSerif", 13));
        cadastroLink.setTextFill(Color.web(BLUE_LINK));
        cadastroLink.setBorder(Border.EMPTY);
        cadastroLink.setPadding(Insets.EMPTY);
        cadastroLink.setOnAction(e -> showCadastro());

        VBox linkBox = new VBox(2, naoTem, cadastroLink);
        linkBox.setAlignment(Pos.CENTER);
        root.getChildren().add(linkBox);

        Scene scene = new Scene(root);
        applyEntrance(root);
        primaryStage.setScene(scene);
    }

    // ── TELA DE CADASTRO ────────────────────────────────────────────
    private void showCadastro() {
        VBox root = new VBox(8);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(36, 32, 36, 32));
        root.setStyle(BG_STYLE);

        root.getChildren().add(buildLogo());

        Label title = new Label("Cadastro");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 26));
        title.setTextFill(Color.web(BLUE_LINK));
        title.setPadding(new Insets(4, 0, 12, 0));
        root.getChildren().add(title);

        // ── Campos vinculados ao UsuarioController ──────────────────
        UsuarioController controller = new UsuarioController();

        root.getChildren().add(buildLabel("Nome"));
        TextField nomeField = new TextField();
        nomeField.setPromptText("Seu nome completo");
        nomeField.setStyle(FIELD_STYLE);
        nomeField.setMaxWidth(Double.MAX_VALUE);
        controller.setNomeField(nomeField);
        root.getChildren().add(nomeField);

        root.getChildren().add(new Region() {{ setMinHeight(8); }});

        root.getChildren().add(buildLabel("E-Mail"));
        TextField emailField = new TextField();
        emailField.setPromptText("seu@email.com");
        emailField.setStyle(FIELD_STYLE);
        emailField.setMaxWidth(Double.MAX_VALUE);
        controller.setEmailField(emailField);
        root.getChildren().add(emailField);

        root.getChildren().add(new Region() {{ setMinHeight(8); }});

        root.getChildren().add(buildLabel("Senha"));
        PasswordField senhaField = new PasswordField();
        senhaField.setPromptText("••••••••");
        senhaField.setStyle(FIELD_STYLE);
        senhaField.setMaxWidth(Double.MAX_VALUE);
        controller.setSenhaField(senhaField);
        root.getChildren().add(senhaField);

        root.getChildren().add(new Region() {{ setMinHeight(8); }});

        root.getChildren().add(buildLabel("Data de Nascimento"));
        DatePicker dataField = new DatePicker();
        dataField.setPromptText("dd/mm/aaaa");
        dataField.setStyle(FIELD_STYLE);
        dataField.setMaxWidth(Double.MAX_VALUE);
        controller.setDataField(dataField);
        root.getChildren().add(dataField);

        root.getChildren().add(new Region() {{ setMinHeight(24); }});

        // Label de aviso — vinculado ao controller
        Label labelAviso = new Label("");
        labelAviso.setFont(Font.font("SansSerif", 12));
        labelAviso.setTextFill(Color.web("#C62828"));
        labelAviso.setWrapText(true);
        controller.setLabelAviso(labelAviso);
        root.getChildren().add(labelAviso);

        // Botão Cadastrar — chama controller.cadastrar()
        Button btnCadastrar = new Button("Cadastrar");
        btnCadastrar.setStyle(BTN_STYLE);
        btnCadastrar.setMaxWidth(Double.MAX_VALUE);
        btnCadastrar.setOnMouseEntered(e -> btnCadastrar.setStyle(BTN_HOVER));
        btnCadastrar.setOnMouseExited(e  -> btnCadastrar.setStyle(BTN_STYLE));
        btnCadastrar.setEffect(new DropShadow(8, Color.web(NAVY, 0.4)));
        btnCadastrar.setOnAction(e -> controller.cadastrar());
        root.getChildren().add(btnCadastrar);

        root.getChildren().add(new Region() {{ setMinHeight(16); }});

        Label jaTemConta = new Label("Já possui uma conta?");
        jaTemConta.setFont(Font.font("SansSerif", 13));
        jaTemConta.setTextFill(Color.web(TEXT_LABEL));

        Hyperlink loginLink = new Hyperlink("Faça login aqui");
        loginLink.setFont(Font.font("SansSerif", 13));
        loginLink.setTextFill(Color.web(BLUE_LINK));
        loginLink.setBorder(Border.EMPTY);
        loginLink.setPadding(Insets.EMPTY);
        loginLink.setOnAction(e -> showLogin());

        VBox linkBox = new VBox(2, jaTemConta, loginLink);
        linkBox.setAlignment(Pos.CENTER);
        root.getChildren().add(linkBox);

        Scene scene = new Scene(root);
        applyEntrance(root);
        primaryStage.setScene(scene);
    }

    // ── Logo (Depois eu coloco o arquivo .svg) ────────────────────────────────────────────────────────
    private StackPane buildLogo() {
        Circle circle = new Circle(52);
        circle.setFill(Color.web(NAVY));
        circle.setStroke(Color.web("#3F51B5"));
        circle.setStrokeWidth(3);

        VBox inner = new VBox(0);
        inner.setAlignment(Pos.CENTER);

        Label icon = new Label("📖");
        icon.setFont(Font.font(28));

        Label brand = new Label("Cognito");
        brand.setFont(Font.font("Georgia", FontWeight.BOLD, 11));
        brand.setTextFill(Color.WHITE);

        inner.getChildren().addAll(icon, brand);

        StackPane logo = new StackPane(circle, inner);
        logo.setPadding(new Insets(0, 0, 8, 0));
        return logo;
    }

    // ── Label de campo ──────────────────────────────────────────────
    private Label buildLabel(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("SansSerif", FontWeight.BOLD, 13));
        lbl.setTextFill(Color.web(TEXT_LABEL));
        lbl.setPadding(new Insets(4, 0, 2, 2));
        VBox.setMargin(lbl, new Insets(6, 0, 0, 0));
        return lbl;
    }

    // ── Animação de entrada ─────────────────────────────────────────
    private void applyEntrance(VBox root) {
        root.setOpacity(0);
        root.setTranslateY(20);

        FadeTransition fade = new FadeTransition(Duration.millis(350), root);
        fade.setFromValue(0);
        fade.setToValue(1);

        TranslateTransition slide = new TranslateTransition(Duration.millis(350), root);
        slide.setFromY(20);
        slide.setToY(0);

        new ParallelTransition(fade, slide).play();
    }

    public static void main(String[] args) {
        launch();
    }
}