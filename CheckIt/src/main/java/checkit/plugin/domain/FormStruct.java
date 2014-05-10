/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The FormStruct class represents plugins website form struct, which is suited for Thymeleaf needs.
 */

package checkit.plugin.domain;

import java.util.ArrayList;

public class FormStruct {

    public FormStruct() {
    }

    private ArrayList<FormStructRow> arrData;

    public ArrayList<FormStructRow> getArrData() {
        return arrData;
    }

    public void setArrData(ArrayList<FormStructRow> arrData) {
        this.arrData = arrData;
    }

    public void addArrData(FormStructRow arrData) {
        this.arrData.add(arrData);
    }

}
