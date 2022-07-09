package binarek.robio.auth;

import binarek.robio.shared.DateTimeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AuthSchedulers {

    private static final String ZONE_EXPRESSION = "#{dateTimeProvider.getBusinessZoneId()}";

    private static final Logger logger = LoggerFactory.getLogger(AuthSchedulers.class);

    private final RefreshTokenWhitelistRepository refreshTokenWhitelistRepository;
    private final DateTimeProvider dateTimeProvider;

    AuthSchedulers(RefreshTokenWhitelistRepository refreshTokenWhitelistRepository,
                   DateTimeProvider dateTimeProvider) {
        this.refreshTokenWhitelistRepository = refreshTokenWhitelistRepository;
        this.dateTimeProvider = dateTimeProvider;
    }

    @Scheduled(cron = "${robio.auth.core.scheduler.remove-expired-refresh-tokens.cron:0 0 0 * * *}", zone = ZONE_EXPRESSION)
    public void removeExpiredRefreshTokensFromWhitelist() {
        logger.info("Removing expired refresh tokens from whitelist");
        refreshTokenWhitelistRepository.removeByExpireAtBefore(dateTimeProvider.currentInstant());
    }
}
