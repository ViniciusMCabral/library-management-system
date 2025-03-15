package br.edu.ifba.inf008.shell;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.net.URLClassLoader;

import br.edu.ifba.inf008.App;
import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.IPluginController;

public class PluginController implements IPluginController {
    public boolean init() {
        try {
            File pluginsDir = new File("./plugins");
            
            FilenameFilter jarFilter = (dir, name) -> 
                name.toLowerCase().endsWith(".jar");
            
            String[] plugins = pluginsDir.list(jarFilter);
            if (plugins == null) {
                System.out.println("Plugins not found in: " + pluginsDir.getAbsolutePath());
                return false;
            }
            
            URL[] jars = new URL[plugins.length];
            for (int i = 0; i < plugins.length; i++) {
                File jarFile = new File(pluginsDir, plugins[i]);
                jars[i] = jarFile.toURI().toURL(); 
            }
            
            URLClassLoader ulc = new URLClassLoader(jars, App.class.getClassLoader());
            
            for (String plugin : plugins) {
                String pluginName = plugin.split("\\.")[0];
                try {
                    Class<?> clazz = Class.forName("br.edu.ifba.inf008.plugins." + pluginName, true, ulc);
                    IPlugin pluginInstance = (IPlugin) clazz.getDeclaredConstructor().newInstance();
                    pluginInstance.init();
                    System.out.println("Plugin loaded: " + pluginName);
                } catch (Exception e) {
                    System.err.println("Error loading plugin: " + pluginName + ": " + e.getMessage());
                }
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error loading plugin: " + e.getMessage());
            return false;
        }
    }
}