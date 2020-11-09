package binarek.robio.ftl.api.execution.model;

import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.UUID;

public final class FtlRobotResult {

    private final UUID competitionId;
    private final UUID robotId;
    @Nullable
    private final Integer place;
    private final boolean isDisqualified;

    private FtlRobotResult(UUID competitionId, UUID robotId, @Nullable Integer place, boolean isDisqualified) {
        this.competitionId = competitionId;
        this.robotId = robotId;
        this.place = place;
        this.isDisqualified = isDisqualified;
    }

    public UUID competitionId() {
        return competitionId;
    }

    public UUID robotId() {
        return robotId;
    }

    @Nullable
    public Integer place() {
        return place;
    }

    public boolean isDisqualified() {
        return isDisqualified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FtlRobotResult that = (FtlRobotResult) o;
        return isDisqualified == that.isDisqualified &&
                competitionId.equals(that.competitionId) &&
                robotId.equals(that.robotId) &&
                Objects.equals(place, that.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(competitionId, robotId, place, isDisqualified);
    }

    @Override
    public String toString() {
        return "FtlRobotResult{" +
                "competitionId=" + competitionId +
                ", robotId=" + robotId +
                ", place=" + place +
                ", isDisqualified=" + isDisqualified +
                '}';
    }
}