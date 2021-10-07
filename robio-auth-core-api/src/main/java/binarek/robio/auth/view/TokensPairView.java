package binarek.robio.auth.view;

public interface TokensPairView {

    TokenView getRefreshToken();

    TokenView getAccessToken();
}
