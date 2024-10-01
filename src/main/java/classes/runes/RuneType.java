package classes.runes;

public enum RuneType {
    UNDEFINED(0, "UNDEFINED", 0),
    ENERGY(1, "Energy", 2),
    FATAL(2, "Fatal", 4),
    BLADE(3, "Blade", 2),
    RAGE(4, "Rage", 4),
    SWIFT(5, "Swift", 4),
    FOCUS(6, "Focus", 2),
    GUARD(7, "Guard", 2),
    ENDURE(8, "Endure", 2),
    VIOLENT(9, "Violent", 4),
    WILL(10, "Will", 2),
    NEMESIS(11, "Nemesis", 2),
    SHIELD(12, "Shield", 2),
    REVENGE(13, "Revenge", 2),
    DESPAIR(14, "Despair", 4),
    VAMPIRE(15, "Vampire", 4),
    DESTROY(16, "Destroy", 2),
    FIGHT(17, "Fight", 2),
    DETERMINATION(18, "Determination", 2),
    ENHANCE(19, "Enhance", 2),
    ACCURACY(20, "Accuracy", 2),
    TOLERANCE(21, "Tolerance", 2),
    INTANGIBLE(22, "Intangible", 2),
    SEAL(23, "Seal", 2);

    private int com2us_id;
    private String name;
    private int set_count;

    RuneType(int com2us_id, String name, int set_count) {
        this.com2us_id = com2us_id;
        this.name = name;
        this.set_count = set_count;
    }

    public static RuneType getRuneType(int i){
        switch(i){
            case 1:
                return RuneType.ENERGY;
            case 2:
                return RuneType.FATAL;
            case 3:
                return RuneType.BLADE;
            case 4:
                return RuneType.RAGE;
            case 5:
                return RuneType.SWIFT;
            case 6:
                return RuneType.FOCUS;
            case 7:
                return RuneType.GUARD;
            case 8:
                return RuneType.ENDURE;
            case 9:
                return RuneType.VIOLENT;
            case 10:
                return RuneType.WILL;
            case 11:
                return RuneType.NEMESIS;
            case 12:
                return RuneType.SHIELD;
            case 13:
                return RuneType.REVENGE;
            case 14:
                return RuneType.DESPAIR;
            case 15:
                return RuneType.VAMPIRE;
            case 16:
                return RuneType.DESTROY;
            case 17:
                return RuneType.FIGHT;
            case 18:
                return RuneType.DETERMINATION;
            case 19:
                return RuneType.ENHANCE;
            case 20:
                return RuneType.ACCURACY;
            case 21:
                return RuneType.TOLERANCE;
            case 22:
                return RuneType.INTANGIBLE;
            case 23:
                return RuneType.SEAL;
            default:
                return UNDEFINED;
        }
    }
}
