public class Hello {
    public static void main(String[] args) {
        try {
            var x = 1;
            var y = 0;
            if (y == 0){
                System.out.println("division by 0 is not allowed");
            } else {
                var z = divide(x, y);
                System.out.println("Hello World!");
            }
        }
        catch (ArithmeticException exception){
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private static int divide(int x, int y) {
        var z = x / y;
        return z;
    }
}
