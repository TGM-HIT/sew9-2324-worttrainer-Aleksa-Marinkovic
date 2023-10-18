package amarinkovic;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class JSONSafe implements SaveType{
    @Override
    public void save(String path, Worttrainer worttrainer) throws IOException {
        if(!new File(path).exists()) new File(path).createNewFile();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("wordPairs", worttrainer.getWordPairs());
        jsonObject.put("currentCard", worttrainer.getCurrentPair());
        jsonObject.put("correctAnswers", worttrainer.getCorrectAnswers());
        jsonObject.put("wrongAnswers", worttrainer.getWrongAnswers());
        jsonObject.put("tries", worttrainer.getTries());

        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(jsonObject.toString());
        fileWriter.close();
    }

    @Override
    public Worttrainer load(String path, Worttrainer worttrainer) {
        if(!new File(path).exists()) return null;
        File file = new File(path);

        try {
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));

            JSONObject jsonObject = new JSONObject(content);
            worttrainer.setWordPair(convertJSONArrayToArrayList(jsonObject.getJSONArray("wordCards")));
            worttrainer.setCurrentCard(jsonObject.getInt("currentCard"));
            worttrainer.setCorrectAnswers(jsonObject.getInt("correctAnswers"));
            worttrainer.setWrongAnswers(jsonObject.getInt("wrongAnswers"));
            worttrainer.setTries(jsonObject.getInt("tries"));

            return worttrainer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<Wortpaare> convertJSONArrayToArrayList(JSONArray array) {
        ArrayList<Wortpaare> wordpaare = new ArrayList<Wortpaare>();
        for(int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            wordpaare.add(new Wortpaare(jsonObject.getString("url"), jsonObject.getString("word")));
        }
        return wordpaare;
    }

}
