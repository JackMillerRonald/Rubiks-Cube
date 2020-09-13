package GraphicsTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.JFrame;
import java.awt.Color;
import java.util.Iterator;

public class Drawing extends Canvas {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My Drawing");
        Drawing canvas = new Drawing();
        canvas.setSize(900, 900);
        int size = 3;
        int totalSize = size * size * 6;
        ArrayList<ArrayList<ArrayList<Color>>> cube = new ArrayList<ArrayList<ArrayList<Color>>>(6);

        canvas.initCube(cube);
        canvas.printCube(cube);
        canvas.moveRight(cube,0,0);
        canvas.printCube(cube);
        frame.add(canvas);
        Color bg = new Color(255, 255, 255);
        canvas.setBackground(bg);
        frame.pack();
        frame.setVisible(true);
    }
    //paint runs automatically
    public void paint(Graphics g) {
        //g.fillOval(100, 100, 200, 200);
        Rectangle rect = new Rectangle(350,700,200,200);
        ArrayList<ArrayList<ArrayList<Color>>> cube = new ArrayList<ArrayList<ArrayList<Color>>>(6);


        initCube(cube);
        drawCube(g, cube);

        mickey(g, rect);
    }
    
    public void initCube(ArrayList<ArrayList<ArrayList<Color>>> cube) {
    	Random rand = new Random(0);
    	ArrayList<Integer> colors = new ArrayList<Integer>(6);
    	for(int i=0; i<6; i++) {
    		colors.add(0);
    	}
    	
        for(int i=0; i<6; i++) {
        	ArrayList<ArrayList<Color>> side = new ArrayList<ArrayList<Color>>(3);
        	for(int j=0; j<3; j++) {
    			ArrayList<Color> row = new ArrayList<Color>(3);
        		for(int k=0; k<3; k++) {
        			int x = rand.nextInt(6);
            		Color color;
            		while(colors.get(x) >= 9) {
            			x = rand.nextInt(6);
            		}
            		if( x == 0) {
            			color = Color.RED; 
            		}else if( x == 1) {
            			color = Color.BLUE; 
            		}else if( x == 2) {
            			color = Color.GREEN; 
            		}else if( x == 3) {
            			color = Color.YELLOW; 
            		}else if( x == 4) {
            			color = Color.WHITE; 
            		}else {
            			color = Color.GRAY; 
            		}
            		colors.set(x,colors.get(x)+1);
            		row.add(color);
        		}
        		side.add(row);
        	}
        	cube.add(side);
        }
    }
    

    
    public void printCube(ArrayList<ArrayList<ArrayList<Color>>> cube) {
    	for(int i=0; i<cube.size(); i++) {
	        System.out.printf("Side %d\n",i);

    		for(int j=0; j<cube.get(i).size(); j++) {
    			for(int k=0; k<3; k++) {
        			System.out.format("%2d %s ", k, cube.get(i).get(j).get(k));
    			}
    			System.out.println();
    		}
	    }
    	System.out.println("");
    }
    
    public void moveRight(ArrayList<ArrayList<ArrayList<Color>>> cube, int side, int row) {
    	ArrayList<ArrayList<Color>> tiles = cube.get(3);
    	// need a temporary place holder to shift rows in cube
    	ArrayList<Color> tempRow = tiles.get(row);
    	
    	for(int i=3; i>=1; i--) {
    		cube.get(i).set(row, cube.get(i-1).get(row));
    	}
        cube.get(0).set(row, tempRow); 
        if(row == 0) {
        	rotateLeft(cube,4);
        }else if(row==3) {
        	rotateRight(cube,5);
        }
    }
    
    public void rotateRight(ArrayList<ArrayList<ArrayList<Color>>> cube, int side) {
    	ArrayList<ArrayList<Color>> tiles = cube.get(side);
    	ArrayList<ArrayList<Color>> transposedSide = transpose(tiles);
    	cube.set(side, transposedSide);
    	for(int i=0; i<tiles.size();i++) {
    		reverse(tiles.get(i));
    	}
    }
    public void rotateLeft(ArrayList<ArrayList<ArrayList<Color>>> cube, int side) {
    	ArrayList<ArrayList<Color>> tiles = cube.get(side);
    	for(int i=0; i<tiles.size();i++) {
    		reverse(tiles.get(i));
    	}
    	ArrayList<ArrayList<Color>> transposedSide = transpose(tiles);
    	cube.set(side, transposedSide);

    }
    
    public void reverse(ArrayList<Color> row) {
    	Color temp = row.get(0);
    	row.set(0, row.get(2));
    	row.set(2, temp);
    }
    public void flip(ArrayList<ArrayList<Color>> tiles) {
    	ArrayList<Color> temp = tiles.get(0);
    	tiles.set(0, tiles.get(2));
    	tiles.set(2, temp);	
    }
    public void moveLeft(ArrayList<ArrayList<ArrayList<Color>>> cube, int side, int row) {
    	ArrayList<ArrayList<Color>> tiles = cube.get(0);
    	// need a temporary place holder to shift rows in cube
    	ArrayList<Color> tempRow = tiles.get(row);
    	
    	for(int i=0; i<3; i++) {
    		cube.get(i).set(row, cube.get(i+1).get(row));
    	}
        cube.get(3).set(row, tempRow);        
    }

    public ArrayList<ArrayList<Color>> transpose(ArrayList<ArrayList<Color>> side){
    	ArrayList<ArrayList<Color>> result = new ArrayList<ArrayList<Color>>();
    	for(int i=0; i<side.size(); i++) {
    		ArrayList<Color> row = new ArrayList<Color>(3);
    		for(int j=0; j<side.get(i).size(); j++) {
    			row.add(side.get(j).get(i));
    		}
    		result.add(row);
    	}
    	return result;
    }

	public void boxOval(Graphics g, Rectangle bb) {
		g.drawRect(bb.x, bb.y, bb.width, bb.height);
	    g.fillOval(bb.x, bb.y, bb.width, bb.height);
	}
	
    public void drawCube( Graphics g, ArrayList<ArrayList<ArrayList<Color>>> cube) {
    	int size = 60;

    	int x = 0;
    	int y = size*4;
    	for(int i=0; i<3; i++) {
    		for(int j = 0; j<12; j++) {
            	if( j % 3 == 0 && j != 0) {
            		x+=size;
            	}

            	Color zeColour = cube.get(i+j/3).get(i).get(j%3);

            	g.setColor(zeColour);
            	g.fillRect(x, y, size, size);
            	g.setColor(Color.BLACK);
            	g.drawRect(x, y, size, size);
            	x+=size;
    		}
    		y+=size;
    		x=0;
    	}
    	x = size*4+size*2;
    	y = size;
    	for(int i=0; i<3; i++) {
    		for(int j = 0; j<3; j++) {
    	    	int [] xCoords = {x, x+size, x+size*2, x+size};
    	    	int [] yCoords = {y+size, y+size,y,y};
            	g.setColor(cube.get(4).get(i).get(j%3));
    	    	g.fillPolygon(xCoords, yCoords, 4);
    	    	g.setColor(Color.BLACK);
    	    	g.drawPolygon(xCoords, yCoords, 4);
            	x+=size;
    		}
    		y+=size;
    		x-=size*4;
    	}
    	/*
    	x = size*4;
    	y = size;
    	for(int i=0; i<3; i++) {
    		for(int j = 0; j<3; j++) {
            	g.setColor(cube.get(4).get(i).get(j%3));
            	g.fillRect(x, y, size, size);
            	g.setColor(Color.BLACK);
            	g.drawRect(x, y, size, size);

            	x+=size;

    		}
    		y+=size;
    		x=size*4;
    	}
    	*/
    	x = size*4;
    	y = size*7;
    	for(int i=0; i<3; i++) {
    		for(int j = 0; j<3; j++) {
    			int [] xCoords = {x, x+size, x+size*2, x+size};
    	    	int [] yCoords = {y, y,y+size,y+size};
            	g.setColor(cube.get(4).get(i).get(j%3));
    	    	g.fillPolygon(xCoords, yCoords, 4);
    	    	g.setColor(Color.BLACK);
    	    	g.drawPolygon(xCoords, yCoords, 4);
            	x+=size;

    		}
    		y+=size;
    		x-=size*2;
    	}
    	
    	
    	
    	/*
    	x = size*4;
    	y = size*8;
    	for(int i=0; i<3; i++) {
    		for(int j = 0; j<3; j++) {
    			
            	g.setColor(cube.get(5).get(i).get(j%3));
            	g.fillRect(x, y, size, size);
            	g.setColor(Color.BLACK);
            	g.drawRect(x, y, size, size);

            	x+=size;

    		}
    		y+=size;
    		x=size*4;
    	}
    	*/

    }
    
	
	public void mickey(Graphics g, Rectangle bb) {
		//boxOval(g,bb);
	    Rectangle smaller = new Rectangle(bb.x+bb.width/8, bb.y+bb.height/4, bb.width-(bb.width/8)*2, bb.height-(bb.height/8)*2);

	    boxOval(g, smaller);
	
	    int dx = smaller.width / 2;
	    int dy = smaller.height / 2;
	    Rectangle half = new Rectangle(smaller.x, smaller.y, dx, dy);

	    half.translate(-dx / 2, -dy / 2);
	    boxOval(g, half);

	    half.translate(dx * 2, 0);
	    boxOval(g, half);

	}
}