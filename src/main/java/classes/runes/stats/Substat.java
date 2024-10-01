package classes.runes.stats;

import com.fasterxml.jackson.databind.JsonNode;

public class Substat extends Stat{
    private boolean enchanted;
    private int grind_value;

    public Substat(JsonNode subNode){
        int[] stat_line = new int[4];
        for(int i = 0; subNode.has(i) && i < 4; i++){
            stat_line[i] = subNode.get(i).asInt();
        }
        super(stat_line[0], stat_line[1]);
        this.enchanted = stat_line[2] == 1;
        this.grind_value = stat_line[3];
    }
}
