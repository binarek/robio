package binarek.robio.ftl.domain.execution.model;

import binarek.robio.common.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableFtlRun.class)
public abstract class FtlRun {

    public abstract FtlParticipation participation();

    public abstract Integer number();

    public abstract Time time();

    public abstract Boolean passed();
}
