import classes.runes.Rune;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        String path = "./json/Karueo.json";

        try {
            File jsonFile = new File(path);
            JsonNode jsonData = objectMapper.readTree(jsonFile);

            parseRunes(jsonData.get("runes"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Rune> parseRunes(JsonNode runeData){
        ArrayList<Rune> runeList = new ArrayList<Rune>();

        for(JsonNode runeNode :  runeData){
            Rune runeObj = new Rune(runeNode);
            runeList.add(runeObj);
        }

        return runeList;
    }
}
