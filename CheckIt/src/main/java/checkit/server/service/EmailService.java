/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.server.domain.User;

/**
 *
 * @author Dodo
 */
public interface EmailService {
    public boolean isEmailValid(String email);
    public String getActivationCode(User user);
    public void sendActivationLink(User user, String hash);
    public void sendUpdateLink(User user, String hash);
}
