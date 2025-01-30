package classes.runes;

import classes.runes.stats.Stat;
import classes.runes.stats.StatType;
import classes.runes.stats.Substat;

import java.util.ArrayList;
import java.util.Arrays;

public class RuneArchetype {
    private String name;
    private ArrayList<StatType> relatedStats;

    public String getName() {
        return name;
    }   

    public ArrayList<StatType> getRelatedStats() {
        return relatedStats;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRelatedStats(ArrayList<StatType> relatedStats) {
        this.relatedStats = relatedStats;
    }

    public RuneArchetype(String name, ArrayList<StatType> relatedStats) {
        this.name = name;
        this.relatedStats = relatedStats;
    }

    public RuneArchetype(String name, StatType... types){
        this.name = name;
        this.relatedStats = new ArrayList<>(Arrays.asList(types));
    }

    private boolean isRelevant(Stat stat){
        return this.relatedStats.contains(stat.getStat());
    }

    public double getArchetypeEff(Rune rune){
        double sum = 1;

        Stat innate = rune.getInnate();

        if(innate.getStat() != null && isRelevant(innate)){
            sum += (double) innate.getValue() / (innate.getMaxValue() * 5);
        }

        for (Substat stat : rune.getSubs()){
            if(stat != null && isRelevant(stat))
                sum += (double) (stat.getValue() + stat.getGrindValue()) / (stat.getMaxValue() * 5);
        }
        return sum/2.8 * 100;
    }

    public double getArchetypeEff(Rune rune, int type){
        double sum = 1;

        double replaceValue; // Value is max hero gem + grind stat% / stat% max
        if(type == 1){
            replaceValue = 20/40.0; //Legend
         } else if (type == 2){
            replaceValue = 23/40.0; // Max Legend
        } else {
            replaceValue = 18/40.0; // Max Hero
        }

        if(rune.getInnate().getStat() != null && isRelevant(rune.getInnate())){
            sum += (double) (rune.getInnate().getValue() - replaceValue) / (rune.getInnate().getMaxValue() * 5);
        }

        double minEffiency = Double.MAX_VALUE;
        boolean isEnchanted = false;
        for (Substat stat : rune.getSubs()){
            if(stat != null ){
                double substatEfficiency = 0;
                if(isRelevant(stat)){
                    substatEfficiency = (double) (stat.getValue() + stat.getGrindValue()) / (stat.getMaxValue() * 5);
                    sum += substatEfficiency;
                }
                if(stat.isEnchanted()){
                    minEffiency = substatEfficiency;
                    isEnchanted = true;
                } else if(substatEfficiency < minEffiency && !isEnchanted){
                    minEffiency = substatEfficiency;
                }
            }
        }

        if(minEffiency != Double.MAX_VALUE){
            sum -= minEffiency;
        }
        sum += replaceValue;

        return sum/2.8 * 100;
    }
}
