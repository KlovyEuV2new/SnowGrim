package ac.grim.grimac.utils.math;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class McMath {
    private static final float[] SIN_TABLE_FAST = new float[4096];
    private static final float radToIndex = roundToFloat(651.8986469044033D);
    public static boolean fastMath = false;
    private static final float[] SIN_TABLE = make(new float[65536], (p123) ->
    {
        for (int i = 0; i < p123.length; ++i) {
            p123[i] = (float) Math.sin((double) i * Math.PI * 2.0D / 65536.0D);
        }
    });
    private static final Random RANDOM = new Random();

    public static <T> T make(Supplier<T> supplier)
    {
        return supplier.get();
    }

    public static <T> T make(T object, Consumer<T> consumer)
    {
        consumer.accept(object);
        return object;
    }

    public static float roundToFloat(double d)
    {
        return (float)((double)Math.round(d * 1.0E8D) / 1.0E8D);
    }

    public static float sin(float value) {
        return fastMath ? SIN_TABLE_FAST[(int) (value * radToIndex) & 4095] : SIN_TABLE[(int) (value * 10430.378F) & 65535];
    }

    public static float sin(double value) {
        return fastMath ? SIN_TABLE_FAST[(int) (value * radToIndex) & 4095] : SIN_TABLE[(int) (value * 10430.378F) & 65535];
    }

    public static float cos(float value) {
        return fastMath ? SIN_TABLE_FAST[(int) (value * radToIndex + 1024.0F) & 4095] : SIN_TABLE[(int) (value * 10430.378F + 16384.0F) & 65535];
    }

    public static float cos(double value) {
        return fastMath ? SIN_TABLE_FAST[(int) (value * radToIndex + 1024.0F) & 4095] : SIN_TABLE[(int) (value * 10430.378F + 16384.0F) & 65535];
    }
}
