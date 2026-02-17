package ac.grim.grimac.utils.anticheat;

import com.github.retrooper.packetevents.util.Vector3d;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;

public class BoatUtil {
    public static final float smooth_thr = 5.0E-9F;

    private static final ArrayList<CollideVector> vectors = new ArrayList<>(
            Arrays.asList(
                    new CollideVector(new Vector3d(0,0.5625,0), CollideType.BOAT_GROUND),
                    new CollideVector(new Vector3d(0,0.5380759117863079,0), CollideType.BOAT_JUMP)
            )
    );

    public static class CollideVector {
        @Getter
        private CollideType type;
        @Getter
        private Vector3d vector;
        public CollideVector(Vector3d vector, CollideType type) {
            this.type = type;
            this.vector = vector;
        }
        public boolean isMatch(Vector3d vector) {
            return (this.vector.x == 0 || isInteger(this.vector.x - vector.x, smooth_thr)) &&
                    (this.vector.y == 0 || isInteger(this.vector.y - vector.y, smooth_thr)) &&
                    (this.vector.z == 0 || isInteger(this.vector.z - vector.z, smooth_thr));
        }
    }

    public enum CollideType {
        BOAT_GROUND, BOAT_JUMP
    }

    public static CollideVector getBoatStep(Vector3d move) {
        CollideVector result = null;
        for (CollideVector vector : vectors) {
            if (vector.isMatch(move)) {
                result = vector;
                break;
            }
        }
        return result;
    }
    public static boolean isInteger(double v, double t) {
        return Math.abs(v - (double)Math.round(v)) < t;
    }
}
