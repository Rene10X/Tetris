import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener,KeyListener{
	
	// 10 * 20 grid
	
	int screenWidth = 400;
	int screenHeight = 800;
	int delay = 100;
	int fallingBlockSpeed = 500;
	boolean newBlockNeeded = true;
	int fallingBlockX = 3;
	int fallingBlockY = 0;
	boolean firstFrame = true;
	
	int[][] grid = new int[10][20] ;
	
	// I-Block = 1;
	int[] listOfBlocks = {1, 2, 3, 4, 5, 6, 7};
	
	Block fallingBlock;
	Random random = new Random();
	Timer timer = new Timer(delay, this);
	Timer timerFallBlock = new Timer(fallingBlockSpeed, new fallingBlockListener());

	GamePanel(){		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.addKeyListener(this);
		this.setFocusable(true);
		timer.start();
		timerFallBlock.start();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		draw(g);
	}

	private void draw(Graphics g) {
		
		if(!firstFrame) {
			drawBlocks(g);
			drawGrid(g);
		} else {
			firstFrame = false;
			if(newBlockNeeded){
				/*
				 * will uncomment when out of testing
				int newBlock = listOfBlocks[random.nextInt(7)];
				System.out.println(newBlock);
				*/
				int newBlock = 1;
				
				switch(newBlock) {
					case 1:
						fallingBlock = new Block(1);
				}
				
				newBlockNeeded = false;
			}
		}
				
		
	}


	private void drawBlocks(Graphics g) {
		//delete the previous
		if(fallingBlock.y >= 1) {
			for(int i = 0; i < 4; i++) {
				for(int j = 0; j < 4; j++) {
					if(fallingBlock.blockShape[i][j] == fallingBlock.shape) {
						grid[fallingBlock.x + i][fallingBlock.y + j - 1] = 0;
					}
					
				}
			}
		}
		
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				
				if(fallingBlock.blockShape[i][j] == fallingBlock.shape) {
					grid[fallingBlock.x + i][fallingBlock.y + j] = 8;
				}
				
				
			}
		}
		
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 20; j++) {
				if(grid[i][j] == 8) {
					g.setColor(Color.red);
					g.fillRect(i * 40, j * 40, 40, 40);
				}
			}
		}
		
	}

	private void drawGrid(Graphics g) {
		
		g.setColor(Color.white);
		g.drawRect(0, 1, screenWidth - 1, screenHeight - 2);
		
		for(int i = 40; i < screenWidth; i += 40) {
			g.setColor(Color.white);
			g.drawLine(i, 0, i, screenHeight);
			for(int j = 0; j < screenHeight; j += 40) {
				g.drawLine(0, j, screenWidth, j);
			}
		}
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {

		
		if(newBlockNeeded){
			/*
			 * will uncomment when out of testing
			int newBlock = listOfBlocks[random.nextInt(7)];
			System.out.println(newBlock);
			*/
			int newBlock = 1;
			
			switch(newBlock) {
				case 1:
					fallingBlock = new Block(1);
			}
			
			newBlockNeeded = false;
		}
		
		
				
		repaint();
	}
	
	private void checkCollision(int offsetX, int offsetY) {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				
				if(fallingBlock.blockShape[i][j] == fallingBlock.shape) {
					if(fallingBlock.y + j == 19 || fallingBlock.y == 19) {
						lockIn();
					}
					
					
					if(grid[fallingBlock.x + i + offsetX][fallingBlock.y + j + offsetY] != 8) {
						lockIn();
					}
					
					
				}
				
				
				
			}
		}
	}

	private void lockIn() {
		newBlockNeeded = true;
		
	}

	private class fallingBlockListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!newBlockNeeded) {
				moveFallingBlock();
				checkCollision(0, 1);
			}
			
			repaint();
			
		}
		
	}

	private void moveFallingBlock() {
		fallingBlock.y++;		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			//left arrow
			case 37:
				if(fallingBlock.x > 0) {
					clearFallingBlock();
					fallingBlock.x--;
				}
				break;
				
			//up arrow
			case 38:
				clearFallingBlock();
				fallingBlock.rotateFallingBlock();
				break;
			// right arrow
			case 39:
				if(fallingBlock.x <= 8) {
					clearFallingBlock();
					fallingBlock.x++;
				}				
				break;
			//down arrow
			case 40:
				clearFallingBlock();
				fallingBlock.y++;
				break;
		}
		
	}


	private void clearFallingBlock() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(fallingBlock.blockShape[i][j] == fallingBlock.shape) {
					grid[fallingBlock.x + i][fallingBlock.y + j] = 0;
				}
				
			}
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
