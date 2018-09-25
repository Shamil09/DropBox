package com.sc.cloud.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class CheckInController {
    @FXML
    public Hyperlink idMesErLogin;
    @FXML
    private VBox idVBoxCheckIn;
    @FXML
    public TextField idTFLogin;
    @FXML
    Button idButCheckIn;

    private void closeScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Scene scene = new Scene(root);
            ((Stage) idVBoxCheckIn.getScene().getWindow()).setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onBtnCheckIn() {
        String logDir=Global.ROOT_FOLDER+"/"+idTFLogin.getText()+Global.LOGIN_PREF;
        try {
            Files.createDirectories(Paths.get(logDir));
        }catch(InvalidPathException e){
            System.out.println("Ошибка указания пути " + e);
        }catch(NotDirectoryException e){
            System.out.println(logDir+" не является каталогом");
        }catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: "+e);
        }
            closeScene();
    }

    public void onCancel(ActionEvent actionEvent) {
        closeScene();
    }


    //"CON","NUL","COM1","COM2","COM3","COM4","COM5","COM6","COM7","COM8","COM9","LPT1","LPT2",
//                     "LPT3","LPT4","LPT5","LPT6","LPT7","LPT8","LPT9","AUX","PRN","AND"
    private String ch[] = {"/", ":", ".", ",", "\\", "[", "]", ";", "|", "=", "?", "<", ">", "*", "@", "#", "&", "^"};

    public void onKeyPresTF(KeyEvent keyEvent) {
        for (String aCh : ch) {
            if (idTFLogin.getText().contains(aCh)) {
                idMesErLogin.setText("Не допустимый символ в логине: \"" + aCh + "\"");
                idMesErLogin.setVisible(true);
                idButCheckIn.setDisable(true);
                return;
            } else {
                idMesErLogin.setVisible(false);
                idButCheckIn.setDisable(false);
            }
        }
    }
}


//    String c = keyEvent.getText();
//        System.out.println("\\: "+c);
//        if (c.equals("/")) {
//        System.out.println(c);
//        keyEvent.consume();
//    }

