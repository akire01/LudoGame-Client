/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.erika.javafx.app.userScreen;

import hr.erika.javafx.chat.ChatClient;
import hr.erika.javafx.data.Color;
import hr.erika.javafx.data.DiceNumber;
import hr.erika.javafx.data.PawnStatus;
import hr.erika.javafx.data.Player;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import hr.erika.javafx.utils.FindElementsPosition;
import hr.erika.javafx.utils.MessageUtils;
import hr.erika.javafx.utils.RulesOfGame;
import hr.erika.javafx.data.Pawn;
import hr.erika.javafx.state.State;
import hr.erika.javafx.thread.ClientThread;
import hr.erika.javafx.utils.DOMUtils;
import hr.erika.javafx.utils.FileUtils;
import hr.erika.javafx.utils.ReflectionUtils;
import hr.erika.javafx.utils.SerializationUtils;
import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author ER
 */
public class UserScreenController implements Initializable {

    private ClientThread clientThread;

    private static final int MAXIMUM_NUMBER = DiceNumber.values().length;
    private int numberOfPlayers;
    private int turns;
    int currentNumber;
    private int currentPlayer = 0;
    Pawn currentPawn;
    Shape startPosition;
    Color currentColor;
    boolean noWinner = true;
    final String FIRST_PLAYER = "YellowDog";
    final String SECOND_PLAYER = "Green Software";
    final String THIRD_PLAYER = "Deep Blue";
    final String FOURTH_PLAYER = "Red Hat";
    Player[] players;
    Shape[] yellowQquare;
    Shape[] greenSquare;
    Shape[] redSquare;
    Shape[] blueSquare;

    @FXML
    private GridPane gridPane;
    @FXML
    private Shape yellow1;
    @FXML
    private Shape yellow2;
    @FXML
    private Shape yellow3;
    @FXML
    private Shape yellow4;
    @FXML
    private Pawn yellowPawns[];
    @FXML
    private Shape green1;
    @FXML
    private Shape green2;
    @FXML
    private Shape green3;
    @FXML
    private Shape green4;
    @FXML
    private Pawn greenPawns[];
    @FXML
    private Shape red1;
    @FXML
    private Shape red2;
    @FXML
    private Shape red3;
    @FXML
    private Shape red4;
    @FXML
    private Pawn redPawns[];
    @FXML
    private Shape blue1;
    @FXML
    private Shape blue2;
    @FXML
    private Shape blue3;
    @FXML
    private Shape blue4;
    @FXML
    private Pawn bluePawns[];
    @FXML
    private Shape startYellow;
    @FXML
    private Shape startGreen;
    @FXML
    private Shape startRed;
    @FXML
    private Shape startBlue;
    @FXML
    private ImageView diceImage;
    @FXML
    private Shape field1;
    @FXML
    private Shape field2;
    @FXML
    private Shape field3;
    @FXML
    private Shape field4;
    @FXML
    private Shape field5;
    @FXML
    private Shape field6;
    @FXML
    private Shape field7;
    @FXML
    private Shape field8;
    @FXML
    private Shape finishGreen;
    @FXML
    private Shape field11;
    @FXML
    private Shape field12;
    @FXML
    private Shape field13;
    @FXML
    private Shape field14;
    @FXML
    private Shape field15;
    @FXML
    private Shape field16;
    @FXML
    private Shape field17;
    @FXML
    private Shape field18;
    @FXML
    private Shape finishRed;
    @FXML
    private Shape field21;
    @FXML
    private Shape field22;
    @FXML
    private Shape field23;
    @FXML
    private Shape field24;
    @FXML
    private Shape field25;
    @FXML
    private Shape field26;
    @FXML
    private Shape field27;
    @FXML
    private Shape field28;
    @FXML
    private Shape finishBlue;
    @FXML
    private Shape field31;
    @FXML
    private Shape field32;
    @FXML
    private Shape field33;
    @FXML
    private Shape field34;
    @FXML
    private Shape field35;
    @FXML
    private Shape field36;
    @FXML
    private Shape field37;
    @FXML
    private Shape field38;
    @FXML
    private Shape finishYellow;
    @FXML
    private Shape squareYellow1;
    @FXML
    private Shape squareYellow2;
    @FXML
    private Shape squareYellow3;
    @FXML
    private Shape squareYellow4;
    @FXML
    private Shape squareGreen1;
    @FXML
    private Shape squareGreen2;
    @FXML
    private Shape squareGreen3;
    @FXML
    private Shape squareGreen4;
    @FXML
    private Shape squareRed1;
    @FXML
    private Shape squareRed2;
    @FXML
    private Shape squareRed3;
    @FXML
    private Shape squareRed4;
    @FXML
    private Shape squareBlue1;
    @FXML
    private Shape squareBlue2;
    @FXML
    private Shape squareBlue3;
    @FXML
    private Shape squareBlue4;
    @FXML
    private Shape homeYellow1;
    @FXML
    private Shape homeYellow2;
    @FXML
    private Shape homeYellow3;
    @FXML
    private Shape homeYellow4;
    @FXML
    private Shape homeGreen1;
    @FXML
    private Shape homeGreen2;
    @FXML
    private Shape homeGreen3;
    @FXML
    private Shape homeGreen4;
    @FXML
    private Shape homeRed1;
    @FXML
    private Shape homeRed2;
    @FXML
    private Shape homeRed3;
    @FXML
    private Shape homeRed4;
    @FXML
    private Shape homeBlue1;
    @FXML
    private Shape homeBlue2;
    @FXML
    private Shape homeBlue3;
    @FXML
    private Shape homeBlue4;
    @FXML
    private Button btnSerialization;
    @FXML
    private Button btnDeserialization;
    @FXML
    private Button btnGenerateDocumentation;
    @FXML
    private Text txtMessage;

