/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.agent.jdbc;

import checkit.server.domain.Server;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Dodo
 */
public class ServerRowMapper implements RowMapper<Server> {

    @Override
    public Server mapRow(ResultSet resultSet, int line) throws SQLException {
        ServerExtractor serverExtractor = new ServerExtractor();
        return serverExtractor.extractData(resultSet);
    }
    
}
