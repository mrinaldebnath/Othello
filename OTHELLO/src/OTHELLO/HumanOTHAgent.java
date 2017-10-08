package OTHELLO;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class HumanOTHAgent extends Agent{

    public HumanOTHAgent(String name) {
        super(name);
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
    public void makeMove(Game game) {
        if(hasValid==0)return;
        OTHELLO oth = (OTHELLO) game;
        OTHELLOGUI fg = oth.fg;
        fg.humanTurn = true;
        while(fg.humanTurn) {
            try {
                Thread.sleep(1000);
                if(fg.humanTurn)    continue;
                Pair cell=fg.clickedCell;
                int row=(int)cell.getKey();
                int col = (int)cell.getValue();
                if(!isValid(row, col, oth))
                {
                    oth.updateMessage("Invalid move");
                    fg.humanTurn=true;
                    continue;
                }
                if(isValid(row, col,oth))
                {
                    oth.board[row][col] = role;
                    available[row][col]=0;
                    newx=row;
                    newy=col;
                    oth.refresh(row, col, role);
                    fg.repaint();
                    
                }
                else
                {
                    oth.updateMessage("Invalid move");
                    fg.humanTurn=true;
                    continue;
                }
                
             
                break;
            } catch (InterruptedException ex) {
                Logger.getLogger(HumanOTHAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        fg.repaint();
        //System.out.println("clicked on ");
    }
    boolean isValid(int r,int c,OTHELLO g)    {
        return r>=0 && c>=0 && r<g.board[0].length && c<g.board[0].length && available[r][c]==1;
    }
    
    
}
