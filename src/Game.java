
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author mitrm7692
 */
public class Game extends JComponent implements KeyListener, MouseMotionListener, MouseListener {

    // Height and Width of our game
    static final int WIDTH = 1275;
    static final int HEIGHT = 956;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    //Player position variables
    int x = 100;
    int y = 500;
    //mouse variables
    int mouseX = 0;
    int mouseY = 0;
    boolean buttonPressed = false;
    //player variables
    Rectangle player = new Rectangle(10, 200, 25, 50);
    int moveX = 0;
    int moveY = 0;
    //int for the cameras x axis
    int camx = -50;
    //inAir is equal to false because player starts on ground
    boolean inAir = false;
    //gravity variables
    int gravity = 1;
    int gravBlock = gravity - 2;
    int frameCount = 0;
    //keyboard variables
    boolean up = false;
    boolean down = false;
    boolean right = false;
    boolean left = false;
    boolean jump = false;
    boolean prevJump = jump;
    boolean enter = false;
    //int for picture being displayed on screen
    int screen = 0;
    //block array
    ArrayList<Rectangle> blocks = new ArrayList<>();
    //images
    BufferedImage initialJ = loadImage("InitialJump.png");
    BufferedImage secJump = loadImage("secJump.png");
    BufferedImage finalJ = loadImage("finalJ.png");
    BufferedImage InitialJL = loadImage("InitialJL.png");
    BufferedImage secJL = loadImage("secJL.png");
    BufferedImage finalJL = loadImage("finalJL.png");
    BufferedImage tile = loadImage("tile.png");
    BufferedImage backG = loadImage("mountain.jpg");
    BufferedImage startS = loadImage("Bhop_mountainzStart2.jpg");
    BufferedImage gameOvr = loadImage("GameOver.jpg");
    BufferedImage winner = loadImage("winner.jpg");

