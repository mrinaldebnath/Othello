package OTHELLO;

import java.util.Random;

public abstract class Game 
{
	Agent agent[]; // Array containing all the agents, here we only consider two player games.
	String name = "A Generic Game"; //A name for the Game object, it will be changed by the actual game class extending it
	int first;
	Random random = new Random();
        boolean draw;
	Agent winner = new Agent("winner") {
            @Override
            public void makeMove(Game game) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setAvailable(int rol, int dim) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setNew(OTHELLO oth) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            
        }; // The winning agent will be saved here after the game compeltes, if null the game is drawn.
	
	
	public Game(Agent a, Agent b) 
	{
		// TODO Auto-generated constructor stub
		agent = new Agent[2];
		agent[0] = a;
		agent[1] = b;
                winner.setRole(2);
                //System.out.println(" 1st winner role: "+winner.role+" agent[0]: "+agent[0].role+" agent[1]: "+agent[1].role);
		draw=false;
	}
	
	/**
	 * The actual game loop, each player takes turn.
	 * The first player is selected randomly
	 */
	public void play()
	{
                
		updateMessage("Starting " + name + " between "+ agent[0].name+ " and "+ agent[1].name+".");
                random = new Random(System.currentTimeMillis());
		int turn = random.nextInt(2);
                first=turn;
		int dimension=8;
		//System.out.println(agent[turn].name+ " makes the first move.");
		//initialize(false,dimension,dimension);
                set(agent[first].role);
                agent[0].setAvailable(agent[first].role, dimension);
                agent[1].setAvailable(agent[first].role, dimension);
                
                
		//showGameState();
                
		while(!isFinished())
		{
			updateMessage(agent[turn].name+ "'s turn. ");
			agent[turn].makeMove(this);
                        agent[(turn+1)%2].off(agent[turn].newx,agent[turn].newy );
                        agent[(turn+1)%2].setNew((OTHELLO)this);
                        
                        
			showGameState();
			
			turn = (turn+1)%2;
		}
		
               // System.out.println(" final winner role: "+winner.role+" agent[0]: "+agent[0].role+" agent[1]: "+agent[1].role);
                
		
		if(draw)	
			updateMessage("Game drawn!!");
                else updateMessage(winner.name+ " wins!!!");
		
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "";
		return str;
	}
	
	/**
	 * 
	 * @return Returns true if the game has finished. Also must update the winner member variable.
	 */
	abstract boolean isFinished();
	
	/**
	 * Initializes the game as needed. If the game starts with different initial configurations, it can be read from file.
	 * @param fromFile if true loads the initial state from file. 
	 */
	abstract void initialize(boolean fromFile,int r,int c);
	
	/**
	 * Prints the game state in console, or show it in the GUI
	 */
	abstract void showGameState();
	
	/**
	 * Shows game messages in console, or in the GUI
	 */
	abstract void updateMessage(String msg);
        abstract void set(int f);
}
