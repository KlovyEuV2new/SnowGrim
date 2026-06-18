package ac.grim.grimac.utils.raytrace;

import com.github.retrooper.packetevents.util.Vector3d;

public final class RayTraceData {

    private final Vector3d start;
    private final Vector3d end;

    public RayTraceData(Vector3d start, Vector3d end) {
        this.start = start;
        this.end = end;
    }

    public Vector3d getStart() {
        return start;
    }

    public Vector3d getEnd() {
        return end;
    }
}
