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
public interface Check {
    public Object getOptionsJSON();
    public Object getTableRequiredParamsName();
    public Object getTableRequiredHeaderTitle();
}
