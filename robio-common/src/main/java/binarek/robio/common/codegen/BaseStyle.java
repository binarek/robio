package binarek.robio.common.codegen;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@JsonSerialize
@Value.Style(redactedMask = "***")
public @interface BaseStyle {
}
