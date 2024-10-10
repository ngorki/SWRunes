package classes.runes.stats;

import com.fasterxml.jackson.databind.JsonNode;

public class Stat {
    private StatType stat;
    private int value;


    public Stat(int id, int value){
        this.stat = StatType.getStatType(id);
        this.value = value;
    }

    public Stat(JsonNode statNode){
        this.stat = StatType.getStatType(statNode.get(0).asInt());
        this.value = statNode.get(1).asInt();
    }

    public StatType getStat() {
        return stat;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMaxValue(){
        return this.stat.getMaxValue();
    }
}
