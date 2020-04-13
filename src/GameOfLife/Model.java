/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameOfLife;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Nawra
 */
public class Model {

    private int generations = 0;
    private final int d = 500;
    private Cell[][] map = new Cell[d][d];
    private ArrayList<Cell> aliveCells = new ArrayList<Cell>();
    private int speed = 300;
    private int x1, x2, y1, y2, dx, dy;

    public int getD() {
        return d;
    } 
    
    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }

    public Cell[][] getMap(){
        return this.map;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public ArrayList<Cell> getAliveCells() {
        return aliveCells;
    }

    public void setAliveCells(ArrayList<Cell> aliveCells) {
        this.aliveCells = aliveCells;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }
    
    
    
    public Model() {
        createMapData();
    }

    public void createMapData() {
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                map[i][j] = new Cell(i, j);
                map[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }
    }

    public void checkNeighbors() {

        short NumberOfNeighbours = 0; //that are alive
        for (int i = 176; i <= 325; i++) {
            for (int j = 176; j <= 325; j++) {
                Cell c = map[i][j];
                c.aliveNeighbors = 0;
                //for most cases (middle of grid)

                if (map[c.x - 1][c.y - 1].isAlive()) {
                    c.aliveNeighbors += 1;
                }
                if (map[c.x][c.y - 1].isAlive()) {
                    c.aliveNeighbors += 1;
                }
                if (map[c.x + 1][c.y - 1].isAlive()) {
                    c.aliveNeighbors += 1;
                }
                if (map[c.x - 1][c.y].isAlive()) {
                    c.aliveNeighbors += 1;
                }
                if (map[c.x + 1][c.y].isAlive()) {
                    c.aliveNeighbors += 1;
                }
                if (map[c.x - 1][c.y + 1].isAlive()) {
                    c.aliveNeighbors += 1;
                }
                if (map[c.x][c.y + 1].isAlive()) {
                    c.aliveNeighbors += 1;
                }
                if (map[c.x + 1][c.y + 1].isAlive()) {
                    c.aliveNeighbors += 1;
                }

            }

        }
        for (int i = 176; i <= 325; i++) {
            for (int j = 176; j <= 325; j++) {
                Cell c = map[i][j];
                if (c.isAlive() == false && c.aliveNeighbors == 3) {
                    c.alive = true;
                    c.updateCellColor();
                } else if (c.isAlive() && c.aliveNeighbors < 2 || c.aliveNeighbors > 3) {
                    c.alive = false;
                    c.updateCellColor();

                } else if (c.isAlive() && c.aliveNeighbors == 2 || c.aliveNeighbors == 3) {
                    c.alive = true;
                    c.updateCellColor();

                }
            }
        }
    }

    //clear the map
    public void clearMap() {

        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                map[i][j].alive = false;
                map[i][j].aliveNeighbors = 0;
                map[i][j].updateCellColor();
            }
        }

    }

    /**
     * **** this section is to add shapes*******
     */
    public void addGliderPattern() {
        // we dont want to delete other existing shapes
        map[250][250].alive = false;
        map[251][250].alive = true;
        map[249][250].alive = false;
        map[250][249].alive = true;
        map[249][249].alive = false;
        map[251][249].alive = false;
        map[251][251].alive = true;
        map[250][251].alive = true;
        map[249][251].alive = true;
        map[250][250].updateCellColor();
        map[251][250].updateCellColor();
        map[249][250].updateCellColor();
        map[250][249].updateCellColor();
        map[249][249].updateCellColor();
        map[251][249].updateCellColor();
        map[251][251].updateCellColor();
        map[250][251].updateCellColor();
        map[249][251].updateCellColor();

    }

    public void addRPentPattern() {
        map[250][250].alive = true;
        map[251][250].alive = false;
        map[249][250].alive = true;
        map[250][249].alive = true;
        map[249][249].alive = false;
        map[251][249].alive = true;
        map[251][251].alive = false;
        map[250][251].alive = true;
        map[249][251].alive = false;
        map[250][250].updateCellColor();
        map[251][250].updateCellColor();
        map[249][250].updateCellColor();
        map[250][249].updateCellColor();
        map[249][249].updateCellColor();
        map[251][249].updateCellColor();
        map[251][251].updateCellColor();
        map[250][251].updateCellColor();
        map[249][251].updateCellColor();
    }

    public void addSmallExploderPattern() {
        map[250][250].alive = false;
        map[251][250].alive = true;
        map[249][250].alive = true;
        map[250][249].alive = true;
        map[249][249].alive = true;
        map[251][249].alive = true;
        map[251][251].alive = false;
        map[250][251].alive = true;
        map[249][251].alive = false;
        map[250][248].alive = true;
        map[249][248].alive = false;
        map[251][248].alive = false;
        map[250][250].updateCellColor();
        map[251][250].updateCellColor();
        map[249][250].updateCellColor();
        map[250][249].updateCellColor();
        map[251][249].updateCellColor();
        map[249][249].updateCellColor();
        map[251][251].updateCellColor();
        map[250][251].updateCellColor();
        map[249][251].updateCellColor();
        map[250][248].updateCellColor();
        map[249][248].updateCellColor();
        map[251][248].updateCellColor();

    }

    public void addTumblerPattern() {

        map[250][249].alive = true;
        map[250][251].alive = true;
        map[249][249].alive = true;
        map[248][249].alive = true;
        map[249][251].alive = true;
        map[248][251].alive = true;
        map[248][248].alive = true;
        map[249][248].alive = true;
        map[248][252].alive = true;
        map[249][252].alive = true;
        map[251][249].alive = true;
        map[252][249].alive = true;
        map[253][248].alive = true;
        map[253][247].alive = true;
        map[252][247].alive = true;
        map[251][247].alive = true;
        map[251][251].alive = true;
        map[252][251].alive = true;
        map[253][252].alive = true;
        map[253][253].alive = true;
        map[252][253].alive = true;
        map[251][253].alive = true;
        map[250][249].updateCellColor();
        map[250][251].updateCellColor();
        map[249][249].updateCellColor();
        map[248][249].updateCellColor();
        map[249][251].updateCellColor();
        map[248][251].updateCellColor();
        map[248][248].updateCellColor();
        map[249][248].updateCellColor();
        map[248][252].updateCellColor();
        map[249][252].updateCellColor();
        map[251][249].updateCellColor();
        map[252][249].updateCellColor();
        map[253][248].updateCellColor();
        map[253][247].updateCellColor();
        map[252][247].updateCellColor();
        map[251][247].updateCellColor();
        map[251][251].updateCellColor();
        map[252][251].updateCellColor();
        map[253][252].updateCellColor();
        map[253][253].updateCellColor();
        map[252][253].updateCellColor();
        map[251][253].updateCellColor();
    }

    public void addSmallSpaceshipPattern() {
        map[250][250].alive = true;
        map[248][250].alive = true;
        map[250][253].alive = true;
        map[249][254].alive = true;
        map[247][251].alive = true;
        map[247][252].alive = true;
        map[247][253].alive = true;
        map[247][254].alive = true;
        map[248][254].alive = true;
        map[250][250].updateCellColor();
        map[248][250].updateCellColor();
        map[250][253].updateCellColor();
        map[249][254].updateCellColor();
        map[247][251].updateCellColor();
        map[247][252].updateCellColor();
        map[247][253].updateCellColor();
        map[247][254].updateCellColor();
        map[248][254].updateCellColor();
    }
}

class Cell extends JPanel {

    public boolean alive = false;
    public int aliveNeighbors;
    public int x = 0;
    public int y = 0;
    public Cell[] neighbors = new Cell[8];

    public Cell(int a, int b) {
        x = a;
        y = b;
        updateCellColor();
    }

    public void updateCellColor() {
        if (alive) {
            this.setBackground(Color.CYAN);
        } else {
            this.setBackground(Color.GRAY);
        }
    }

    public int getCellX() {
        return x;
    }

    public int getCellY() {
        return y;
    }

    public void setCellX(int x) {
        this.x = x;
    }

    public void setCellY(int y) {
        this.y = y;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
