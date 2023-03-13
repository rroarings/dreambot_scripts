package pfenchanter.src.data;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public enum Enchantable {

    SAPPHIRE_RING("Sapphire ring", "Ring of recoil"),
    SAPPHIRE_NECKLACE("Sapphire necklace", "Games necklace"),
    SAPPHIRE_BRACELET("Sapphire bracelet", "Bracelet of clay"),
    SAPPHIRE_AMULET("Sapphire amulet", "Amulet of magic"),
    OPAL_RING("Opal ring", "Ring of pursuit"),
    OPAL_BRACELET("Opal bracelet", "Expeditious bracelet"),
    OPAL_NECKLACE("Opal necklace", "Dodgy necklace"),
    OPAL_AMULET("Opal amulet", "Amulet of bounty"),
    EMERALD_RING("Emerald ring", "Ring of dueling"),
    EMERALD_NECKLACE("Emerald necklace", "Binding necklacee"),
    EMERALD_BRACELET("Emerald bracelet", "Castle wars bracelet"),
    EMERALD_AMULET("Emerald amulet", "Amulet of defence"),
    PRENATURE_AMULET("Pre-nature amulet", "Amulet of nature"),
    JADE_RING("Jade ring", "Ring of returning"),
    JADE_NECKLACE("Jade necklace", "Flamtaer bracelet"),
    JADE_BRACELET("Jade bracelet", "Necklace of passage"),
    JADE_AMULET("Jade amulet", "Amulet of chemistry"),
    RUBY_RING("Ruby ring", "Ring of forging"),
    RUBY_BRACELET("Ruby bracelet", "Inoculation bracelet"),
    RUBY_AMULET("Ruby amulet", "Amulet of strength"),
    RUBY_NECKLACE("Ruby necklace", "Digsite pendant"),
    TOPAZ_RING("Topaz ring", "Efaritay's aid"),
    TOPAZ_BRACELET("Topaz bracelet", "Bracelet of slaughter"),
    TOPAZ_NECKLACE("Topaz necklace", "Necklace of faith"),
    TOPAZ_AMULET("Topaz amulet", "Burning amulet"),
    DIAMOND_RING("Diamond ring", "Ring of life"),
    DIAMOND_NECKLACE("Diamond necklace", "Phoenix necklace"),
    DIAMOND_BRACELET("Diamond bracelet", "Abyssal bracelet"),
    DIAMOND_AMULET("Diamond amulet", "Amulet of power"),
    DRAGONSTONE_RING("Dragonstone ring", "Ring of wealth"),
    DRAGONSTONE_NECKLACE("Dragonstone necklace", "Skills necklace"),
    DRAGONSTONE_BRACELET("Dragonstone bracelet", "Combat bracelet"),
    DRAGONSTONE_AMULET("Dragonstone amulet", "Amulet of glory"),
    ONYX_RING("Onyx ring", "Ring of stone"),
    ONYX_NECKLACE("Onyx ring", "Berserker necklace"),
    ONYX_BRACELET("Onyx bracelet", "Regen bracelet"),
    ONYX_AMULET("Onyx amulet", "Amulet of fury"),
    ZENYTE_RING("Zenyte ring", "Ring of suffering"),
    ZENYTE_NECKLACE("Zenyte necklace", "Necklace of anguish"),
    ZENYTE_BRACELET("Zenyte bracelet", "Tormented bracelet"),
    ZENYTE_AMULET("Zenyte amulet", "Amulet of torture");

    private final String regularName;
    private final String enchantedName;

    public String getEnchantedName() {
        return enchantedName;
    }

    Enchantable(String name, String enchantedName) {
        this.regularName = name;
        this.enchantedName = enchantedName;
    }

    public String getRegularName() {
        return regularName;
    }

    public static Set<String> getAllItemsFor(EnchantSpell spell) {
        Set<String> result = null;
        switch (spell) {
            case LEVEL_1:
                result = Set.of(
                        SAPPHIRE_RING.regularName,
                        SAPPHIRE_NECKLACE.regularName,
                        SAPPHIRE_BRACELET.regularName,
                        SAPPHIRE_AMULET.regularName,
                        OPAL_RING.regularName,
                        OPAL_BRACELET.regularName,
                        OPAL_NECKLACE.regularName,
                        OPAL_AMULET.regularName);
                break;
            case LEVEL_2:
                result = Set.of(
                        EMERALD_RING.regularName,
                        EMERALD_NECKLACE.regularName,
                        EMERALD_BRACELET.regularName,
                        EMERALD_AMULET.regularName,
                        PRENATURE_AMULET.regularName,
                        JADE_RING.regularName,
                        JADE_NECKLACE.regularName,
                        JADE_BRACELET.regularName,
                        JADE_AMULET.regularName);
                break;
            case LEVEL_3:
                result = Set.of(
                        RUBY_RING.regularName,
                        RUBY_BRACELET.regularName,
                        RUBY_AMULET.regularName,
                        RUBY_NECKLACE.regularName,
                        TOPAZ_RING.regularName,
                        TOPAZ_BRACELET.regularName,
                        TOPAZ_NECKLACE.regularName,
                        TOPAZ_AMULET.regularName);
                break;
            case LEVEL_4:
                result = Set.of(
                        DIAMOND_RING.regularName,
                        DIAMOND_NECKLACE.regularName,
                        DIAMOND_BRACELET.regularName,
                        DIAMOND_AMULET.regularName);
                break;
            case LEVEL_5:
                result = Set.of(
                        DRAGONSTONE_RING.regularName,
                        DRAGONSTONE_NECKLACE.regularName,
                        DRAGONSTONE_BRACELET.regularName,
                        DRAGONSTONE_AMULET.regularName);
                break;
            case LEVEL_6:
                result = Set.of(
                        ONYX_RING.regularName,
                        ONYX_NECKLACE.regularName,
                        ONYX_BRACELET.regularName,
                        ONYX_AMULET.regularName);
                break;

            case LEVEL_7:
                result = Set.of(
                        ZENYTE_RING.regularName,
                        ZENYTE_NECKLACE.regularName,
                        ZENYTE_BRACELET.regularName,
                        ZENYTE_AMULET.regularName);
                break;
            default:

                break;
            }

                // Do nothing


        return result;
    }
}
