/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Scanning report folder for plugins
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
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class FolderScanReport {
    @Autowired
    private PluginReportService reportService;

    @Autowired
    MessageSource messageSource;
    
    Locale locale = LocaleContextHolder.getLocale();

    /**
     * Scan specific folder for create, delete and modify events.
     * 
     */
    @Async
    public void run() {
        boolean scanFolder = true; //TODO - create global settings for administrator, add this variable to the future settings
        Path myDir = Paths.get(messageSource.getMessage("path.plugin.report", null, locale));
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
                            if (reportService.getPluginByFilename(tokens[0]) == null) {
                                reportService.registerPlugin(tokens[0]);
                            }
                        }
                    }
                    if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                        String[] tokens = event.context().toString().split("\\.(?=[^\\.]+$)");
                        if (reportService.getPluginByFilename(tokens[0]) != null) {
                            reportService.deletePlugin(tokens[0]);
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
