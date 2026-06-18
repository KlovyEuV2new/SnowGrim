package ac.grim.grimac.utils.raytrace;

import com.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import com.github.retrooper.packetevents.util.Vector3d;
import lombok.Getter;

public abstract class RayTraceResult
{
    protected final Vector3d hitResult;
    @Getter
    protected final WrappedBlockState state;

    protected RayTraceResult(Vector3d hitVec, WrappedBlockState state)
    {
        this.hitResult = hitVec;
        this.state = state;
    }

    public abstract Type getType();

    /**
     * Returns the hit position of the raycast, in absolute world coordinates
     */
    public Vector3d getHitVec()
    {
        return this.hitResult;
    }

    public static enum Type
    {
        MISS,
        BLOCK,
        ENTITY;
    }
}
