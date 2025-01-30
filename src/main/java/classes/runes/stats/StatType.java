package classes.runes.stats;

public enum StatType {
    HP(1, "HP"),
    HP_PCT(2, "HP_PERCENT"), 
    ATK(3, "ATK"),
    ATK_PCT(4, "ATK_PERCENT"),
    DEF(5, "DEF"), 
    DEF_PCT(6, "DEF_PERCENT"),
    SPD(8, "SPD"),
    CRIT_RATE_PCT(9, "CRIT_RATE"),
    CRIT_DMG_PCT(10, "CRIT_DMG"),
    RESIST_PCT(11, "RESISTANCE"),
    ACCURACY_PCT(12, "ACCURACY"),
    SPD_PCT(13, "SPD_PERCENT");  // Swift rune set bonus only

    private final int id;
    private final String name;

    StatType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static StatType getStatType(int id) {
        return switch (id) {
            case 1 -> StatType.HP;
            case 2 -> StatType.HP_PCT;
            case 3 -> StatType.ATK;
            case 4 -> StatType.ATK_PCT;
            case 5 -> StatType.DEF;
            case 6 -> StatType.DEF_PCT;
            case 8 -> StatType.SPD;
            case 9 -> StatType.CRIT_RATE_PCT;
            case 10 -> StatType.CRIT_DMG_PCT;
            case 11 -> StatType.RESIST_PCT;
            case 12 -> StatType.ACCURACY_PCT;
            case 13 -> StatType.SPD_PCT;
            default -> null; // or an optional UNDEFINED enum if you want to handle unknown IDs
        };
    }

    public int getMaxValue() {
        // Set max_value based on the stat type
        return switch (this) {
            case HP -> 375;
            case HP_PCT, ATK_PCT, DEF_PCT, RESIST_PCT, ACCURACY_PCT -> 8;
            case ATK, DEF -> 20;
            case SPD, CRIT_RATE_PCT -> 6;
            case CRIT_DMG_PCT -> 7;
            default -> 0; // Or any default value if the stat type is undefined
        };
    }

}

