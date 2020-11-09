package binarek.robio.ftl.api.execution.model;

import binarek.robio.common.codegen.ValueDefStyle;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@ValueDefStyle
interface FtlCompetitionResultsDef {

    List<FtlRobotResult> results();
}
