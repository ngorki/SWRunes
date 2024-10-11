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
            if(stat != null)
                sum += (double) (stat.getValue() + stat.getGrindValue()) / (stat.getMaxValue() * 5);
        }
        this.efficiency = sum/2.8 * 100;
    }

    public double getEfficiency(){
        return this.efficiency;
    }

    public boolean insertIntoDB(Connection conn){
        StringBuilder insertSQL = new StringBuilder("INSERT INTO Runes (type, slot, grade, stars, level, main_stat, main_value, innate_stat, innate_value, sub1_stat, sub1_value, sub2_stat, sub2_value, sub3_stat, sub3_value, sub4_stat, sub4_value, ancient, equipped, value, com2us_id, efficiency");

        for (RuneArchetype archetype : Archetypes.archetypes) {
            insertSQL.append(", ").append(archetype.getName()).append("_Efficiency");
        }

        insertSQL.append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?");

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
            for (int i = 0; i < 4; i++) {
                if (i < subs.length && subs[i] != null) {
                    preparedStatement.setString(10 + i * 2, subs[i].getStat().getName());
                    preparedStatement.setInt(11 + i * 2, subs[i].getValue() + subs[i].getGrindValue());
                } else {
                    preparedStatement.setNull(10 + i * 2, java.sql.Types.VARCHAR);
                    preparedStatement.setNull(11 + i * 2, java.sql.Types.INTEGER);
                }
            }
            preparedStatement.setBoolean(18, ancient);
            preparedStatement.setLong(19, equipped);
            preparedStatement.setLong(20, value);
            preparedStatement.setLong(21, com2us_id);
            preparedStatement.setDouble(22, efficiency);
            
            // Set archetype efficiencies
            int paramIndex = 23;
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


