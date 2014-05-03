/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.plugin.check;

import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;

/**
 *
 * @author Dodo
 */
public class FTP implements CheckAgent {

    @Override
    public Object getCallRequiredParamsName() {
        return new Object[]{"url", "username", "password"};
    }

    @Override
    public Object getResultParamsName() {
        return new Object[]{"ok"};
    }

    @Override
    public Object run(Object[] params) {
        String url = params[0].toString();
        String username = params[1].toString();
        String password = params[2].toString();
        boolean answer = false;
        
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(url, 21);
            boolean login = ftpClient.login(username, password);
            if (login) {
                answer = ftpClient.sendNoOp();
            }
        } catch (FTPConnectionClosedException e) {
        } catch (IOException | NullPointerException e) {
        }
        return new Object[]{answer};
    }

    @Override
    public Object isItOk(Object[] params) {
        return params[0];
    }
    
}
