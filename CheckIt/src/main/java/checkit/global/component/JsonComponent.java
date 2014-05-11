/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * JSON functions
 * Globally used functions
 */

package checkit.global.component;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgresql.util.PGobject;
import org.springframework.stereotype.Component;

@Component
public class JsonComponent {
    
    /**
     * Convert JSON string to Postgres JSON
     *
     * @param jsonString JSON string to convert
     *
     * @return Postgres JSON.
     */
    public PGobject stringToJSON(String jsonString) {
        PGobject json = new PGobject();
        json.setType("json");
        try {
            json.setValue(jsonString);
        } catch (SQLException ex) {
            Logger.getLogger(JsonComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
    
}
