/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The Input class represents one input field from plugins website form and all their settings.
 */

package checkit.plugin.domain;

import java.util.List;

public class Input {
    private String id;
    private String type;
    private String title;
    private String description;
    private List<String> options;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
