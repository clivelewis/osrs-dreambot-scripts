package com.clivelewis.chatSpammer.nodes;

import com.clivelewis.chatSpammer.Main;
import com.clivelewis.chatSpammer.Node;
import com.clivelewis.chatSpammer.utils.Utils;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.randoms.RandomEvent;

import java.awt.*;
import java.net.InetAddress;
import java.util.Arrays;

import static org.dreambot.api.methods.MethodProvider.sleep;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;

/**
 * @author Clive on 10/1/2019
 */
public class AccountChangeNode extends Node {

    public AccountChangeNode(Main main) {
        super(main);
    }

    @Override
    public boolean validate() {
        return main.timeToChangeAccount || !Utils.isConnectedToInternet();
    }

    @Override
    public int execute() {

        while(!Utils.isConnectedToInternet()){
            MethodProvider.log("Waiting for internet connection....");
            sleep(5000);
        }

        main.getRandomManager().disableSolver(RandomEvent.LOGIN);
        main.getRandomManager().enableSolver(RandomEvent.LOGIN);

        if(main.getClient().isLoggedIn()){
            main.getTabs().logout();
            MethodProvider.sleep(2000);
        }



        main.getLoginUtility().login(main.getCurrentAccountUsername(), main.getCurrentAccountPassword());
        String[] responses = main.getLoginUtility().login(main.getCurrentAccountUsername(), main.getCurrentAccountPassword()).getResponses();

        MethodProvider.sleep(3000);
        MethodProvider.log(Arrays.toString(responses));

        if(responses[0].contains("disabled") || responses[0].contains("locked")){
            main.getMouse().move(new Point(380,330));
            MethodProvider.sleep(1000);
            main.getMouse().click();

            main.setNextAccount();
        }else{
            sleepUntil(() -> main.getClient().isLoggedIn(), 1000);
            main.setNextAccount();
            main.resetAccountChangeTimer();
            main.timeToChangeAccount = false;
        }


        return 3000;
    }

}

