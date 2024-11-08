package org.firstinspires.ftc.teamcode.Utils;

public class Vector2D {
    private double x, y;

    // Constructor
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Getters
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    // Setters
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }

    // Scalar multiplication
    public Vector2D multiply(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    // Arithmetic Operations
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    // Find magnitude and direction
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }
    public double direction() {
        return Math.atan2(y, x);
    }

    // Override toString method for easy printing/debugging
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
