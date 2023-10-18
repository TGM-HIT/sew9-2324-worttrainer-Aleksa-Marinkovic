package amarinkovic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Worttrainer {
    private ArrayList<Wortpaare> wordpairs;
    private SaveType saveStrategy;
    private String file;
    private int currentPair;
    private int correctAnswers;
    private int wrongAnswers;
    private int tries;
    public Worttrainer(String filePath) {
        this.file = filePath;
        this.wordpairs = new ArrayList<Wortpaare>();
        this.saveStrategy = new JSONSafe();
        this.currentPair = 0;
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
        this.tries = 0;

        this.wordpairs.add(new Wortpaare("https://image.stern.de/33481110/t/XY/v1/w1440/r1.7778/-/hund.jpg", "Hund"));
        this.wordpairs.add(new Wortpaare("https://media.os.fressnapf.com/cms/2020/05/Ratgeber-Katze-Gesundheit-KatzeWiese_1200x527.jpg", "Katze"));

        load();
    }

    public static void main(String[] args) {
        Worttrainer wt = new Worttrainer("src/test/resources/wordpairs.json");
        wt.play();
    }
    public void play() {
        if(currentPair >= this.wordpairs.size()) this.resetStats();
        if(currentPair == 0) randomizePairs(); // Randomize cards if first time playing
        while(currentPair < this.wordpairs.size()) {
            Image img = getImage();

            String res = (String) JOptionPane.showInputDialog(null, "Wie heisst das Wort auf dem Bild?",
                    "Word Trainer", JOptionPane.QUESTION_MESSAGE, new ImageIcon(img), null, null);
            if(res.isEmpty()) {
                save();
                return;
            }
            boolean guessed = res.equalsIgnoreCase(getCardAt(currentPair).getWort());
            if(guessed) {
                this.correctAnswers++;
                this.currentPair++;
            } else {
                this.wrongAnswers++;
            }
            this.tries++;
            JOptionPane.showMessageDialog(null, (guessed ? "Richtig!" : "Falsch!") + "\nVersuche: " + tries + "\nRichtig: " + correctAnswers + "\nFalsch: " + wrongAnswers);
            if(guessed) this.tries = 0;
            this.save();
        }
        resetStats();
        this.save();
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
        return currentPair;
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

    public void setCurrentPair(int currentPair) {
        this.currentPair = currentPair;
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
        System.out.println(getCardAt(this.currentPair).getBildURL());
        try {
            URL url = new URL(getCardAt(this.currentPair).getBildURL());
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
        this.currentPair = 0;
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
        this.tries = 0;
        randomizePairs();
    }
    public void load() {
        Worttrainer loaded = this.saveStrategy.load(this.file, this);
        if(loaded != null) {
            this.wordpairs = loaded.getWordPairs();
            this.currentPair = loaded.getCurrentPair();
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
