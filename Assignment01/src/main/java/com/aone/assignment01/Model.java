package com.aone.assignment01;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;

public class Model {
    @FXML
    private StringProperty id;
    @FXML
    private IntegerProperty italian;
    @FXML
    private IntegerProperty indian;
    @FXML
    private IntegerProperty japanese;
    @FXML
    private IntegerProperty mexican;

    public Model(String id, Integer italian, Integer indian, Integer japanese, Integer mexican) {
        this.id = new SimpleStringProperty(id);
        this.italian = new SimpleIntegerProperty(italian);
        this.indian = new SimpleIntegerProperty(indian);
        this.japanese = new SimpleIntegerProperty(japanese);
        this.mexican = new SimpleIntegerProperty(mexican);
    }



    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public Integer getItalian() {
        return italian.get();
    }

    public IntegerProperty italianProperty() {
        return italian;
    }

    public void setItalian(Integer italian) {
        this.italian.set(italian);
    }

    public Integer getIndian() {
        return indian.get();
    }

    public IntegerProperty indianProperty() {
        return indian;
    }

    public void setIndian(Integer indian) {
        this.indian.set(indian);
    }

    public Integer getJapanese() {
        return japanese.get();
    }

    public IntegerProperty japaneseProperty() {
        return japanese;
    }

    public void setJapanese(Integer japanese) {
        this.japanese.set(japanese);
    }

    public Integer getMexican() {
        return mexican.get();
    }

    public IntegerProperty mexicanProperty() {
        return mexican;
    }

    public void setMexican(Integer mexican) {
        this.mexican.set(mexican);
    }
}
