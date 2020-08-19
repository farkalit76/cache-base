package ae.gov.sdg.paperless.services.common;

/**
 * GenericAuthenticationService service is responsible of obtaining access tokens from IDS
 *
 */
public interface GenericAuthenticationService {

    /**
     * Gets an GenericAuthToken access token from GSB using provided credentials
     *
     * @param tokenName Cached token name
     * @param username  Username
     * @param password  Password
     * @return An instance of <code>GenericAuthToken</code>
     */
    GenericAuthToken getOAuthAccessToken(String tokenName, String username, String password);
}