package models;

import java.awt.*;

public class Manager {
    public String name;
    public String imagePath;

    public Color color;


    public Manager(String name, Color color, String imagePath) {
        this.name = name;
        this.color = color;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", color=" + color +
                '}';
    }
}
