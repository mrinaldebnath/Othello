/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OTHELLO;

import java.util.Random;

public class AlphaBetaOTHAgent extends Agent{
    int winnerPoint;
    int ROW,COL;
   
    public AlphaBetaOTHAgent(String name) {
        super(name);
        winnerPoint=10000000;
    }
    
   
    @Override
    public void setNew(OTHELLO oth)
    {
        hasValid=0;
        for(int i=0;i<oth.board.length;i++)
        {
            for(int j=0;j<oth.board.length;j++)
            {
                available[i][j]=0;
                if(oth.board[i][j]==0)
                {
                    int a=0;
                    
                    for(int k=0;k<8;k++)
                    {
                       if(i+dirx[k]>=0 && j+diry[k]>=0 && j+diry[k]<oth.board.length && i+dirx[k]<oth.board.length)
                {
                    setr(oth, i,j,a,k,i+dirx[k],j+diry[k]);
                }
                    }
                    
                }
            }
        }
        
    }
    
    public void setr(OTHELLO oth,int row,int col,int a,int k,int r,int c)
    {
        if(r<0 || c<0 || r>=oth.board.length || c>=oth.board.length)return;
        if(oth.board[r][c]==(-1)*role)setr(oth, row,col,1,k,r+dirx[k],c+diry[k]);
        else if(oth.board[r][c]==role)
        {
            if(a==1)
            {
                available[row][col]=1;
                hasValid++;
                //System.out.println("i: "+row+" j "+col+" "+hasValid);
            }
            return;
        }
        return;
    }
    
    
    @Override
    public void setAvailable(int rol,int dim)
    {
        hasValid=4;
        available=new int[dim][dim];
        if(rol==role)
        {
            available[2][3]=1;
            available[3][2]=1;
            available[4][5]=1;
            available[5][4]=1;
        }
        else
        {
            available[2][4]=1;
            available[4][2]=1;
            available[5][3]=1;
            available[3][5]=1;
        }
    }
    
    @Override
    public void makeMove(Game game) {
        // TODO Auto-generated method stub
        if(hasValid==0)return;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        OTHELLO othGame = (OTHELLO) game;
        ROW=othGame.ROW;
        COL=othGame.COL;
        CellValueTuple best = maxMove(othGame,2,Integer.MIN_VALUE,Integer.MAX_VALUE);
        System.out.println("best: "+best.row+" "+best.col);
        othGame.board[best.row][best.col] = role;
        available[best.row][best.col]=0;
        newx=best.row;
        newy=best.col;
        othGame.refresh(best.row, best.col, role);
        int own=0,other=0;
        for(int i=0;i<2;i++)
        {
            if(othGame.agent[i].role==this.role)
            {
                own=i;
                other=1-i;
                break;
            }
        }
        //othGame.agent[own].setNew(othGame);
        
        
        othGame.fg.setBoard(othGame.board);
        othGame.fg.repaint();
        //                        System.out.println(best.col+" "+best.utility);

    }

