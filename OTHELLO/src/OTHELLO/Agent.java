package OTHELLO;

public abstract class Agent 
{
	String name; // Name of the agent
	int role,total;
        int sta;
		
        int available[][];
        int dirx[]={0,1,0,-1,1,1,-1,-1};
        int diry[]={1,0,-1,0,1,-1,1,-1};
        int newx,newy;
        int hasValid;
	public Agent(String name) 
	{
		// TODO Auto-generated constructor stub
		this.name = name;
		
	}
	
	/**
	 * Sets the role of this agent. Typlically will be called by your extended Game class (The  class which extends the Game Class).
	 * @param role
	 */
	public void setRole(int role) {
		this.role = role;
                this.total=0;
                this.sta=0;
	}

	/**
	 * Implement this method to select a move, and change the game state according to the chosen move.
	 * @param game
	 */
	public abstract void makeMove(Game game);
	public abstract void setAvailable(int rol,int dim);
        public abstract void setNew(OTHELLO oth);
        public void off(int row,int col)
        {
            available[row][col]=0;
        }

}
