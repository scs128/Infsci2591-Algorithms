//package HW7;
//import FastInvSqrt.*;

public class InvSqrt{
    final static float threehalfs = 1.5F;
    public static void main (String[]args){

        testInvSqrt();
        
        // Code to run tests on a single input value
        /* 
        float num = Float.MIN_VALUE + (float)Math.random() * (Float.MAX_VALUE) ;
        System.out.println(num);

        float approximation = fastTest(num);
        float actual = standardTest(num);

        System.out.println("Value Difference: " + (actual-approximation));
        */
    }

    // Runs 10000 different random values and outputs the runtimes and average difference
    //      between fast inverse square root algorithm and the basic method
    public static void testInvSqrt(){
        float fastResult;
        float standardResult;
        long startTime;
        long endTime;
        long fastTotalTime = 0;
        long standardTotalTime = 0;
        float difference = 0;
        for(int i = 0; i < 10000; i++){
            float num = Float.MIN_VALUE + (float)Math.random() * (Float.MAX_VALUE) ;

            // Calculate fast inverse square root
            startTime = System.nanoTime();
            fastResult = fastInvSqrt(num);
            endTime = System.nanoTime();
            fastTotalTime = fastTotalTime + (endTime - startTime);

            // Calculate basic inverse square root
            startTime = System.nanoTime();
            standardResult = 1 / (float)Math.sqrt(num);
            endTime = System.nanoTime();
            standardTotalTime = standardTotalTime + (endTime - startTime);

            difference = Math.abs(fastResult - standardResult);
        }

        System.out.println("Fast inverse square root runtime: " + fastTotalTime);
        System.out.println("Basic inverse square root runtime: " + standardTotalTime);

        System.out.println("\nAverage difference between values: " + (difference / 10000));
    }

    // Tests fast inverse square root algorithm for an input value
    public static float fastTest(float num){
        System.out.println("Running fast inverse square root.");
        float result = 0;
        long startTime = System.nanoTime();

        // Run iterations
        for(int i = 0; i < 10000; i++){
            result = fastInvSqrt(num);
        }
        long endTime = System.nanoTime();
        System.out.println("Result: " + result);
        long totalTime = endTime - startTime;

        System.out.println("Runtime: " + totalTime + "\n");

        return result;
    }

    // Tests basic inverse square root method for an input value
    public static float standardTest(float num){
        System.out.println("Running standard inverse square root using Math.sqrt()");
        float result = 0;
        long startTime = System.nanoTime();

        // Run iterations
        for(int i = 0; i < 10000; i++){
            result = 1 / (float) Math.sqrt(num);
        }

        long endTime = System.nanoTime();
        System.out.println("Result: " + result);
        long totalTime = endTime - startTime;

        System.out.println("Runtime: " + totalTime + "\n");

        return result;
    }


    public static float fastInvSqrt(float num){
        long i;
        float x2, y;
        

        x2 = num * 0.5F;
        y = num;
        i = Float.floatToIntBits(y);
        i = 0x5f3759df - (i >> 1);
        y = Float.intBitsToFloat((int)i);
        y *= (threehalfs - (x2 * y * y));

        return y;
    }
}