/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Interface for check plugin for agent.
 */

package checkit.plugin.check;

public interface CheckAgent {

    /**
     * Get values of parameters required to run.
     * Parameters name equals to "id" from getOptionsJSON function.
     * 
     * @return Always return ARRAY of objects
     */
    public Object getCallRequiredParamsName();

    /**
     * Get values of parameters required to evaluating if result is ok or not.
     * Parameters name equals to "id" from getOptionsJSON function.
     * 
     * @return Always return ARRAY of objects
     */
    public Object getResultParamsName();

    /**
     * Run plugin and get results.
     * 
     * @param params Optional parameters for running. If plugin has no parameters, call (Object[]) null.
     * 
     * @return Always return ARRAY of objects
     */
    public Object run(Object[] params);

    /**
     * Evaluate if result is ok or not.
     * 
     * @param params Parameters are called from getResultParamsName function.
     * 
     * @return Always return ARRAY of objects
     */
    public Object isItOk(Object[] params);
}
