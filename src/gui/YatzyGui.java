package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.Die;
import models.RaffleCup;
import models.YatzyResultCalculator;

public class YatzyGui extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Yatzy");
        GridPane pane = new GridPane();
        ScrollPane scrollPane = new ScrollPane(pane);
        pane.setHgap(10);
        pane.setVgap(10);
        initContent(pane);
        Scene scene = new Scene(scrollPane, 450, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    RaffleCup raffleCup = new RaffleCup();
    Button btnThrowDice = new Button("Kast Terninger");
    CheckBox[] holdCheckBoxes = new CheckBox[5];
    ToggleButton[] toggleButtonsDice = new ToggleButton[5];
    int throwCount = 3;
    Label lblThrowsLeft = new Label("Antal kast tilbage: 3");
    private final TextField[] txfEyeResult = new TextField[6];
    Label lblSum = new Label("Sum");
    TextField txfSum = smallTextfield();
    Label lblBonus = new Label("Bonus");
    TextField txfBonus = smallTextfield();
    int[] upperscores = new int[6];

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        for (int i = 0; i < 5; i++) {
            toggleButtonsDice[i] = new ToggleButton();
            pane.add(toggleButtonsDice[i], i, 0);
            toggleButtonsDice[i].setPrefSize(70, 70);
            toggleButtonsDice[i].setDisable(true);
            holdCheckBoxes[i] = new CheckBox("Hold");
            pane.add(holdCheckBoxes[i], i, 1);
        }

        for (int i = 1; i < 7; i++) {
            Label lblEyeResult = new Label(i + "'ere");
            pane.add(lblEyeResult, 0, 2 + i);
            txfEyeResult[i - 1] = smallTextfield();
            pane.add(txfEyeResult[i - 1], 1, 2 + i);
            txfEyeResult[i - 1].setOnMouseClicked(event -> this.chooseFieldAction(event));
        }

        pane.add(btnThrowDice, 3, 2, 3, 1);
        pane.add(lblThrowsLeft, 1, 2, 3, 1);
        btnThrowDice.setOnAction(event -> rollDice());

        pane.add(lblSum, 2, 9);
        pane.add(txfSum, 3, 9);
        pane.add(lblBonus, 2, 10);
        pane.add(txfBonus, 3, 10);
        txfSum.setPrefSize(20, 20);
        txfBonus.setPrefSize(20, 20);

        Label lblPair = new Label("Et par");
        pane.add(lblPair, 0, 11);
        TextField txfPair = smallTextfield();
        pane.add(txfPair, 1, 11);

        Label lblTwoPair = new Label("To par");
        pane.add(lblTwoPair, 0, 12);
        TextField txfTwoPairs = smallTextfield();
        pane.add(txfTwoPairs, 1, 12);

        Label lblThreeOfAKind = new Label("Tre ens");
        pane.add(lblThreeOfAKind, 0, 13);
        TextField txfThreeOfAKind = smallTextfield();
        pane.add(txfThreeOfAKind, 1, 13);

        Label lblFourOfAKind = new Label("Fire ens");
        pane.add(lblFourOfAKind, 0, 14);
        TextField txfFourOfAKind = smallTextfield();
        pane.add(txfFourOfAKind, 1, 14);

        Label lblSmallStraight = new Label("Lille Straight");
        pane.add(lblSmallStraight, 0, 15);
        TextField txfSmallStraight = smallTextfield();
        pane.add(txfSmallStraight, 1, 15);

        Label lblBigStraight = new Label("Stor Straight");
        pane.add(lblBigStraight, 0, 16);
        TextField txfBigStraight = smallTextfield();
        pane.add(txfBigStraight, 1, 16);

        Label lblFullHouse = new Label("Fuldt Hus");
        pane.add(lblFullHouse, 0, 17);
        TextField txfFullHouse = smallTextfield();
        pane.add(txfFullHouse, 1, 17);

        Label lblChance = new Label("Chance");
        pane.add(lblChance, 0, 18);
        TextField txfChance = smallTextfield();
        pane.add(txfChance, 1, 18);

        Label lblYatzy = new Label("Yatzy");
        pane.add(lblYatzy, 0, 19);
        TextField txfYatzy = smallTextfield();
        pane.add(txfYatzy, 1, 19);

        Label lblTotal = new Label("Total");
        pane.add(lblTotal, 2, 20);
        TextField txfTotal = smallTextfield();
        pane.add(txfTotal, 3, 20);
    }

    private void chooseFieldAction(MouseEvent event) {
        TextField textField = (TextField) event.getSource();
        textField.setDisable(true);
        throwCount = 3;
        lblThrowsLeft.setText("Antal kast tilbage:" + " " + throwCount);
        for (int i = 0; i < 5; i++) {
            holdCheckBoxes[i].setSelected(false);
        }
    }

    private TextField smallTextfield() {
        TextField tf = new TextField();
        tf.setPrefSize(20, 20);
        tf.setEditable(false);
        return tf;
    }

    private void rollDice() {
        Die[] dice = raffleCup.getDice();
        if (throwCount > 0) {
            for (int i = 0; i < toggleButtonsDice.length; i++) {
                if (!holdCheckBoxes[i].isSelected()) {
                    dice[i].roll();
                }
                toggleButtonsDice[i].setText(String.valueOf(dice[i].getEyes()));
            }
            throwCount--;
            lblThrowsLeft.setText("Antal kast tilbage:" + " " + throwCount);
            displayScoreUppersection();
        }
    }

    private void displayScoreUppersection() {
        Die[] dice = raffleCup.getDice();
        YatzyResultCalculator uppersectionCalc = new YatzyResultCalculator(dice);
        int sum = 0;

        for (int eyes = 1; eyes <= 6; eyes++) {
            int index = eyes - 1;
            if (!txfEyeResult[index].isDisable()) {
                int score = uppersectionCalc.upperSectionScore(eyes);
                upperscores[eyes - 1] = score;
                txfEyeResult[eyes - 1].setText(String.valueOf(score));
            }
            if (!txfEyeResult[index].getText().isEmpty()) {
                sum += Integer.parseInt(txfEyeResult[index].getText());
            }
        }
        txfSum.setText(String.valueOf(sum));
        if (sum > 63) {
            txfBonus.setText(String.valueOf(50));
        } else {
            txfBonus.setText(String.valueOf(0));
        }
    }
}