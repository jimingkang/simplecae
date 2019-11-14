package sustech.mse.simplecae.jni;

public class Sample {



    // --- Native methods

    public native int addPoint(double x,double y);

    public native boolean addLine(double x,double y);

    public native String addCircle(String text);

    public native int intArrayMethod(int[] intArray);





    // --- Main method to test our native library

    public static void main(String[] args) {

        System.loadLibrary("Sample");

        Sample sample = new Sample();

        int square = sample.addPoint(5,10);

        boolean bool = sample.addLine(5,10);

      //  String text = sample.stringMethod("java");

      //  int sum = sample.intArrayMethod(new int[] {1, 1, 2, 3, 5, 8, 13});



        System.out.println("intMethod: " + square);

        System.out.println("booleanMethod: " + bool);

       // System.out.println("stringMethod: " + text);

      //  System.out.println("intArrayMethod: " + sum);

    }

}