    Shape[] homeFieldsYellow;
    Shape[] homeFieldsGreen;
    Shape[] homeFieldsRed;
    Shape[] homeFieldsBlue;

    Shape[] allFieldsArray;
    Pawn[] allPawns;

    Player player1;
    Player player2;
    Player player3;
    Player player4;

    int finishYellowIndex;
    int finishGreenIndex;
    int finishRedIndex;
    int finishBlueIndex;
    int currentFinishIndex;

    String gameStatus;
    public Player thisPlayer;

    @FXML
    private TextField tfMessage;
    @FXML
    private Button btnSendMessage;
    @FXML
    private ScrollPane spContainer;
    @FXML
    private VBox vbMessages;

    private ObservableList<Node> messages;
    private ChatClient chatClient;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //yellow pawns
        Pawn yellowPawn1 = new Pawn(yellow1, Color.YELLOW, startYellow, PawnStatus.SQUARE, squareYellow1);
        Pawn yellowPawn2 = new Pawn(yellow2, Color.YELLOW, startYellow, PawnStatus.SQUARE, squareYellow2);
        Pawn yellowPawn3 = new Pawn(yellow3, Color.YELLOW, startYellow, PawnStatus.SQUARE, squareYellow3);
        Pawn yellowPawn4 = new Pawn(yellow4, Color.YELLOW, startYellow, PawnStatus.SQUARE, squareYellow4);
        //green pawns
        Pawn greenPawn1 = new Pawn(green1, Color.GREEN, startYellow, PawnStatus.SQUARE, squareGreen1);
        Pawn greenPawn2 = new Pawn(green2, Color.GREEN, startYellow, PawnStatus.SQUARE, squareGreen2);
        Pawn greenPawn3 = new Pawn(green3, Color.GREEN, startYellow, PawnStatus.SQUARE, squareGreen3);
        Pawn greenPawn4 = new Pawn(green4, Color.GREEN, startYellow, PawnStatus.SQUARE, squareGreen4);
        //red pawns
        Pawn redPawn1 = new Pawn(red1, Color.RED, startYellow, PawnStatus.SQUARE, squareRed1);
        Pawn redPawn2 = new Pawn(red2, Color.RED, startYellow, PawnStatus.SQUARE, squareRed2);
        Pawn redPawn3 = new Pawn(red3, Color.RED, startYellow, PawnStatus.SQUARE, squareRed3);
        Pawn redPawn4 = new Pawn(red4, Color.RED, startYellow, PawnStatus.SQUARE, squareRed4);
        //blue pawns
        Pawn bluePawn1 = new Pawn(blue1, Color.BLUE, startYellow, PawnStatus.SQUARE, squareBlue1);
        Pawn bluePawn2 = new Pawn(blue2, Color.BLUE, startYellow, PawnStatus.SQUARE, squareBlue2);
        Pawn bluePawn3 = new Pawn(blue3, Color.BLUE, startYellow, PawnStatus.SQUARE, squareBlue3);
        Pawn bluePawn4 = new Pawn(blue4, Color.BLUE, startYellow, PawnStatus.SQUARE, squareBlue4);

