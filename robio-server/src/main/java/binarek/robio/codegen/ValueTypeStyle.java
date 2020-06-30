package binarek.robio.codegen;

import org.immutables.value.Value;

@Value.Style(
        typeAbstract = "*Value",
        typeImmutable = "*",
        visibility = Value.Style.ImplementationVisibility.PUBLIC,
        defaults = @Value.Immutable(/*builder = false, */copy = false), // builder = true makes MapStruct not work (in version 1.3.1.Final)
        redactedMask = "***")
public @interface ValueTypeStyle {
}
