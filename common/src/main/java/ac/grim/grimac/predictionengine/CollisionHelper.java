package ac.grim.grimac.predictionengine;

import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.collisions.datatypes.SimpleCollisionBox;
import ac.grim.grimac.utils.math.Vector3dm;
import ac.grim.grimac.utils.nmsutil.Collisions;
import com.github.retrooper.packetevents.util.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class CollisionHelper {

    public static Vector3d getAllowedMovement(Vector3d vec, SimpleCollisionBox collisionBox, GrimPlayer player) {
        double d0 = vec.x;
        double d1 = vec.y;
        double d2 = vec.z;

        List<SimpleCollisionBox> boxes = new ArrayList<>();
        SimpleCollisionBox expandedBox = collisionBox.copy().expand(vec.x, vec.y, vec.z);
        Collisions.getCollisionBoxes(player, expandedBox, boxes, false);

        if (d1 != 0.0D) {
            d1 = Collisions.collideBoundingBoxLegacy(
                    new Vector3dm(0, d1, 0),
                    collisionBox,
                    boxes,
                    Collisions.nonStupidityCombinations.get(0)
            ).getY();

            if (d1 != 0.0D) {
                collisionBox = collisionBox.offset(0.0D, d1, 0.0D);
            }
        }

        boolean flag = Math.abs(d0) < Math.abs(d2);

        if (flag && d2 != 0.0D) {
            d2 = Collisions.collideBoundingBoxLegacy(
                    new Vector3dm(0, 0, d2),
                    collisionBox,
                    boxes,
                    Collisions.nonStupidityCombinations.get(0)
            ).getZ();

            if (d2 != 0.0D) {
                collisionBox = collisionBox.offset(0.0D, 0.0D, d2);
            }
        }

        if (d0 != 0.0D) {
            d0 = Collisions.collideBoundingBoxLegacy(
                    new Vector3dm(d0, 0, 0),
                    collisionBox,
                    boxes,
                    Collisions.nonStupidityCombinations.get(0)
            ).getX();

            if (!flag && d0 != 0.0D) {
                collisionBox = collisionBox.offset(d0, 0.0D, 0.0D);
            }
        }

        if (!flag && d2 != 0.0D) {
            d2 = Collisions.collideBoundingBoxLegacy(
                    new Vector3dm(0, 0, d2),
                    collisionBox,
                    boxes,
                    Collisions.nonStupidityCombinations.get(0)
            ).getZ();
        }

        return new Vector3d(d0, d1, d2);
    }
}
