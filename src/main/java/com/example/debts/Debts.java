package com.example.debts;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.util.Arrays;

public class Debts extends Application
{
    @FXML
    Scene scene;
    @FXML
    Label label2change;
    @FXML
    Label label3change;
    @FXML
    Label label4change;
    @FXML
    Label label5change;
    @FXML
    Label label6change;
    @FXML
    Label label7change;
    @FXML
    Label label8change;
    @FXML
    Label label9change;
    @FXML
    Label label10change;
    @FXML
    Label label11numberof;
    @FXML
    TextField textfield1change;
    @FXML
    TextField textfield2change;
    @FXML
    TextField textfield3change;
    @FXML
    TextField textfield4change;
    @FXML
    TextField textfield5change;
    @FXML
    TextField textfield6change;
    @FXML
    TextField textfield7change;
    @FXML
    TextField textfield8change;
    @FXML
    TextField textfield9search;
    @FXML
    ComboBox combobox1предмет;
    @FXML
    ComboBox combobox2группа;
    @FXML
    TableView<String[]> tableview1audiolibrary;
    @FXML
    Button button1преподаватели;
    @FXML
    Button button2группы;
    @FXML
    Button button3студенты;
    @FXML
    Button button4предметы;
    @FXML
    Button button5заданияидолги;
    @FXML
    Button button6добавить;
    @FXML
    Button button7удалить;

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Debts.class.getResource("Debts.fxml"));
        System.setProperty("prism.lcdtext", "false");
        scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("/ContextMenu.css");
        stage.setTitle("Долги");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }

    public void initialize()
    {
        button5заданияидолги.fire();
    }

    int tabl, ncombobox, norow;
    ObservableList<String[]> obslist = FXCollections.observableArrayList();

    public void SeveralCombobox(ComboBox combobox1, ComboBox combobox2, int cnum1, int cnum2)
    {
        ncombobox = 0;
        try
        {
            obslist.clear();
            obslist.addAll(Arrays.asList(DebtsDB.SELECT(tabl)));
        }
        catch (Exception e)
        {
            throw new RuntimeException();
        }
        if (combobox2.getValue() != null)
                Filtering(combobox2, cnum2);
        Filtering(combobox1, cnum1);
    }

    public void TextfieldClear()
    {
        textfield1change.setText("");
        textfield2change.setText("");
        textfield3change.setText("");
        textfield4change.setText("");
        textfield5change.setText("");
        textfield6change.setText("");
        textfield7change.setText("");
        textfield8change.setText("");
    }

    public void Filtering(ComboBox combobox, int cnum)
    {
        int no = 0;

        if (combobox1предмет.getValue() != null && ncombobox == 1)
            SeveralCombobox(combobox1предмет, combobox2группа, 0, 3);
        if (combobox2группа.getValue() != null && ncombobox == 1)
            SeveralCombobox(combobox2группа, combobox1предмет, 3, 0);

        tableview1audiolibrary.getSelectionModel().select(0);
        for (int i = 0; i < tableview1audiolibrary.getItems().size(); i++)
        {
            if (cnum == 0)
                if (!String.valueOf(combobox.getValue()).equals(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum)))
                {
                    tableview1audiolibrary.getItems().remove(i);
                    i -= 1;
                }
                else
                    no += 1;
            else if (cnum == 3)
            {
                if ((!String.valueOf(combobox.getValue()).equals(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum)) && !String.valueOf(combobox.getValue()).equals("-")) || ((String.valueOf(combobox.getValue()).equals("-")) && !String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum)).equals("null")))
                {
                    tableview1audiolibrary.getItems().remove(i);
                    i -= 1;
                }
                else
                    no += 1;
            }
            tableview1audiolibrary.getSelectionModel().select(i + 1);
        }
        label11numberof.setText("Количество заданий (с учетом долгов студентов): " + no);

        TextfieldClear();
        tableview1audiolibrary.getSelectionModel().select(null);
    }

    public void Numberof(int norow)
    {
        int fno = 0, sno = 0;

        for (int i = 0; i < tableview1audiolibrary.getItems().size(); i++)
        {
            tableview1audiolibrary.getSelectionModel().select(i);
            fno += 1;
        }

        if (tabl == 2 || tabl == 4)
        {
            for (int i = 0; i < tableview1audiolibrary.getItems().size(); i++)
            {
                tableview1audiolibrary.getSelectionModel().select(i);
                sno += Integer.parseInt(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(norow));
            }
            if (tabl == 2)
                label11numberof.setText("Количество групп: " + fno + "; количество студентов: " + sno);
            else if (tabl == 4)
                label11numberof.setText("Количество предметов: " + fno + "; количество заданий: " + sno);
        }
        else if (tabl == 1)
            label11numberof.setText("Количество преподавателей: " + fno);
        else if (tabl == 3)
            label11numberof.setText("Количество студентов: " + fno);
        else if (tabl == 5)
            label11numberof.setText("Количество заданий (с учетом долгов студентов): " + fno);

        TextfieldClear();
        tableview1audiolibrary.getSelectionModel().select(null);
    }

    int rfstnum, rsndnum, rtrdnum, rfthnum;

    public void Searching(ObservableList<String[]> obslist, int norow)
    {
        textfield9search.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent ke) ->
        {
            if (ke.getCode() == KeyCode.BACK_SPACE || ke.getCode() == KeyCode.SPACE || textfield9search.getSelectedText().length() == textfield9search.getText().length())
            {
                try
                {
                    obslist.clear();
                    obslist.addAll(Arrays.asList(DebtsDB.SELECT(tabl)));
                }
                catch (Exception e)
                {
                    throw new RuntimeException();
                }
                if (combobox1предмет.getValue() != null || combobox2группа.getValue() != null)
                {
                    ncombobox = 1;
                    Filtering(null, -1);
                }
            }
        });

        tableview1audiolibrary.getSelectionModel().select(0);
        for (int i = 0; i < tableview1audiolibrary.getItems().size(); i++)
        {
            if (!String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(rfstnum)).toLowerCase().contains(textfield9search.getText().toLowerCase()) && !String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(rsndnum)).toLowerCase().contains(textfield9search.getText().toLowerCase()) && !String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(rtrdnum)).toLowerCase().contains(textfield9search.getText().toLowerCase()) && !String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(rfthnum)).toLowerCase().contains(textfield9search.getText().toLowerCase()))
            {
                tableview1audiolibrary.getItems().remove(i);
                i -= 1;
            }
            tableview1audiolibrary.getSelectionModel().select(i + 1);
        }

        tableview1audiolibrary.getSelectionModel().select(null);
        Numberof(norow);
    }

    public void TextfieldEditable(Boolean edit)
    {
        textfield1change.setEditable(edit);
        textfield2change.setEditable(edit);
        textfield3change.setEditable(edit);
        textfield4change.setEditable(edit);
        textfield5change.setEditable(edit);
        textfield6change.setEditable(edit);
        textfield7change.setEditable(edit);
        textfield8change.setEditable(edit);
    }

    public void ColumnProperties(TableColumn col1, TableColumn col2, TableColumn col3, TableColumn col4, TableColumn col5, TableColumn col6, TableColumn col7, TableColumn col8)
    {
        TableColumn col = col1;
        col.setResizable(false);
        col.setReorderable(false);
        if (col2 != null)
        {
            col1 = col2;
            ColumnProperties(col1, null, col3, col4, col5, col6, col7, col8);
        }
        if (col3 != null)
        {
            col1 = col3;
            ColumnProperties(col1, null, null, col4, col5, col6, col7, col8);
        }
        if (col4 != null)
        {
            col1 = col4;
            ColumnProperties(col1, null, null, null, col5, col6, col7, col8);
        }
        if (col5 != null)
        {
            col1 = col5;
            ColumnProperties(col1, null, null, null, null, col6, col7, col8);
        }
        if (col6 != null)
        {
            col1 = col6;
            ColumnProperties(col1, null, null, null, null, null, col7, col8);
        }
        if (col7 != null)
        {
            col1 = col7;
            ColumnProperties(col1, null, null, null, null, null, null, col8);
        }
        if (col8 != null)
        {
            col1 = col8;
            ColumnProperties(col1, null, null, null, null, null, null, null);
        }
    }

    public void Clipboard(Clipboard cb, ClipboardContent cbc, TextField textfield)
    {
        cbc.putString(textfield.getSelectedText());
        cb.setContent(cbc);
    }

    public void ContextMenu(TextField textfield)
    {
        ContextMenu cm = new ContextMenu();
        MenuItem cmmi1 = new MenuItem("Copy");
        MenuItem cmmi2 = new MenuItem("Cut");
        MenuItem cmmi3 = new MenuItem("Paste");
        ContextMenu cm1 = new ContextMenu();
        MenuItem cm1mi1 = new MenuItem("Copy");
        cm.getItems().addAll(cmmi1, cmmi2, cmmi3);
        cm1.getItems().add(cm1mi1);
        if (tabl == 1 || tabl == 2 || tabl == 3 || tabl == 4)
            textfield.setContextMenu(cm1);
        else
            textfield.setContextMenu(cm);
        textfield9search.setContextMenu(cm);

        Clipboard cb = Clipboard.getSystemClipboard();
        ClipboardContent cbc = new ClipboardContent();
        cmmi1.setOnAction(e -> Clipboard(cb, cbc, textfield));
        cm1mi1.setOnAction(e -> Clipboard(cb, cbc, textfield));
        cmmi2.setOnAction(ecm ->
        {
            int sel;

            Clipboard(cb, cbc, textfield);
            sel = textfield.getCaretPosition() - textfield.getSelectedText().length();
            textfield.setText(textfield.getText().replace(textfield.getSelectedText(), ""));
            textfield.positionCaret(sel);
        });
        cmmi3.setOnAction(ecm ->
        {
            if (textfield.getSelectedText().length() > 0)
            {
                int sel;

                sel = (textfield.getCaretPosition() - textfield.getSelectedText().length()) + cb.getString().length();
                textfield.setText(textfield.getText().replace(textfield.getSelectedText(), cb.getString()));
                textfield.positionCaret(sel);
            }
            else
            {
                textfield.insertText(textfield.getCaretPosition(), cb.getString());
                textfield.positionCaret(textfield.getCaretPosition());
            }
        });
    }

    int nodat;

    public void Table(String tablnam, String nam1, String nam2, String nam3, String nam4, String nam5, String nam6, String nam7, String nam8, int siz1, int siz2, int siz3, int siz4, int siz5, int siz6, int siz7, int siz8) throws Exception
    {
        obslist.clear();

        ContextMenu(textfield1change);
        ContextMenu(textfield2change);
        ContextMenu(textfield3change);
        ContextMenu(textfield4change);
        ContextMenu(textfield5change);
        ContextMenu(textfield6change);
        ContextMenu(textfield7change);
        ContextMenu(textfield8change);
        ContextMenu(textfield9search);
        label6change.setVisible(false);
        label7change.setVisible(false);
        label8change.setVisible(false);
        label9change.setVisible(false);
        label10change.setVisible(false);
        textfield4change.setVisible(false);
        textfield5change.setVisible(false);
        textfield6change.setVisible(false);
        textfield7change.setVisible(false);
        textfield8change.setVisible(false);
        label3change.setVisible(true);
        label4change.setVisible(true);
        label5change.setVisible(true);
        textfield1change.setVisible(true);
        textfield2change.setVisible(true);
        textfield3change.setVisible(true);
        if (tabl == 1 || tabl == 2 || tabl == 3 || tabl == 5)
        {
            label6change.setVisible(true);
            textfield4change.setVisible(true);
            if (tabl == 1 || tabl == 3)
            {
                label7change.setVisible(true);
                textfield5change.setVisible(true);
                label8change.setVisible(true);
                textfield6change.setVisible(true);
                label9change.setVisible(true);
                textfield7change.setVisible(true);
                if (tabl == 3)
                {
                    label10change.setVisible(true);
                    textfield8change.setVisible(true);
                }
            }
        }
        textfield9search.setText("");
        combobox1предмет.setVisible(false);
        combobox2группа.setVisible(false);
        combobox1предмет.setValue(null);
        combobox2группа.setValue(null);
        if (tabl == 5)
        {
            combobox1предмет.setVisible(true);
            combobox2группа.setVisible(true);
            TextfieldEditable(true);
            button6добавить.setVisible(true);
            button7удалить.setVisible(true);
        }
        else
        {
            TextfieldEditable(false);
            button6добавить.setVisible(false);
            button7удалить.setVisible(false);
        }

        tableview1audiolibrary.getColumns().clear();
        tableview1audiolibrary.getSelectionModel().setCellSelectionEnabled(true);
        obslist.addAll(Arrays.asList(DebtsDB.SELECT(tabl)));
        TableColumn first = new TableColumn<>(nam1);
        TableColumn second = new TableColumn<>(nam2);
        tableview1audiolibrary.getColumns().add(first);
        tableview1audiolibrary.getColumns().add(second);
        first.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[0]));
        second.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[1]));
        first.setPrefWidth(siz1);
        second.setPrefWidth(siz2);
        ColumnProperties(first, second, null, null, null, null, null, null);
        label2change.setText(tablnam);
        label3change.setText(nam1);
        label4change.setText(nam2);
        TableColumn third = new TableColumn<>(nam3);
        tableview1audiolibrary.getColumns().add(third);
        third.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[2]));
        third.setPrefWidth(siz3);
        ColumnProperties(first, second, third, null, null, null, null, null);
        label5change.setText(nam3);
        if (tabl == 1 || tabl == 2 || tabl == 3 || tabl == 5)
        {
            TableColumn fourth = new TableColumn<>(nam4);
            tableview1audiolibrary.getColumns().add(fourth);
            fourth.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[3]));
            fourth.setPrefWidth(siz4);
            ColumnProperties(first, second, third, fourth, null, null, null, null);
            label6change.setText(nam4);
            if (tabl == 1 || tabl == 2 || tabl == 3)
            {
                if (tabl == 1 || tabl == 3)
                {
                    label7change.setText(nam5);
                    label8change.setText(nam6);
                    label9change.setText(nam7);
                    TableColumn fifth = new TableColumn<>(nam5);
                    tableview1audiolibrary.getColumns().add(fifth);
                    fifth.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[4]));
                    fifth.setPrefWidth(siz5);
                    ColumnProperties(first, second, third, fourth, fifth, null, null, null);
                    TableColumn six = new TableColumn<>(nam6);
                    tableview1audiolibrary.getColumns().add(six);
                    six.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[5]));
                    six.setPrefWidth(siz6);
                    ColumnProperties(first, second, third, fourth, fifth, six, null, null);
                    TableColumn seven = new TableColumn<>(nam7);
                    tableview1audiolibrary.getColumns().add(seven);
                    seven.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[6]));
                    seven.setPrefWidth(siz7);
                    ColumnProperties(first, second, third, fourth, fifth, six, seven, null);
                    if (tabl == 3)
                    {
                        label10change.setText(nam8);
                        TableColumn eight = new TableColumn<>(nam8);
                        tableview1audiolibrary.getColumns().add(eight);
                        eight.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[7]));
                        eight.setPrefWidth(siz8);
                        ColumnProperties(first, second, third, fourth, fifth, six, seven, eight);
                    }
                }
            }
        }
        tableview1audiolibrary.setItems(obslist);
        Numberof(norow);

        tableview1audiolibrary.getSelectionModel().selectedItemProperty().addListener((s, os, ns) ->
        {
            if (ns != null)
            {
                textfield1change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(0));
                textfield2change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(1));
                if (nodat == 3 || nodat == 4 || nodat == 7 || nodat == 8)
                {
                    textfield3change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(2));
                    if (nodat == 4 || nodat == 7 || nodat == 8)
                    {
                        textfield4change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(3));
                        if (nodat == 7 || nodat == 8)
                        {
                            textfield5change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(4));
                            textfield6change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(5));
                            textfield7change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(6));
                            if (nodat == 8)
                                textfield8change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(7));
                        }
                    }
                    switch (nodat)
                    {
                        case 4:
                            textfield4change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(3));
                            break;
                        case 7:
                            textfield5change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(4));
                            textfield6change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(5));
                            textfield7change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(6));
                            break;
                        case 8:
                            textfield8change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(7));
                            break;
                    }
                }
            }
            if (String.valueOf(textfield4change.getText()).equals("0"))
                textfield4change.setText("");
        });

        textfield9search.textProperty().addListener((o, ov, nv) -> Searching(obslist, norow));
    }

    public void button1преподаватели() throws Exception
    {
        tabl = 1;
        nodat = 7;
        rfstnum = 0;
        rsndnum = 1;
        rtrdnum = 6;
        rfthnum = 0;
        norow = 0;
        Table("Преподаватель","Фамилия", "Имя", "Отчество", "Адрес", "Почта", "Телефон", "Категория", "", 150, 150, 150, 175, 135, 110, 105, 0);
    }

    public void button2группы() throws Exception
    {
        tabl = 2;
        nodat = 4;
        rfstnum = 0;
        rsndnum = 1;
        rtrdnum = 3;
        rfthnum = 0;
        norow = 2;
        Table("Группа","Номер", "Специальность", "Количество студентов", "Куратор", "", "", "", "", 75, 600, 175, 138, 0, 0, 0, 0);
    }

    public void button3студенты() throws Exception
    {
        tabl = 3;
        nodat = 8;
        rfstnum = 0;
        rsndnum = 1;
        rtrdnum = 7;
        rfthnum = 0;
        norow = 0;
        Table("Студент", "Фамилия", "Имя", "Отчество", "Дата рождения", "Адрес", "Почта", "Телефон", "Группа", 115, 115, 115, 135, 175, 135, 110, 75);
    }

    public void button4предметы() throws Exception
    {
        tabl = 4;
        nodat = 3;
        rfstnum = 0;
        rsndnum = 2;
        rtrdnum = 0;
        rfthnum = 0;
        norow = 1;
        Table("Предмет","Название", "Количество заданий", "Преподаватель", "", "", "", "", "", 650, 175, 150, 0, 0, 0, 0, 0);
    }

    public void ComboboxAction(ComboBox combobox, int cnum)
    {
        textfield9search.setText("");

        if (combobox.getValue() == null)
            ncombobox = 0;
        else
            ncombobox = 1;
        Filtering(combobox, cnum);
    }

    public void button5заданияидолги() throws Exception
    {
        tabl = 5;
        nodat = 4;
        rfstnum = 0;
        rsndnum = 1;
        rtrdnum = 2;
        rfthnum = 3;
        norow = 0;
        Table("Задание и долги","Предмет", "Задание", "Студент с долгом", "Группа", "", "", "", "", 650, 100, 150, 75, 0, 0, 0, 0);

        combobox1предмет.setItems(FXCollections.observableArrayList("Внедрение и поддержка компьютерных систем",
                                                                        "Иностранный язык",
                                                                        "Компьютерные сети",
                                                                        "Обеспечение качества функционирования компьютерных систем",
                                                                        "Основы философии",
                                                                        "Психология общения",
                                                                        "Сертификация информационных систем",
                                                                        "Технология разработки и защиты баз данных",
                                                                        "Управление и автоматизация баз данных",
                                                                        "Физическая культура"));
        combobox2группа.setItems(FXCollections.observableArrayList("-",
                                                                       "315",
                                                                       "327",
                                                                       "329"));
        combobox1предмет.setOnAction(e -> ComboboxAction(combobox1предмет, 0));
        combobox2группа.setOnAction(e -> ComboboxAction(combobox2группа, 3));
    }

    int chang;

    public void ChangeData() throws Exception
    {
        String col1, col2, col3, col4;

        Alert al;
        al = new Alert(Alert.AlertType.NONE);
        DialogPane dp;
        dp = al.getDialogPane();
        dp.getButtonTypes().add(ButtonType.OK);
        dp.getStylesheets().add("/Alert.css");

        String[] datarr = new String[4];
        datarr[0] = textfield3change.getText();
        datarr[1] = textfield4change.getText();
        datarr[2] = textfield2change.getText();
        datarr[3] = textfield1change.getText();
        if (chang == 6)
            al.setContentText(DebtsDB.INSERT(datarr));
        else if (chang == 7)
            al.setContentText(DebtsDB.DELETE(datarr));
        al.show();

        col1 = datarr[3];
        col2 = datarr[2];
        col3 = datarr[0];
        col4 = datarr[1];
        obslist.clear();
        obslist.addAll(Arrays.asList(DebtsDB.SELECT(tabl)));
        tableview1audiolibrary.getSelectionModel().select(0);
        for (int i = 0; i < tableview1audiolibrary.getItems().size(); i++)
        {
            if (!String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(0)).equals(col1) || !String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(1)).equals(col2) || !String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(2)).equals(col3) || !String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(3)).equals(col4))
            {
                tableview1audiolibrary.getItems().remove(i);
                i -= 1;
            }
            tableview1audiolibrary.getSelectionModel().select(i + 1);
        }
        Numberof(0);
        textfield9search.setText("");
    }

    public void button6добавить() throws Exception
    {
        chang = 6;
        ChangeData();
    }

    public void button7удалить() throws Exception
    {
        chang = 7;
        ChangeData();
    }
}