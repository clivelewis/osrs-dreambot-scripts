package com.clivelewis.chaosdruid.nodes;

import com.clivelewis.chaosdruid.Main;
import com.clivelewis.chaosdruid.Node;
import com.clivelewis.chaosdruid.utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;

/**
 * @author Clive on 10/20/2019
 */
public class BankNode extends Node {
    private final Point LOOTING_BAG_BUTTON_POINT = new Point(575, 220);
    private final Point LOOTING_BAG_EXIT_POINT = new Point(725, 220);
    public BankNode(Main main) {
        super(main);
    }

    @Override
    public boolean validate() {
        return main.getInventory().isFull() && !main.isInsideDungeon;
    }

    @Override
    public int execute() {
        main.currentState = STATE.BANK;

        if(main.getBank().isOpen()){
            Item lootingBag = main.getInventory().get(item -> item.getName().equals("Looting bag"));
            if(lootingBag != null){
                lootingBag.interact("View");
                MethodProvider.sleep(3000);
                main.getMouse().click(LOOTING_BAG_BUTTON_POINT);
                MethodProvider.sleep(2000);
                main.getMouse().click(LOOTING_BAG_EXIT_POINT);
                MethodProvider.sleep(2000);
            }

            boolean allItemsDeposited = main.getBank().depositAllExcept(item -> item.getName().equals("Looting bag"));
            MethodProvider.sleepUntil(() -> allItemsDeposited, 15000);
        }else{
            main.getBank().openClosest();
        }
//        main.getMagic().castSpell(Normal.CAMELOT_TELEPORT);
//        MethodProvider.sleep(5000);
//        main.stop();
        return Calculations.random(2000, 5000);
    }
}