        diceImage.setVisible(false);

        //init players
        this.yellowPawns = new Pawn[]{yellowPawn1, yellowPawn2, yellowPawn3, yellowPawn4};
        player1 = new Player("YellowDog", Color.YELLOW, yellowPawns);
        this.greenPawns = new Pawn[]{greenPawn1, greenPawn2, greenPawn3, greenPawn4};
        player2 = new Player("Green software", Color.GREEN, greenPawns);
        this.redPawns = new Pawn[]{redPawn1, redPawn2, redPawn3, redPawn4};
        player3 = new Player("Red Hat", Color.RED, redPawns);
        this.bluePawns = new Pawn[]{bluePawn1, bluePawn2, bluePawn3, bluePawn4};
        player4 = new Player("Deep Blue", Color.BLUE, bluePawns);

        //init home fields
        homeFieldsYellow = new Shape[]{homeYellow1, homeYellow2, homeYellow3, homeYellow4};
        homeFieldsGreen = new Shape[]{homeGreen1, homeGreen2, homeGreen3, homeGreen4};
        homeFieldsRed = new Shape[]{homeRed1, homeRed2, homeRed3, homeRed4};
        homeFieldsBlue = new Shape[]{homeBlue1, homeBlue2, homeBlue3, homeBlue4};
        //init all fileds on playing board
        allFieldsArray = new Shape[]{startYellow, field1, field2, field3, field4, field5, field6, field7, field8, finishGreen, startGreen, field11, field12, field13, field14,
            field15, field16, field17, field18, finishRed, startRed, field21, field22, field23, field24, field25, field26, field27, field28, finishBlue, startBlue,
            field31, field32, field33, field34, field35, field36, field37, field38, finishYellow};
        //init all pawns
        allPawns = new Pawn[]{yellowPawn1, yellowPawn2, yellowPawn3, yellowPawn4, greenPawn1, greenPawn2, greenPawn3, greenPawn4, redPawn1, redPawn2, redPawn3, redPawn4,
            bluePawn1, bluePawn2, bluePawn3, bluePawn4};

