/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.plugin.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class FolderScanCheck {
    @Autowired
    private PluginCheckService checkService;

    @Async
    public void run() {
        boolean scanFolder = true; //TODO - add to global settings
        Path myDir = Paths.get("D:\\Skola\\CVUT\\bakule\\! zdrojak\\plugins\\check\\");
        while (scanFolder) {
            try {
                WatchService watcher = myDir.getFileSystem().newWatchService();
                myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
                WatchKey watckKey = watcher.take();

                List<WatchEvent<?>> events = watckKey.pollEvents();
                for (WatchEvent event : events) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        String[] tokens = event.context().toString().split("\\.(?=[^\\.]+$)");
                        if (tokens[1].equalsIgnoreCase("jar")) {
                            if (checkService.getPluginByFilename(tokens[0]) == null) {
                                checkService.registerPlugin(tokens[0]);
                            }
                        }
                    }
                    if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                        String[] tokens = event.context().toString().split("\\.(?=[^\\.]+$)");
                        if (checkService.getPluginByFilename(tokens[0]) != null) {
                            checkService.deletePlugin(tokens[0]);
                        }
                    }
                    if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                    }
                }
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(FolderScanReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
