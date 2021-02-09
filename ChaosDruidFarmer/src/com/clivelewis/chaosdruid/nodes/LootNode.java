package com.clivelewis.chaosdruid.nodes;

import com.clivelewis.chaosdruid.Main;
import com.clivelewis.chaosdruid.Node;
import com.clivelewis.chaosdruid.utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.items.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author Clive on 10/20/2019
 */
public class LootNode extends Node {

    // Other.
    private static final String DRAGON_SPEAR = "Dragon spear";
    private static final String SHIELD_LEFT_HALF = "Shield left half";
    private static final String RUNE_SPEAR = "Rune spear";
    private static final String RUNE_JAVELIN = "Rune javelin";
    private static final String TOOTH_HALF_OF_KEY = "Tooth half of key";
    private static final String LOOP_HALF_OF_KEY = "Loop half of key";
    private static final String UNCUT_DIAMOND = "Uncut diamond";
    private static final String UNCUT_RUBY = "Uncut ruby";
    private static final String ENSOULED_HEAD = "Ensouled chaos druid head";
    private static final String MITHRIL_BOLTS = "Mithril bolts";
    private static final String LAW_RUNE = "Law rune";
    private static final String NATURE_RUNE = "Nature rune";
    private static final String LOOT_BAG = "Looting bag";

    // Herbs.
    private static final String HARRALANDER = "Grimy harralander";
    private static final String RANARR_WEED = "Grimy ranarr weed";
    private static final String AVANTOE = "Grimy avantoe";
    private static final String CADANTINE = "Grimy cadantine";
    private static final String LANTADYME = "Grimy lantadyme";
    private static final String KWUARM = "Grimy kwuarm";
    private static final String IRIT_LEAF = "Grimy irit leaf";
    private static final String DWARF_WEED = "Grimy dwarf weed";

    private static List<String> viableLoot = new ArrayList<>(Arrays.asList(LOOT_BAG, DRAGON_SPEAR, SHIELD_LEFT_HALF, RUNE_SPEAR,
            RUNE_JAVELIN, TOOTH_HALF_OF_KEY, LOOP_HALF_OF_KEY, UNCUT_DIAMOND, UNCUT_RUBY, ENSOULED_HEAD,
            MITHRIL_BOLTS, LAW_RUNE, NATURE_RUNE, HARRALANDER, RANARR_WEED, AVANTOE, CADANTINE, LANTADYME, KWUARM,
            IRIT_LEAF, DWARF_WEED));

    private static Filter<GroundItem> viableItems = item -> {
        if(item == null) return false;
        return viableLoot.contains(item.getName());
    };

    public LootNode(Main main) {
        super(main);
    }

    @Override
    public boolean validate() {
        return main.getGroundItems().closest(viableItems) != null &&
                main.getLocalPlayer().distance(main.getGroundItems().closest(viableItems)) < 5;
    }

    @Override
    public int execute() {
        main.currentState = STATE.LOOT;

        GroundItem closest = main.getGroundItems().closest(viableItems);
        if(closest != null){
            boolean take = closest.interact("Take");

            if(take){
                main.itemCounter++;
                if(closest.getName().equals(RANARR_WEED)){
                    main.ranarrCounter++;
                }
            }
        }



        return Calculations.random(1000, 2000);
    }
}
