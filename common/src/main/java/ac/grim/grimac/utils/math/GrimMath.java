package ac.grim.grimac.utils.math;

import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.data.Pair;
import com.github.retrooper.packetevents.protocol.potion.PotionTypes;
import com.github.retrooper.packetevents.util.Vector3i;
import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class GrimMath {
    public static final double MINIMUM_DIVISOR = ((Math.pow(0.2f, 3) * 8) * 0.15) - 1e-3; // 1e-3 for float imprecision
    private static final float DEGREES_TO_RADIANS = (float) Math.PI / 180f;
    private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
            0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
    };

    public static final int PACKED_HORIZONTAL_LENGTH = 1 + GrimMath.log2(GrimMath.smallestEncompassingPowerOfTwo(30000000));
    public static final int PACKED_Y_LENGTH = 64 - 2 * PACKED_HORIZONTAL_LENGTH;
    private static final long PACKED_X_MASK = (1L << PACKED_HORIZONTAL_LENGTH) - 1L;
    private static final long PACKED_Y_MASK = (1L << PACKED_Y_LENGTH) - 1L;
    private static final long PACKED_Z_MASK = (1L << PACKED_HORIZONTAL_LENGTH) - 1L;
    private static final int Z_OFFSET = PACKED_Y_LENGTH;
    private static final int X_OFFSET = PACKED_Y_LENGTH + PACKED_HORIZONTAL_LENGTH;

    public static double gcd(double a, double b) {
        if (a == 0) return 0;

        // Make sure a is larger than b
        if (a < b) {
            double temp = a;
            a = b;
            b = temp;
        }

        while (b > MINIMUM_DIVISOR) { // Minimum minecraft sensitivity
            double temp = a - (Math.floor(a / b) * b);
            a = b;
            b = temp;
        }

        return a;
    }

    public static double calculateSD(List<Double> numbers) {
        double sum = 0.0;
        double standardDeviation = 0.0;

        for (double rotation : numbers) {
            sum += rotation;
        }

        double mean = sum / numbers.size();

        for (double num : numbers) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation / numbers.size());
    }

    public static int floor(double d) {
        return (int) Math.floor(d);
    }

    public static int ceil(double d) {
        return (int) Math.ceil(d);
    }

    // Should produce the same output as Math.floor() and Math.ceil() but mojang do it differently
    // Replicating what they do jussst in case
    public static int mojangFloor(double num) {
        final int floor = (int) num;
        return floor == num ? floor : floor - (int) (Double.doubleToRawLongBits(num) >>> 63);
    }

    public static int mojangCeil(final double num) {
        final int floor = (int) num;
        return floor == num ? floor : floor + (int) (~Double.doubleToRawLongBits(num) >>> 63);
    }

    public static double clamp(double num, double min, double max) {
        if (num < min) {
            return min;
        }
        return Math.min(num, max);
    }

    public static int clamp(int num, int min, int max) {
        if (num < min) {
            return min;
        }
        return Math.min(num, max);
    }

    public static float clamp(float num, float min, float max) {
        if (num < min) {
            return min;
        }
        return Math.min(num, max);
    }

    public static double lerp(double lerpAmount, double start, double end) {
        return start + lerpAmount * (end - start);
    }

    public static double frac(double p_14186_) {
        return p_14186_ - lfloor(p_14186_);
    }

    public static long lfloor(double p_14135_) {
        long i = (long) p_14135_;
        return p_14135_ < (double) i ? i - 1L : i;
    }

    public static <T> Collection<T> distinct(final Collection<T> data) {
        return new HashSet<>(data);
    }

    public static int getDuplicates(final Collection<?> data) {
        if (data.isEmpty()) return 0;
        return data.size() - distinct(data).size();
    }

    public static double roundToPlace(double value, int places) {
        double multiplier = Math.pow(10, places);
        return Math.round(value * multiplier) / multiplier;
    }

    public static float yawDiff(float fromYaw, float toYaw) {
        if (fromYaw <= -360.0f) {
            fromYaw = -(-fromYaw % 360.0f);
        } else if (fromYaw >= 360.0f) {
            fromYaw %= 360.0f;
        }
        if (toYaw <= -360.0f) {
            toYaw = -(-toYaw % 360.0f);
        } else if (toYaw >= 360.0f) {
            toYaw %= 360.0f;
        }
        float yawDiff = toYaw - fromYaw;
        if (yawDiff < -180.0f) {
            yawDiff += 360.0f;
        } else if (yawDiff > 180.0f) {
            yawDiff -= 360.0f;
        }
        return yawDiff;
    }

    public static float pitchDiff(float fromPitch, float toPitch) {
        if (fromPitch <= -180.0f) {
            fromPitch = -(-fromPitch % 180.0f);
        } else if (fromPitch >= 180.0f) {
            fromPitch %= 180.0f;
        }
        if (toPitch <= -180.0f) {
            toPitch = -(-toPitch % 180.0f);
        } else if (toPitch >= 180.0f) {
            toPitch %= 180.0f;
        }
        float pitchDiff = toPitch - fromPitch;
        if (pitchDiff < -90.0f) {
            pitchDiff += 180.0f;
        } else if (pitchDiff > 90.0f) {
            pitchDiff -= 180.0f;
        }
        return pitchDiff;
    }

    public static int sign(double x) {
        if (x == 0.0) {
            return 0;
        } else {
            return x > 0.0 ? 1 : -1;
        }
    }

    public static float square(float value) {
        return value * value;
    }

    public static float sqrt(float value) {
        return (float)Math.sqrt(value);
    }

    // Find the closest distance to (1 / 64)
    // All poses horizontal length is 0.2 or 0.6 (0.1 or 0.3)
    // and we call this from the player's position
    //
    // We must find the minimum of the three numbers:
    // Distance to (1 / 64) when we are around -0.1
    // Distance to (1 / 64) when we are around 0
    // Distance to (1 / 64) when we are around 0.1
    //
    // Someone should likely just refactor this entire method, although it is cold being called twice every movement
    public static double distanceToHorizontalCollision(double position) {
        return Math.min(Math.abs(position % (1 / 640d)), Math.abs(Math.abs(position % (1 / 640d)) - (1 / 640d)));
    }

    public static boolean betweenRange(double value, double min, double max) {
        return value > min && value < max;
    }

    public static boolean inRange(double value, double min, double max) {
        return value >= min && value <= max;
    }

    public static boolean inRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public static double hypot(final double x, final double z) {
        return Math.sqrt(x * x + z * z);
    }

    public static double magnitude(final double x, final double y, final double z) {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public static String trim(final double x) {
        return new DecimalFormat("#.##").format(x);
    }

    public static float distanceBetweenAngles(final float alpha, final float beta) {
        final float alphaX = alpha % 360.0f;
        final float betaX = beta % 360.0f;
        final float delta = Math.abs(alphaX - betaX);
        return (float)Math.abs(Math.min(360.0 - delta, delta));
    }

    public static double getVariance(final Collection<? extends Number> data) {
        int count = 0;
        double sum = 0.0;
        double variance = 0.0;
        for (final Number number : data) {
            sum += number.doubleValue();
            ++count;
        }
        final double average = sum / count;
        for (final Number number : data) {
            variance += Math.pow(number.doubleValue() - average, 2.0);
        }
        return variance;
    }

    public static double getStandardDeviation(final Collection<? extends Number> data) {
        final double variance = getVariance(data);
        return Math.sqrt(variance);
    }

    public static double getSkewness(final Collection<? extends Number> data) {
        double sum = 0.0;
        int count = 0;
        final List<Double> numbers = Lists.newArrayList();
        for (final Number number : data) {
            sum += number.doubleValue();
            ++count;
            numbers.add(number.doubleValue());
        }
        Collections.sort(numbers);
        final double mean = sum / count;
        final double median = (count % 2 != 0) ? numbers.get(count / 2) : ((numbers.get((count - 1) / 2) + numbers.get(count / 2)) / 2.0);
        final double variance = getVariance(data);
        return 3.0 * (mean - median) / variance;
    }

    public static double getAverage(Collection<? extends Number> data, boolean trues) {
        double sum = 0.0;
        Number number;
        for(Iterator var3 = data.iterator(); var3.hasNext(); sum += number.doubleValue()) {
            number = (Number)var3.next();
        }
        double result = sum / (double)data.size();
        return Double.isNaN(result) ? 0.0 : result;
    }

    public static double getAverage(final Collection<? extends Number> data) {
        if (data == null || data.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (final Number number : data) {
            sum += number.doubleValue();
        }
        return sum / data.size();
    }

    public static List<Float> getJiffDelta(List<? extends Number> data, int depth) {
        List<Float> result = new ArrayList();
        Iterator var3 = data.iterator();

        while(var3.hasNext()) {
            Number n = (Number)var3.next();
            result.add(n.floatValue());
        }

        for(int i = 0; i < depth; ++i) {
            List<Float> calculate = new ArrayList();
            float old = Float.MIN_VALUE;
            Iterator var6 = result.iterator();

            while(var6.hasNext()) {
                float n = (Float)var6.next();
                if (old == Float.MIN_VALUE) {
                    old = n;
                } else {
                    calculate.add(Math.abs(Math.abs(n) - Math.abs(old)));
                    old = n;
                }
            }
            result = new ArrayList(calculate);
        }
        return result;
    }

    public static double KsgoTest(List<? extends Number> data, Function<Double, Double> cdfFunction) {
        List<Double> sorted = (List)data.stream().map(Number::doubleValue).sorted().collect(Collectors.toList());
        int n = sorted.size();
        double dStatistic = 0.0;

        for(int i = 0; i < n; ++i) {
            double empiricalCDF = (double)(i + 1) / (double)n;
            double theoreticalCDF = (Double)cdfFunction.apply(sorted.get(i));
            dStatistic = Math.max(dStatistic, Math.abs(empiricalCDF - theoreticalCDF));
        }
        return dStatistic;
    }

    public static double getKurtosis(final Collection<? extends Number> data) {
        double sum = 0.0;
        int count = 0;
        for (final Number number : data) {
            sum += number.doubleValue();
            ++count;
        }
        if (count < 3.0) {
            return 0.0;
        }
        final double efficiencyFirst = count * (count + 1.0) / ((count - 1.0) * (count - 2.0) * (count - 3.0));
        final double efficiencySecond = 3.0 * Math.pow(count - 1.0, 2.0) / ((count - 2.0) * (count - 3.0));
        final double average = sum / count;
        double variance = 0.0;
        double varianceSquared = 0.0;
        for (final Number number2 : data) {
            variance += Math.pow(average - number2.doubleValue(), 2.0);
            varianceSquared += Math.pow(average - number2.doubleValue(), 4.0);
        }
        return efficiencyFirst * (varianceSquared / Math.pow(variance / sum, 2.0)) - efficiencySecond;
    }

    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }

    private static double getMedian(final List<Double> data) {
        if (data.size() % 2 == 0) {
            return (data.get(data.size() / 2) + data.get(data.size() / 2 - 1)) / 2.0;
        }
        return data.get(data.size() / 2);
    }

    public static boolean isExponentiallySmall(final Number number) {
        return number.doubleValue() < 1.0 && Double.toString(number.doubleValue()).contains("E");
    }

    public static double angleOf(final double minX, final double minZ, final double maxX, final double maxZ) {
        final double deltaY = minZ - maxZ;
        final double deltaX = maxX - minX;
        final double result = Math.toDegrees(Math.atan2(deltaY, deltaX));
        return (result < 0.0) ? (360.0 + result) : result;
    }

    public static double getDistanceBetweenAngles360(final double alpha, final double beta) {
        final double abs = Math.abs(alpha % 360.0 - beta % 360.0);
        return Math.abs(Math.min(360.0 - abs, abs));
    }

    public static double getDistanceBetweenAngles360Raw(final double alpha, final double beta) {
        return Math.abs(alpha % 360.0 - beta % 360.0);
    }

    public static double getCps(final Collection<? extends Number> data) {
        return 20.0 / getAverage(data) * 50.0;
    }

    public static boolean isNearlySame(double a, double b, double epoch) {
        return Math.abs(a - b) < epoch;
    }

    public static long hashCode(double x, int y, double z) {
        long l = (long) (x * 3129871) ^ (long) z * 116129781L ^ (long) y;
        l = l * l * 42317861L + l * 11L;
        return l >> 16;
    }

    public static float radians(float degrees) {
        return degrees * DEGREES_TO_RADIANS;
    }

    public static long asLong(int x, int y, int z) {
        long value = 0L;
        value |= (x & PACKED_X_MASK) << X_OFFSET;
        value |= (y & PACKED_Y_MASK) << 0;
        return value | (z & PACKED_Z_MASK) << Z_OFFSET;
    }

    public static long asLong(Vector3i vector) {
        return asLong(vector.getX(), vector.getY(), vector.getZ());
    }

    public static int log2(int value) {
        return ceillog2(value) - (isPowerOfTwo(value) ? 0 : 1);
    }

    public static int ceillog2(int value) {
        value = isPowerOfTwo(value) ? value : smallestEncompassingPowerOfTwo(value);
        return MULTIPLY_DE_BRUIJN_BIT_POSITION[(int)(value * 125613361L >> 27) & 31];
    }

    public static boolean isPowerOfTwo(int value) {
        return value != 0 && (value & value - 1) == 0;
    }

    public static int smallestEncompassingPowerOfTwo(int value) {
        int output = value - 1;
        output |= output >> 1;
        output |= output >> 2;
        output |= output >> 4;
        output |= output >> 8;
        output |= output >> 16;
        return output + 1;
    }

    public static boolean equal(double first, double second) {
        return Math.abs(second - first) < 1.0E-5F;
    }

    public static double square(double num) {
        return num * num;
    }

    public static long getGcd(long current, long previous) {
        return previous <= 16384L ? current : getGcd(previous, current % previous);
    }

    public static double getGcd(double a, double b) {
        if (a < b) {
            return getGcd(b, a);
        } else {
            return Math.abs(b) < 0.001D ? a : getGcd(b, a - Math.floor(a / b) * b);
        }
    }

    public static int getMode(Collection<? extends Number> array) {
        int mode = (Integer) array.toArray()[0];
        int maxCount = 0;
        Iterator var3 = array.iterator();

        while (var3.hasNext()) {
            Number value = (Number) var3.next();
            int count = 1;
            Iterator var6 = array.iterator();

            while (var6.hasNext()) {
                Number i = (Number) var6.next();
                if (i.equals(value)) {
                    ++count;
                }

                if (count > maxCount) {
                    mode = (Integer) value;
                    maxCount = count;
                }
            }
        }
        return mode;
    }

    public static double getSumDouble(final Collection<Double> nums) {
        if (nums.isEmpty()) return 0D;
        double sum = 0D;
        for (final double num : nums) sum += num;
        return sum;
    }

    public static int getSumInt(final Collection<Integer> nums) {
        if (nums.isEmpty()) return 0;
        int sum = 0;
        for (final int num : nums) sum += num;
        return sum;
    }

    public static long getSumLong(final Collection<Long> nums) {
        if (nums.isEmpty()) return 0L;
        long sum = 0L;
        for (final long num : nums) sum += num;
        return sum;
    }

    public static float getSumFloat(final Collection<Float> nums) {
        if (nums.isEmpty()) return 0F;
        float sum = 0F;
        for (final float num : nums) sum += num;
        return sum;
    }

    public static double getAverageDouble(final Collection<Double> nums) {
        if (nums.isEmpty()) return 0D;
        return getSumDouble(nums) / nums.size();
    }

    public static Pair<List<Double>, List<Double>> getOutliers(final Collection<? extends Number> collection) {
        final List<Double> values = new ArrayList<>();
        for (final Number number : collection) {
            values.add(number.doubleValue());
        }
        final double q1 = getMedian(values.subList(0, values.size() / 2));
        final double q3 = getMedian(values.subList(values.size() / 2, values.size()));
        final double iqr = Math.abs(q1 - q3);
        final double lowThreshold = q1 - 1.5 * iqr, highThreshold = q3 + 1.5 * iqr;
        final Pair<List<Double>, List<Double>> tuple = new Pair<>(new ArrayList<>(), new ArrayList<>());
        for (final Double value : values) {
            if (value < lowThreshold) {
                tuple.first().add(value);
            } else if (value > highThreshold) {
                tuple.second().add(value);
            }
        }
        return tuple;
    }

    public static double getPlayerSpeed(GrimPlayer player) {
        double deltaX = Math.abs(player.x - player.lastX);
        double deltaY = Math.abs(player.y - player.lastY);
        double deltaZ = Math.abs(player.z - player.lastZ);
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        double speed = distance / 0.05D;
        speed -= (double) player.fallDistance / 0.05D;
        return speed;
    }

    public static double getPlayerMBPT(GrimPlayer player, SpeedType type) {
        double speed;
        switch (type) {
            case Y_UP ->
            {
                speed = 0.41999998688697815;
                if (player.compensatedEntities.self.hasPotionEffect(PotionTypes.JUMP_BOOST)) {
                    OptionalInt level = player.compensatedEntities.self.getPotionEffectLevel(PotionTypes.JUMP_BOOST);
                    if (level.isPresent()) {
                        speed *= (1 + (level.getAsInt() + 1) * 0.2518);
                    }
                }
                return speed;
            }
        }
        return Integer.MAX_VALUE;
    }

    public enum SpeedType {
        Y_UP, X, Z, XZ, XYZ
    }

    public static int getDistinct(Collection<? extends Number> data) {
        return (int)data.stream().distinct().count();
    }

    public static double distanceXZ(double x1, double x2, double z1, double z2) {
        double dx = x2 - x1;
        double dz = z2 - z1;
        return Math.sqrt(dx * dx + dz * dz);
    }
}
