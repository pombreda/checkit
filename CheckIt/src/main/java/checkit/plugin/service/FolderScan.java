/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    @PostConstruct
    public void initialize() {
        reportFolderScan.run();
        checkFolderScan.run();
    }
}
