package com.sc.cloud.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static com.sc.cloud.client.Dialogs.exceptionDialog;
import static com.sc.cloud.client.Global.*;

public class Controller {
    @FXML
     private TableView<Atributes> tableView;
    @FXML
     private TableColumn<Atributes, String> idColName;
    @FXML
     private TableColumn<Atributes, String> idColType;
    @FXML
     private TableColumn<Atributes, Integer> idColSaze;
    @FXML
     private TableColumn<Atributes, Integer> idColDate;
    @FXML
     private TextField textField;
    @FXML
     private Button btnSend;
    @FXML
     private Label idLabelLog;

    private final String TYPE_DIR="<DIR>";
    private final String TYPE_FILE="file";
    private final String DIR_EXIT_ID="...";

    private String dirName = ROOT_FOLDER+userFolderDir;
    private String inLog=strL;

    public Controller() {
    }

    @FXML
    private void initialize() {
        String typePath;
        ObservableList<Atributes> atribData = FXCollections.observableArrayList();
        tableView.getItems().clear();
        try (DirectoryStream<Path> dirstrm =
                      Files.newDirectoryStream(Paths.get(dirName)) )
        {
            if (!dirName.equals(ROOT_FOLDER+userFolderDir)) {
                atribData.add(new Atributes(DIR_EXIT_ID, "", 0, null));
                tableView.setItems(atribData);
            }
            for (Path entry:dirstrm) {
                BasicFileAttributes attribs=Files.readAttributes(entry,BasicFileAttributes.class);
                if(attribs.isDirectory()) typePath=TYPE_DIR;
                else typePath=TYPE_FILE;
                atribData.add(new Atributes(entry.getName(entry.getNameCount()-1).toString(), typePath, attribs.size(), attribs.lastModifiedTime()));
                // устанавливаем тип и значение которое должно хранится в колонке
                idColName.setCellValueFactory(new PropertyValueFactory<>("name"));
                idColType.setCellValueFactory(new PropertyValueFactory<>("type"));
                idColSaze.setCellValueFactory(new PropertyValueFactory<>("saze"));
                idColDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                // заполняем таблицу данными
                tableView.setItems(atribData);
            }
        }catch(InvalidPathException e){
            exceptionDialog("СИСТЕМНАЯ ОШИБКА: указания пути ",new InvalidPathException("СИСТЕМНАЯ ОШИБКА: указания пути ","еще чтото"));
            System.out.println("Ошибка указания пути " + e);
        }catch(NotDirectoryException e){
            System.out.println(dirName+" не является каталогом");
        }catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: "+e);
        }
        idLabelLog.setText(inLog);
        //событие на выдиление строки
        TableView.TableViewSelectionModel<Atributes> selectionModel = tableView.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //if(newValue != null) //idLabelLog.setText("Selected: "+newValue.getName());
        });
    }

    public void btnClickSend(ActionEvent actionEvent) {
        textField.clear();
        btnSend.setDisable(true);
        textField.requestFocus();
    }

    public void KeyPressedTextField(KeyEvent keyEvent) {
        if (textField.getText().equals("")) btnSend.setDisable(true);
        else btnSend.setDisable(false);
    }

    public void onMouseClick(MouseEvent mouseEvent) {
        Atributes artData = tableView.getSelectionModel().getSelectedItem();
        if (mouseEvent.getClickCount() == 2 && artData != null) {
            if (artData.getType().equals(TYPE_DIR)) {
                inLog = inLog + "\\" + artData.getName();
                dirName = dirName + "\\" + artData.getName();
                initialize();
            } else if (artData.getName().equals(DIR_EXIT_ID)) {
                //преобразуем стоку в файловый путь затеим определяем родительский каталог и
                //преобразуем обрадно в строку и присваиваем в dirname пример: С е:\каталог1\каталог2 получаем е:\каталог1
                inLog = Paths.get(inLog).getParent().toString();
                dirName = Paths.get(dirName).getParent().toString();
                initialize();
            }
        }
    }
    static boolean btnShowAutorizeModal() {
//        try {
//            Stage stage = new Stage();
//            FXMLLoader loader = new FXMLLoader(Controller.class.getResource("/Login.fxml"));
//            Parent root = loader.load();
//            LoginController lc = (LoginController) loader.getController();
//            lc.id = 100;
//            stage.setTitle("Авторизация");
//            stage.setScene(new Scene(root, 400, 200));
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Parent root = null;
        try {
            root = FXMLLoader.load(Controller.class.getResource("/Login.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Global.key;
    }
    private javafx.event.EventHandler<WindowEvent> closeEventHandler = event -> System.out.println("закрылось гл.");

    javafx.event.EventHandler<WindowEvent> getCloseEventHandler(){
        return closeEventHandler;
    }
}
