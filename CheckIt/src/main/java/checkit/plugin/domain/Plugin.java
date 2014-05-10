/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The Plugin class represents domain class and equals to one row in table "plugins_report" or "plugins_check" from database.
 */

package checkit.plugin.domain;

import org.hibernate.validator.constraints.NotEmpty;

public class Plugin {
    private String filename;
    private boolean enabled;
    @NotEmpty
    private String title;
    private String description;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
}
