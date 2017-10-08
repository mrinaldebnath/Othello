package OTHELLO;

import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class OTHELLO extends Game{
    OTHELLOGUI fg;
    int[][] board;
    int[] table={4,-3,2,2,2,2,-3,4,
                -3,-4,-1,-1,-1,-1,-4,-3,
                2,-1,1,0,0,1,-1,2,
                2,-1,0,1,1,0,-1,2,
                2,-1,0,1,1,0,-1,2,
                2,-1,1,0,0,1,-1,2,
                -3,-4,-1,-1,-1,-1,-4,-3,
                4,-3,2,2,2,2,-3,4
                 };
    int ROW,COL;
    
    /**
     * 
     * 
     * @param a the first agent. a's role will be 1.
     * @param b the second agent. b's role will be -1.
     */
    public OTHELLO(Agent a, Agent b) {
        super(a, b);
        a.setRole(1);
        b.setRole(-1);
        name="Four In A Row";
        int dimension=8;
        this.initialize(false,dimension,dimension);
        fg = new OTHELLOGUI(board);
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                fg.setVisible(true);
            }
        });
    }
    
    public OTHELLO(Agent a, Agent b,int[][] b1) {
        super(a, b);
        a.setRole(1);
        b.setRole(-1);
        name="Four In A Row";
        int dimension=8;
        this.initialize(false,dimension,dimension);
        for(int i=0;i<b1.length;i++)
        {
            for(int j=0;j<b1.length;j++)board[i][j]=b1[i][j];
        }
        
    }
    
    /**
     *
     * @param b
     */
    
    public void set(int f)
    {
        board[3][3]=(-1)*f;
        board[4][4]=(-1)*f;
        board[3][4]=f;
        board[4][3]=f;
    }

    @Override
    boolean isFinished() {
//        Scanner sc=new Scanner(System.in);
//        for(int i=0;i<ROW;i++)  for(int j=0;j<COL;j++)  board[i][j]=sc.nextInt();
        
        if(isBoardFull() || (agent[0].hasValid==0 && agent[1].hasValid==0))
        {
            if(checkForWin()==0)draw=true;
            return true;
        }
        return false;
    }
    
    
    /**
     * Returns role of the winner (1 or -1), if no winner/ still game is going on 0.
     *
     * @return role of the winner (1 or -1), if no winner/ still game is going on 0.
     */
    int checkForWin()   {
        agent[0].total=0;
        agent[1].total=0;
        agent[0].sta=0;
        agent[1].sta=0;
        for(int i=0;i<ROW;i++)
        {
            for(int j=0;j<COL;j++)
            {
                if(board[i][j]==-1)
                {
                    agent[1].total++;
                    agent[1].sta+=table[i*8+j];
                }
                else if(board[i][j]==1)
                {
                    agent[0].total++;
                    agent[0].sta+=table[i*8+j];
                }
            }
        }
        if(agent[0].total>agent[1].total)
        {
            winner=agent[0];
            return 1;
        }
        else if(agent[0].total<agent[1].total)
        {
            winner=agent[1];
            return -1;
        }
        return 0;
    }
    
    
    boolean isBoardFull()   {
        for(int i=0;i<ROW;i++)  {
            for(int j=0;j<COL;j++)  if(board[i][j]==0)  return false;
        }
        return true;
    }
    
    
    @Override
    void initialize(boolean fromFile,int row,int col) {
        ROW=row;
        COL=col;
        board = new int[ROW][COL];
    }

    @Override
    void showGameState() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.


        System.out.println("-------------");

        for (int i = 0; i < board.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    System.out.print(" " + " | ");
                } else if (board[i][j] == -1) {
                    System.out.print("O | ");
                } else {
                    System.out.print("X | ");
                }
            }
            System.out.println();
            System.out.println("-------------");
        }
        System.out.println("finished: "+isFinished());
    }

    @Override
    void updateMessage(String msg) {
        this.fg.setLabelText(msg);
        System.out.println(msg);
    }
    
    
    void refresh(int row,int col,int role)
    {
        int minrole=(-1)*role;
            for(int j=col+1;j<COL;j++)  {
                if(board[row][j]==minrole)  {
                    
                    if(j==COL-1)
                    {
                        for(int k=col+1;k<j;k++)  {
                        board[row][k]=minrole;
                    } 
                        break;
                    }
                    
                    else board[row][j]=role;
                }
                else if(board[row][j]==role)break;
                else if(board[row][j]==0)   {
                    
                for(int k=col+1;k<j;k++)  {
                    board[row][k]=minrole;
                    } 
                break;
                }
                
            }
            
            for(int j=col-1;j>=0;j--)  {
                if(board[row][j]==minrole)  {
                    
                    if(j==0)
                    {
                        for(int k=col-1;k>j;k--)  {
                        board[row][k]=minrole;
                    } 
                        break;
                    }
                    
                    else board[row][j]=role;
                }
                else if(board[row][j]==role)break;
                else if(board[row][j]==0)   {
                    
                for(int k=col-1;k>j;k--)  {
                    board[row][k]=minrole;
                    } 
                break;
                }
                
            }
            
            for(int j=row+1;j<ROW;j++)  {
                if(board[j][col]==minrole)  {
                    if(j==ROW-1)
                    {
                        for(int k=row+1;k<j;k++)  {
                        board[k][col]=minrole;
                    } 
                        break;
                    }
                    
                    else board[j][col]=role;
                }
                else if(board[j][col]==role)break;
                
                else if(board[j][col]==0)   {
                    
                for(int k=row+1;k<j;k++)  {
                    board[k][col]=minrole;
                    } 
                break;
                }
                
            }
            
            for(int j=row-1;j>=0;j--)  {
                if(board[j][col]==minrole)  {
                    if(j==0)
                    {
                        for(int k=row-1;k>j;k--)  {
                        board[k][col]=minrole;
                    } 
                        break;
                    }
                    
                    else board[j][col]=role;
                }
                else if(board[j][col]==role)break;
                
                else if(board[j][col]==0)   {
                    
                for(int k=row-1;k>j;k--)  {
                    board[k][col]=minrole;
                    } 
                break;
                }
                
            }
        
            for(int j=1;j+row<ROW && j+col<COL;j++)   {
                if(board[row+j][col+j]==minrole)
                {
                    if(j+row==ROW-1 || j+col==COL-1)
                    {
                        for(int k=1;k<j;k++)  {
                        board[row+k][col+k]=minrole;
                    } 
                        break;
                    }
    
                    else board[row+j][col+j]=role;
                }
                else if(board[row+j][col+j]==role)break;  
                
                else if(board[row+j][col+j]==0)   {
                    
                for(int k=1;k<j;k++)  {
                        board[row+k][col+k]=minrole;
                    } 
                        break;
                }
                
            
        }
            
            for(int j=1;row-j>=0 && col-j>=0;j++)   {
                if(board[row-j][col-j]==minrole)
                {
                    if(row-j==0 || col-j==0)
                    {
                        for(int k=1;k<j;k++)  {
                        board[row-k][col-k]=minrole;
                    } 
                        break;
                    }
    
                    else board[row-j][col-j]=role;
                }
                else if(board[row-j][col-j]==role)break;  
                
                else if(board[row-j][col-j]==0)   {
                    
                for(int k=1;k<j;k++)  {
                        board[row-k][col-k]=minrole;
                    } 
                        break;
                }
                
            
        }
            
            for(int j=1;row-j>=0 && col+j<COL;j++)   {
                if(board[row-j][col+j]==minrole)
                {
                    if(row-j==0 || col+j==COL-1)
                    {
                        for(int k=1;k<j;k++)  {
                        board[row-k][col+k]=minrole;
                    } 
                        break;
                    }
    
                    else board[row-j][col+j]=role;
                }
                else if(board[row-j][col+j]==role)break;  
                
                else if(board[row-j][col+j]==0)   {
                    
                for(int k=1;k<j;k++)  {
                        board[row-k][col+k]=minrole;
                    } 
                        break;
                }
                
            
        }
         
            for(int j=1;row+j<ROW && col-j>=0;j++)   {
                if(board[row+j][col-j]==minrole)
                {
                    if(row+j==ROW-1 || col-j==0)
                    {
                        for(int k=1;k<j;k++)  {
                        board[row+k][col-k]=minrole;
                    } 
                        break;
                    }
    
                    else board[row+j][col-j]=role;
                }
                else if(board[row+j][col-j]==role)break;  
                
                else if(board[row+j][col-j]==0)   {
                    
                for(int k=1;k<j;k++)  {
                        board[row+k][col-k]=minrole;
                    } 
                        break;
                }
                
            
        }
        
    }
    
}
