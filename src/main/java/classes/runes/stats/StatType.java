package classes.runes.stats;

public enum StatType {
    STAT_HP(1, "HP"),
    STAT_HP_PCT(2, "HP %"),
    STAT_ATK(3, "ATK"),
    STAT_ATK_PCT(4, "ATK %"),
    STAT_DEF(5, "DEF"),
    STAT_DEF_PCT(6, "DEF %"),
    STAT_SPD(8, "SPD"),
    STAT_CRIT_RATE_PCT(9, "CRI Rate %"),
    STAT_CRIT_DMG_PCT(10, "CRI Dmg %"),
    STAT_RESIST_PCT(11, "Resistance %"),
    STAT_ACCURACY_PCT(12, "Accuracy %"),
    STAT_SPD_PCT(13, "SPD %");  // Swift rune set bonus only

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
            case 1 -> StatType.STAT_HP;
            case 2 -> StatType.STAT_HP_PCT;
            case 3 -> StatType.STAT_ATK;
            case 4 -> StatType.STAT_ATK_PCT;
            case 5 -> StatType.STAT_DEF;
            case 6 -> StatType.STAT_DEF_PCT;
            case 8 -> StatType.STAT_SPD;
            case 9 -> StatType.STAT_CRIT_RATE_PCT;
            case 10 -> StatType.STAT_CRIT_DMG_PCT;
            case 11 -> StatType.STAT_RESIST_PCT;
            case 12 -> StatType.STAT_ACCURACY_PCT;
            case 13 -> StatType.STAT_SPD_PCT;
            default -> null; // or an optional UNDEFINED enum if you want to handle unknown IDs
        };
    }

}

