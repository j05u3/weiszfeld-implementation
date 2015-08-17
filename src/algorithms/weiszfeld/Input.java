package algorithms.weiszfeld;

import java.util.List;

public class Input {
    private int dimension; // dimension of the points
    private List<WeightedPoint> points;
    private Double permissibleError; // maximum permissible permissibleError
    private Integer maxIterations;

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public List<WeightedPoint> getPoints() {
        return points;
    }

    public void setPoints(List<WeightedPoint> points) {
        this.points = points;
    }

    public Double getPermissibleError() {
        return permissibleError;
    }

    public void setPermissibleError(Double permissibleError) {
        this.permissibleError = permissibleError;
    }

    public Integer getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(Integer maxIterations) {
        this.maxIterations = maxIterations;
    }
}
