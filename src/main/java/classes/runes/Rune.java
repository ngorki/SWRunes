package classes.runes;

import classes.runes.stats.Stat;
import classes.runes.stats.Substat;
import com.fasterxml.jackson.databind.JsonNode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Rune {
    private long id;
    private RuneType type;
    private int slot;
    private int grade;
    private int stars;
    private int level;
    private Stat main;
    private Stat innate;
    private Substat[] subs;
    private boolean ancient;
    private long equipped;
    private long value;
    private long com2us_id;
    private double efficiency;
    private int score;
    private boolean useGrind = false;

    public Rune(){}

    public Rune(long id, RuneType type, int slot, Stat main, Stat innate, Substat[] subs, boolean ancient) {
        this.id = id;
        this.type = type;
        this.slot = slot;
        this.main = main;
        this.innate = innate;
        this.subs = subs;
        this.ancient = ancient;
    }

    public Rune(JsonNode runeNode){
        parseRune(runeNode);
    }

    public void parseRune(JsonNode runeNode){
        setType(RuneType.getRuneType(runeNode.get("set_id").asInt()));
        setSlot(runeNode.get("slot_no").asInt());
        setGrade(runeNode.get("extra").asInt());
        setStars(runeNode.get("class").asInt());
        setLevel(runeNode.get("upgrade_curr").asInt());
        setMain(runeNode.get("pri_eff"));
        setInnate(runeNode.get("prefix_eff"));
        setSubs(runeNode.get("sec_eff"));
        setEquipped(runeNode.get("occupied_id").asLong());
        setValue(runeNode.get("sell_value").asLong());
        setCom2us_id(runeNode.get("rune_id").asLong());
        setEfficiency();
        setScore();
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        if(stars > 10){
            stars -= 10;
            setAncient(true);
        }
        this.stars = stars;
    }

    public Stat getMain() {
        return main;
    }

    public void setMain(JsonNode statNode){
        this.main = new Stat(statNode);
    }

    public void setMain(Stat main) {
        this.main = main;
    }

    public long getCom2us_id() {
        return com2us_id;
    }

    public void setCom2us_id(long com2us_id) {
        this.com2us_id = com2us_id;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getEquipped() {
        return equipped;
    }

    public void setEquipped(long equipped) {
        this.equipped = equipped;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RuneType getType() {
        return type;
    }

    public void setType(RuneType type) {
        this.type = type;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Stat getInnate() {
        return innate;
    }

    public void setInnate(Stat innate) {
        this.innate = innate;
    }

    public void setInnate(JsonNode statNode){
        this.innate = new Stat(statNode);
    }

    public Substat[] getSubs() {
        return subs;
    }

    public void setSubs(Substat[] subs) {
        this.subs = subs;
    }

    public void setSubs(JsonNode subsNode){
        // subsNode contains a node with all substats as children
        int arrSize = 4;
        Substat[] newSubs = new Substat[arrSize];
        int i = 0;
        for(JsonNode substatNode : subsNode){
            // substatNode contains the info of one substat
            Substat sub_line = new Substat(substatNode);
            newSubs[i++] = sub_line;
        }
        this.subs = newSubs;
    }

    public boolean isAncient() {
        return ancient;
    }

    public void setAncient(boolean ancient) {
        this.ancient = ancient;
    }

    public void setEfficiency(){
        double sum = 1;
        if(innate.getStat() != null){
            sum += (double) innate.getValue() / (innate.getMaxValue() * 5);
        }

        for (Substat stat : subs){
            if(stat != null){
                if(useGrind)
                    sum += (double) (stat.getValue() + stat.getGrindValue()) / (stat.getMaxValue() * 5);
                else
                    sum += (double) stat.getValue() / (stat.getMaxValue() * 5);
            }
        }
        this.efficiency = sum/2.8 * 100;
    }

    public void setScore() {
        double hpPercent = 0, atkPercent = 0, defPercent = 0, acc = 0, res = 0;
        double spd = 0, critRate = 0, critDamage = 0, hp = 0, atk = 0, def = 0;

        if(innate.getStat() != null){
            switch (innate.getStat().getName()) {
                case "HP_PERCENT":
                    hpPercent += innate.getValue();
                    break;
                case "ATK_PERCENT":
                    atkPercent += innate.getValue();
                    break;
                case "DEF_PERCENT":
                    defPercent += innate.getValue();
                    break;
                case "ACCURACY":
                    acc += innate.getValue();
                    break;
                case "RESISTANCE":
                    res += innate.getValue();
                    break;
                case "SPD":
                    spd += innate.getValue();
                    break;
                case "CRIT_RATE":
                    critRate += innate.getValue();
                    break;
                case "CRIT_DMG":
                    critDamage += innate.getValue();
                    break;
                case "HP":
                    hp += innate.getValue();
                    break;
                case "ATK":
                    atk += innate.getValue();
                    break;
                case "DEF":
                    def += innate.getValue();
                    break;
            }
        }

        for (Substat sub : subs) {
            if (sub != null && sub.getStat() != null) {
                String statName = sub.getStat().getName();
                switch (statName) {
                    case "HP_PERCENT":
                        hpPercent += useGrind ? sub.getValue() + sub.getGrindValue() : sub.getValue();
                        break;
                    case "ATK_PERCENT":
                        atkPercent += useGrind ? sub.getValue() + sub.getGrindValue() : sub.getValue();
                        break;
                    case "DEF_PERCENT":
                        defPercent += useGrind ? sub.getValue() + sub.getGrindValue() : sub.getValue();
                        break;
                    case "ACCURACY":
                        acc += useGrind ? sub.getValue() + sub.getGrindValue() : sub.getValue();
                        break;
                    case "RESISTANCE":
                        res += useGrind ? sub.getValue() + sub.getGrindValue() : sub.getValue();
                        break;
                    case "SPD":
                        spd += useGrind ? sub.getValue() + sub.getGrindValue() : sub.getValue();
                        break;
                    case "CRIT_RATE":
                        critRate += useGrind ? sub.getValue() + sub.getGrindValue() : sub.getValue();
                        break;
                    case "CRIT_DMG":
                        critDamage += useGrind ? sub.getValue() + sub.getGrindValue() : sub.getValue();
                        break;
                    case "HP":
                        hp += useGrind ? sub.getValue() + sub.getGrindValue() : sub.getValue();
                        break;
                    case "ATK":
                        atk += useGrind ? sub.getValue() + sub.getGrindValue() : sub.getValue();
                        break;
                    case "DEF":
                        def += useGrind ? sub.getValue() + sub.getGrindValue() : sub.getValue();
                        break;
                }
            }
        }

        this.score = (int) Math.round(((hpPercent + atkPercent + defPercent + acc + res) / 40 +
                (spd + critRate) / 30 + critDamage / 35 +
                hp / 1875 * 0.35 + (atk + def) / 100 * 0.35) * 100);
    }

    public double getEfficiency(){
        return this.efficiency;
    }

    public boolean insertIntoDB(Connection conn){
        StringBuilder insertSQL = new StringBuilder("INSERT INTO Runes (type, slot, grade, stars, level, main_stat, main_value, innate_stat, innate_value, HP, HP_PERCENT, ATK, ATK_PERCENT, DEF, DEF_PERCENT, SPD, CRIT_RATE, CRIT_DMG, RESISTANCE, ACCURACY, ancient, equipped, value, com2us_id, efficiency, score");

        for (RuneArchetype archetype : Archetypes.archetypes) {
            insertSQL.append(", ").append(archetype.getName()).append("_Efficiency");
        }

        insertSQL.append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?");

        for (int i = 0; i < Archetypes.archetypes.size(); i++) {
            insertSQL.append(", ?");
        }

        insertSQL.append(");");

        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL.toString())) {
            preparedStatement.setString(1, type.name());
            preparedStatement.setInt(2, slot);
            preparedStatement.setInt(3, grade);
            preparedStatement.setInt(4, stars);
            preparedStatement.setInt(5, level);
            preparedStatement.setString(6, main.getStat().getName());
            preparedStatement.setInt(7, main.getValue());
            preparedStatement.setString(8, innate.getStat() != null ? innate.getStat().getName() : null);
            preparedStatement.setInt(9, innate.getValue());

            // Initialize all substat values to null
            for(int i = 10; i <= 20; i++) {
                preparedStatement.setNull(i, java.sql.Types.INTEGER);
            }

            // Set values for substats that exist
            for(Substat sub : subs) {
                if(sub != null && sub.getStat() != null) {
                    String statName = sub.getStat().getName();
                    int columnIndex = -1;
                    
                    // Map stat names to column indices
                    switch(statName) {
                        case "HP": columnIndex = 10; break;
                        case "HP_PERCENT": columnIndex = 11; break;
                        case "ATK": columnIndex = 12; break;
                        case "ATK_PERCENT": columnIndex = 13; break;
                        case "DEF": columnIndex = 14; break;
                        case "DEF_PERCENT": columnIndex = 15; break;
                        case "SPD": columnIndex = 16; break;
                        case "CRIT_RATE": columnIndex = 17; break;
                        case "CRIT_DMG": columnIndex = 18; break;
                        case "RESISTANCE": columnIndex = 19; break;
                        case "ACCURACY": columnIndex = 20; break;
                    }
                    
                    if(columnIndex != -1) {
                        preparedStatement.setInt(columnIndex, sub.getValue() + sub.getGrindValue());
                    }
                }
            }

            preparedStatement.setBoolean(21, ancient);
            preparedStatement.setLong(22, equipped);
            preparedStatement.setLong(23, value);
            preparedStatement.setLong(24, com2us_id);
            preparedStatement.setDouble(25, efficiency);
            preparedStatement.setInt(26, score);
            
            // Set archetype efficiencies
            int paramIndex = 27;
            for (RuneArchetype archetype : Archetypes.archetypes) {
                double archetypeEff = archetype.getArchetypeEff(this);
                preparedStatement.setDouble(paramIndex++, archetypeEff);
            }

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return false;
    }
}


