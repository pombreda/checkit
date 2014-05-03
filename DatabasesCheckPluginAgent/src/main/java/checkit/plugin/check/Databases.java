/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.plugin.check;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Dodo
 */
public class Databases implements CheckAgent {

    @Override
    public Object getCallRequiredParamsName() {
        return new Object[]{"url", "username", "password", "db"};
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
        String db = params[3].toString();
        boolean reachable = false;

        switch (db) {
            case "MSSQL":
                db = "sqlserver";
                break;
            case "PostgreSQL":
                db = "postgresql";
                break;
            case "MySQL":
                db = "mysql";
                break;
        }
        try {
            Connection con = DriverManager.getConnection("jdbc:" + db + "://" + url, username, password);
            reachable = con.isValid(5);
        } catch (SQLException ex) {
        }
        return new Object[] {reachable};
    }

    @Override
    public Object isItOk(Object[] params) {
        return params[0];
    }
    
}
