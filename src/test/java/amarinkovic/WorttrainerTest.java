package amarinkovic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class WorttrainerTest {
    private Worttrainer worttrainer;
    @BeforeEach
    public void setup(){
        this.worttrainer = new Worttrainer("src/test/resources/wordpairs.json");
        this.worttrainer.clearPairs();
        this.worttrainer.resetStats();
    }
    @Test
    public void testWordCountZero() {
        assertEquals(0, this.worttrainer.getWordPairs().size());
    }

    @Test
    public void testWordCountOne() {
        this.worttrainer.addPair(new Wortpaare("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Google_2015_logo.svg/368px-Google_2015_logo.svg.png", "Google"));
        assertEquals(1, this.worttrainer.getWordPairs().size());
    }

    @Test
    public void testSerialization() {
        this.worttrainer.addPair(new Wortpaare("https://media.os.fressnapf.com/cms/2020/05/Ratgeber-Katze-Gesundheit-KatzeWiese_1200x527.jpg", "Katze"));
        this.worttrainer.save();
        Worttrainer wordTrainer = new Worttrainer("src/test/resources/wordpairs.json");
        assertEquals("Katze", wordTrainer.getCardAt(0).getWort());
    }

    @Test
    public void testImageValidationError() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Wortpaare("https://elearning.tgm.ac.at/my/", "Hund");
        });
    }

    @Test
    public void testImageValidation() {
        assertDoesNotThrow(() -> {
            new Wortpaare("https://image.stern.de/33481110/t/XY/v1/w1440/r1.7778/-/hund.jpg", "Hund");
        });
    }

}
