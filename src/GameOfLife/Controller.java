
package GameOfLife;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer;

public class Controller {
    
    private Model model;
    private View view;
    private Timer timer;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        timer = new Timer(model.getSpeed(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerAction();
            }
        });

        ///////////////////// config box
        view.getConfigBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.out.println("Selected configuration: " + view.getConfigBox().getSelectedItem());

                if (view.getConfigBox().getSelectedItem().equals("Glider")) {
                    model.addGliderPattern(); //flyy
                } else if (view.getConfigBox().getSelectedItem().equals("Clear")) {
                    {
                        model.clearMap();
                    }
                } else if (view.getConfigBox().getSelectedItem().equals("R-Pentomino")) {
                    model.addRPentPattern();
                } else if (view.getConfigBox().getSelectedItem().equals("Small-Exploder")) {
                    model.addSmallExploderPattern(); // go boom
                } else if (view.getConfigBox().getSelectedItem().equals("Tumbler")) {
                    model.addTumblerPattern();
                } else if (view.getConfigBox().getSelectedItem().equals("Small-Spaceship")) {
                    model.addSmallSpaceshipPattern();
                }

            }
        });
        view.getConfigBox().setSelectedItem(view.getConfigs()[0]);

        /////////////////////// next button
        view.getNext().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Next pressed.");
                model.checkNeighbors(); // or basically simulate one generation
                view.updateMap();
                int generations = model.getGenerations() + 1;
                model.setGenerations(generations);
                view.getGensLabel().setText("Generations: " + model.getGenerations());
            }
        });
        /////////////////////// start/stop button
        view.getStart().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (view.getStart().getText().equals("Stop")) {
                    System.out.println("Stop pressed.");
                    view.getStart().setText("Start");
                    timer.stop();
                    // do stop action
                } else if (view.getStart().getText().equals("Start")) {
                    System.out.println("Start pressed.");
                    view.getStart().setText("Stop");
                    // do start action
                    timer.start();
                }
            }
        });

        /////////////////////////// speed menu
        view.getSpeedBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.out.println("Selected speed: " + view.getSpeedBox().getSelectedItem());
                String s = (String) view.getSpeedBox().getSelectedItem();
                if (s == "Slow") {
                    model.setSpeed(500);
                    timer.stop();
                    timer = new Timer(model.getSpeed(), new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            timerAction();

                        }
                    });
                    timer.start();
                }
                if (s == "Medium") {
                    model.setSpeed(300);
                    timer.stop();
                    timer = new Timer(model.getSpeed(), new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            timerAction();

                        }
                    });
                    timer.start();
                }
                if (s == "Fast") {
                    model.setSpeed(50);   
                    timer.stop();
                    timer = new Timer(model.getSpeed(), new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            timerAction();
                        }
                    });
                    timer.start();
                }
            }
        });
        /////////////////////////// selected zoom
        view.getZoomBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.out.println("Selected zoom: " + view.getZoomBox().getSelectedItem());
                //// UPDATE THE VIEW
                view.updateMap();
            }
        });

        /////////////////////////// Clicking on cells
        /*
        Clicking on a cell is supposed to give it life
        or kill it.. Cool
         */
        for (int i = 0; i < model.getD(); i++) {
            for (int j = 0; j < model.getD(); j++) {

                Cell c = model.getMap()[i][j];
                model.getMap()[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent event) {
                        c.setAlive(!c.alive);
                        c.updateCellColor();
                        System.out.println("\nX: " + c.x + " Y: " + c.y);

                        if (c.alive) {
                            model.getAliveCells().add(c);
                        } else if (c.alive == false) {
                            model.getAliveCells().remove(c);
                        }
                        c.repaint();

                        for (Cell x : model.getAliveCells()) {
                            System.out.println("X: " + x.x + " Y: " + x.y + " alive=" + x.alive);

                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        System.out.println(e.getComponent().getLocation());
                        model.setX1(e.getXOnScreen());
                        model.setY1(e.getYOnScreen());
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        model.setX2(e.getXOnScreen());
                        model.setY2(e.getYOnScreen());
                        int dx = model.getX2() - model.getX1();
                        int dy = model.getY2() - model.getY1();
                        model.setDx(dx);
                        model.setDy(dy);
                        view.updateMap();
                        System.out.println("released.\n dx = " + model.getDx() + " dy = " + model.getDy());
                    }
                });

            }
        }

    }

    public void timerAction() {
        model.checkNeighbors();
        view.updateMap();
        int newGenerations = model.getGenerations() + 1;
        model.setGenerations(newGenerations);
        view.getGensLabel().setText("Generations: " + model.getGenerations());
    }
}
