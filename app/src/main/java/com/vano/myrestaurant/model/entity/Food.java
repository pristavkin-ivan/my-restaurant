package com.vano.myrestaurant.model.entity;

import java.util.Objects;

public class Food {

    private Integer id;

    private String name;

    private String description;

    private Integer weight;

    private int resourceId;

    private Boolean favorite;

    public Food() {
    }

    public Food(Integer id, String name, String description, Integer weight, int resourceId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.favorite = false;
        this.resourceId = resourceId;
    }

    public Boolean isFavorite() {
        return favorite;
    }

    public Food(Integer id, String name, String description, Integer weight, int resourceId, Boolean favorite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.favorite = favorite;
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
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
        Food food = (Food) o;
        return resourceId == food.resourceId && Objects.equals(name, food.name)
                && Objects.equals(description, food.description) && Objects.equals(weight, food.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, weight, resourceId);
    }

    @Override
    public String toString() {
        return name;
    }
}
