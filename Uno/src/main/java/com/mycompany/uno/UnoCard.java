/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.uno;

/**
 *
 * @author jacob
 */
public class UnoCard {
    enum Color {
        Red, Yellow, Blue, Green, Wild;

        private static final Color[] colors = Color.values();
        public static Color getColor(int index) {
            return Color.colors[index];
        }
    }

    enum Value {
        One, Two, Three, Four, Five, Six, Seven, Eight, Nine, DrawTwo, Skip, Reverse, Wild, WildFour;

        private static final Value[] values = Value.values();
        public static Value getValue(int index) {
            return Value.values[index];
        }
    }

    private final Color color;
    private final Value value;

    public UnoCard(Color color, Value value) {
        this.color = color;
        this.value = value;
    }

    public Color getColor() {
        return this.color;
    }

    public Value getValue() {
        return this.value;
    }

    public String toString() {
        return this.color + " " + this.value;
    }
}
