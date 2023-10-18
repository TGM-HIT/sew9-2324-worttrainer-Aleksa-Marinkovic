package amarinkovic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Worttrainer {
    private ArrayList<Wortpaare> wordpairs;
    private SaveType saveStrategy;
    private String file;
    private int currentCard;
    private int correctAnswers;
    private int wrongAnswers;
    private int tries;
    public Worttrainer(String filePath) {
        this.file = filePath;
        this.wordpairs = new ArrayList<Wortpaare>();
        this.saveStrategy = new JSONSafe();
        this.currentCard = 0;
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
        this.tries = 0;

        load();
    }
    public Wortpaare getCardAt(int index) {
        return this.wordpairs.get(index);
    }

    public SaveType getSaveStrategy() {
        return saveStrategy;
    }

    public void setSaveStrategy(SaveType saveStrategy) {
        this.saveStrategy = saveStrategy;
    }

    public ArrayList<Wortpaare> getWordPairs() {
        return wordpairs;
    }

    public void setWordPair(ArrayList<Wortpaare> wordCards) {
        this.wordpairs = wordCards;
    }

    public String getFilePath() {
        return file;
    }

    public int getCurrentPair() {
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

    public void clearPairs() {
        this.wordpairs.clear();
    }

    public void addPair(Wortpaare card) {
        this.wordpairs.add(card);
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
    private void randomizePairs() {
        // Shuffle array
        for (int i = 0; i < this.wordpairs.size(); i++) {
            int randomIndexToSwap = (int) (Math.random() * this.wordpairs.size());
            Wortpaare temp = this.wordpairs.get(randomIndexToSwap);
            this.wordpairs.set(randomIndexToSwap, this.wordpairs.get(i));
            this.wordpairs.set(i, temp);
        }
    }

    public void resetStats() {
        this.currentCard = 0;
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
        this.tries = 0;
        randomizePairs();
    }
    public void load() {
        Worttrainer loaded = this.saveStrategy.load(this.file, this);
        if(loaded != null) {
            this.wordpairs = loaded.getWordPairs();
            this.currentCard = loaded.getCurrentPair();
            this.correctAnswers = loaded.getCorrectAnswers();
            this.wrongAnswers = loaded.getWrongAnswers();
            this.tries = loaded.getTries();
        }
    }

    public void save() {
        try {
            this.saveStrategy.save(this.file, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
