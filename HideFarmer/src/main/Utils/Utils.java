package main.Utils;

import main.Main;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.utilities.Timer;

/**
 * @author Clive on 9/25/2019
 */
public class Utils {
    private Main main;

    public int hidesCollected = 0;

    public Utils(Main main){
        this.main = main;
    }


    public int getHPPercent(){
        return (100* main.getSkills().getBoostedLevels(Skill.HITPOINTS) / main.getSkills().getRealLevel(Skill.HITPOINTS));
    }

    public void checkForMods() {
        if (!main.getPlayers().all(f -> f != null && f.getName().contains("Mod")).isEmpty()) {
            MethodProvider.log("We just found a JMod! Switched worlds, quickly... Time: " + System.currentTimeMillis());
            main.shouldHopWorld = true;
            MethodProvider.sleep(Calculations.random(8000, 14000));
        }
    }

    public void checkPlayerMinimalStats() {
        if(main.getSkills().getRealLevel(Skill.ATTACK) >= 10 && main.getSkills().getRealLevel(Skill.STRENGTH) >= 10){
            switchCombat(3, 16, "Defense");
        }else if(main.getSkills().getRealLevel(Skill.ATTACK) >= 10 && main.getSkills().getRealLevel(Skill.STRENGTH) < 10){
            switchCombat(1, 9, "Strength");
        }
    }

    public boolean gotMinimalStats() {
        return main.getSkills().getRealLevel(Skill.ATTACK) >= 10
                && main.getSkills().getRealLevel(Skill.STRENGTH) >= 10
                && main.getSkills().getRealLevel(Skill.DEFENCE) >= 10;
    }

    public void switchCombat(int conf, int child, String name) {
        if (main.getPlayerSettings().getConfig(43) != conf) {
            MethodProvider.log("Switching combat: "+name);
            main.getTabs().open(Tab.COMBAT);
            MethodProvider.sleep(Calculations.random(600) + 600);

            if (main.getWidgets().getWidgetChild(593, child) != null) {
                main.getWidgets().getWidgetChild(593, child).interact();
                MethodProvider.sleep(1000,1200);
            }

            MethodProvider.sleep(Calculations.random(600) + 600);
            main.getTabs().open(Tab.INVENTORY);
        }
    }

    public String timerToString(Timer timer){
        long timeLeft = timer.remaining();
        long second = (timeLeft / 1000) % 60;
        long minute = (timeLeft / (1000 * 60)) % 60;
        long hour = (timeLeft / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public void checkPlayersAround() {
        if(main.getPlayers().all(player -> player != null && !player.equals(main.getLocalPlayer())&& main.getLocalPlayer().distance(player) < 7).size() >= 2){
            main.shouldHopWorld = true;
        }
    }

    public int getPlayersAround(){
        return main.getPlayers().all(player -> player != null && !player.equals(main.getLocalPlayer()) && main.getLocalPlayer().distance(player) < 7).size();
    }
}