        DOMUtils.deleteXml();
    }

   
    public void initClientThread() throws IOException {
        clientThread = new ClientThread(this);
        clientThread.setDaemon(true);
        clientThread.start();
        gameStatus = "online";
        spContainer.setVisible(true);
        btnSendMessage.setVisible(true);
        tfMessage.setVisible(true);

        chatClient = new ChatClient(this);
        messages = FXCollections.observableArrayList();
        Bindings.bindContentBidirectional(messages, vbMessages.getChildren());

    }

    public void setThisPlayer(String color) {
        switch (color) {
            case "YELLOW":
                thisPlayer = player1;
                break;
            case "GREEN":
                thisPlayer = player2;
                break;
            case "RED":
                thisPlayer = player3;
                break;
            case "BLUE":
                thisPlayer = player4;
                break;
        }
    }

    public void roll() {
        diceImage.setDisable(true);
        diceImage.setImage(new Image("assets/die's_animation.gif"));

        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(event -> {

            int number = new Random().nextInt(MAXIMUM_NUMBER);
            DiceNumber diceNumber = DiceNumber.values()[number];

            switch (diceNumber) {
                case ONE:
                    diceImage.setImage(new Image("assets/die1.png"));
                    currentNumber = 1;
                    break;
                case TWO:
                    diceImage.setImage(new Image("assets/die2.png"));
                    currentNumber = 2;
                    break;
                case THREE:
                    diceImage.setImage(new Image("assets/die3.png"));
                    currentNumber = 3;
                    break;
                case FOUR:
                    diceImage.setImage(new Image("assets/die4.png"));
                    currentNumber = 4;
                    break;
                case FIVE:
                    diceImage.setImage(new Image("assets/die5.png"));
                    currentNumber = 5;
                    break;
                case SIX:
                    diceImage.setImage(new Image("assets/die6.png"));
                    currentNumber = 6;
                    break;
                default:
                    diceImage.setImage(null);
                    break;
            }
            playThePawn();

        });

        pause.play();
    }

    
    public void setPawns(int numOfPlayers) {
        for (Shape field : allFieldsArray) {
            field.setDisable(true);
        }
        numberOfPlayers = numOfPlayers;
        switch (numberOfPlayers) {
            case 2:
                Pawn.hidePawns(redPawns);
                Pawn.hidePawns(bluePawns);
                Pawn.unHidePawns(greenPawns);
                Pawn.unHidePawns(yellowPawns);
                players = new Player[]{player1, player2};
                break;
            case 3:
                Pawn.hidePawns(redPawns);
                Pawn.unHidePawns(bluePawns);
                Pawn.unHidePawns(greenPawns);
                Pawn.unHidePawns(yellowPawns);
                players = new Player[]{player1, player2, player4};
                break;
            case 4:
                Pawn.unHidePawns(redPawns);
                Pawn.unHidePawns(bluePawns);
                Pawn.unHidePawns(greenPawns);
                Pawn.unHidePawns(yellowPawns);
                players = new Player[]{player1, player2, player3, player4};
                break;
        }
        
        

    }

    
    public void showInformation() {
        //MessageUtils.showAlert(Player.getPlayersNames(players) + "good luck and have fun! \n\nRemember not to get mad!", "Dear players");
        gameBegin();
    }

    
    void gameBegin() {
        for (int i = 0; i < allFieldsArray.length; i++) {
            if (allFieldsArray[i] == finishYellow) {
                finishYellowIndex = i;
            } else if (allFieldsArray[i] == finishGreen) {
                finishGreenIndex = i;
            } else if (allFieldsArray[i] == finishRed) {
                finishRedIndex = i;
            } else if (allFieldsArray[i] == finishBlue) {
                finishBlueIndex = i;
            }
        }
        playersTurn(players[currentPlayer]);

    }

   
    private void playersTurn(Player player) {
        turns = 3;
        Pawn[] pawns = player.getPlayersPawns();
        for (Pawn pawn : pawns) {
            if (pawn.getStatus() == PawnStatus.OUT) {
                turns = 1;
                break;
            }
        }

        if ("online".equals(gameStatus)) {
           
            sendState();
        }
        State state = new State(players, currentPlayer, turns);
        
        
        if (!isReplay) {
            DOMUtils.saveStates(state);
        }
        
        goPlay();
    }

    private void goPlay() {

        if ("online".equals(gameStatus) && players[currentPlayer] == thisPlayer) {
            diceImage.setVisible(true);
        } else if (!"online".equals(gameStatus)) {
            diceImage.setVisible(true);
        } else {
            diceImage.setVisible(false);
            txtMessage.setText("Player " + players[currentPlayer] + " is on the move");
        }

        //MessageUtils.showAlert(players[currentPlayer].getName() + ", click on the dice!", "Turn");

        //sljedece je roll...
    }

    void clickablePawn(Boolean clickable) {
        if (!clickable) {
            for (Pawn pawn : allPawns) {
                pawn.getCircle().setDisable(true);
            }
        } else {
            for (Pawn pawn : allPawns) {
                if (pawn.getStatus() == PawnStatus.OUT) {
                    pawn.getCircle().setDisable(false);
                }
            }
        }

    }

    private void playThePawn() {
        clickablePawn(false);
        turns--;

        Boolean allInSquare = true;
        Pawn[] pawnsOfPlayer = players[currentPlayer].getPlayersPawns();
        for (Pawn pawn : pawnsOfPlayer) {
            if (pawn.getStatus() != PawnStatus.SQUARE && pawn.getStatus() != PawnStatus.HOME) {
                allInSquare = false;
                break;
            }
        }

        if (allInSquare && currentNumber != 6) {
           // MessageUtils.showAlert(players[currentPlayer].getName() + " sorry, no luck", "Turn");
            diceImage.setDisable(false);

            if (turns == 0) {
                currentPlayer++;
                if (currentPlayer >= players.length) {
                    currentPlayer = 0;
                }

                playersTurn(players[currentPlayer]);
            }
        }

        if (currentNumber == 6) {
            for (Pawn pawn : pawnsOfPlayer) {
                if (pawn.getStatus() != PawnStatus.HOME) {
                    pawn.getCircle().setDisable(false);
                }
            }
            //MessageUtils.showAlert(players[currentPlayer].getName() + " click on desired pawn to play the move", "Turn");

            diceImage.setDisable(true);
            turns = 1;
        } else if (!allInSquare) {
            // MessageUtils.showAlert(players[currentPlayer].getName() + " click on desired pawn to play the move", "Turn");
            for (Pawn pawn : pawnsOfPlayer) {
                if (pawn.getStatus() == PawnStatus.OUT) {
                    pawn.getCircle().setDisable(false);
                }
            }
            diceImage.setDisable(true);
        }

    }

    @FXML
    public void pawnClicked(MouseEvent event) {

        //is called when user clicks on the Pawn, after clicking on the dice
        clickablePawn(false);
        Shape circle = (Shape) event.getSource();
        Pawn[] playersPawns = players[currentPlayer].getPlayersPawns();
        for (Pawn playersPawn : playersPawns) {
            if (playersPawn.getCircle().getId() == null ? circle.getId() == null : playersPawn.getCircle().getId().equals(circle.getId())) {
                currentPawn = playersPawn;
                break;
            }
        }

        startPosition = startYellow;
        switch (currentPawn.getColor()) {
            case GREEN:
                startPosition = startGreen;
                break;
            case BLUE:
                startPosition = startBlue;
                break;
            case RED:
                startPosition = startRed;
                break;
        }
        try {
            move(circle);
        } catch (Exception ex) {
             Logger.getLogger(UserScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void move(Shape circle) {
        Optional<Shape> destination = Optional.empty();
        try {
            if (currentNumber == 6 && currentPawn.getStatus() == PawnStatus.SQUARE) {
                destination = Optional.of(startPosition);
                currentPawn.setStatus(PawnStatus.OUT);
                Optional<Pawn> existingPawn = Optional.empty();
                existingPawn = Pawn.checkExistingPawn(destination.get(), allPawns);

                if (existingPawn.isPresent() && existingPawn.get().getColor() == players[currentPlayer].getColor()) {
                    MessageUtils.showAlert(players[currentPlayer].getName()
                            + " , You can not move Your pawn to the place of Your other pawn", "Turn");
                    for (Pawn pawn : players[currentPlayer].getPlayersPawns()) {
                        if (currentNumber != 6 && pawn.getStatus() == PawnStatus.OUT) {
                            pawn.getCircle().setDisable(false);
                        } else if (currentNumber == 6 && pawn.getStatus() != PawnStatus.HOME) {
                            pawn.getCircle().setDisable(false);
                        }
                    }
                    return;
                } else if (existingPawn.isPresent() && existingPawn.get().getColor() != players[currentPlayer].getColor()) {
                    gridPane.getChildren().remove(existingPawn.get().getCircle());
                    existingPawn.get().setStatus(PawnStatus.SQUARE);
                    try {
                        gridPane.add(existingPawn.get().getCircle(), FindElementsPosition.getColumn(existingPawn.get().getSquarePosition()),
                                FindElementsPosition.getRow(existingPawn.get().getSquarePosition()));
                    } catch (Exception ex) {
                         Logger.getLogger(UserScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    currentPawn.setStatus(PawnStatus.OUT);
                    gridPane.add(circle, FindElementsPosition.getColumn(destination.get()), FindElementsPosition.getRow(destination.get()));
                } else {
                    currentPawn.setStatus(PawnStatus.OUT);
                    gridPane.add(circle, FindElementsPosition.getColumn(destination.get()), FindElementsPosition.getRow(destination.get()));
                }

            } else {
                int PawnsColumn = FindElementsPosition.getColumn(circle);
                int PawnsRow = FindElementsPosition.getRow(circle);
                int currentIndex = 0;
                int destinationIndex = 0;
                for (int i = 0; i < allFieldsArray.length; i++) {
                    if (FindElementsPosition.getColumn(allFieldsArray[i]) == PawnsColumn && FindElementsPosition.getRow(allFieldsArray[i]) == PawnsRow) {
                        currentIndex = i;
                        break;
                    }
                }
                destinationIndex = currentIndex + currentNumber;

                Optional<Shape> homeDestination = checkFinish(destinationIndex);
                if (homeDestination.isPresent()) {
                    try {
                        gridPane.add(circle, FindElementsPosition.getColumn(homeDestination.get()),
                                FindElementsPosition.getRow(homeDestination.get()));
                    } catch (Exception ex) {
                        Logger.getLogger(UserScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    currentPawn.setStatus(PawnStatus.HOME);
                    Boolean allInHome = true;
                    for (Pawn pawn : players[currentPlayer].getPlayersPawns()) {
                        if (pawn.getStatus() != PawnStatus.HOME) {
                            allInHome = false;
                            break;
                        }
                    }
                    if (allInHome) {
                        MessageUtils.showAlert(players[currentPlayer].getName()
                                + " has won! Congratulations! ", "GAME OVER");
                        diceImage.setDisable(true);
                        return;
                    }
                } else if ((players[currentPlayer].getColor() == Color.YELLOW && destinationIndex < currentFinishIndex)
                        || (players[currentPlayer].getColor() != Color.YELLOW && (currentIndex < currentFinishIndex && destinationIndex <= currentFinishIndex))) {
                    try {
                        gridPane.add(circle, FindElementsPosition.getColumn(destination.get()), FindElementsPosition.getRow(destination.get()));
                    } catch (Exception ex) {
                        Logger.getLogger(UserScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if ((players[currentPlayer].getColor() == Color.YELLOW && destinationIndex > currentFinishIndex)
                        || (players[currentPlayer].getColor() != Color.YELLOW && currentIndex < currentFinishIndex && destinationIndex > currentFinishIndex)) {
                    if (turns == 0) {
                        currentPlayer++;

                        if (currentPlayer >= players.length) {
                            currentPlayer = 0;
                        }

                        playersTurn(players[currentPlayer]);
                    }
                    diceImage.setDisable(false);
                    return;
                } else {
                    try {
                        gridPane.add(circle, FindElementsPosition.getColumn(destination.get()), FindElementsPosition.getRow(destination.get()));
                    } catch (Exception ex) {
                        Logger.getLogger(UserScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (currentPawn.getStatus() != PawnStatus.HOME) {
                    if (destinationIndex >= allFieldsArray.length) {
                        destinationIndex = destinationIndex - allFieldsArray.length;
                    }

                    destination = Optional.of(allFieldsArray[destinationIndex]);
                    Optional<Pawn> existingPawn = Optional.empty();
                    existingPawn = Pawn.checkExistingPawn(destination.get(), allPawns);

                    if (existingPawn.isPresent() && existingPawn.get().getColor() == players[currentPlayer].getColor()) {
                        MessageUtils.showAlert(players[currentPlayer].getName()
                                + " , You can not move Your pawn to the place of Your other pawn", "Turn");
                        for (Pawn pawn : players[currentPlayer].getPlayersPawns()) {
                            if (currentNumber != 6 && pawn.getStatus() == PawnStatus.OUT) {
                                pawn.getCircle().setDisable(false);
                            } else if (currentNumber == 6 && pawn.getStatus() != PawnStatus.HOME) {
                                pawn.getCircle().setDisable(false);
                            }
                        }
                        return;
                    } else if (existingPawn.isPresent() && existingPawn.get().getColor() != players[currentPlayer].getColor()) {
                        gridPane.getChildren().remove(existingPawn.get().getCircle());
                        existingPawn.get().setStatus(PawnStatus.SQUARE);
                        try {
                            gridPane.add(existingPawn.get().getCircle(), FindElementsPosition.getColumn(existingPawn.get().getSquarePosition()),
                                    FindElementsPosition.getRow(existingPawn.get().getSquarePosition()));
                        } catch (Exception ex) {
                        }
                        currentPawn.setStatus(PawnStatus.OUT);
                        gridPane.add(circle, FindElementsPosition.getColumn(destination.get()), FindElementsPosition.getRow(destination.get()));
                    } else {
                        currentPawn.setStatus(PawnStatus.OUT);
                        gridPane.add(circle, FindElementsPosition.getColumn(destination.get()), FindElementsPosition.getRow(destination.get()));
                    }
                }

            }
        } catch (Exception ex) {
            Logger.getLogger(UserScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (turns == 0) {
            currentPlayer++;

            if (currentPlayer >= players.length) {
                currentPlayer = 0;
            }
            Player p = players[currentPlayer];
            playersTurn(players[currentPlayer]);
        }

        diceImage.setDisable(false);

    }

    private Optional<Shape> checkFinish(int destinationIndex) {
        int finishIndex;

        switch (currentPawn.getColor()) {
            case YELLOW:
                currentFinishIndex = finishYellowIndex;
                if (destinationIndex > finishYellowIndex) {

                    finishIndex = destinationIndex - finishYellowIndex;
                    finishIndex--;
                    if (finishIndex > homeFieldsYellow.length - 1) {
                        MessageUtils.showAlert(players[currentPlayer].getName() + ", Your number is too big", "Turn");
                        return Optional.empty();
                    }
                    Optional<Pawn> p = Pawn.checkExistingPawn(homeFieldsYellow[finishIndex], allPawns);
                    if (p.isPresent()) {
                        MessageUtils.showAlert(players[currentPlayer].getName()
                                + ", You can not move Your pawn to the place of Your other Pawn", "Turn");
                        return Optional.empty();
                    }

                    return Optional.of(homeFieldsYellow[finishIndex]);
                }

                break;
            case GREEN:
                currentFinishIndex = finishGreenIndex;
                if (destinationIndex - currentNumber <= finishGreenIndex) {
                    if (destinationIndex >= finishGreenIndex) {
                        finishIndex = destinationIndex - finishGreenIndex;
                        finishIndex--;

                        if (finishIndex < 0) {
                            return Optional.empty();
                        }

                        if (finishIndex > homeFieldsGreen.length - 1) {
                            MessageUtils.showAlert(players[currentPlayer].getName()
                                    + ", Your number is too big", "Turn");
                            return Optional.empty();
                        }

                        Optional<Pawn> p = Pawn.checkExistingPawn(homeFieldsGreen[finishIndex], allPawns);
                        if (p.isPresent()) {
                            MessageUtils.showAlert(players[currentPlayer].getName()
                                    + ", You can not move Your pawn to the place of Your other Pawn", "Turn");
                            return Optional.empty();
                        }
                        return Optional.of(homeFieldsGreen[finishIndex]);
                    }

                }

                break;
            case RED:
                currentFinishIndex = finishRedIndex;
                if (destinationIndex - currentNumber <= finishRedIndex) {
                    if (destinationIndex >= finishRedIndex) {
                        finishIndex = destinationIndex - finishRedIndex;
                        finishIndex--;

                        if (finishIndex < 0) {
                            return Optional.empty();
                        }

                        if (finishIndex > homeFieldsRed.length - 1) {
                            MessageUtils.showAlert(players[currentPlayer].getName()
                                    + ", Your number is too big", "Turn");
                            return Optional.empty();
                        }
                        Optional<Pawn> p = Pawn.checkExistingPawn(homeFieldsRed[finishIndex], allPawns);
                        if (p.isPresent()) {
                            MessageUtils.showAlert(players[currentPlayer].getName()
                                    + ", You can not move Your pawn to the place of Your other Pawn", "Turn");
                            return Optional.empty();
                        }
                        return Optional.of(homeFieldsRed[finishIndex]);
                    }

                }

                break;
            case BLUE:
                currentFinishIndex = finishBlueIndex;
                if (destinationIndex - currentNumber <= finishBlueIndex) {
                    if (destinationIndex >= finishBlueIndex) {
                        finishIndex = destinationIndex - finishBlueIndex;
                        finishIndex--;

                        if (finishIndex < 0) {
                            return Optional.empty();
                        }

                        if (finishIndex > homeFieldsBlue.length - 1) {
                            MessageUtils.showAlert(players[currentPlayer].getName()
                                    + ", Your number is too big", "Turn");
                            return Optional.empty();
                        }
                        Optional<Pawn> p = Pawn.checkExistingPawn(homeFieldsBlue[finishIndex], allPawns);
                        if (p.isPresent()) {
                            MessageUtils.showAlert(players[currentPlayer].getName()
                                    + ", You can not move Your pawn to the place of Your other Pawn", "Turn");
                            return Optional.empty();
                        }
                        return Optional.of(homeFieldsBlue[finishIndex]);
                    }

                }

                break;

        }
        return Optional.empty();
    }

    public void showRules() {
        RulesOfGame.showRules();
    }

    public void serialize() {
        try {
            File file = FileUtils.saveFileDialog(btnSerialization.getScene().getWindow(), "ser");
            if (file != null) {
                State state = new State(players, currentPlayer, turns);
                SerializationUtils.write(state, file.getAbsolutePath());
            }
            MessageUtils.showAlert("Data successfully saved in file", "Saved");
        } catch (IOException ex) {
            MessageUtils.showError("Could not save the data", "Save Error");
            Logger.getLogger(UserScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendState() {
        State state = new State(players, currentPlayer, turns);
        clientThread.send(state);
    }

    @FXML
    private void deserialize() {
        File file = FileUtils.uploadFileDialog(btnDeserialization.getScene().getWindow(), "ser");
        if (file != null) {
            try {
                State state = (State) SerializationUtils.read(file.getAbsolutePath());
                load(state);
            } catch (IOException | ClassNotFoundException ex) {
                MessageUtils.showError("Could not load the data", "Load Error");
                Logger.getLogger(UserScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateState(State state) {
        State currentState = state;
        refreshGame(currentState);
    }

    public void refreshGame(State currentState) {
        load(currentState);
    }

    private void load(State state) {
        currentPlayer = state.getCurrentPlayer();
        turns = state.getTurns();
        setPawns(state.getPlayers().length);
        for (Player savedPlayer : state.getPlayers()) {
            for (Player existingPlayer : players) {

                if (savedPlayer.getName().equals(existingPlayer.getName())) {

                    for (Pawn savedPawn : savedPlayer.getPlayersPawns()) {

                        for (Pawn existingPawn : existingPlayer.getPlayersPawns()) {
                            if (Objects.equals(savedPawn.getShapeId(), existingPawn.getShapeId())) {
                                existingPawn.setStatus(savedPawn.getStatus());
                                try {
                                    gridPane.add(existingPawn.getCircle(), savedPawn.getColumn(), savedPawn.getRow());
                                } catch (Exception ex) {
                                    
                                    Logger.getLogger(UserScreenController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                            }
                        } // end foreach existing pawn

                    }// end foreach saved pawn

                } // end if saved player is exsiting player

            } // end foreach existing player

        } // end foreach saved player

        
        goPlay();
    }

    public void generateDoc() throws IntrospectionException {
        ReflectionUtils.fillMaps();
        MessageUtils.showAlert("Documentation is generated", "Documentation");

    }

    public void postMessage(String message) {
        Platform.runLater(() -> {
            addMessage(message);
        });
    }

    private void addMessage(String message) {
        Label label = new Label();
        label.setFont(new Font(15));
        label.setText(message);
        if (message.contains(thisPlayer.getName())) {
            label.setStyle("-fx-font-weight:bold;");
        }
        messages.add(label);
        moveScrollPane();
    }

    private void moveScrollPane() {
        spContainer.applyCss();
        spContainer.layout();
        spContainer.setVvalue(1D);
    }

    @FXML
    private void onEnterPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            sendChatMessage();
        }
    }

    @FXML
    private void sendChatMessage() {
        if (tfMessage.getText().trim().length() > 0) {
            chatClient.sendMessage(String.format("%s: %s", thisPlayer.getName(), tfMessage.getText().trim()));
            tfMessage.clear();
        }
    }
    
    
    private List<State> replayStates;
    private boolean isReplay = false;
    private int replayIndex = 0;
    
    @FXML
    public void loadXml(){
        
        try{
            replayStates = DOMUtils.loadGame();
            isReplay = true;
            
            replayState();
            
           
        }
        catch(Exception ex){
             MessageUtils.showError(ex.getMessage(), "Error");
        }
        
    }
    
    private void replayState() {
       if(replayIndex >= replayStates.size()) {
           replayIndex = 0;
           replayStates = null;
           isReplay = false;
           
           MessageUtils.showAlert("Replay finished", "Replay Game");
           return;
       }
        
       State state = replayStates.get(replayIndex);
       
       PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
       pause.setOnFinished(event -> {
           replayIndex++;
            replayState();
       });

        load(state);
        pause.play();
    }

}
