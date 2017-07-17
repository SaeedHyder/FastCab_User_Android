package com.app.fastcab.entities;

/**
 * Created by saeedhyder on 7/14/2017.
 */

public class SelectCarEnt {

    private String unselectedCarImage;
    private Integer unselectedCarBackground;
    private String carName;
    private Integer selectedTextColor;
    private Integer UnseelectedTextColor;
    private String selectedCarImage;
    private Integer selectedCarBackground;
    public SelectCarEnt(String unselectedCarImage, Integer unselectedCarBackground, String carName, Integer selectedTextColor, Integer unseelectedTextColor, String selectedCarImage, Integer selectedCarBackground) {
        this.unselectedCarImage = unselectedCarImage;
        this.unselectedCarBackground = unselectedCarBackground;
        this.carName = carName;
        this.selectedTextColor = selectedTextColor;
        UnseelectedTextColor = unseelectedTextColor;
        this.selectedCarImage = selectedCarImage;
        this.selectedCarBackground = selectedCarBackground;
    }

    public Integer getUnselectedCarBackground() {
        return unselectedCarBackground;
    }

    public void setUnselectedCarBackground(Integer unselectedCarBackground) {
        this.unselectedCarBackground = unselectedCarBackground;
    }

    public Integer getSelectedTextColor() {
        return selectedTextColor;
    }

    public void setSelectedTextColor(Integer selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }

    public Integer getUnseelectedTextColor() {
        return UnseelectedTextColor;
    }

    public void setUnseelectedTextColor(Integer unseelectedTextColor) {
        UnseelectedTextColor = unseelectedTextColor;
    }

    public String getSelectedCarImage() {
        return selectedCarImage;
    }

    public void setSelectedCarImage(String selectedCarImage) {
        this.selectedCarImage = selectedCarImage;
    }

    public Integer getSelectedCarBackground() {
        return selectedCarBackground;
    }

    public void setSelectedCarBackground(Integer selectedCarBackground) {
        this.selectedCarBackground = selectedCarBackground;
    }



    public String getUnselectedCarImage() {
        return unselectedCarImage;
    }

    public void setUnselectedCarImage(String unselectedCarImage) {
        this.unselectedCarImage = unselectedCarImage;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }
}
