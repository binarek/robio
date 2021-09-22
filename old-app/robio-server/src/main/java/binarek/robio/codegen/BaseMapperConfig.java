package binarek.robio.codegen;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BaseMapperConfig {
}
