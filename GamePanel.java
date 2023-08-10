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
	int previousY = 0;
	int newY = 0;
	boolean lockIn = false;
	
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
			newBlockNeeded();
			}
		}
				
		

	private void newBlockNeeded() {
		if(newBlockNeeded){
			
			
			int newBlock = listOfBlocks[random.nextInt(7)];
			System.out.println(newBlock);
			
			//int newBlock = 2;
			
			fallingBlock = new Block(newBlock);
			newBlockNeeded = false;
		}
		
	}

	private void drawBlocks(Graphics g) {
		
		
		
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 20; j++) {
				if(grid[i][j] == fallingBlock.shape) {
					g.setColor(Color.red);
					g.fillRect(i * 40, j * 40, 40, 40);
				}
			}
		}
		
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
		
		if(lockIn) {
			lockIn();
			lockIn = false;
		}
		
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 20; j++) {
				if(grid[i][j] == 1) {
					g.setColor(Color.red);
					g.fillRect(i * 40, j * 40, 40, 40);
				} else if(grid[i][j] == 8) {
					g.setColor(Color.green);
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
			
			newBlockNeeded();
		}
		
		checkForRows();
				
		repaint();
	}
	
	private void checkForRows() {
		
		for(int j = 0; j < 20; j++) {
			boolean found0 = false;
			
			for(int i = 0; i < 10; i++) {
				
				if(grid[i][j] == 0 || grid[i][j] == 8) {
					found0 = true;	
					break;
				} else if(i == 9 && !found0) {
					clearRow(j);
					
				}
			}
		}
		
	}

	private void clearRow(int row) {
		for(int i = 0; i < 10; i++) {
			grid[i][row] = 0;
		}
		
	}

	private void lockIn() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(fallingBlock.blockShape[i][j] == fallingBlock.shape) {
					grid[fallingBlock.x + i][fallingBlock.y + j] = 1;
					
				}
			}
		}
		newBlockNeeded = true;		
	}

	private class fallingBlockListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!newBlockNeeded) {
				if(isNextMoveColliding()) {
					lockIn = true;
					//lockIn();
				}else {
					moveFallingBlock();
				}
				
				//checkCollision();
			}
						
			repaint();
			
		}

		private boolean isNextMoveColliding() {
			for(int i = 0; i < 4; i++) {
				for(int j = 0; j < 4; j++) {
					
					int add1ToY = 1;
					
					if(fallingBlock.blockShape[i][j] == fallingBlock.shape) {
						if(fallingBlock.y + j >= 19) {
							add1ToY = 0;
						}
						if((grid[fallingBlock.x + i][fallingBlock.y + j + add1ToY] != 0 && grid[fallingBlock.x + i][fallingBlock.y + j + add1ToY] != 8)) {
							return true;
						}
						// bottom check
						if(fallingBlock.shape == 1) {
							if((fallingBlock.rotation == 1 || fallingBlock.rotation == 3) && fallingBlock.y + j == 19 ||
									(fallingBlock.rotation == 2 || fallingBlock.rotation == 4) && fallingBlock.y + j == 20) {
										return true;
									}
						} else {
							if(fallingBlock.y + j == 19) {
										return true;
									}
						}
					}
					
					
					
					
				}
			}
			
			return false;
			
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
		if(fallingBlock.y < 19) {
			
			boolean exit = false;
			switch(e.getKeyCode()) {
				//left arrow
				case 37:
					
					
					for(int i = 0; i < 4; i++) {
						if(!exit) {
							for(int j = 0; j < 4; j++) {
								
								if(fallingBlock.blockShape[i][j] == fallingBlock.shape) {
									if(fallingBlock.x + i >= 1 && fallingBlock.x >= 1) {
										
										clearFallingBlock();
										fallingBlock.x--;
										exit = true;
										break;
										
									}
								}
								
								
							}
						}
						
					}
					
					break;
					
				//up arrow
				case 38:
					clearFallingBlock();
					fallingBlock.rotateFallingBlock();
					break;
				// right arrow
				case 39:
					
						
						for(int i = 0; i < 4; i++) {
							if(!exit) {
								for(int j = 0; j < 4; j++) {
																		
									if(fallingBlock.blockShape[i][j] == fallingBlock.shape) {
										if(fallingBlock.shape == 6) {
											if(fallingBlock.x + i <= 6 && fallingBlock.x <= 6) {											
												clearFallingBlock();
												fallingBlock.x++;
												exit = true;
												break;
											}
										} else {
											if(fallingBlock.x + i <= 7 && fallingBlock.x <= 7) {											
												clearFallingBlock();
												fallingBlock.x++;
												exit = true;
												break;
											}
										}
										
									}
									
									
								}
							}
							
						}
										
									
					break;
				//down arrow
				case 40:
					
					if(fallingBlock.y <= 15) {
						clearFallingBlock();
						fallingBlock.y++;
					}				
					break;
			}
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
