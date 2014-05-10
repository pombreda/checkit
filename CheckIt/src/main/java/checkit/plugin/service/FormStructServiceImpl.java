/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The FormStructService implementation
 */

package checkit.plugin.service;

import checkit.plugin.domain.FormStruct;
import checkit.plugin.domain.FormStructRow;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class FormStructServiceImpl implements FormStructService {

    /**
     * Resolve collapsing data sended from HTML form.
     *
     * @param formStruct Sended form.
     * 
     * @return Form struct with resolved collapsing data.
     */
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

    /**
     * Convert form struct to JSON string.
     *
     * @param formStruct Form struct to convert.
     * 
     * @return JSON string.
     */
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
