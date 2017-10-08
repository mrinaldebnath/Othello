package OTHELLO;

import OTHELLO.Agent;

public class Solver {

   
    public static void main(String[] args) {

        Agent human = new AlphaBetaOTHAgent("BOT_1");
        //Agent human = new MinimaxTTTAgent("007");
        Agent machine = new AlphaBetaOTHAgent("BOT_2");

        //System.out.println(human.name+" vs. "+machine.name);
        Game game = new OTHELLO(human, machine);
        game.play();

    }

}
