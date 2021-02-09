package com.clivelewis.chatSpammer.nodes;

import com.clivelewis.chatSpammer.Main;
import com.clivelewis.chatSpammer.Node;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;

/**
 * @author Clive on 10/1/2019
 */
public class SpamNode extends Node {
    private final String FIRST = "cyan: Bet King - #1 RS Discord Gambling Community! Giveaways, Games, Sports!";
    private final String SECOND ="red:Discord[dot]gg/hav7k4k OR google \"BetKing Discord\"";
    private final String THIRD = "green:Join now and get free 100k on balance! <3";

    public SpamNode(Main main) {
        super(main);
    }

    @Override
    public boolean validate() {
        return main.currentSpamArea.getArea(10).contains(main.getLocalPlayer()) && !main.getKeyboard().isTyping();
    }

    @Override
    public int execute() {
        for(String message: main.messageList){
            main.getKeyboard().type(message);
            MethodProvider.sleep(1000);
        }


        return main.messageDelay;
    }
}
