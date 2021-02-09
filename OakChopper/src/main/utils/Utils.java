package main.utils;

import main.Main;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.utilities.Timer;

/**
 * @author Clive on 9/25/2019
 */
public class Utils {
    private Main main;

    public Utils(Main main){
        this.main = main;
    }


    public int getHPPercent(){
        return (100* Skills.getBoostedLevels(Skill.HITPOINTS) / Skills.getRealLevel(Skill.HITPOINTS));
    }

    public void checkForMods() {
        if (!Players.all(f -> f != null && f.getName().contains("Mod")).isEmpty()) {
            MethodProvider.log("We just found a JMod! Switched worlds, quickly... Time: " + System.currentTimeMillis());
            main.shouldHopWorld = true;
            MethodProvider.sleep(Calculations.random(8000, 14000));
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
        if(!Players.all(player -> player != null && !player.equals(main.getLocalPlayer())&& main.getLocalPlayer().distance(player) < 2).isEmpty()){
            main.shouldHopWorld = true;
        }
    }

    public int getPlayersAround(){
        return Players.all(player -> player != null && !player.equals(main.getLocalPlayer()) && main.getLocalPlayer().distance(player) < 7).size();
    }
}
