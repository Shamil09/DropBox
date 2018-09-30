package com.sc.cloud.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.awt.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.List;

public class CheckInController {
    @FXML
    Hyperlink idMesErLogin;
    @FXML
    Hyperlink idMesErPass;
    @FXML
    VBox idVBoxCheckIn;
    @FXML
    TextField idTFLogin;
    @FXML
    Button idButCheckIn;
    @FXML
    Label idLKolSim;
    @FXML
    PasswordField idPassF1;
    @FXML
    PasswordField idPassF2;

    private boolean action,actionPass1, actionPass2;
    @FXML
    private void initialize() {
        action=actionPass1=actionPass2=false;
    }
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
        String logDir = Global.ROOT_FOLDER + "/" + idTFLogin.getText() + Global.LOGIN_PREF;
        try {
            Files.createDirectories(Paths.get(logDir));
        } catch (InvalidPathException e) {
            System.out.println("Ошибка указания пути " + e);
        } catch (NotDirectoryException e) {
            System.out.println(logDir + " не является каталогом");
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e);
        }
        closeScene();
    }

    public void onCancel(ActionEvent actionEvent) {
        closeScene();
    }


    //"CON","NUL","COM1","COM2","COM3","COM4","COM5","COM6","COM7","COM8","COM9","LPT1","LPT2",
   //"LPT3","LPT4","LPT5","LPT6","LPT7","LPT8","LPT9","AUX","PRN","AND"
    private String searchSymLogin() {
        List<String> ch = Arrays.asList("\\", "/", "(", ")", "[", "]", "<", ">", ":", ".", ",", ";", "|", "=",
                "?", "*", "@", "&", "^", "%");
        StringBuilder s= new StringBuilder();
        for (int i = 0; i < ch.size(); i++)
            if (idTFLogin.getText().contains(ch.get(i))) s.append(" ").append(ch.get(i));
        return s.toString();
    }

    public void onKeyPresTF(KeyEvent keyEvent) {
        String s = searchSymLogin();
        if (action = !idTFLogin.getText().equals("")) {
            if (action = s.equals("")) idMesErLogin.setVisible(false);
            else {
                idMesErLogin.setText("Не допустимый символ в логине: \"" + s + "\"");
                idMesErLogin.setVisible(true);
            }
            if (action = (idTFLogin.getText().length() > 3)) idLKolSim.setTextFill(Color.GREEN);
            else idLKolSim.setTextFill(Color.RED);
        }
        System.out.println("->" + s);
        idButCheckIn.setDisable(!(action && actionPass1 && actionPass2));
    }
    private void passwordMatchCheck(){
        if (actionPass1=idPassF1.getText().equals(idPassF2.getText())){
            idMesErPass.setText("Подтверждение совпадает с паролем");
            idMesErPass.setTextFill(Color.GREEN);
        }else{
            idMesErPass.setText("Подтверждение не совпадает с паролем");
            idMesErPass.setTextFill(Color.RED);
        }
    }
    public void onKeyReleasPass1(KeyEvent keyEvent) {
        actionPass1=!idPassF1.getText().equals("");
        passwordMatchCheck();
        idButCheckIn.setDisable(!(action && actionPass1 && actionPass2));
    }

    public void onKeyReleasPass2(KeyEvent keyEvent) {
        actionPass2=!idPassF2.getText().equals("");
        passwordMatchCheck();
        idButCheckIn.setDisable(!(action && actionPass1 && actionPass2));
    }
}

//    String c = keyEvent.getText();
//        System.out.println("\\: "+c);
//        if (c.equals("/")) {
//        System.out.println(c);
//        keyEvent.consume();
//    }

