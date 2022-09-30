package com.thatdudo.omniscience.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thatdudo.omniscience.Omniscience;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ConfigManager {
    private static boolean initialized = false;

    private static Config config = null;
    private static final Config defaults = new Config();

    private static Gson gson;
    private static File configFile;

    private static final Executor executor = Executors.newSingleThreadExecutor();

    public static void init() {
        if (initialized) {
            return;
        }

        gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), Omniscience.CONFIG_FILE_NAME);
        readConfig(false);

        initialized = true;
    }

    public static void readConfig(boolean async) {
        Runnable task = () -> {
            try {
                //read if exists
                if (configFile.exists()) {
                    String fileContents = FileUtils.readFileToString(configFile, Charset.defaultCharset());
                    config = gson.fromJson(fileContents, Config.class);

                    //If there were any invalid options, write the fixed config
                    if (config.validate()) {
                        writeConfig(true);
                    }

                } else { //write new if no config file exists
                    writeNewConfig();
                }

            } catch (Exception e) {
                e.printStackTrace();
                writeNewConfig();
            }
        };

        if (async) executor.execute(task);
        else task.run();
    }

    public static void writeNewConfig() {
        config = new Config();
        writeConfig(false);
    }

    public static void writeConfig(boolean async) {
        Runnable task = () -> {
            try {
                if (config != null) {
                    String serialized = gson.toJson(config);
                    FileUtils.writeStringToFile(configFile, serialized, Charset.defaultCharset());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        if (async) executor.execute(task);
        else task.run();
    }

    public static Config getConfig() {
        return config;
    }

    public static Config getDefaults() {
        return defaults;
    }
}
