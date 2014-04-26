/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.plugin.service;

import checkit.plugin.domain.FormStruct;
import checkit.plugin.domain.FormStructRow;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class FormStructServiceImpl implements FormStructService {

    @Override
    public FormStruct resolveNull(FormStruct formStruct) {
        ArrayList<FormStructRow> rows = formStruct.getArrData();
        for (FormStructRow row : rows) {
            if (row.getValue() == null) {
                if (row.getType().equals("checkbox")) {
                    row.setValue(false);
                } else {
                    row.setValue("");
                }
            }
        }
        formStruct.setArrData(rows);
        return formStruct;
    }

    @Override
    public String getDataJSON(FormStruct formStruct) {
        ArrayList<FormStructRow> rows = formStruct.getArrData();
        JSONObject json = new JSONObject();
        for (FormStructRow row : rows) {
            json.put(row.getName(), row.getValue());
        }
        return json.toJSONString();
    }

}
