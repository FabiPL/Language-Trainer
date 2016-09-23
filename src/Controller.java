import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * bugs
 * - randomly generated answer is also answer (listed twice)
 *
 * add maybe
 * idea: on start random different langauges "willkommen", "привет",..
 * settings (for russian) -> italic font is like handwriting -> on / off
 * if word not known allow to click on single words of sentence to find out their meaning
 */

public class Controller implements Initializable {

    public Button answ1Button, answ2Button, answ3Button, answ4Button, continueButton, leftButton, rightButton;
    public Label termLabel, sentenceLabel, feedbackLabel;
    public TrainerFct trainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        trainer = new TrainerFct();
        try {
            trainer.load();
        } catch(IOException e) {
            System.out.println("LWT-T: Problem loading file...");
        }
        Button[] answerButtons = {answ1Button,answ2Button,answ3Button,answ4Button, leftButton, rightButton};

        termLabel.setText("Welcome!");
        leftButton.getStyleClass().add("green-button");
        rightButton.getStyleClass().add("red-button");

        for (Button button : answerButtons) {
            button.setDisable(true);
        }

        feedbackLabel.setText("Enjoy learning with LT. Ready when you are.");
        continueButton.setText("Start");
        sentenceLabel.setText("");
    }

    public void continueButtonClicked() {
        Button[] answerButtons = {answ1Button,answ2Button,answ3Button,answ4Button};
        continueButton.setText("Continue");

        continueButton.setDisable(true);
        for(Button button : answerButtons) {
            button.getStyleClass().clear();
            button.getStyleClass().add("button");
            button.setDisable(true);
            button.setText("");
        }
        leftButton.setDisable(false);
        rightButton.setDisable(false);

        termLabel.setText(trainer.randomTermFromDatabase());
        sentenceLabel.setText(trainer.getSentence(termLabel.getText()));
        feedbackLabel.setText("Do you know the answer? Choose green or red.");
    }

    public void leftButtonClicked() throws IOException {
        if(feedbackLabel.getText().equals("Did you choose correctly? Choose green or red.")) {
            int statusBefore = trainer.getStatus(termLabel.getText());
            int statusAfter = trainer.changeStatus(termLabel.getText(),statusBefore+1);

            if(statusAfter > statusBefore) {
                feedbackLabel.setText("Status moved from " + statusBefore + " to " + statusAfter + ".");
                trainer.save();
            } else {
                feedbackLabel.setText("Status unchanged (5)");
            }

            leftButton.setDisable(true);
            rightButton.setDisable(true);
            continueButton.setDisable(false);
        } else {
            Button[] answerButtons = {answ1Button, answ2Button, answ3Button, answ4Button};

            for (Button button : answerButtons) {
                button.setDisable(true);
                button.setText(trainer.getTranslation(trainer.randomTermFromDatabase()));
            }
            Random rand = new Random();
            int random = rand.nextInt(4);
            answerButtons[random].setText(trainer.getTranslation(termLabel.getText()));

            for (Button button : answerButtons) {
                if (trainer.getDatabase().get(termLabel.getText()).get(0).equals(button.getText())) {
                    button.getStyleClass().addAll("green-button", "correct-answer");
                }
            }
            feedbackLabel.setText("Did you choose correctly? Choose green or red.");
        }
    }

    public void rightButtonClicked() throws IOException {
        Button[] answerButtons = {answ1Button, answ2Button, answ3Button, answ4Button};
        if(feedbackLabel.getText().equals("Did you choose correctly? Choose green or red.")) {
            int statusBefore = trainer.getStatus(termLabel.getText());
            int statusAfter = trainer.changeStatus(termLabel.getText(),statusBefore-1);

            for (Button button : answerButtons) {
                if(trainer.getDatabase().get(termLabel.getText()).get(0).equals(button.getText())) {
                    button.getStyleClass().addAll("red-button", "correct-answer");
                }
            }

            if(statusBefore > statusAfter) {
                feedbackLabel.setText("Status moved from " + statusBefore + " to " + statusAfter + ".");
                trainer.save();
            } else {
                feedbackLabel.setText("Status unchanged (1)");
            }

            leftButton.setDisable(true);
            rightButton.setDisable(true);
            continueButton.setDisable(false);
        } else {
            leftButton.setDisable(true);
            rightButton.setDisable(true);
            continueButton.setDisable(true);

            for (Button button : answerButtons) {
                button.setText(trainer.getTranslation(trainer.randomTermFromDatabase()));
                button.setDisable(false);
            }
            Random rand = new Random();
            int random = rand.nextInt(4);
            answerButtons[random].setText(trainer.getTranslation(termLabel.getText()));

            feedbackLabel.setText("C'mon, give it a try...!!");
        }
    }

    public void answ1ButtonClicked() {
        Button[] answerButtons = {answ1Button,answ2Button,answ3Button,answ4Button};

        if(answ1Button.getText().equals(trainer.getDatabase().get(termLabel.getText()).get(0))) {
            feedbackLabel.setText("Good job..!!");
            answ1Button.getStyleClass().addAll("green-button", "correct-answer");
        } else {
            feedbackLabel.setText("Next time maybe..");
            answ1Button.getStyleClass().addAll("red-button", "wrong-answer");
            for (Button button : answerButtons) {
                if(trainer.getDatabase().get(termLabel.getText()).get(0).equals(button.getText())) {
                    button.getStyleClass().addAll("green-button", "correct-answer");
                }
            }
        }

        for(Button button : answerButtons) {
            button.setDisable(true);
        }
        continueButton.setDisable(false);
    }

    public void answ2ButtonClicked() {
        Button[] answerButtons = {answ1Button,answ2Button,answ3Button,answ4Button};

        if(answ2Button.getText().equals(trainer.getDatabase().get(termLabel.getText()).get(0))) {
            feedbackLabel.setText("Good job..!!");
            answ2Button.getStyleClass().addAll("green-button", "correct-answer");
        } else {
            feedbackLabel.setText("Next time maybe..");
            answ2Button.getStyleClass().addAll("red-button", "wrong-answer");
            for (Button button : answerButtons) {
                if(trainer.getDatabase().get(termLabel.getText()).get(0).equals(button.getText())) {
                    button.getStyleClass().addAll("green-button", "correct-answer");
                }
            }
        }

        for(Button button : answerButtons) {
            button.setDisable(true);
        }
        continueButton.setDisable(false);
    }

    public void answ3ButtonClicked() {
        Button[] answerButtons = {answ1Button,answ2Button,answ3Button,answ4Button};

        if(answ3Button.getText().equals(trainer.getDatabase().get(termLabel.getText()).get(0))) {
            feedbackLabel.setText("Good job..!!");
            answ3Button.getStyleClass().addAll("green-button", "correct-answer");
        } else {
            feedbackLabel.setText("Next time maybe..");
            answ3Button.getStyleClass().addAll("red-button", "wrong-answer");
            for (Button button : answerButtons) {
                if(trainer.getDatabase().get(termLabel.getText()).get(0).equals(button.getText())) {
                    button.getStyleClass().addAll("green-button", "correct-answer");
                }
            }
        }

        for(Button button : answerButtons) {
            button.setDisable(true);
        }
        continueButton.setDisable(false);
    }

    public void answ4ButtonClicked() {
        Button[] answerButtons = {answ1Button,answ2Button,answ3Button,answ4Button};

        if(answ4Button.getText().equals(trainer.getDatabase().get(termLabel.getText()).get(0))) {
            feedbackLabel.setText("Good job..!!");
            answ4Button.getStyleClass().addAll("green-button", "correct-answer");
        } else {
            feedbackLabel.setText("Next time maybe..");
            answ4Button.getStyleClass().addAll("red-button", "wrong-answer");
            for (Button button : answerButtons) {
                if(trainer.getDatabase().get(termLabel.getText()).get(0).equals(button.getText())) {
                    button.getStyleClass().addAll("green-button", "correct-answer");
                }
            }
        }

        for(Button button : answerButtons) {
            button.setDisable(true);
        }
        continueButton.setDisable(false);
    }
}