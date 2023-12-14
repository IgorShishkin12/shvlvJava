
import java.awt.Point;

abstract class Shape {
    abstract boolean isPointInside(Point point);
}

class Circle extends Shape {
    private Point center;
    private double radius;

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    boolean isPointInside(Point point) {
        double distance = Math.sqrt(Math.pow(point.x - center.x, 2) + Math.pow(point.y - center.y, 2));
        return distance <= radius;
    }
}

class Rectangle extends Shape {
    private Point topLeft;
    private Point bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    boolean isPointInside(Point point) {
        return point.x >= topLeft.x && point.x <= bottomRight.x &&
                point.y >= topLeft.y && point.y <= bottomRight.y;
    }
}

public class Main {
    public static void main(String[] args) {
        Circle circle = new Circle(new Point(0, 0), 5);
        Rectangle rectangle = new Rectangle(new Point(-3, -3), new Point(3, 3));

        Point insidePoint = new Point(2, 2);
        Point outsidePoint = new Point(6, 6);

        System.out.println("Is point inside circle? " + circle.isPointInside(insidePoint));
        System.out.println("Is point inside rectangle? " + rectangle.isPointInside(insidePoint));

        System.out.println("Is point inside circle? " + circle.isPointInside(outsidePoint));
        System.out.println("Is point inside rectangle? " + rectangle.isPointInside(outsidePoint));
    }
}
