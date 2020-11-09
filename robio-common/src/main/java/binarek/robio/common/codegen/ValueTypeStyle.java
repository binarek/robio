package binarek.robio.common.codegen;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@JsonSerialize
@Value.Style(
        typeAbstract = "*",
        of = "ofValue",
        visibility = Value.Style.ImplementationVisibility.PACKAGE,
        defaults = @Value.Immutable(/*builder = false, */copy = false), // builder = true makes MapStruct not work (in version 1.3.1.Final)
        redactedMask = "***")
public @interface ValueTypeStyle {
}
