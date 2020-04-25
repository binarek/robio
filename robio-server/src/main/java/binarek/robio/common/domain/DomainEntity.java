package binarek.robio.common.domain;

import org.springframework.lang.Nullable;

import java.util.UUID;

public interface DomainEntity {

    @Nullable
    UUID getId();

    String getName();

    @Nullable
    String getNotes();
}
