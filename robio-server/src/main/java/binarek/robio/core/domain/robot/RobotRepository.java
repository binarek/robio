package binarek.robio.core.domain.robot;

import org.springframework.lang.Nullable;

import java.util.Optional;
import java.util.UUID;

public interface RobotRepository {

    Optional<Robot> getById(UUID id);

    boolean existsByIdOrName(@Nullable UUID id, String name);

    Robot insert(Robot robot);

    Robot insertOrUpdate(Robot entity);

    boolean delete(UUID entityId);
}
