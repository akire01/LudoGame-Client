/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.erika.javafx.app.firstScreen;

import hr.erika.javafx.app.Main;
import hr.erika.javafx.app.userScreen.UserScreenController;
import hr.erika.javafx.data.NumberOfPlayers;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import hr.erika.javafx.utils.RulesOfGame;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author ER
 */
public class FirstScreenController implements Initializable {

    @FXML
    private ComboBox<NumberOfPlayers> playersCombobox;
    @FXML
    private Button goPlaybtn;
    @FXML
    private Button btnOnline;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<NumberOfPlayers> players
                = FXCollections.observableArrayList(
                        Arrays.asList(NumberOfPlayers.values()));
        
        playersCombobox.setItems(players);
        playersCombobox.getSelectionModel().select(NumberOfPlayers.TWO);
    }    
    
    public void showRules(){
        RulesOfGame.showRules();
    }
    
    public void goPlay(){  
        try {
        int numberOfPlayers = playersCombobox.getSelectionModel().getSelectedItem().getValue();
        Stage stage = (Stage) goPlaybtn.getScene().getWindow();
        stage.close();
        
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("userScreen/UserScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        Stage stage2 = new Stage();
        stage2.setTitle("Man Don't Get Mad");
        stage2.setScene(scene);
        UserScreenController controller = fxmlLoader.getController();
        stage2.setOnShown((WindowEvent event) -> {
           
            controller.setPawns(numberOfPlayers);
            controller.showInformation();
        }); 
        stage2.show();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void goOnline(){
        try {
        Stage stage = (Stage) btnOnline.getScene().getWindow();
        stage.close();
        
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("userScreen/UserScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1350, 800);
        Stage stage2 = new Stage();
        stage2.setTitle("Man Don't Get Mad");
        stage2.setScene(scene);
        stage2.setOnCloseRequest(e->
            System.exit(0));
        UserScreenController controller = fxmlLoader.getController();
        stage2.setOnShown((WindowEvent event) -> {
            try {
                controller.initClientThread();
            } catch (IOException ex) {
                Logger.getLogger(FirstScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
         
        }); 
        stage2.show();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
