package binarek.robio.common.domain.entity;

import binarek.robio.common.domain.value.Notes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;

import java.util.UUID;

public interface Entity {

    @Nullable
    @JsonIgnore
    UUID getIdValue();

    @Nullable
    Long getVersion();

    @JsonIgnore
    String getNameValue();

    @Nullable
    Notes getNotes();
}