    //image importer
    public static BufferedImage loadImage(String name) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(name));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return img;
    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE
        //displays start screen
        if (screen == 0) {
            g.drawImage(startS, 0, 0, null);
            //if enter is pressed main game is displayed
            if (enter) {
                //Screen 1 is equal main game screen 
                screen = 1;
            }
            //else if the screen is equal to 2 the GameOver screen with appear
        } else if (screen == 2) {
            g.drawImage(gameOvr, 0, 0, null);
            //if enter is pressed screen 1 is shown
            if (enter) {
                screen = 1;
            }
        }else if(screen==3){
           g.drawImage(winner, 0, 0, null);
       }

        //if screen is equal to 1 main game is displayed
        if (screen == 1) {
            g.drawImage(backG, 0, 0, null);

            //sets colour to black
            g.setColor(Color.black);

            //loops array to move blocks on the x axis when player is moving
            for (Rectangle block : blocks) {
                g.fillRect(block.x - camx, block.y, block.width, block.height);
            }
            //sets colour to black
            g.setColor(Color.black);

            //Animation for when the player is not moving left or right but is jumping
            if (moveX == 0 && inAir) {
                if (moveX == 0 && moveY == 0 && !inAir) {
                    g.drawImage(initialJ, player.x - camx, player.y - 45, player.width + 55, player.height + 50, null);
                } else if (moveX == 0 && moveY <= -5 || moveY >= 5 && inAir) {
                    g.drawImage(secJump, player.x - camx, player.y - 45, player.width + 50, player.height + 50, null);
                } else if (moveX == 0 && moveY > -5 && moveY < 5 && inAir) {
                    g.drawImage(finalJ, player.x - camx, player.y - 45, player.width + 55, player.height + 50, null);
                }
            }
            //Picture for when the player is standing still
            if (!inAir && moveX == 0) {
                g.drawImage(initialJ, player.x - camx, player.y - 45, player.width + 55, player.height + 50, null);
            }
            //Animations for when player is moving to the left
            if (left) {
                if (moveY == 0 && !inAir) {
                    g.drawImage(InitialJL, player.x - camx, player.y - 45, player.width + 55, player.height + 50, null);
                } else if (moveY <= -5 || moveY >= 5 && inAir) {
                    g.drawImage(secJL, player.x - camx, player.y - 45, player.width + 50, player.height + 50, null);
                } else if (moveY > -5 && moveY < 5 && inAir) {
                    g.drawImage(finalJL, player.x - camx, player.y - 45, player.width + 55, player.height + 50, null);
                }
                //Animations for when player is moving to the right
            } else if (right) {
                if (moveY == 0 && !inAir) {
                    g.drawImage(initialJ, player.x - camx, player.y - 45, player.width + 55, player.height + 50, null);
                } else if (moveY <= -5 || moveY >= 5 && inAir) {
                    g.drawImage(secJump, player.x - camx, player.y - 45, player.width + 50, player.height + 50, null);
                } else if (moveY > -5 && moveY < 5 && inAir) {
                    g.drawImage(finalJ, player.x - camx, player.y - 45, player.width + 55, player.height + 50, null);
                }
            }
            //draw the tile image on block that is specified
            g.drawImage(tile, 0 - camx, 500, 50, 25, null);
            g.setColor(Color.black);
            g.fillRect(200 - camx, 500, 90, 30);
            g.fillRect(500 - camx, 500, 60, 30);
            g.drawImage(tile, 200 - camx, 500, 90, 30, null);
            //Randomly genetared blocks have texture
            for (Rectangle block : blocks) {
                g.drawImage(tile, block.x - camx, block.y, block.width, block.height, null);
            }
        }
        //if the character reaches a x of 0 or greater and the y position is between 800 and 900 the gameover screen with appear
        if (player.x >= 0 && player.y <= 900 && player.y >= 800) {
            screen = 2;
        }
        //if player reaches x of 2500 or greater and y of 500 winner screen appears
       if (player.x <= 2500 && player.y ==500) {
            screen = 3;
       }
    }
        // GAME DRAWING ENDS HERE
    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        //intial things to do before game starts
        //add blocks
        blocks.add(new Rectangle(0, 500, 50, 25));
        blocks.add(new Rectangle(200, 500, 90, 30));
        blocks.add(new Rectangle(500, 500, 60, 30));
        //END INTIAL THINGS TO DO

        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        blockGen();

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            x = mouseX;
            y = mouseY;

            //if A is being pressed the player moves left -5 pixels
            if (left) {
                moveX = -5;
                //if D is being pressed the player moves right 5 pixels
            } else if (right) {
                moveX = 5;
                //if player is standing still x is equal to 0
            } else {
                moveX = 0;
            }
            //Makes gravity between 199 and 291 on the x axis lower between 250 and 500 on the y axis
            if (player.x >= 199 && player.x <= 291 && player.y <= 500 && player.y >= 250) {
                moveY = moveY + gravBlock;
            }
            //if the players x is equal to or greater than 0 and the players y is between 750 and 900 the player is reset to starting position
            if (player.x >= 0 && player.y <= 900 && player.y >= 800) {
                player.x = 10;
                player.y = 475;
                camx = player.x - 50;
            }

            frameCount++;
            if (frameCount >= 1) {
                //gravity pulling player down
                moveY = moveY + gravity;
                frameCount = 0;
            }
            //jumping
            //jump being pressed and not being in air
            if (jump && prevJump == false && !inAir) {
                // make a big change in y direction
                moveY = -20;
                inAir = true;
            }

            //Prevjump is equal to jump when space bar is pressed
            prevJump = jump;

            //move the player
            player.x = player.x + moveX;
            player.y = player.y + moveY;
            camx = camx + moveX;

            //if feet of player become lower than the screen
            if (player.y > 906) {
                player.y = HEIGHT - player.height;
                moveY = 0;
                inAir = false;
            }
            //collision for blocks that are drawn
            for (Rectangle block : blocks) {
                //is the player hitting a block
                if (player.intersects(block)) {
                    //get the collision rectangle
                    Rectangle intersection = player.intersection(block);
                    if (intersection.width < intersection.height) {
                        if (player.x == block.x) {
                            player.x = player.x + intersection.width;
                            camx = camx + intersection.width;
                        }
                    } else {//fix the y
                        //hit the block with my head
                        if (player.y > block.y) {
                            player.y = player.y + intersection.height;
                            moveY = 0;
                        } else {
                            player.y = player.y - intersection.height;
                            moveY = 0;
                            inAir = false;
                        }
                    }
                }
            }
            //if left mouse button is pressed blocks show and have collision 

            //if player x is less than 0 player x equals 0 and the camera is -50 pixels behind
            if (player.x < 0) {
                player.x = 0;
                camx = -50;

            }
            //if the player y is less than 0 player y is equal to 0
            if (player.y < 0) {
                player.y = 0;
            }
            // GAME LOGIC ENDS HERE 

            // update the drawing (calls paintComponent)
            repaint();

            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if (deltaTime > desiredTime) {

                //took too much time, don't wait
            } else {
                try {
                    Thread.sleep(desiredTime - deltaTime);
                } catch (Exception e) {
                };
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("bhop_mountainz");

        // creates an instance of my game
        Game game = new Game();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(game);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);

        //add the listeners
        frame.addKeyListener(game); //keyboard
        game.addMouseListener(game); //Mouse
        game.addMouseMotionListener(game); //Mouse

        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            left = true;
        } else if (key == KeyEvent.VK_D) {
            right = true;
        } else if (key == KeyEvent.VK_SPACE) {
            jump = true;
        } else if (key == KeyEvent.VK_SPACE) {
            jump = true;
        } else if (key == KeyEvent.VK_ENTER) {
            enter = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            left = false;
        } else if (key == KeyEvent.VK_D) {
            right = false;
        } else if (key == KeyEvent.VK_SPACE) {
            jump = false;
        } else if (key == KeyEvent.VK_SPACE) {
            jump = false;
        } else if (key == KeyEvent.VK_ENTER) {
            enter = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            buttonPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            buttonPressed = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void blockGen() {
        for (int i = 0; i < 100; i++) {
            int rbX = (int) (Math.random() * (2500 - 400 + 1));
            blocks.add(new Rectangle(rbX + 100, 500, 30, 30));

        }

    }

}
