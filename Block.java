
public class Block {
	
	int x = 3;
	int y = 0;
	int[][] blockShape;
	int shape;
	int rotation = 1;
	Block(int shape){
		
		this.shape = shape;
		
		switch(shape) {
			case 1:
				this.blockShape = new int[][] {
						{1, 1, 1, 1},
						{0, 0, 0, 0},
						{0, 0, 0, 0},
						{0, 0, 0, 0}
				};
				break;
		}
		
	}
	
	public void rotateFallingBlock(){
		switch(shape) {
			case  1:
				switch(rotation) {
					case 1:
						this.blockShape = new int[][] {
							{0, 0, 1, 0},
							{0, 0, 1, 0},
							{0, 0, 1, 0},
							{0, 0, 1, 0}
						};
						rotation++;
						break;	
					case 2:
						this.blockShape = new int[][] {
							{0, 0, 0, 0},
							{0, 0, 0, 0},
							{1, 1, 1, 1},
							{0, 0, 0, 0}
						};
						rotation++;
						break;	
					case 3:
						this.blockShape = new int[][] {
							{0, 1, 0, 0},
							{0, 1, 0, 0},
							{0, 1, 0, 0},
							{0, 1, 0, 0}
						};
						rotation++;
						break;	
					case 4:
						this.blockShape = new int[][] {
							{0, 0, 0, 0},
							{1, 1, 1, 1},
							{0, 0, 0, 0},
							{0, 0, 0, 0}
						};
						rotation = 1;
						break;	
				}
				break;
			
		}
	}
}
