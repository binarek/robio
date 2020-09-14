package binarek.robio.registration.app;

import binarek.robio.common.codegen.BaseStyle;
import binarek.robio.registration.domain.competitor.Email;
import binarek.robio.registration.domain.competitor.FirstName;
import binarek.robio.registration.domain.competitor.LastName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableCreateCompetitorCommand.class)
public interface CreateCompetitorCommand {

    Email getEmail();

    FirstName getFirstName();

    LastName getLastName();

    @JsonProperty("isUnderage")
    boolean isUnderage();
}