    private CellValueTuple maxMove(OTHELLO g, int depth,double alpha,double beta) {
        
        int own=0,other=0;
        for(int i=0;i<2;i++)
        {
            if(g.agent[i].role==this.role)
            {
                own=i;
                other=1-i;
                break;
            }
        }
        
        
        
        CellValueTuple maxCVT = new CellValueTuple();
        maxCVT.utility = Integer.MIN_VALUE;

        
        if (g.isFinished())
        {
           
            int winner = g.checkForWin();
            
            if (winner == g.agent[own].role) {
            maxCVT.utility = winnerPoint; //this agent wins
            return maxCVT;
        } else if (winner == -1*g.agent[own].role) {
            maxCVT.utility = -winnerPoint; //opponent wins
            return maxCVT;
        } else  {
            maxCVT.utility = 0; //draw
            return maxCVT;
        }
        }

        if(depth==0)    {
            return evaluate(g);
        }
//        for (int i = 0; i < game.board.length; i++) {

        if(g.agent[own].hasValid==0)
        {
            double v = minMove(g,depth-1,alpha,beta).utility;
            CellValueTuple mCVT = new CellValueTuple(-1,-1,v);
            return mCVT;
        }
        
        for (int col=0;col<g.board[0].length;col++)  {
            
            for (int row = 0; row < g.board.length; row++) {
                
               for(int i=0;i<2;i++)
                {
                    g.agent[i].setNew(g);
                    //if(i==0)System.out.println("bot:");
                } 
                
                if (g.agent[own].available[row][col] != 1) {
                    continue;
                }
                

                OTHELLO game=new OTHELLO(g.agent[0],g.agent[1],g.board);
                game.board[row][col] = g.agent[own].role;
                game.refresh(row, col, g.agent[own].role);
                
                
                for(int i=0;i<2;i++)
                {
                    game.agent[i].setNew(game);
                    //if(i==0)System.out.println("bot:");
                }
                
                
                double v = minMove(game,depth-1,alpha,beta).utility;
                if (v > maxCVT.utility) {
                    maxCVT.utility = v;
                   maxCVT.row = row;
                    maxCVT.col = col;
                }
                //game.board[row][col] = 0; // reverting back to original state
                if (v>=beta)    {
//                    System.out.println("pruning on depth"+depth);
                    return maxCVT;
                }
                alpha = Math.max(alpha, v);
//                break;
            }
        }
        return maxCVT;

    }
    
    
    private CellValueTuple minMove(OTHELLO g,int depth,double alpha,double beta) {
        
        int own=0,other=0;
        for(int i=0;i<2;i++)
        {
            if(g.agent[i].role==this.role)
            {
                own=i;
                other=1-i;
                break;
            }
        }
        
        
        
        CellValueTuple minCVT = new CellValueTuple();
        minCVT.utility = Integer.MAX_VALUE;
        
        
        if (g.isFinished())
        {
            int winner = g.checkForWin();
            
            if (winner == g.agent[own].role) {
            minCVT.utility = winnerPoint; //this agent wins
            return minCVT;
        } else if (winner == -1*g.agent[own].role) {
            minCVT.utility = -winnerPoint; //opponent wins
            return minCVT;
        } else  {
            minCVT.utility = 0; //draw
            return minCVT;
        }
        }
        
        
        

        if(depth==0)    {
            return evaluate(g);
        }
        
        if(g.agent[other].hasValid==0)
        {
            double v = maxMove(g,depth-1,alpha,beta).utility;
            CellValueTuple mCVT = new CellValueTuple(-1,-1,v);
            return mCVT;
        }
        
        for (int col=0; col<g.board[0].length; col++)    {
           for (int row = 0; row < g.board.length; row++) {
               
               for(int i=0;i<2;i++)
                {
                    g.agent[i].setNew(g);
                    //if(i==0)System.out.println("bot:");
                }
               
               
                if (g.agent[other].available[row][col] != 1) {
                    continue;
                }
                
                
                OTHELLO game=new OTHELLO(g.agent[0],g.agent[1],g.board);
                game.board[row][col] = minRole();
                game.refresh(row, col, minRole());
                
                
                //game.showGameState();
                //System.out.println("human:");
                
                for(int i=0;i<2;i++)
                {
                    game.agent[i].setNew(game);
                    //if(i==0)System.out.println("bot:");
                }
                              
                
                double v = maxMove(game,depth-1,alpha,beta).utility;
                if (v < minCVT.utility ) {
                    minCVT.utility = v;
                    minCVT.row = row;
                    minCVT.col = col;
                }
                //game.board[row][col] = 0;
                if(v<=alpha)    return minCVT;
                beta = Math.min(beta, v);
            }
        }
        return minCVT;

    }

    private int minRole() {
        if (role == -1) {
            return 1;
        } else {
            return -1;
        }
    }

    class CellValueTuple {

        int row, col;
        double utility;

        public CellValueTuple() {
            // TODO Auto-generated constructor stub
            row = -1;
            col = -1;
        }
        public CellValueTuple(int r,int c)  {
            row=r;
            col=c;
            utility=-1;
        }
        public CellValueTuple(int r,int c,double u)    {
            row=r;
            col=c;
            utility=u;
        }
    }
    
    private CellValueTuple evaluate(OTHELLO g)    {
        double ret=0;
        
        int own=0,other=0;
        for(int i=0;i<2;i++)
        {
            if(g.agent[i].role==this.role)
            {
                own=i;
                other=1-i;
                break;
            }
        }
        
        int tempRole=g.agent[own].role;
        
        for(int col=0;col<g.board[0].length;col++)   {
            for (int row = 0; row < g.board.length; row++) {
                
                for(int i=0;i<2;i++)
                {
                    g.agent[i].setNew(g);
                    //if(i==0)System.out.println("bot:");
                }
                
                
                if (g.agent[own].available[row][col] != 1) {
                    continue;
                }   
                
            OTHELLO game=new OTHELLO(g.agent[0],g.agent[1],g.board);
                game.board[row][col] = tempRole;
                game.refresh(row, col, tempRole);
                for(int i=0;i<2;i++)
                {
                    game.agent[i].setNew(game);
                }    
                
            if(game.isFinished())
            {
                int tempWinner = game.checkForWin();

            if(tempWinner==this.role)    return new CellValueTuple(row, col, winnerPoint);
            else if(tempWinner==minRole())  return new CellValueTuple(row, col, -winnerPoint);
            }
            }
    }  
        
        int y=g.checkForWin();
        double s;
        double mobility=(g.agent[own].hasValid-g.agent[other].hasValid)/(g.agent[own].hasValid+g.agent[other].hasValid);
        double pairity=(g.agent[own].total-g.agent[other].total)/(g.agent[own].total+g.agent[other].total);
        if((g.agent[own].sta+g.agent[other].sta)!=0) s=(g.agent[own].sta-g.agent[other].sta)/(g.agent[own].sta+g.agent[other].sta);
        else s=0;
        
        
        int m=0,p=0,sta=0;
        double fra=(g.agent[own].total+g.agent[other].total)/64;
        if(fra<=0.30)
        {
            sta=100;
            m=100;
            p=50;
        }
        else if(fra<=0.70)
        {
            sta=150;
            m=50;
            p=50;
        }
        else
        {
            p=200;
            sta=25;
            m=25;
        }
        
        ret=s*sta+m*mobility+p*pairity;
        return new CellValueTuple(0,0,ret);
    }
}
