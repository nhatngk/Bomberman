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
    static Group rootGame;
    static Group rootGameOver;
    static Group rootTransfer;
    static Canvas canvas;
    static GraphicsContext graphicsContext;
    private static boolean sceneStarted;
    static Player player;
    private static Font font = Font.loadFont(Main.class.getResourceAsStream("/Font/joystix monospace.ttf"), 18);

    public static Pane paneGame;
    public static Pane paneGameOver;
    public static Pane paneTransfer;
    public static Label score;
    public static Label bombs;
    public static Label levels;
    public static Label enemies;

    public static char[][] myMap;
    public static char[][] mapMatrix;
    private static final List<Enemy> enemyLayer = new ArrayList<>();
    private static final List<Entity> topLayer = new ArrayList<>();
    private static final List<Entity> midLayer = new ArrayList<>();
    private static final List<Entity> boardLayer = new ArrayList<>();
    public static int CANVAS_WIDTH;
    public static int CANVAS_HEIGHT;

    public static int currentLevel = 5;
    public static int gameScore = 0;
    private static boolean continueL = false;

    public static boolean isPassLevel = false;
    public static int mapWidth;
    public static int mapHeight;
    public static int mapLevel;
    public static boolean win = false;


    static {
        sceneStarted = false;
    }

    private static void initGame() {
        rootGame = new Group();
        scene = new Scene(rootGame, Const.SCENE_WIDTH, Const.SCENE_HEIGHT);
        canvas = new Canvas();
        initLabel();
        rootGame.getChildren().addAll(paneGame, canvas, score, enemies, bombs, levels);
        graphicsContext = canvas.getGraphicsContext2D();
        createMap(currentLevel);
        GameLoop.start(getGraphicsContext());
        Controller.attachEventHandler(scene);
    }

    private static void initLabel() {
        paneGame = new Pane();
        paneGame.setLayoutX(0);
        paneGame.setLayoutY(0);
        paneGame.setPrefWidth(Const.SCENE_WIDTH);
        paneGame.setPrefHeight(48);
        paneGame.setStyle("-fx-background-color: #babab8");

        score = new Label("Score");
        score.setFont(font);
        score.autosize();
        score.setLayoutX(50);
        score.setLayoutY(12);

        enemies = new Label("Enemies");
        enemies.setFont(font);
        enemies.autosize();
        enemies.setLayoutX(240);
        enemies.setLayoutY(12);

        bombs = new Label("Bomb");
        bombs.setFont(font);
        bombs.autosize();
        bombs.setLayoutX(430);
        bombs.setLayoutY(12);

        levels = new Label("");
        levels.setFont(font);
        levels.autosize();
        levels.setLayoutX(600);
        levels.setLayoutY(12);
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
        if (!continueL) {
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
        if (player.getY_pos() < (Const.SCENE_HEIGHT + 2 * paneGame.getHeight()) / 2) {
            canvas.setLayoutY(paneGame.getHeight());
        } else if (player.getY_pos() > CANVAS_HEIGHT + paneGame.getHeight() - Const.SCENE_HEIGHT / 2) {
            canvas.setLayoutY(Const.SCENE_HEIGHT - CANVAS_HEIGHT);
        } else {
            canvas.setLayoutY((Const.SCENE_HEIGHT) / 2.0 - player.getY_pos() + 2 * paneGame.getHeight());
            paneGame.setLayoutY(0);
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
        if (currentLevel < 5) {
            currentLevel += 1;
        } else {
            currentLevel = 1;
        }
    }

    public static void nextLevel() {
        System.out.println("test1");
        continueL = false;
        clearMap();
        nextMap();
        createMap(currentLevel);
        player.setBombCount();
        scene.setRoot(rootGame);
    }

    public static void gameOver(String mess) {
        // reset level 0
        try {
            File file = new File("Level0.txt");
            OutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write("");
            writer.close();
            if (Menu.top < gameScore) {
                Menu.top = gameScore;
                System.out.println(Menu.top);
                System.out.println(gameScore);
                outputStream = new FileOutputStream(new File("top.txt"));
                writer = new OutputStreamWriter(outputStream);
                writer.write(String.valueOf(Menu.top));
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        rootGameOver = new Group();
        continueL = false;
        clearMap();
        Player.resetPlayer();
        player = null;
        paneGameOver = new Pane();
        paneGameOver.setLayoutX(0);
        paneGameOver.setLayoutY(0);
        paneGameOver.setPrefWidth(Const.SCENE_WIDTH);
        paneGameOver.setPrefHeight(Const.SCENE_HEIGHT);
        paneGameOver.setStyle("-fx-background-color: BLACK");
        //Game over
        Label temp = new Label(mess);
        Font tempfont = Font.loadFont(Main.class.getResourceAsStream("/Font/joystix monospace.ttf"), 50);
        temp.setFont(tempfont);
        temp.setTextFill(Color.web("#ffffff"));
        temp.autosize();
        temp.setLayoutX(200);
        temp.setLayoutY(230);
        //New game
        Label newGame = new Label("NEW GAME");
        tempfont = Font.loadFont(Main.class.getResourceAsStream("/Font/joystix monospace.ttf"), 30);
        newGame.setFont(tempfont);
        newGame.setTextFill(Color.web("#ffffff"));
        newGame.autosize();
        newGame.setLayoutX(280);
        newGame.setLayoutY(370);
        newGame.setOnMouseEntered(MouseEvent -> newGame.setTextFill(Color.web("#ff3422")));
        newGame.setOnMouseExited(MouseEvent -> newGame.setTextFill(Color.web("#ffffff")));
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
        menu.setLayoutX(330);
        menu.setLayoutY(430);
        menu.setOnMouseEntered(MouseEvent -> menu.setTextFill(Color.web("#ff3422")));
        menu.setOnMouseExited(MouseEvent -> menu.setTextFill(Color.web("#ffffff")));
        menu.setOnMouseClicked(MouseEvent ->{
            Sound.BGM.stop();
            Main.getStage().setScene(Menu.menuScene(Main.getStage()));
        });
        Label score = new Label("SCORE " + gameScore);
        tempfont = Font.loadFont(Main.class.getResourceAsStream("/Font/joystix monospace.ttf"), 22);
        score.setFont(tempfont);
        score.setTextFill(Color.web("#ffffff"));
        score.autosize();
        score.setLayoutX(310);
        score.setLayoutY(320);
        score.setOnMouseEntered(MouseEvent -> menu.setTextFill(Color.web("#ff3422")));
        score.setOnMouseExited(MouseEvent -> menu.setTextFill(Color.web("#ffffff")));
        score.setOnMouseClicked(MouseEvent ->{
            Sound.BGM.stop();
            Main.getStage().setScene(Menu.menuScene(Main.getStage()));
        });
        rootGameOver.getChildren().addAll(paneGameOver, temp, newGame, menu, score);
        scene.setRoot(rootGameOver);
    }

    public static void tranfer() {
        rootTransfer = new Group();
        paneTransfer = new Pane();
        paneTransfer.setLayoutX(0);

        paneTransfer.setLayoutY(0);
        paneTransfer.setPrefWidth(Const.SCENE_WIDTH);
        paneTransfer.setPrefHeight(Const.SCENE_HEIGHT);
        paneTransfer.setStyle("-fx-background-color: BLACK");
        //Show stage
        Label temp = new Label("Stage " + (currentLevel+1));
        Font tempfont = Font.loadFont(Main.class.getResourceAsStream("/Font/joystix monospace.ttf"), 40);
        temp.setFont(tempfont);
        temp.setTextFill(Color.web("#ffffff"));
        temp.autosize();
        temp.setLayoutX(250);
        temp.setLayoutY(250);
        rootTransfer.getChildren().addAll(paneTransfer, temp);
        scene.setRoot(rootTransfer);
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
        switch (c) {
            //maze
            case '#' -> boardLayer.add(new Wall(x, y));
            case '*' -> {
                boardLayer.add(new Grass(x, y));
                topLayer.add(new Brick(x, y));
            }
            case 'x' -> {
                boardLayer.add(new Grass(x, y));
                midLayer.add(new Portal(x, y));
                topLayer.add(new Brick(x, y));
            }
            case ' ' -> boardLayer.add(new Grass(x, y));

            //powerups
            case 'b' -> {
                boardLayer.add(new Grass(x, y));
                midLayer.add(new BombsItem(x, y));
                topLayer.add(new Brick(x, y));
            }
            case 's' -> {
                boardLayer.add(new Grass(x, y));
                midLayer.add(new SpeedItem(x, y));
                topLayer.add(new Brick(x, y));
            }
            case 'f' -> {
                boardLayer.add(new Grass(x, y));
                midLayer.add(new FlamesItem(x, y));
                topLayer.add(new Brick(x, y));
            }
            case 'd' -> {
                boardLayer.add(new Grass(x, y));
                midLayer.add(new DetonatorItem(x, y));
                topLayer.add(new Brick(x, y));
            }
            case 'w' -> {
                boardLayer.add(new Grass(x, y));
                midLayer.add(new WallPassItem(x, y));
                topLayer.add(new Brick(x, y));
            }
            case 'm' -> {
                boardLayer.add(new Grass(x, y));
                midLayer.add(new FlamePassItem(x, y));
                topLayer.add(new Brick(x, y));
            }
            case 'n' -> {
                boardLayer.add(new Grass(x, y));
                midLayer.add(new BombPassItem(x, y));
                topLayer.add(new Brick(x, y));
            }
            //player
            case 'p' -> {
                boardLayer.add(new Grass(x, y));
                player = Player.setPlayer(x, y);
            }
            //enemies
            case '1' -> {
                boardLayer.add(new Grass(x, y));
                enemyLayer.add(new Balloom(x, y));
            }
            case '2' -> {
                boardLayer.add(new Grass(x, y));
                enemyLayer.add(new Oneal(x, y));
            }
            case '3' -> {
                boardLayer.add(new Grass(x, y));
                enemyLayer.add(new Doll(x, y));
            }
            case '4' -> {
                boardLayer.add(new Grass(x, y));
                enemyLayer.add(new Minvo(x, y));
            }
            case '5' -> {
                boardLayer.add(new Grass(x, y));
                enemyLayer.add(new Kondoria(x, y));
            }
        }
    }

    public static void loadMapFile(String filePath) {
        try {
            URL fileMapPath;
            BufferedReader reader;
            if (filePath.equals("Level0.txt")) {
                reader = new BufferedReader(new FileReader(new File(filePath)));
            } else {
                fileMapPath = Map.class.getResource(filePath);
                reader = new BufferedReader(new InputStreamReader(fileMapPath.openStream()));
            }
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
            if (filePath.equals("Level0.txt")) {
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
