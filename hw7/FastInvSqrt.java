//package HW7;

public class FastInvSqrt{
    public static float fastInvSqrt(float num){
        long i;
        float x2, y;
        final float threehalfs = 1.5F;

        x2 = num * 0.5F;
        y = num;
        i = Float.floatToIntBits(y);
        i = 0x5f3759df - (i >> 1);
        y = Float.intBitsToFloat((int)i);
        y *= (threehalfs - (x2 * y * y));

        return y;
    }
}