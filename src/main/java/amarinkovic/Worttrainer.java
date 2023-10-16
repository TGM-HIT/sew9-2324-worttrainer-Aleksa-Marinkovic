package amarinkovic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Worttrainer {
    private ArrayList<Wortpaare> wortpaare;
    private SaveType saveStrategy;
    private String file;
    private int currentCard;
    private int correctAnswers;
    private int wrongAnswers;
    private int tries;
    public Worttrainer(String filePath) {
        this.file = filePath;
        this.wortpaare = new ArrayList<Wortpaare>();
        this.saveStrategy = new JSONSafe();
        this.currentCard = 0;
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
        this.tries = 0;
    }
    public Wortpaare getCardAt(int index) {
        return this.wortpaare.get(index);
    }

    public SaveType getSaveStrategy() {
        return saveStrategy;
    }

    public void setSaveStrategy(SaveType saveStrategy) {
        this.saveStrategy = saveStrategy;
    }

    public ArrayList<Wortpaare> getWordCards() {
        return wortpaare;
    }

    public void setWordCards(ArrayList<Wortpaare> wordCards) {
        this.wortpaare = wordCards;
    }

    public String getFilePath() {
        return file;
    }

    public int getCurrentCard() {
        return currentCard;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public int getTries() {
        return tries;
    }

    public void setFilePath(String filePath) {
        this.file = filePath;
    }

    public void setCurrentCard(int currentCard) {
        this.currentCard = currentCard;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public void clearCards() {
        this.wortpaare.clear();
    }

    public void addCard(Wortpaare card) {
        this.wortpaare.add(card);
    }
    private Image getImage() {
        Image img = null;
        System.out.println(getCardAt(this.currentCard).getBildURL());
        try {
            URL url = new URL(getCardAt(this.currentCard).getBildURL());
            img = ImageIO.read(url);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }
}
