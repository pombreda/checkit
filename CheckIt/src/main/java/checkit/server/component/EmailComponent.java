/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The EmailComponent interface
 */

package checkit.server.component;

import checkit.server.domain.User;

public interface EmailComponent {
    public boolean isEmailValid(String email);
    public String getActivationCode(User user);
    public void sendActivationLink(User user, String hash);
    public void sendUpdateLink(User user, String hash);
}
