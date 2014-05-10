/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Interface for report plugin.
 */

package checkit.plugin.report;

public interface Report {

    /**
     * Get JSON string which defines user input.
     * For example (example is taken from check plugin): 
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
     * Get values of parameters required to reporting.
     * Parameters name equals to "id" from getOptionsJSON function.
     * 
     * @return No datato return, reporting is consist of void functions.
     */
    public Object getCallRequiredParamsName();

    /**
     * Send reports on DOWN event.
     *
     * @param checkTitle Title of check to report.
     * @param params Optional parameters for running. If plugin has no parameters, call (Object[]) null.
     */
    public void reportDown(String checkTitle, Object[] params);

    /**
     * Send reports on UP event.
     *
     * @param checkTitle Title of check to report.
     * @param params Optional parameters for running. If plugin has no parameters, call (Object[]) null.
     */
    public void reportUp(String checkTitle, Object[] params);

    /**
     * Send reports on REGULAR event.
     *
     * @param checkTitle Title of check to report.
     * @param numberOfDowns Number of times the service has been DOWN during last period.
     * @param timeOfDowns Time how long the service has been DOWN during last period.
     * @param params Optional parameters for running. If plugin has no parameters, call (Object[]) null.
     */
    public void reportRegular(String checkTitle, int numberOfDowns, long timeOfDowns, Object[] params);
}
