package main.nodes;

import main.Main;
import main.Node;
import main.utils.STATE;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;

/**
 * @author Clive on 9/21/2019
 */
public class BankNode extends Node {

    public BankNode(Main main) {
        super(main);
    }

    @Override
    public boolean validate() {
        return Inventory.isFull();
    }

    @Override
    public int execute() {
        main.state = STATE.BANKING;

        if(Bank.isOpen()){
            Bank.depositAllExcept(item -> item != null && (item.getName().contains("axe")));
        }else{
           Bank.openClosest();
        }
        return Calculations.random(1000, 2500);
    }
}
