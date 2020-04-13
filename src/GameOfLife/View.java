/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameOfLife;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Nawra
 */
public class View {

    private Model model;
    private JFrame frame = new JFrame();
    private final String TITLE = "The Game of Life";
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int sizeX = (int) screenSize.getWidth();
    private final int sizeY = (int) screenSize.getHeight();
    private final JPanel centralPanel = new JPanel();
    private final JPanel southPanel = new JPanel();
    private final SouthPanel southPanel2 = new SouthPanel();
    private Thread t = new Thread(southPanel2);
    private final JComboBox configBox = new JComboBox();
    private final JButton next = new JButton("Next");
    private final JButton start = new JButton("Start");
    private final JComboBox speedBox = new JComboBox();
    private final JComboBox zoomBox = new JComboBox();
    private final String[] configs = {"Clear", "Glider", "R-Pentomino", "Small-Exploder", "Small-Spaceship", "Tumbler"};
    private final String[] zooms = {"Small", "Medium", "Large"};
    private final String[] speeds = {"Slow", "Medium", "Fast"};
    private GridLayout smallLayout = new GridLayout(50, 50);
    private GridLayout mediumLayout = new GridLayout(100, 100);
    private GridLayout largeLayout = new GridLayout(150, 150);
    private JLabel gensLabel = new JLabel();

    public View(Model model) {
        this.model = model;
        initFrame(frame);

        // set visible is the last thing
        frame.setVisible(true);
    }
    
    public JComboBox getConfigBox(){
        return this.configBox;
    }
    public JComboBox getSpeedBox(){
        return this.speedBox;
    }
    public JComboBox getZoomBox(){
        return this.zoomBox;
    }
    public String[] getConfigs(){
        return this.configs;
    }
    public JButton getNext(){
        return this.next;
    }
    public JButton getStart(){
        return this.start;
    }
    public JLabel getGensLabel(){
        return this.gensLabel;
    }

    /*
    This method must only be called once to initialze the frame
    The frame must not be updated through this method
     */
    public void initFrame(JFrame frame) {
        frame.setTitle(TITLE);
        frame.setSize(sizeX / 2, sizeY - 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        // adding the south panel to the south region
        t.start();
        try {

            t.sleep(3000); // threaded southpanel
        } catch (InterruptedException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        // adding the central panel, which will render the 
        // map for the first time
        addCentralPanel(frame, centralPanel); // to be called once
    }

    /*
    This method must only be called once. Any updates shouldnt
    be done by recalling this method.
     */
    private void addSouthPanel(JFrame frame, JPanel southPanel) {

        for (String x : configs) {
            configBox.addItem(x);
        }

        for (String x : speeds) {
            speedBox.addItem(x);
        }
        speedBox.setSelectedItem(speeds[1]);

        for (String x : zooms) {
            zoomBox.addItem(x);
        }
        zoomBox.setSelectedItem(zooms[0]);

        gensLabel.setText("Generations: " + model.getGenerations());
        southPanel.add(configBox);
        southPanel.add(next);
        southPanel.add(start);
        southPanel.add(speedBox);
        southPanel.add(zoomBox);
        southPanel.add(gensLabel);
        // and finally we add it to the south region
        frame.add(southPanel, BorderLayout.SOUTH); // border layout is the default for jframe

    }

    
    
    
    /*
    This method must only be called once. Any updates shouldnt
    be done by recalling this method.
     */
    public void addCentralPanel(JFrame frame, JPanel centralPanel) {
        frame.add(centralPanel);
        renderMap(model.getMap(), centralPanel, model.getDx(), model.getDy()); // to render the map
    }

    /*
    this method will be called multiple times to update 
    the display
     */
    public void renderMap(Cell[][] map, JPanel centralPanel, int dx, int dy) {
        centralPanel.removeAll();

        if (zoomBox.getSelectedItem().equals("Small")) {
            centralPanel.setLayout(smallLayout);
            for (int i = 226 + dy / (sizeX / 50); i <= 275 + dy / (sizeX / 50); i++) {
                for (int j = 226 + dx / (sizeY / 50); j <= 275 + dx / (sizeY / 50); j++) {
                    try {
                        centralPanel.add(map[i][j]);
                    } catch (Exception e) {
                    }
                }
            }
            centralPanel.revalidate();
            centralPanel.repaint();

        }
        if (zoomBox.getSelectedItem().equals("Medium")) {
            centralPanel.setLayout(mediumLayout);
            for (int i = 201 + dy / (sizeX / 100); i <= 300 + dy / (sizeX / 100); i++) {
                for (int j = 201 + dx / (sizeX / 100); j <= 300 + dx / (sizeX / 100); j++) {
                    centralPanel.add(map[i][j]);
                }
            }
            centralPanel.revalidate();
            centralPanel.repaint();

        }
        if (zoomBox.getSelectedItem().equals("Large")) {
            centralPanel.setLayout(largeLayout);
            for (int i = 176 + dy / (sizeX / 150); i <= 325 + dy / (sizeX / 150); i++) {
                for (int j = 176 + dx / (sizeX / 150); j <= 325 + dx / (sizeX / 150); j++) {
                    centralPanel.add(map[i][j]);
                }
            }
            centralPanel.revalidate();
            centralPanel.repaint();
        }

    }

    public void updateMap() {
        renderMap(model.getMap(), centralPanel, model.getDx(), model.getDy());
    }

    // inner class
    class SouthPanel extends JPanel implements Runnable {

        public SouthPanel() {

        }

        @Override
        public void run() {
            addSouthPanel(frame, this);
        }

    }
}

