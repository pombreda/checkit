/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.server.domain.Testing;

/**
 *
 * @author Dodo
 */
public interface NetworkService {
    public void postCreatingToAgent(Testing testing);
    public void postDeletingToAgent(Testing testing);
    public void postUpdatingToAgent(Testing testing);
}
