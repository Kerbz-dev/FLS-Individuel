package presentation;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.CalculationLgc;
import logic.CalculationLgc.kreditRating;
import logic.kundeCheckDuplicate;
import logic.opretKunde;

public class OpretKundeUI {

	private long cpr;
	private int tlf, postnr, husnr;
	private DecimalFormat numberFormatter = new DecimalFormat("0");
	private TextField tlfTField, PostnrTField, VejTField, ByTField, HusnrTField, cprTField, mailTField,
			kundefornavnTField, kundeefternavnTField;
	private Button opretKundeBtn;
	private Stage opretKundeStage;
	private kundeCheckDuplicate kundelogic = new kundeCheckDuplicate();
	private String fornavnGetText, efternavnGetText, tlfGetText, postnrGetText, byGetText, vejGetText, husnrGetText,
			cprGetText, mailGetText;
	private Label opretStatusLbl;
	private Scene scene;
	private Pane pane;
	private Image ferrari;
	private ImageView ferraripic;
	private kreditRating kreditVurdering;

	public void start() {
		opretKundeStage = new Stage();
		opretKundeStage.setTitle("Ferrari lånesystem");
		opretKundeStage.getIcons()
				.add(new Image("https://i.pinimg.com/564x/c9/87/c8/c987c8a5c896fca22c5cfbd62edb7359.jpg"));

		// Laver pane
		pane = new Pane();
		ferrari = new Image(
				"https://3.bp.blogspot.com/-DRM75enaO7s/VDrpAiCm55I/AAAAAAAABGM/VnsBvuXIygU/s1600/Ferrari%2BCar%2Blogos.jpg%22");
		pane.setPrefHeight(670.0);
		ferraripic = new ImageView();

//		// Sætter Knap 
		opretKundeBtn = new Button("Opret Kunde!");
		// Sætter TField
		kundefornavnTField = new TextField();
		kundeefternavnTField = new TextField();
		PostnrTField = new TextField();
		ByTField = new TextField();
		VejTField = new TextField();
		HusnrTField = new TextField();
		tlfTField = new TextField();
		cprTField = new TextField();
		mailTField = new TextField();
		// Sætter Label
		opretStatusLbl = new Label();

		// TField lokation
		kundefornavnTField.relocate(320.0, 195.0);
		kundefornavnTField.setPrefHeight(25.0);
		kundefornavnTField.setPrefWidth(321);
		kundeefternavnTField.relocate(320.0, 225.0);
		kundeefternavnTField.setPrefHeight(25.0);
		kundeefternavnTField.setPrefWidth(321.0);
		PostnrTField.relocate(320.0, 255.0);
		PostnrTField.setPrefHeight(25.0);
		PostnrTField.setPrefWidth(321.0);
		ByTField.setPrefWidth(321.0);
		ByTField.relocate(320.0, 285.0);
		ByTField.setPrefHeight(25.0);
		VejTField.setPrefWidth(321.0);
		VejTField.relocate(320.0, 315.0);
		VejTField.setPrefHeight(25.0);
		HusnrTField.setPrefWidth(321.0);
		HusnrTField.relocate(320.0, 345.0);
		HusnrTField.setPrefHeight(25.0);
		tlfTField.relocate(320.0, 375.0);
		tlfTField.setPrefHeight(25.0);
		tlfTField.setPrefWidth(321.0);
		cprTField.relocate(320.0, 405.0);
		cprTField.setPrefHeight(25.0);
		cprTField.setPrefWidth(321.0);
		mailTField.relocate(320.0, 435);
		mailTField.setPrefHeight(25.0);
		mailTField.setPrefWidth(321.0);
		// Knap lokation
		opretKundeBtn.relocate(390, 550);
		// LOGO lokation
		ferraripic.setFitWidth(350);
		ferraripic.setFitHeight(130);
		ferraripic.setImage(ferrari);
		ferraripic.relocate(305, 25);
		opretKundeStage.setResizable(false);
		// Label lokation

		// Sætter prompt text til kun at være vist når et bogstav er blevet indsat
		mailTField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
		cprTField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
		tlfTField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
		PostnrTField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
		ByTField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
		VejTField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
		HusnrTField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
		kundefornavnTField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
		kundeefternavnTField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
		pane.setStyle("-fx-background-color: #F40808");

		// Sætter promptText
		kundefornavnTField.setPromptText("Fornavn:");
		kundeefternavnTField.setPromptText("Efternavn:");
		mailTField.setPromptText("E-Mail:");
		cprTField.setPromptText("CPR-nr:");
		tlfTField.setPromptText("Tlf.nr:");
		PostnrTField.setPromptText("Postnr:");
		HusnrTField.setPromptText("Husnr:");
		VejTField.setPromptText("Vejnavn:");
		ByTField.setPromptText("By:");

		// Implementerer at TField kun kan sættes til tal værdier
		cprTField.setTextFormatter(new TextFormatter<>(c -> {
			if (c.getControlNewText().isEmpty()) {
				return c;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = numberFormatter.parse(c.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
				return null;
			} else {
				return c;
			}
		}));

		PostnrTField.setTextFormatter(new TextFormatter<>(c -> {
			if (c.getControlNewText().isEmpty()) {
				return c;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = numberFormatter.parse(c.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
				return null;
			} else {
				return c;
			}
		}));

		tlfTField.setTextFormatter(new TextFormatter<>(c -> {
			if (c.getControlNewText().isEmpty()) {
				return c;
			}

			ParsePosition parsePosition = new ParsePosition(0);
			Object object = numberFormatter.parse(c.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
				return null;
			} else {
				return c;
			}
		}));

		// Font størrelse på TField
		opretStatusLbl.setFont(new Font(24));
		kundeefternavnTField.setFont(new Font(14));
		kundefornavnTField.setFont(new Font(14));
		opretKundeBtn.setFont(new Font(24));
		mailTField.setFont(new Font(14));
		cprTField.setFont(new Font(14));
		tlfTField.setFont(new Font(14));
		PostnrTField.setFont(new Font(14));
		HusnrTField.setFont(new Font(14));
		VejTField.setFont(new Font(14));
		ByTField.setFont(new Font(14));

		// Tilføjer alt til pane
		pane.getChildren().addAll(kundefornavnTField, kundeefternavnTField, PostnrTField, ByTField, VejTField,
				HusnrTField, tlfTField, cprTField, mailTField, opretKundeBtn, opretStatusLbl, ferraripic);

		// Vis scene
		scene = new Scene(pane, 950, 670);
		opretKundeStage.setScene(scene);
		opretKundeStage.show();

		// Sætter set on action på knap
		opretKundeBtn.setOnAction(e -> opretKunde());
	}

	// Metoder
	private void opretKunde() {
		fornavnGetText = kundefornavnTField.getText();
		efternavnGetText = kundeefternavnTField.getText();
		tlfGetText = tlfTField.getText();
		postnrGetText = PostnrTField.getText();
		byGetText = ByTField.getText();
		vejGetText = VejTField.getText();
		husnrGetText = HusnrTField.getText();
		cprGetText = cprTField.getText();
		mailGetText = mailTField.getText();

		if (tfieldTomme() == true) {
			kundeFejl();

		} else if (cprTField.getText().length() != 10) {
			cprFejl();
		} else if (kundelogic.KundeCheckDuplicate(cprGetText, tlfGetText) == true) {
			kundeDuplikeret();
		} else {
			checkRating();
		}
	}

	private boolean tfieldTomme() {
		if (fornavnGetText.isEmpty() || efternavnGetText.isEmpty() || tlfGetText.isEmpty() || postnrGetText.isEmpty()
				|| byGetText.isEmpty() || vejGetText.isEmpty() || husnrGetText.isEmpty() || cprGetText.isEmpty()
				|| mailGetText.isEmpty()) {
			return true;
		}
		return false;
	}

	private void cprFejl() {
		opretStatusLbl.setText("Der skal være 10 CPR-cifre. Nuværende antal: " + cprTField.getText().length());
		opretStatusLbl.setTextFill(Color.WHITE);
		opretStatusLbl.relocate(200, 490);
	}

	private void kundeSuccess() {
		opretKunde OpretKunde = new opretKunde();

		opretStatusLbl.setText("Kunde oprettet med kreditvurdering: " + kreditVurdering);
		opretStatusLbl.setTextFill(Color.LIGHTGREEN);
		opretStatusLbl.relocate(280, 490);
		cpr = Long.parseLong(cprGetText);
		tlf = Integer.parseInt(tlfGetText);
		postnr = Integer.parseInt(postnrGetText);
		husnr = Integer.parseInt(husnrGetText);

		OpretKunde.CreateKunde(fornavnGetText, efternavnGetText, tlf, postnr, byGetText, vejGetText, husnr, cpr,
				mailGetText, kreditVurdering);
	}

	private void kundeDuplikeret() {
		opretStatusLbl.setText("Bruger eksisterer allerede med valgte CPR og/eller tlfnr!");
		opretStatusLbl.setTextFill(Color.WHITE);
		opretStatusLbl.relocate(190, 490);

	}

	private void kundeFejl() {
		opretStatusLbl.setText("Udfyld venligst alle felter!");
		opretStatusLbl.setTextFill(Color.WHITE);
		opretStatusLbl.relocate(345, 490);

	}

	private void kreditRatingFejl() {
		opretStatusLbl.setText("Kundens kreditværdighed er under den tilladte grænse!");
		opretStatusLbl.setTextFill(Color.WHITE);
		opretStatusLbl.relocate(175, 490);
	}

	private void systemFejl() {
		opretStatusLbl.setText("Der opstod en fejl i systemet! Genstart venligst programmet.");
		opretStatusLbl.setTextFill(Color.WHITE);
		opretStatusLbl.relocate(150, 490);
	}

	private void checkRating() {
		opretStatusLbl.setText("Beregner kreditvurdering... Vent venligst");
		opretStatusLbl.setTextFill(Color.WHITE);
		opretStatusLbl.relocate(275, 490);
		opretKundeBtn.setDisable(true);
		CalculationLgc getKV = new CalculationLgc();
		kreditVurdering = getKV.getKVWithCallback(cprGetText, OpretKundeUI.this::showRating);

		// Defaulting close button to ask for annuller/ok while calculating
		opretKundeStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {

				// consume event
				event.consume();

				// show close dialog
				Alert alert = new Alert(AlertType.CONFIRMATION);
				Button annullerBtn = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
				annullerBtn.setText("Annuller");
				alert.setTitle("Advarsel!");
				alert.setHeaderText(
						"Programmet er ved at beregne kreditværdighed. Er du sikker på at du vil lukke programmet?");
				alert.initOwner(opretKundeStage);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					opretKundeStage.close();
				}
			}
		});
	}

	public kreditRating showRating(kreditRating kv) {

		// Checking result from calc
		kreditVurdering = kv;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				switch (kreditVurdering) {
				case A:
					kundeSuccess();
					break;
				case B:
					kundeSuccess();
					break;
				case C:
					kundeSuccess();
					break;
				case D:
					kreditRatingFejl();
					break;
				case error:
					systemFejl();
					break;
				}

				opretKundeBtn.setDisable(false);

				// Setting close event to default once calc is complete
				opretKundeStage.setOnCloseRequest(e -> opretKundeStage.close());

			}

		});

		return kreditVurdering;
	}

}
