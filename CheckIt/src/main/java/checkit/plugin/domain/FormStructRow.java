/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The FormStructRow class represents one row in plugins website forms, which is suited for Thymeleaf needs.
 */

package checkit.plugin.domain;

public class FormStructRow {
    private String name;
    private Object value;
    private String type;

    public FormStructRow() {
    }

    public FormStructRow(String name, String value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
