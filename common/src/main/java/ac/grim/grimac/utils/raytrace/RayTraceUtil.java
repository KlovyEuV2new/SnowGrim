package ac.grim.grimac.utils.raytrace;

import ac.grim.grimac.utils.math.McMath;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.util.Vector3i;

import java.util.function.BiFunction;
import java.util.function.Function;

public class RayTraceUtil {
    public static <T> T doRayTrace(RayTraceData context, BiFunction<RayTraceData, Vector3i, T> rayTracer, Function<RayTraceData, T> missFactory) {
        Vector3d vector3d = context.getStart();
        Vector3d vector3d1 = context.getEnd();

        if (vector3d.equals(vector3d1)) {
            return missFactory.apply(context);
        } else {
            double d0 = McMath.lerp(-1.0E-7D, vector3d1.x, vector3d.x);
            double d1 = McMath.lerp(-1.0E-7D, vector3d1.y, vector3d.y);
            double d2 = McMath.lerp(-1.0E-7D, vector3d1.z, vector3d.z);
            double d3 = McMath.lerp(-1.0E-7D, vector3d.x, vector3d1.x);
            double d4 = McMath.lerp(-1.0E-7D, vector3d.y, vector3d1.y);
            double d5 = McMath.lerp(-1.0E-7D, vector3d.z, vector3d1.z);
            int i = McMath.floor(d3);
            int j = McMath.floor(d4);
            int k = McMath.floor(d5);
            Vector3i blockpos$mutable = new Vector3i(i, j, k);
            T t = rayTracer.apply(context, blockpos$mutable);

            if (t != null) {
                return t;
            } else {
                double d6 = d0 - d3;
                double d7 = d1 - d4;
                double d8 = d2 - d5;
                int l = McMath.signum(d6);
                int i1 = McMath.signum(d7);
                int j1 = McMath.signum(d8);
                double d9 = l == 0 ? Double.MAX_VALUE : (double) l / d6;
                double d10 = i1 == 0 ? Double.MAX_VALUE : (double) i1 / d7;
                double d11 = j1 == 0 ? Double.MAX_VALUE : (double) j1 / d8;
                double d12 = d9 * (l > 0 ? 1.0D - McMath.frac(d3) : McMath.frac(d3));
                double d13 = d10 * (i1 > 0 ? 1.0D - McMath.frac(d4) : McMath.frac(d4));
                double d14 = d11 * (j1 > 0 ? 1.0D - McMath.frac(d5) : McMath.frac(d5));

                while (d12 <= 1.0D || d13 <= 1.0D || d14 <= 1.0D) {
                    if (d12 < d13) {
                        if (d12 < d14) {
                            i += l;
                            d12 += d9;
                        } else {
                            k += j1;
                            d14 += d11;
                        }
                    } else if (d13 < d14) {
                        j += i1;
                        d13 += d10;
                    } else {
                        k += j1;
                        d14 += d11;
                    }

                    T t1 = rayTracer.apply(context, new Vector3i(i, j, k));

                    if (t1 != null) {
                        return t1;
                    }
                }

                return missFactory.apply(context);
            }
        }
    }
}
