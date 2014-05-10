/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The EmailService interface
 */

package checkit.server.service;

import checkit.server.domain.User;

public interface EmailService {
    public boolean isEmailValid(String email);
    public String getActivationCode(User user);
    public void sendActivationLink(User user, String hash);
    public void sendUpdateLink(User user, String hash);
}
