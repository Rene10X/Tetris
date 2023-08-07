import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	GamePanel panel;

	GameFrame(){
		panel = new GamePanel();
		
		this.setTitle("Tetris");
		this.add(panel);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
}
