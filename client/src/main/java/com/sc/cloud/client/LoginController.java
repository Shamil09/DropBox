package com.sc.cloud.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static com.sc.cloud.client.Dialogs.exceptionDialog;
import static com.sc.cloud.client.Global.*;

public class LoginController {
    @FXML
    ComboBox<String> idLogin;
    @FXML
    PasswordField idPassword;
    @FXML
    VBox globParent;
    @FXML
    Button idBtnGoIn;

//    private <T> void filterItems(String filter, ComboBox<T> comboBox,List<T> items) {
//        List<T> filteredItems = new ArrayList<>();
//        for (T item : items) {
//            if (item.toString().toLowerCase().startsWith(filter.toLowerCase())) {
//                filteredItems.add(item);
//            }
//        }
//        comboBox.setItems(FXCollections.observableArrayList(filteredItems));
//    }
    @FXML
    private void initialize() {
        idLogin.setStyle("-fx-font-size:14"); //-fx-font-family: "какой не будь шрифт";
        ObservableList<String> loginsFul=loginsList(ROOT_FOLDER,'D');
        ObservableList<String> logins=FXCollections.observableArrayList();
        for (String entry : loginsFul)
            logins.add(entry.split("@", 2)[0]);
        idLogin.itemsProperty().setValue(logins);
        new AutoCompleteComboBoxListener<>(idLogin);
    }

    private ObservableList<String> loginsList(String dirName, char tl) {
        ObservableList<String> logins = FXCollections.observableArrayList();
        try (DirectoryStream<Path> dirstrm = Files.newDirectoryStream(Paths.get(dirName))) {
            switch (tl) {
                case 'D':case 'd':
                    for (Path entry : dirstrm)
                        if (Files.readAttributes(entry, BasicFileAttributes.class).isDirectory())
                            logins.add(entry.getName(entry.getNameCount() - 1).toString());
                    return logins;
                case 'F':case 'f':
                    for (Path entry : dirstrm)
                        if (Files.readAttributes(entry, BasicFileAttributes.class).isRegularFile())
                            logins.add(entry.getName(entry.getNameCount() - 1).toString());
                    return logins;
                default:
                    for (Path entry : dirstrm) logins.add(entry.getName(entry.getNameCount() - 1).toString());
            }
        } catch (InvalidPathException e) {
            System.out.println("СИСТЕМНАЯ ОШИБКА: указания пути " + e);
            exceptionDialog("СИСТЕМНАЯ ОШИБКА: указания пути ",new InvalidPathException("СИСТЕМНАЯ ОШИБКА: указания пути ", "xnjnj"));
        } catch (NotDirectoryException e) {
            System.out.println("СИСТЕМНАЯ ОШИБКА: " + dirName + " не является каталогом");
        } catch (IOException e) {
            System.out.println("СИСТЕМНАЯ ОШИБКА: ввода-вывода: " + e);
        }
        return logins;
    }

    public void auth() {
        strL = idLogin.getValue().replaceAll("\\s", "");
        //String strFolder = strL+ Global.LOGIN_PREF;
        userFolderDir = strL+ Global.LOGIN_PREF;
        ObservableList<String> logins =loginsList(ROOT_FOLDER,'D');
        int i = 0;
        for (String entry : logins) {
            System.out.println(entry + "=" + userFolderDir + "->" + entry.equals(userFolderDir));
            if (entry.equals(userFolderDir)) {
                Global.key = true;
                globParent.getScene().getWindow().hide();
                break;
            }
        }
        idPassword.clear();
    }

    public void onCheckIn(ActionEvent actionEvent) {
//        globParent.getScene().getWindow().hide();
//        try {
//            Stage stage = new Stage();
//            FXMLLoader loader = new FXMLLoader(Controller.class.getResource("/CheckIn.fxml"));
//            Parent root = loader.load();
//            CheckInController lc = (CheckInController) loader.getController();
//            //lc.id = 100;
//            stage.setTitle("Регистрация");
//            stage.setScene(new Scene(root, 400, 200));
//            stage.initModality(Modality.WINDOW_MODAL);
//            stage.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Controller.btnShowModal(null);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/CheckIn.fxml"));
            Scene scene = new Scene(root);
            ((Stage)globParent.getScene().getWindow()).setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private javafx.event.EventHandler<WindowEvent> closeEventHandler = event ->auth();

    javafx.event.EventHandler<WindowEvent> getCloseEventHandler(){
        return closeEventHandler;
    }

    private void btnActiv(){
        if (idLogin.getValue()==null || idPassword.getText()==null) idBtnGoIn.setDisable(true);
        else if (idLogin.getValue().equals("") || idPassword.getText().equals("")) idBtnGoIn.setDisable(true);
        else idBtnGoIn.setDisable(false);
    }
    //отлавливаем нажатие кл. в поле пароль
    public void onKeyRelePassF(KeyEvent keyEvent) {
        btnActiv();
        System.out.println("Тут ловит "+idPassword.getText());
    }
    //отлавливаем отпускание кл. в поле логин
    public void onKeyPresCBLog(KeyEvent keyEvent) {
        btnActiv();
        System.out.println("Тут НЕ ловит ->"+idPassword.getText());
    }
    //отлавливаем нажатие кл. в поле логин
    public void OnKeyReleasedCBLog(KeyEvent keyEvent) {
        System.out.println("А тут не отлавливает");
    }
}
