/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The FormStructService interface
 */

package checkit.plugin.service;

import checkit.plugin.domain.FormStruct;

public interface FormStructService {
    public FormStruct resolveNull(FormStruct formStruct);
    public String getDataJSON(FormStruct formStruct);
}
