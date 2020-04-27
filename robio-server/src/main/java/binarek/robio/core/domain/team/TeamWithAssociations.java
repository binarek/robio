package binarek.robio.core.domain.team;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.List;
import java.util.UUID;

@Value.Immutable
@JsonDeserialize(as = ImmutableTeamWithAssociations.class)
public interface TeamWithAssociations extends Team {

    List<UUID> getRobotsIds();
}
