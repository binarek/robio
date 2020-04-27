package binarek.robio.core.domain.team;

import binarek.robio.common.domain.DomainEntityAlreadyExistsException;
import org.springframework.lang.Nullable;

import java.util.UUID;

public class TeamAlreadyExistsException extends DomainEntityAlreadyExistsException {

    public TeamAlreadyExistsException(@Nullable UUID id, String name) {
        super("Team", id, name);
    }
}
