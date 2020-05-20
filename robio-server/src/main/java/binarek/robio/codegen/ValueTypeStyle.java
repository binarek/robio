package binarek.robio.codegen;

import org.immutables.value.Value;

@Value.Style(
        typeAbstract = "*Type",
        typeImmutable = "*",
        visibility = Value.Style.ImplementationVisibility.PUBLIC,
        defaults = @Value.Immutable(builder = false, copy = false),
        redactedMask = "***")
public @interface ValueTypeStyle {
}
