/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.plugin.service;

import checkit.plugin.domain.FormStruct;

/**
 *
 * @author Dodo
 */
public interface FormStructService {
    public FormStruct resolveNull(FormStruct formStruct);
    public String getDataJSON(FormStruct formStruct);
}
