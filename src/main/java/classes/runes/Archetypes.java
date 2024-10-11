package classes.runes;

import java.util.ArrayList;
import java.util.Arrays;

import classes.runes.stats.StatType;
import static classes.runes.stats.StatType.*;

public class Archetypes {
    public static ArrayList<RuneArchetype> archetypes;

    static {
        ArrayList<StatType> allStatTypes = new ArrayList<>(Arrays.asList(StatType.values()));
        archetypes = new ArrayList<>();
        archetypes.add(new RuneArchetype("General", allStatTypes));
        archetypes.add(new RuneArchetype("DMG", HP_PCT, ATK_PCT, CRIT_RATE_PCT, CRIT_DMG_PCT, SPD));
    }

    public static ArrayList<RuneArchetype> getArchetypes() {
        return archetypes;
    }
}
