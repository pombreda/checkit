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

    @PostConstruct
    public void initialize() {
        reportFolderScan.run();
        checkFolderScan.run();
    }
}
