package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

// KeyListener: Tastatureingaben -> Richtung ändern
// JPanel: Zeichenbereich

// 3x Einen Bereich in neue Klasse auslagern
// 5x einzelne Methode verbessern / auslagern
// 100x Instanzvariablen lokal machen
// Sonarqube-Meldungen bearbeiten
public class GameviewAndKeyListener extends JPanel implements KeyListener
{
	
	private SnakeMovement snakeMovement;
	
    private ImageIcon rightmouth;
    private ImageIcon upmouth;
    private ImageIcon downmouth;
    private ImageIcon leftmouth;
    
    private ImageIcon snakeimage;
    
    private int[] enemyxpos= {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    private int[] enemyypos= {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
    private ImageIcon enemyimage;
    private Random random=new Random();
    private int xpos =random.nextInt(34);
    private int ypos=random.nextInt(23);
    
    private int score=0;
    private int moves = 0;
    
    private ImageIcon titleImage;
    
	public GameviewAndKeyListener()
	{
		addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        snakeMovement = new SnakeMovement(this);
	}
	@Override
	public void paint (Graphics g)
	{
		if(moves == 0)
        {
            snakeMovement.getSnakeXLength()[2] = 50; 
            snakeMovement.getSnakeXLength()[1] = 75;
            snakeMovement.getSnakeXLength()[0] = 100;
            
            snakeMovement.getSnakeYLength()[2] = 100;
            snakeMovement.getSnakeYLength()[1] = 100;
            snakeMovement.getSnakeYLength()[0] = 100;
            
        }
		//draw title image border
		g.setColor(Color.WHITE);
		g.drawRect(24, 10, 851, 51);
		
		//draw the title image
		titleImage= new ImageIcon("snaketitle.jpg");
		titleImage.paintIcon(this, g, 25, 11);
		
		//draw border for gameplay
		g.setColor(Color.WHITE);
		g.drawRect(24, 74, 851, 577);
		
		//draw background for the gameplay
		g.setColor(Color.black);
		g.fillRect(25, 75, 850, 575);
		
		
		//draw scores
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial",Font.PLAIN, 14));
		g.drawString("Scores: "+score, 780, 30);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial",Font.PLAIN, 14));
		g.drawString("Length: "+snakeMovement.getLengthOfSnake(), 780, 50);
		
		rightmouth = new ImageIcon("rightmouth.png");
        rightmouth.paintIcon(this, g, snakeMovement.getSnakeXLength()[0], snakeMovement.getSnakeYLength()[0]);
        
        for(int a = 0; a<snakeMovement.getLengthOfSnake(); a++)
        {
            if(a==0 && snakeMovement.isRight())
            {
                rightmouth = new ImageIcon("rightmouth.png");
                rightmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
            }
            
            if(a==0 && snakeMovement.isLeft())
            {
                leftmouth = new ImageIcon("leftmouth.png");
                leftmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
            }
            
            if(a==0 && down)
            {
                downmouth = new ImageIcon("downmouth.png");
                downmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
            }
            
            if(a==0 && up)
            {
                upmouth = new ImageIcon("upmouth.png");
                upmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
            }
        
            if(a!=0)
            {
                snakeimage = new ImageIcon("snakeimage.png");
                snakeimage.paintIcon(this, g, snakexlength[a], snakeylength[a]);
            }
                
        }
        
        enemyimage=new ImageIcon("enemy.png");
        
        if((enemyxpos[xpos]== snakexlength[0] && enemyypos[ypos]==snakeylength[0]))
        {
        	lengthofsnake++;
        	score++;
        	xpos=random.nextInt(34);
        	ypos=random.nextInt(23);
        }
        
        enemyimage.paintIcon(this, g, enemyxpos[xpos], enemyypos[ypos]);
        for(int b=1;b<lengthofsnake;b++)
        {
        	if(snakexlength[b]==snakexlength[0] && snakeylength[b]==snakeylength[0])
        	{
        		snakeMovement.setHeadDirection(Direction.NONE);
        		
        		g.setColor(Color.WHITE);
        		g.setFont(new Font("arial",Font.BOLD, 50));
        		g.drawString("GAME OVER", 300, 300);
        		
        		g.setFont(new Font("arial",Font.BOLD, 20));
        		g.drawString("Press Space to Restart", 350, 340);
        	}
        }
        
        g.dispose();
	}
	@Override
    public void keyTyped(KeyEvent ke) {
		// interface KeyListener needs this - we only use keyPressed
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
    	// TODO: Prüfen ob headDirection == NONE -> funktionale Änderung
    	// TODO: auf Switch ändern
    	if(e.getKeyCode()==KeyEvent.VK_SPACE)
    	{
    		moves=0;
    		score=0;
    		snakeMovement.setLengthOfSnake(SnakeMovement.START_SNAKE_LENGTH);
    		repaint();
    	}
    	if(e.getKeyCode()==KeyEvent.VK_RIGHT)
    	{
    		moves++;
    		if(snakeMovement.getHeadDirection() != Direction.LEFT)
    		{
    			snakeMovement.setHeadDirection(Direction.RIGHT);
    		}
    	}
    	if(e.getKeyCode()==KeyEvent.VK_LEFT)
    	{
    		moves++;
    		if(snakeMovement.getHeadDirection() != Direction.RIGHT)
    		{
    			snakeMovement.setHeadDirection(Direction.LEFT);
    		}
    	}
    	if(e.getKeyCode()== KeyEvent.VK_UP)
    	{
    		moves++;
    		if(snakeMovement.getHeadDirection() != Direction.DOWN)
    		{
    			snakeMovement.setHeadDirection(Direction.UP);
    		}
    	}
    	if(e.getKeyCode()== KeyEvent.VK_DOWN)
    	{
    		moves++;
    		if(snakeMovement.getHeadDirection() != Direction.UP)
    		{
    			snakeMovement.setHeadDirection(Direction.DOWN);
    		}
    	}
    }


    @Override
    public void keyReleased(KeyEvent ke) {
    }

}



















