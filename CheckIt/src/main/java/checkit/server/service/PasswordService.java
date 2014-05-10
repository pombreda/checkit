/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The PasswordService interface
 */

package checkit.server.service;

import checkit.server.domain.User;

public interface PasswordService {
    public boolean isPasswordStrong(String password);
    public String encodePassword(User user);
}
