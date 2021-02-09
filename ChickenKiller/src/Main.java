import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.GroundItem;

import java.awt.*;
import java.util.Random;

/**
 * @author Clive on 9/22/2019
 */
@ScriptManifest(author = "CliveLewis", category = Category.COMBAT, name = "Chicken Killer", version = 1.0)
public class Main extends AbstractScript {

    private final Area chickenField = new Area(3171, 3290, 3183, 3301);



    @Override
    public int onLoop() {

        checkPlayerStats();
        if(randomSleep()) return new Random().nextInt(30000) + 30000;
        if(checkBanking()) return new Random().nextInt(2000) + 1000;
        if(checkChickenField()) return new Random().nextInt(2000) + 1000;
        if(checkFeathers()) return new Random().nextInt(2000) + 1000;
        if(fightChickens()) return new Random().nextInt(2000) + 1000;

        return 5000;
    }



    private void checkPlayerStats() {
        if(gotNeededStats()){
            getTabs().logout();
            stop();
        }

        if(getSkills().getRealLevel(Skill.ATTACK) >= 20 && getSkills().getRealLevel(Skill.STRENGTH) >= 20){
            switchCombat(3, 593, 16, 0, "Defense");
        }else if(getSkills().getRealLevel(Skill.ATTACK) >= 20 && getSkills().getRealLevel(Skill.STRENGTH) < 20){
            switchCombat(1, 593, 9, 0, "Strength");
        }
    }

    private boolean gotNeededStats() {
        return getSkills().getRealLevel(Skill.ATTACK) >= 20
                && getSkills().getRealLevel(Skill.STRENGTH) >= 20
                && getSkills().getRealLevel(Skill.DEFENCE) >= 10
                && getSkills().getRealLevel(Skill.HITPOINTS) >= 20;
    }

    private boolean randomSleep() {
        int number = new Random().nextInt(300);
        if(number == 1){
            log("Random sleep!");
            getMouse().moveMouseOutsideScreen();
            return true;
        }

        return false;
    }

    private boolean checkBanking(){
        if(getInventory().isFull()){
            if(getBank().isOpen()){
                getBank().depositAllItems();
            }else{
                getBank().openClosest();
            }
            return true;
        }
        return false;
    }

    private boolean checkChickenField() {
        if(!getLocalPlayer().isInCombat() && !chickenField.contains(getLocalPlayer())){
            getWalking().walk(chickenField.getRandomTile());
            return true;
        }
        return false;
    }

    private boolean checkFeathers() {
        if(getLocalPlayer().isInCombat()) return false;

        if(getGroundItems().closest("Feather") != null && getLocalPlayer()
                .distance(getGroundItems().closest("Feather")) < 5){
            GroundItem feather = getGroundItems().closest("Feather");
            if(feather != null) feather.interact("Take");
            return true;
        }

        return false;
    }

    private boolean fightChickens() {
        if (getLocalPlayer().isInCombat()) {
            getMouse().move(new Point(getMouse().getX() + new Random().nextInt(2) - 1,
                    getMouse().getY() + new Random().nextInt(2) - 1));
            return false;
        }else {
            NPC chicken = getNpcs().closest("Chicken");
            if (chicken != null) {
                chicken.interact("Attack");
            }
            return true;
        }
    }

    private void switchCombat(int conf, int parent, int child, int gchild, String name) {
        if (getPlayerSettings().getConfig(43) != conf) {
            log("Switching combat: "+name);
            getTabs().open(Tab.COMBAT);
            sleep(Calculations.random(600) + 600);
            if (gchild > 0) {
                if (getWidgets().getWidgetChild(parent, child, gchild) != null) {
                    getWidgets().getWidgetChild(parent, child, gchild).interact();
                    sleep(1000,1200);
                }
            } else {
                if (getWidgets().getWidgetChild(parent, child) != null) {
                    getWidgets().getWidgetChild(parent, child).interact();
                    sleep(1000,1200);
                }
            }
            sleep(Calculations.random(600) + 600);
            getTabs().open(Tab.INVENTORY);
        }
    }
}
