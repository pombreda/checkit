/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The PasswordComponent implementation
 * All services related to password
 */

package checkit.server.component;

import checkit.server.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordComponentImpl implements PasswordComponent {
    @Autowired
    private PasswordEncoder passwordEncoder;
    

    /**
     * Check if string is convenient for password
     *
     * @param password Tested string
     * 
     * @return True if string satisfies, false otherwise.
     */
    @Override
    public boolean isPasswordStrong(String password) {
        if (password.length() < 8) return false;
        if (!password.matches(".*[A-Z].*")) return false;
        if (!password.matches(".*[a-z].*")) return false;
        if (!password.matches(".*\\d.*")) return false;
        if (password.matches(".*\\s.*$")) return false;
        return true;
    }

    /**
     * Create password hash for user
     *
     * @param user User for whom is hash generated.
     * 
     * @return Password hash
     */
    @Override
    public String encodePassword(User user) {
        return passwordEncoder.encodePassword(user.getPassword(), user.getUsername());

    }
    
}
