package com.vano.myrestaurant.model.entity;

import java.util.Objects;

public class Drink {

    private Integer id;

    private String name;

    private String description;

    private Integer volume;

    private int resourceId;

    public Drink() {
    }

    public Drink(Integer id, String name, String description, Integer volume, int resourceId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.volume = volume;
        this.resourceId = resourceId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drink drink = (Drink) o;
        return resourceId == drink.resourceId && Objects.equals(name, drink.name)
                && Objects.equals(description, drink.description) && Objects.equals(volume, drink.volume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, volume, resourceId);
    }

    @Override
    public String toString() {
        return name;
    }
}
