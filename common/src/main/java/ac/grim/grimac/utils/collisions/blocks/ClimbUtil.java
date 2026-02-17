package ac.grim.grimac.utils.collisions.blocks;

import ac.grim.grimac.player.GrimPlayer;

import java.util.HashSet;
import java.util.Set;

public class ClimbUtil {
    private static final Set<Double> ALLOWED_Y_DELTAS = new HashSet<>();
    private static final Set<Double> ALLOWED_XZ_DELTAS = new HashSet<>();

    static {
        ALLOWED_Y_DELTAS.add(0.04999999701976776);
        ALLOWED_Y_DELTAS.add(0.050000011920928955);
        ALLOWED_Y_DELTAS.add(0.1176000022888175);
        ALLOWED_Y_DELTAS.add(0.15000000596046448);
        ALLOWED_Y_DELTAS.add(0.1523351865055714);
        ALLOWED_Y_DELTAS.add(0.07840000152587834);

        ALLOWED_XZ_DELTAS.add(0.04999999701976776);
        ALLOWED_XZ_DELTAS.add(0.050000011920928955);
        ALLOWED_XZ_DELTAS.add(0.001011080264741171);
        ALLOWED_XZ_DELTAS.add(0.15000000596046448);
        ALLOWED_XZ_DELTAS.add(0.0016067271901443192);
        ALLOWED_XZ_DELTAS.add(0.001810771141185441);
        ALLOWED_XZ_DELTAS.add(8.0143895344E-5);
        ALLOWED_XZ_DELTAS.add(8.794294397462821E-5);
        ALLOWED_XZ_DELTAS.add(0.0001280150827517874);
        ALLOWED_XZ_DELTAS.add(0.15490000059546493);
        ALLOWED_XZ_DELTAS.add(0.0025580126639175121);
        ALLOWED_XZ_DELTAS.add(0.000118192207109880183);
        ALLOWED_XZ_DELTAS.add(9.53249511229198378E-4);
        ALLOWED_XZ_DELTAS.add(0.002079006644768011);
        ALLOWED_XZ_DELTAS.add(0.0028473061706479896);
        ALLOWED_XZ_DELTAS.add(0.15000000596046403);
        ALLOWED_XZ_DELTAS.add(0.0023880541581600134);
        ALLOWED_XZ_DELTAS.add(0.0023880541581604575);
        ALLOWED_XZ_DELTAS.add(0.15000000596046492);
    }

    public static boolean isClimbing(GrimPlayer player) {
        double distX = player.lastX - player.x, distY = player.lastY - player.y, distZ = player.lastZ - player.z;
        return (ALLOWED_XZ_DELTAS.contains(Math.abs(distX)) || ALLOWED_XZ_DELTAS.contains(Math.abs(distZ)) || ALLOWED_Y_DELTAS.contains(Math.abs(distY)));
    }
}
