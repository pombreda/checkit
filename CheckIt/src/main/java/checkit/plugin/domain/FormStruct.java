/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.plugin.domain;

import java.util.ArrayList;

/**
 *
 * @author Dodo
 */
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
