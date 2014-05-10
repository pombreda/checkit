/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Scanning folders for plugins
 */

package checkit.plugin.service;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FolderScan {
    @Autowired
    FolderScanReport reportFolderScan;

    @Autowired
    FolderScanCheck checkFolderScan;

    /**
     * On server start run folder scanning
     * 
     */
    @PostConstruct
    public void initialize() {
        reportFolderScan.run();
        checkFolderScan.run();
    }
}
