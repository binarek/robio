package binarek.robio.ftl.adapter.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jooq.JSON;
import org.springframework.lang.Nullable;

import static binarek.robio.util.MapperUtil.mapNullSafe;

class FtlJsonTypeMapper<T> {

    private final ObjectMapper objectMapper;
    private final Class<? extends T> handledType;

    static <T> FtlJsonTypeMapper<T> ofType(Class<? extends T> handledType) {
        return new FtlJsonTypeMapper<>(handledType);
    }

    private FtlJsonTypeMapper(Class<? extends T> handledType) {
        this.handledType = handledType;
        this.objectMapper = new ObjectMapper();
    }

    @Nullable
    JSON toJson(@Nullable T object) {
        return mapNullSafe(object, this::toNonNullJson);
    }

    @Nullable
    T toObject(@Nullable JSON json) {
        return mapNullSafe(json, this::toNonNullObject);
    }

    private JSON toNonNullJson(T object) {
        try {
            return JSON.valueOf(objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private T toNonNullObject(JSON json) {
        try {
            return objectMapper.readValue(json.data(), handledType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
