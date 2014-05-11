/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The PasswordComponent interface
 */

package checkit.server.component;

import checkit.server.domain.User;

public interface PasswordComponent {
    public boolean isPasswordStrong(String password);
    public String encodePassword(User user);
}
