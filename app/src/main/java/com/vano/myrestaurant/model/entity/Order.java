package com.vano.myrestaurant.model.entity;

public class Order {

    private Integer id;

    private Food food;

    private Drink drink;

    public Order(Integer id, Food food, Drink drink) {
        this.id = id;
        this.food = food;
        this.drink = drink;
    }

    public Integer getId() {
        return id;
    }

    public Food getFood() {
        return food;
    }

    public Drink getDrink() {
        return drink;
    }

    @Override
    public String toString() {
        return "Order:" +
                id + ". " +
                food + " and "
                + drink + '.';
    }
}
