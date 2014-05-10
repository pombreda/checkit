/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Interface for check plugin for server.
 */

package checkit.plugin.check;

public interface Check {
    /**
     * Get JSON string which defines user input.
     * For example: 
     * {
     *   "inputs": [
     *     {
     *       "id": "db",
     *       "type": "select",
     *       "title": "Database type",
     *       "description": "Choose database type.",
     *       "options" : ["MySQL", "PostgreSQL", "MSSQL"]
     *     },
     *     {
     *       "id": "url",
     *       "type": "text",
     *       "title": "URL of DB server",
     *       "description": "e.g. db.domain.com"
     *     },   
     *     {
     *       "id": "username",
     *       "type": "text",
     *       "title": "DB login",
     *       "description": ""
     *     },
     *     {
     *       "id": "password",
     *       "type": "password",
     *       "title": "DB password",
     *       "description": "ATTENTION! Unfortunately, we have to save password in non-hash form, because we need to use your password to log in. Is strongly recommended to create a new user with only login privileges for testing."
     *     }
     *   ]
     * }
     * 
     * @return JSON string with required user input.
     */
    public Object getOptionsJSON();
    
    /**
     * Get parameters name for additional information for table in result page.
     * Parameters name equals to "id" from getOptionsJSON function.
     *
     * @return List of strings with parametrs name of additional data to display in table.
     */
    public Object getTableRequiredParamsName();

    /**
     * Get header of additional information for table in result page.
     *
     * @return List of strings with table header titles.
     */
    public Object getTableRequiredHeaderTitle();
}
