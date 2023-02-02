package dmit2015.model;

/**
 * This class model a Circle shape.
 *
 * @author Sam Wu
 * @version 2023.01.20
 */
public class Circle {

    /**
     * The radius of this circle.
     */
    private double radius;

    public double getRadius() {
        return radius;
    }

    // Checked-exception example
//    public void setRadius(double radius) throws Exception {
//        if (radius <= 0) {
//            throw new Exception("Radius must be a positive non-zero number");
//        }
//        this.radius = radius;
//    }

    // Runtime exception example

    /**
     * Change the radius of this circle.
     * @param radius the new radius of this circle.
     */
    public void setRadius(double radius) {
        if (radius <= 0) {
            throw new RuntimeException("Radius must be a positive non-zero number");
        }
        this.radius = radius;
    }

    public Circle() {
//        radius = 1;
        setRadius(1);
    }

    public Circle(double radius) {
        //this.radius = radius;
        setRadius(radius);
    }

    /**
     * Compute and return the area of this circle
     * @return area of this circle
     */
    public double area()
    {
        return Math.PI * radius * radius;
    }

    /**
     * Compute and return the diameter of this circle
     * @return diameter of this circle
     */
    public double diameter()
    {
        return 2 * radius;
    }

    /**
     * Compute and return the circumference of this circle
     * @return circumference of this circle
     */
    public double circumference() {
        return 2 * Math.PI * radius;
    }

    public static void main(String[] args)
    {
        // Create a Circle with a radius of 1
        Circle circle1 = new Circle();
        // The radius of circle1 should be 1,
        // area should be 3.14159
        // perimeter should be 6.28
        System.out.printf("The radius of circle1 is %s\n", circle1.getRadius());
        System.out.printf("The area of circle1 is %.5f\n", circle1.area());
        System.out.printf("The perimeter of circle1 is %.2f\n", circle1.diameter());

        // Change the radius of circle1 to 5
        circle1.setRadius(5);
        // The radius of circle1 should be 5,
        // area should be 78.53982
        // perimeter should be 31.41593
        System.out.printf("The radius of circle1 is %s\n", circle1.getRadius());
        System.out.printf("The area of circle1 is %.5f\n", circle1.area());
        System.out.printf("The perimeter of circle1 is %.2f\n", circle1.diameter());

        // Change the radius of cirlce1 to -25
        // It should throw an exception
        try
        {
            circle1.setRadius(-25);
            System.out.println("A exception should have been thrown");
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
