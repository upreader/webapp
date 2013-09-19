package com.upreader.dto;

import java.util.ArrayList;

public class SelectItem {
    private String value;
    private String label;
    private ArrayList<SelectItem> cascadingSelects;

    public SelectItem(){
    }

    public SelectItem(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public SelectItem(ArrayList<SelectItem> cascadingSelects, String value, String label) {
        this.cascadingSelects = cascadingSelects;
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<SelectItem> getCascadingSelects() {
        return cascadingSelects;
    }

    public void setCascadingSelects(ArrayList<SelectItem> cascadingSelects) {
        this.cascadingSelects = cascadingSelects;
    }
}
