/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.plugin.check;

/**
 *
 * @author Dodo
 */
public interface CheckAgent {
    public Object getCallRequiredParamsName();
    public Object getResultParamsName();
    public Object run(Object[] params);
    public Object isItOk(Object[] params);
}
