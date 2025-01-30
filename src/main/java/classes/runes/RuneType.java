package classes.runes;

public enum RuneType {
    UNDEFINED(0, "UNDEFINED", 0),
    ENERGY(1, "Energy", 2),
    GUARD(2, "Guard", 2),
    SWIFT(3, "Swift", 4),
    BLADE(4, "Blade", 2),
    RAGE(5, "Rage", 4),
    FOCUS(6, "Focus", 2),
    ENDURE(7, "Endure", 2),
    FATAL(8, "Fatal", 4),
    DESPAIR(10, "Despair", 4),
    VAMPIRE(11, "Vampire", 4),
    VIOLENT(13, "Violent", 4),
    NEMESIS(14, "Nemesis", 2),
    WILL(15, "Will", 2),
    SHIELD(16, "Shield", 2),
    REVENGE(17, "Revenge", 2),
    DESTROY(18, "Destroy", 2),
    FIGHT(19, "Fight", 2),
    DETERMINATION(20, "Determination", 2),
    ENHANCE(21, "Enhance", 2),
    ACCURACY(22, "Accuracy", 2),
    TOLERANCE(23, "Tolerance", 2),
    SEAL(24, "Seal", 2),
    INTANGIBLE(25, "Intangible", 2);

    private int com2us_id;
    private String name;
    private int set_count;

    RuneType(int com2us_id, String name, int set_count) {
        this.com2us_id = com2us_id;
        this.name = name;
        this.set_count = set_count;
    }

    public int getCom2usId() {
        return com2us_id;
    }

    public String getName() {
        return name;
    }

    public int getSetCount() {
        return set_count;
    }

    public static RuneType getRuneType(int i){
        switch(i){
            case 1:
                return RuneType.ENERGY;
            case 2:
                return RuneType.GUARD;
            case 3:
                return RuneType.SWIFT;
            case 4:
                return RuneType.BLADE;
            case 5:
                return RuneType.RAGE;
            case 6:
                return RuneType.FOCUS;
            case 7:
                return RuneType.ENDURE;
            case 8:
                return RuneType.FATAL;
            case 10:
                return RuneType.DESPAIR;
            case 11:
                return RuneType.VAMPIRE;
            case 13:
                return RuneType.VIOLENT;
            case 14:
                return RuneType.NEMESIS;
            case 15:
                return RuneType.WILL;
            case 16:
                return RuneType.SHIELD;
            case 17:
                return RuneType.REVENGE;
            case 18:
                return RuneType.DESTROY;
            case 19:
                return RuneType.FIGHT;
            case 20:
                return RuneType.DETERMINATION;
            case 21:
                return RuneType.ENHANCE;
            case 22:
                return RuneType.ACCURACY;
            case 23:
                return RuneType.TOLERANCE;
            case 24:
                return RuneType.SEAL;
            case 25:
                return RuneType.INTANGIBLE;
            default:
                return UNDEFINED;
        }
    }
}
