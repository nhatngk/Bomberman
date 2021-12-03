package com.bomberman.control;

import com.bomberman.Main;
import com.bomberman.constants.Const;
import com.bomberman.entities.Entity;
import com.bomberman.entities.mobileEntites.Player;
import com.bomberman.entities.mobileEntites.enemies.*;
import com.bomberman.entities.stacticEntities.Brick;
import com.bomberman.entities.stacticEntities.Grass;
import com.bomberman.entities.stacticEntities.Portal;
import com.bomberman.entities.stacticEntities.Wall;
import com.bomberman.entities.stacticEntities.items.*;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Map {
    static Scene scene;
    static Group root;
    static Canvas canvas;
    static GraphicsContext graphicsContext;
    private static boolean sceneStarted;
    static Player player;
    private static Font font = Font.loadFont(Main.class.getResourceAsStream("/Font/joystix monospace.ttf"), 18);

    public static Pane pane;
    public static Label score;
    public static Label bombs;
    public static Label levels;
    public static Label enemies;

    public static char[][] myMap;
    public static char[][] mapMatrix;
    private static final List<Enemy> enemyLayer = new ArrayList<Enemy>();
    private static final List<Entity> topLayer = new ArrayList<Entity>();
    private static final List<Entity> midLayer = new ArrayList<Entity>();
    private static final List<Entity> boardLayer = new ArrayList<Entity>();
    public static int CANVAS_WIDTH;
    public static int CANVAS_HEIGHT;

    private static int currentLevel = 1;
    public static int gameScore = 0;
    private static boolean continueL = false;
    private static int count = 200;

    public static int mapWidth;
    public static int mapHeight;
    public static int mapLevel;


    static {
        sceneStarted = false;
    }

    private static void initGame() {
        root = new Group();
        scene = new Scene(root, Const.SCENE_WIDTH, Const.SCENE_HEIGHT);
        canvas = new Canvas();
        initLabel();
        root.getChildren().addAll(pane, canvas, score, enemies, bombs, levels);
        graphicsContext = canvas.getGraphicsContext2D();
        createMap(currentLevel);
        GameLoop.start(getGraphicsContext());
        Controller.attachEventHandler(scene);
    }

    private static void initLabel() {
        pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        pane.setPrefWidth(Const.SCENE_WIDTH);
        pane.setPrefHeight(48);
        pane.setStyle("-fx-background-color: #babab8");

        score = new Label("Score");
        score.setFont(font);
        score.autosize();

        enemies = new Label("Enemies");
        enemies.setFont(font);
        enemies.autosize();
        enemies.setLayoutX(150);

        bombs = new Label("Bomb");
        bombs.setFont(font);
        bombs.autosize();
        bombs.setLayoutX(350);

        levels = new Label("");
        levels.setFont(font);
        levels.autosize();
        levels.setLayoutX(550);
    }

    public static void initScene() {
        if (!sceneStarted) {
            initGame();
            sceneStarted = true;
        }
    }

    public static Scene getScene() {
        return scene;
    }

    public static GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public static void createMap(int level) {
        levels.setText("Level: " + level);
        clearMap();
        if (continueL == false) {
            loadMapFile("/levels/Level" + level + ".txt");
        }
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                char c = myMap[i][j];
                addEntity(c, j * Const.SCALED_SIZE, i * Const.SCALED_SIZE);
            }
        }
        canvas.setHeight(CANVAS_HEIGHT);
        canvas.setWidth(CANVAS_WIDTH);
    }

    public static void setCameraView() {
        if (player.getX_pos() < Const.SCENE_WIDTH / 2) {
            canvas.setLayoutX(0);
        } else if (player.getX_pos() > CANVAS_WIDTH - Const.SCENE_WIDTH / 2) {
            canvas.setLayoutX(Const.SCENE_WIDTH - CANVAS_WIDTH);
        } else {
            canvas.setLayoutX(Const.SCENE_WIDTH / 2.0 - player.getX_pos());
        }
        if (player.getY_pos() < (Const.SCENE_HEIGHT + 2 * pane.getHeight()) / 2) {
            canvas.setLayoutY(pane.getHeight());
        } else if (player.getY_pos() > CANVAS_HEIGHT + pane.getHeight() - Const.SCENE_HEIGHT / 2) {
            canvas.setLayoutY(Const.SCENE_HEIGHT - CANVAS_HEIGHT);
        } else {
            canvas.setLayoutY((Const.SCENE_HEIGHT) / 2.0 - player.getY_pos() + 2 * pane.getHeight());
            pane.setLayoutY(0);
        }
    }

    public static void clearMap() {
        graphicsContext.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        enemyLayer.clear();
        topLayer.clear();
        midLayer.clear();
        boardLayer.clear();
        sceneStarted = false;
    }

    public static void nextMap() {
        if (currentLevel <= 5) {
            currentLevel += 1;
        } else {
            currentLevel = 1;
        }
    }

    public static void nextLevel() {
        clearMap();
        nextMap();
        createMap(currentLevel);
        player.setBombCount();
    }

    public static void resetLevel() {
        createMap(currentLevel);
    }

    public static void gameOver() {
        clearMap();
        Player.resetPlayer();
        player = null;
        pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        pane.setPrefWidth(Const.SCENE_WIDTH);
        pane.setPrefHeight(Const.SCENE_HEIGHT);
        pane.setStyle("-fx-background-color: BLACK");
        //Game over
        Label temp = new Label("Game over");
        Font tempfont = Font.loadFont(Main.class.getResourceAsStream("/Font/joystix monospace.ttf"), 30);
        temp.setFont(tempfont);
        temp.setTextFill(Color.web("#ffffff"));
        temp.autosize();
        temp.setLayoutX(250);
        temp.setLayoutY(250);
        //New game
        Label newGame = new Label("NEW GAME");
        tempfont = Font.loadFont(Main.class.getResourceAsStream("/Font/joystix monospace.ttf"), 22);
        newGame.setFont(tempfont);
        newGame.setTextFill(Color.web("#ffffff"));
        newGame.autosize();
        newGame.setLayoutX(280);
        newGame.setLayoutY(300);
        newGame.setOnMouseEntered(MouseEvent ->{
            newGame.setTextFill(Color.web("#ff3422"));
        });
        newGame.setOnMouseExited(MouseEvent ->{
            newGame.setTextFill(Color.web("#ffffff"));
        });
        newGame.setOnMouseClicked(MouseEvent ->{
            Sound.BGM.stop();
            Map.initScene();
            Main.getStage().setScene(scene);
        });
        //Menu
        Label menu = new Label("MENU");
        tempfont = Font.loadFont(Main.class.getResourceAsStream("/Font/joystix monospace.ttf"), 22);
        menu.setFont(tempfont);
        menu.setTextFill(Color.web("#ffffff"));
        menu.autosize();
        menu.setLayoutX(300);
        menu.setLayoutY(330);
        menu.setOnMouseEntered(MouseEvent ->{
            menu.setTextFill(Color.web("#ff3422"));
        });
        menu.setOnMouseExited(MouseEvent ->{
            menu.setTextFill(Color.web("#ffffff"));
        });
        menu.setOnMouseClicked(MouseEvent ->{
            Sound.BGM.stop();
            Main.getStage().setScene(Menu.menuScene(Main.getStage()));
        });
        root.getChildren().addAll(pane, temp, newGame, menu);
    }

    public static void tranfer() {
        root = new Group();
        scene = new Scene(root, Const.SCENE_WIDTH, Const.SCENE_HEIGHT);
        pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        pane.setPrefWidth(Const.SCENE_WIDTH);
        pane.setPrefHeight(Const.SCENE_HEIGHT);
        pane.setStyle("-fx-background-color: BLACK");
        //Show stage
        Label temp = new Label("Stage " + currentLevel);
        Font tempfont = Font.loadFont(Main.class.getResourceAsStream("/Font/joystix monospace.ttf"), 40);
        temp.setFont(tempfont);
        temp.setTextFill(Color.web("#ffffff"));
        temp.autosize();
        temp.setLayoutX(250);
        temp.setLayoutY(250);
        root.getChildren().addAll(pane, temp);
        Main.getStage().setScene(scene);
        Main.getStage().show();
    }

    public static void nextLevelByFn(int next) {
        createMap(next);
    }

    public static void removeEntity() {
        for (int i = 0; i < midLayer.size(); i++) {
            if (midLayer.get(i).isRemoved()) {
                midLayer.remove(i);
                --i;
            }
        }

        for (int i = 0; i < topLayer.size(); i++) {
            if (topLayer.get(i).isRemoved()) {
                topLayer.remove(i);
                --i;
            }
        }
        for (int i = 0; i < enemyLayer.size(); i++) {
            if (enemyLayer.get(i).isRemoved()) {
                gameScore += enemyLayer.get(i).getScore();
                enemyLayer.remove(i);
                --i;
            }
        }
    }

    public static List<Entity> getBoardLayer() {
        return boardLayer;
    }

    public static List<Entity> getMidLayer() {
        return midLayer;
    }

    public static List<Entity> getTopLayer() {
        return topLayer;
    }

    public static List<Enemy> getEnemyLayer() {
        return enemyLayer;
    }

    public static void addEntity(char c, int x, int y) {
        switch(c) {
            //maze
            case '#':
                boardLayer.add(new Wall(x, y));
                break;
            case '*':
                boardLayer.add(new Grass(x, y));
                topLayer.add(new Brick(x, y));
                break;
            case 'x':
                boardLayer.add(new Grass(x, y));
                midLayer.add(new Portal(x, y));
                topLayer.add(new Brick(x, y));
                break;
            case ' ':
                boardLayer.add(new Grass(x, y));
                break;
            //powerups
            case 'b':
                boardLayer.add(new Grass(x, y));
                midLayer.add(new BombsItem(x, y));
                topLayer.add(new Brick(x, y));
                break;
            case 's':
                boardLayer.add(new Grass(x, y));
                midLayer.add(new SpeedItem(x, y));
                topLayer.add(new Brick(x, y));
                break;
            case 'f':
                boardLayer.add(new Grass(x, y));
                midLayer.add(new FlamesItem(x, y));
                topLayer.add(new Brick(x, y));
                break;
            case 'd':
                boardLayer.add(new Grass(x, y));
                midLayer.add(new DetonatorItem(x, y));
                topLayer.add(new Brick(x, y));
                break;
            case 'w':
                boardLayer.add(new Grass(x, y));
                midLayer.add(new WallPassItem(x, y));
                topLayer.add(new Brick(x, y));
                break;
            case 'm':
                boardLayer.add(new Grass(x, y));
                midLayer.add(new FlamePassItem(x, y));
                topLayer.add(new Brick(x, y));
                break;
            case 'n':
                boardLayer.add(new Grass(x, y));
                midLayer.add(new BombPassItem(x, y));
                topLayer.add(new Brick(x, y));
                break;
            //player
            case 'p':
                boardLayer.add(new Grass(x, y));
                player = Player.setPlayer(x, y);
                break;
            //enemies
            case '1':
                boardLayer.add(new Grass(x, y));
                enemyLayer.add(new Balloom(x, y));
                break;
            case '2':
                boardLayer.add(new Grass(x, y));
                enemyLayer.add(new Oneal(x, y));
                break;
            case '3':
                boardLayer.add(new Grass(x, y));
                enemyLayer.add(new Doll(x, y));
                break;
            case '4':
                boardLayer.add(new Grass(x, y));
                enemyLayer.add(new Minvo(x, y));
                break;
            case '5':
                boardLayer.add(new Grass(x, y));
                enemyLayer.add(new Kondoria(x, y));
                break;
        }
    }

    public static void loadMapFile(String filePath) {
        try {
            URL fileMapPath = Map.class.getResource(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileMapPath.openStream()));
            String data = reader.readLine();
            StringTokenizer tokens = new StringTokenizer(data);
            mapLevel = Integer.parseInt(tokens.nextToken());
            currentLevel = mapLevel;
            mapHeight = Integer.parseInt(tokens.nextToken());
            mapWidth = Integer.parseInt(tokens.nextToken());
            CANVAS_HEIGHT = mapHeight * Const.BLOCK_SIZE;
            CANVAS_WIDTH = mapWidth * Const.BLOCK_SIZE;
            myMap = new char[mapHeight][mapWidth];
            mapMatrix = new char[mapHeight][mapWidth];
            for (int i = 0; i < mapHeight; i++) {
                char[] temp = reader.readLine().toCharArray();
                for (int j = 0; j < mapWidth; j++) {
                    myMap[i][j] = temp[j];
                    if ('#' == temp[j] || '*' == temp[j] || 'x' == temp[j]
                            || 'b' == temp[j] || 'f' == temp[j] || 's' == temp[j]
                            || 'w' == temp[j] || 'm' == temp[j] || 'n' == temp[j]) {
                        mapMatrix[i][j] = temp[j];
                    } else {
                        mapMatrix[i][j] = ' ';
                    }
                }
            }
            //level 0: continue read
            if (filePath.equals("/Level0.txt")) {
                continueL = true;
                //gameScore
                data = reader.readLine();
                tokens = new StringTokenizer(data);
                gameScore = Integer.parseInt(tokens.nextToken());
                //player
                data = reader.readLine();
                tokens = new StringTokenizer(data);
                int x = Integer.parseInt(tokens.nextToken()) * Const.SCALED_SIZE;
                int y = Integer.parseInt(tokens.nextToken()) * Const.SCALED_SIZE;
                int bombCount = Integer.parseInt(tokens.nextToken());
                int bombRadius = Integer.parseInt(tokens.nextToken());
                int speed = Integer.parseInt(tokens.nextToken());
                boolean ablePassFlame = Boolean.parseBoolean(tokens.nextToken());
                boolean ablePassBomb = Boolean.parseBoolean(tokens.nextToken());
                boolean ablePassWall = Boolean.parseBoolean(tokens.nextToken());
                boolean ablePassBrick = Boolean.parseBoolean(tokens.nextToken());
                boolean alive = Boolean.parseBoolean(tokens.nextToken());
                player = Player.setPlayerPlus(x, y, bombCount, bombRadius ,speed,
                        ablePassFlame, ablePassBomb, ablePassWall, ablePassBrick, alive);
            }
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public static Entity getStaticEntityAt(int x, int y) {
        for (Entity entity : boardLayer) {
            if (entity instanceof Wall && entity.getX_pos() == x && entity.getY_pos() == y) {
                return entity;
            }
        }
        for (Entity entity : topLayer) {
            if (entity instanceof Brick && entity.getX_pos() == x && entity.getY_pos() == y) {
                return entity;
            }
        }
        return null;
    }

    public static void exportLevel() {
        try {
            File file = new File("Level0.txt");
            OutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write(currentLevel + " " + mapHeight + " " + mapWidth + "\n");
            char[][] temp = new char[mapHeight][mapWidth];
            for (int i = 0; i < mapHeight; i++) {
                for (int j = 0; j < mapWidth; j++) {
                    temp[i][j] = mapMatrix[i][j];
                }
            }
            //player
            temp[player.getY_pos() / Const.BLOCK_SIZE][player.getX_pos() / Const.BLOCK_SIZE] = 'p';
            //enemy
            for (Entity ele : enemyLayer) {
                if (ele instanceof Balloom) {
                    temp[ele.getY_pos() / Const.BLOCK_SIZE][ele.getX_pos() / Const.BLOCK_SIZE] = '1';
                } else if (ele instanceof Doll) {
                    temp[ele.getY_pos() / Const.BLOCK_SIZE][ele.getX_pos() / Const.BLOCK_SIZE] = '3';
                } else if (ele instanceof Kondoria) {
                    temp[ele.getY_pos() / Const.BLOCK_SIZE][ele.getX_pos() / Const.BLOCK_SIZE] = '5';
                } else if (ele instanceof Minvo) {
                    temp[ele.getY_pos() / Const.BLOCK_SIZE][ele.getX_pos() / Const.BLOCK_SIZE] = '4';
                } else if (ele instanceof Oneal) {
                    temp[ele.getY_pos() / Const.BLOCK_SIZE][ele.getX_pos() / Const.BLOCK_SIZE] = '2';
                }
            }
            //midLayer
            for (Entity ele : midLayer) {
                if (ele instanceof Portal) {
                    temp[ele.getY_pos() / Const.BLOCK_SIZE][ele.getX_pos() / Const.BLOCK_SIZE] = 'x';
                } else if (ele instanceof BombsItem) {
                    temp[ele.getY_pos() / Const.BLOCK_SIZE][ele.getX_pos() / Const.BLOCK_SIZE] = 'b';
                } else if (ele instanceof SpeedItem) {
                    temp[ele.getY_pos() / Const.BLOCK_SIZE][ele.getX_pos() / Const.BLOCK_SIZE] = 's';
                } else if (ele instanceof DetonatorItem) {
                    temp[ele.getY_pos() / Const.BLOCK_SIZE][ele.getX_pos() / Const.BLOCK_SIZE] = 'd';
                } else if (ele instanceof FlamesItem) {
                    temp[ele.getY_pos() / Const.BLOCK_SIZE][ele.getX_pos() / Const.BLOCK_SIZE] = 'f';
                } else if (ele instanceof WallPassItem) {
                    temp[ele.getY_pos() / Const.BLOCK_SIZE][ele.getX_pos() / Const.BLOCK_SIZE] = 'w';
                } else if (ele instanceof BombPassItem) {
                    temp[ele.getY_pos() / Const.BLOCK_SIZE][ele.getX_pos() / Const.BLOCK_SIZE] = 'n';
                } else if (ele instanceof FlamePassItem) {
                    temp[ele.getY_pos() / Const.BLOCK_SIZE][ele.getX_pos() / Const.BLOCK_SIZE] = 'm';
                }
            }
            for (int i = 0; i < mapHeight; i++) {
                for (int j = 0; j < mapWidth; j++) {
                    writer.write(temp[i][j]);
                }
                writer.write("\n");
            }
            writer.write(gameScore + "\n");
            writer.write(player.getX_pos() / Const.BLOCK_SIZE + " " + player.getY_pos() / Const.BLOCK_SIZE
                    + " " + player.getBombCount() + " " + player.getBombRadius() + " "
                    + player.getSpeed() + " " + player.isAbleToPassFlame()
                    + " " + player.isAbleToPassBomb() + " " + player.isAbleToPassWall() + " "
                    + player.isAbleToPassBrick() + " " + player.isAlive());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
