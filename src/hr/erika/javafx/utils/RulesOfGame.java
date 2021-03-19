/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.erika.javafx.utils;

import hr.erika.javafx.app.Main;
import hr.erika.javafx.app.userScreen.UserScreenController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ER
 */
public class RulesOfGame {
    
    public static void showRules(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("rules/Rules.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage2 = new Stage();
            stage2.setTitle("Rules of the game");
            stage2.setScene(scene);

            stage2.show();
        } catch (IOException ex) {
            Logger.getLogger(UserScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
