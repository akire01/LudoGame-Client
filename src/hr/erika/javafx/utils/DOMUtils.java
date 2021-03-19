/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.erika.javafx.utils;

import hr.erika.javafx.data.Color;
import hr.erika.javafx.data.Pawn;
import hr.erika.javafx.data.PawnStatus;
import hr.erika.javafx.data.Player;
import hr.erika.javafx.state.State;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author ER
 */
public class DOMUtils {

    private static final String FILENAME_GAME = "game.xml";
    
    private static final List<State> listOfStates = new ArrayList<>();
    public static void saveStates(State state){
        listOfStates.add(state);
        saveGame(listOfStates);
    }

    private static void saveGame(List<State> states) {

        Document doc = createXmlFile();
       
        for (State state : states) {
            appendNodeToDoc(doc, state);
        }
        
        try {
            writeDocToFile(doc);
        } catch (TransformerException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private static Document createXmlFile() {
        
        Document doc = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            try {
                doc = dbBuilder.parse(System.getProperty("user.home") + "/Desktop/" + FILENAME_GAME);
                
            } catch (SAXException ex) {
                Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return doc;

    }
    
    private static void appendNodeToDoc(Document doc, State state){
      
        Element rootElement = doc.getDocumentElement();
        
        Element players = doc.createElement("players");
        rootElement.appendChild(players);

        Element currentPlayerOnTheMove = doc.createElement("currentPlayer");
        Attr attrCurrentName = doc.createAttribute("Index");
        attrCurrentName.setValue(Integer.toString(state.getCurrentPlayer()));
        currentPlayerOnTheMove.setAttributeNode(attrCurrentName);
        //-----------------------------//
        Attr attrTurns = doc.createAttribute("NumberOfTurns");
        attrTurns.setValue(Integer.toString(state.getTurns()));
        currentPlayerOnTheMove.setAttributeNode(attrTurns);
        players.appendChild(currentPlayerOnTheMove);
       

        for (int i = 0; i < state.getPlayers().length; i++) {
            Element player = doc.createElement("player");
            Attr attrColor = doc.createAttribute("color");
            attrColor.setValue(state.getPlayers()[i].getColor().name());
            player.setAttributeNode(attrColor);
            Attr attrName = doc.createAttribute("name");
            attrName.setValue(state.getPlayers()[i].getName());
            player.setAttributeNode(attrName);
            writePlayersPawns(doc, player, i, state);
            players.appendChild(player);
        }
        System.out.println("My xml done");
    }
          

    private static void writePlayersPawns(Document doc, Element element, int i, State state) {
        Player thisPlayer = state.getPlayers()[i];
        Element pawns = doc.createElement("pawns");
        for (int j = 0; j < 4; j++) {
             Pawn currentPawn = thisPlayer.getPlayersPawns()[j];
             currentPawn.refreshRowAndColumn();
            
             Element pawn = doc.createElement("pawn");
             pawn.appendChild(doc.createTextNode(currentPawn.getShapeId()));
             //-----------------------------//
             Attr attrStatus = doc.createAttribute("status");
             attrStatus.setValue(currentPawn.getStatus().toString());
             pawn.setAttributeNode(attrStatus);
              //-----------------------------//
             Attr attrColumn = doc.createAttribute("column");
             attrColumn.setValue(Integer.toString(currentPawn.getColumn()));
             pawn.setAttributeNode(attrColumn);
              //-----------------------------//
             Attr attrRow = doc.createAttribute("row");
             attrRow.setValue(Integer.toString(currentPawn.getRow()));
             pawn.setAttributeNode(attrRow);
             
             pawns.appendChild(pawn);
        }
        element.appendChild(pawns);
    }

    private static void writeDocToFile(Document doc) throws TransformerException {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult streamResult =  new StreamResult(new File(System.getProperty("user.home") + "/Desktop/" + FILENAME_GAME));
            transformer.transform(source, streamResult);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List<State> loadGame(){
        
        List<State> states = new ArrayList<>();
        Document doc = readXmlFileToDoc();
        
        states = readXml(doc);
        
        return states;
    }

    private static Document readXmlFileToDoc() {
        
        Document doc = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
             try {
                 doc = dbBuilder.parse(System.getProperty("user.home") + "/Desktop/" + FILENAME_GAME);
                
             } catch (SAXException ex) {
                 Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
             } catch (IOException ex) {
                 Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
             }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doc;
    }

    private static List<State> readXml(Document doc) {
        
        List<State> states = new ArrayList<>();
        
        int currentPlayerIndex;
        int turns;
        List<Player> players = new ArrayList<>();
        Player[] arrayPlayers = new Player[players.size()];
        List<Pawn> pawns = new ArrayList<>();
        
        Element root = doc.getDocumentElement();
        System.out.println(root.getNodeName());
        
      
        NodeList allPlayers = root.getElementsByTagName("players");
        
        for (int i = 0; i < allPlayers.getLength(); i++) {
            Element playersNode = (Element) allPlayers.item(i);
            
            Element currentPlayer = (Element) playersNode.getElementsByTagName("currentPlayer").item(0);
           
            System.out.println("Index: " +  currentPlayer.getAttribute("Index") );
            currentPlayerIndex = Integer.valueOf(currentPlayer.getAttribute("Index"));
            System.out.println("Turns: " +  currentPlayer.getAttribute("NumberOfTurns") );
            turns = Integer.valueOf(currentPlayer.getAttribute("NumberOfTurns"));
            
            NodeList currentAllPlayers = playersNode.getElementsByTagName("player");
            
            for (int j = 0; j < currentAllPlayers.getLength();  j++) {
                Element singlePlayer = (Element) currentAllPlayers.item(j);
                System.out.println("name: " + singlePlayer.getAttribute("name"));
                String playerName = singlePlayer.getAttribute("name");
                System.out.println("color: " + singlePlayer.getAttribute("color"));
                Color color = Color.valueOf(singlePlayer.getAttribute("color"));
                
                for (int k = 0; k < singlePlayer.getElementsByTagName("pawn").getLength(); k++) {
                    System.out.println("name: " + singlePlayer.getElementsByTagName("pawn").item(k).getTextContent());
                    String name = singlePlayer.getElementsByTagName("pawn").item(k).getTextContent();
                    System.out.println("column: " + singlePlayer.getElementsByTagName("pawn").item(k).getAttributes().item(0).getTextContent());
                    Integer column = Integer.parseInt(singlePlayer.getElementsByTagName("pawn").item(k).getAttributes().item(0).getTextContent());
                    System.out.println("row: " + singlePlayer.getElementsByTagName("pawn").item(k).getAttributes().item(1).getTextContent());
                    Integer row = Integer.valueOf(singlePlayer.getElementsByTagName("pawn").item(k).getAttributes().item(1).getTextContent());
                    System.out.println("status: " + singlePlayer.getElementsByTagName("pawn").item(k).getAttributes().item(2).getTextContent());
                    String status = singlePlayer.getElementsByTagName("pawn").item(k).getAttributes().item(2).getTextContent();
                    System.out.println("- - - - - - - - -");
                    
                    PawnStatus realStatus = null;
                    switch (status) {
                        case "SQUARE":
                            realStatus = PawnStatus.SQUARE;
                            break;
                        case "OUT":
                            realStatus = PawnStatus.OUT;
                            break;
                        case "HOME":
                            realStatus = PawnStatus.HOME;
                            break;

                    }
                    pawns.add(new Pawn(realStatus, name, column, row));
                }
                
                Pawn[] arrayPawn = new Pawn[4];
                pawns.toArray(arrayPawn);

                players.add(new Player(playerName, color, arrayPawn));
                pawns.clear();

                arrayPlayers = players.toArray(new Player[players.size()]);
                
            }
            State state = new State(arrayPlayers, currentPlayerIndex, turns);
            states.add(state);

            arrayPlayers = new Player[players.size()];
            players.clear();
           
        }
      
       
        return states;
        
    }
    
    public static void deleteXml(){
        Document doc = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            try {
                doc = dbBuilder.parse(System.getProperty("user.home") + "/Desktop/" + FILENAME_GAME);
            } catch (SAXException ex) {
                Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Element root = doc.getDocumentElement();
        NodeList players = root.getChildNodes();
        while(players.getLength() >0){
            Node node = players.item(0);
            node.getParentNode().removeChild(node);
        }
        
        try {
            writeDocToFile(doc);
        } catch (TransformerException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
