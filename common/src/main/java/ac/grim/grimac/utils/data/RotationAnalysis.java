package ac.grim.grimac.utils.data;

import java.util.HashSet;
import java.util.Set;


public class RotationAnalysis {

    private final int maxSize;
    private final float lowThreshold, highThreshold;
    private final Set<Float> distinctRotations = new HashSet<>();
    private int size;
    private float average, min, max;
    private int highCount, lowCount, roundedCount;

    public RotationAnalysis(int maxSize, float lowThreshold, float highThreshold) {
        this.size = this.maxSize = maxSize;
        this.lowThreshold = lowThreshold;
        this.highThreshold = highThreshold;
        reset();
    }


    public void process(float rotation) {

        if (isFinished()) return;

        this.average = (this.size - 1) == 0 ? (this.average + rotation) / this.maxSize : this.average + rotation;


        this.distinctRotations.add(rotation);

        this.highCount = rotation > this.highThreshold ? this.highCount + 1 : this.highCount;

        this.lowCount = rotation < this.lowThreshold ? this.lowCount + 1 : this.lowCount;

        this.min = Math.min(rotation, this.min);

        this.max = Math.max(rotation, this.max);

        this.roundedCount = rotation > 1F && rotation % 1.5 != 0F
                && (Math.round(rotation) == 0F || rotation % 1 == 0F) ? this.roundedCount + 1 : this.roundedCount;

        this.size--;
    }

    public void reset() {
        this.size = this.maxSize;
        this.average = this.highCount = this.lowCount = this.roundedCount = 0;
        this.min = Float.MAX_VALUE;
        this.max = Float.MIN_VALUE;
        this.distinctRotations.clear();
    }

    private boolean isFinished() {
        return this.size == 0;
    }

    /**
     * Get the heuristics result if processing is finished, Null otherwise.
     */
    public HeuristicsResult getResult() {

        /*
        Collection is finished, Return the result and reset.
         */
        if (isFinished()) {

            return new HeuristicsResult(
                    this.average, this.min, this.max,
                    (this.maxSize - this.distinctRotations.size()),
                    this.highCount, this.lowCount, this.roundedCount
            );
        }

        /*
        Analysis result not finished yet.
         */
        return null;
    }

    public static class HeuristicsResult {

        private final float average, min, max;
        private final int duplicates, highCount, lowCount, roundedCount;

        public HeuristicsResult(float average, float min, float max, int duplicates, int highCount, int lowCount, int roundedCount) {
            this.average = average;
            this.min = min;
            this.max = max;
            this.duplicates = duplicates;
            this.highCount = highCount;
            this.lowCount = lowCount;
            this.roundedCount = roundedCount;
        }

        public float getAverage() {
            return average;
        }

        public float getMin() {
            return min;
        }

        public float getMax() {
            return max;
        }

        public int getDuplicates() {
            return duplicates;
        }

        public int getHighCount() {
            return highCount;
        }

        public int getLowCount() {
            return lowCount;
        }

        public int getRoundedCount() {
            return roundedCount;
        }
    }
}
