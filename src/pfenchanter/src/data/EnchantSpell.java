package pfenchanter.src.data;

import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.magic.Spell;

public enum EnchantSpell {

    LEVEL_1(Normal.LEVEL_1_ENCHANT),
    LEVEL_2(Normal.LEVEL_2_ENCHANT),
    LEVEL_3(Normal.LEVEL_3_ENCHANT),
    LEVEL_4(Normal.LEVEL_4_ENCHANT),
    LEVEL_5(Normal.LEVEL_5_ENCHANT),
    LEVEL_6(Normal.LEVEL_6_ENCHANT),
    LEVEL_7(Normal.LEVEL_7_ENCHANT);

    public Spell getSpell() {
        return spell;
    }

    private String spellName;

    private final Spell spell;

    EnchantSpell(Spell spell) {
        this.spell = spell;
    }
}
