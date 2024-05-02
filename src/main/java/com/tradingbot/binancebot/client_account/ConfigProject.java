package com.tradingbot.binancebot.client_account;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProject {
    private static final String CONFIG_FILE = "src/main/resources/config.properties";
    private static ConfigProject instance;

    private String UM_BASE_URL;
    private String CM_BASE_URL;
    private String WSS_URL;
    private String API_KEY;
    private String SECRET_KEY;

    private String TESTNET_WSS_URL;
    private String TESTNET_BASE_URL;
    private String TESTNET_API_KEY;
    private String TESTNET_SECRET_KEY;

    private ConfigProject() {
        loadConfig();
    }

    public static ConfigProject getInstance() {
        if (instance == null) {
            instance = new ConfigProject();
        }
        return instance;
    }



    private void loadConfig() {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            Properties prop = new Properties();
            prop.load(input);

            UM_BASE_URL = prop.getProperty("UM_BASE_URL");
            CM_BASE_URL = prop.getProperty("CM_BASE_URL");
            WSS_URL = prop.getProperty("WSS_URL");
            API_KEY = prop.getProperty("API_KEY");
            SECRET_KEY = prop.getProperty("SECRET_KEY");
            TESTNET_WSS_URL = prop.getProperty("TESTNET_WSS_URL");
            TESTNET_BASE_URL = prop.getProperty("TESTNET_BASE_URL");
            TESTNET_API_KEY = prop.getProperty("TESTNET_API_KEY");
            TESTNET_SECRET_KEY = prop.getProperty("TESTNET_SECRET_KEY");

        } catch (IOException ex) {
            System.err.println("Failed to load config file");
            ex.printStackTrace();
        }
    }
    public String getWSS_URL() {
        return WSS_URL;
    }

    public String getUM_BASE_URL() {
        return UM_BASE_URL;
    }

    public String getCM_BASE_URL() {
        return CM_BASE_URL;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public String getSECRET_KEY() {
        return SECRET_KEY;
    }

    public String getTESTNET_BASE_URL() {
        return TESTNET_BASE_URL;
    }

    public String getTESTNET_API_KEY() {
        return TESTNET_API_KEY;
    }

    public String getTESTNET_SECRET_KEY() {
        return TESTNET_SECRET_KEY;
    }
    public String getTESTNET_WSS_URL() {
        return TESTNET_WSS_URL;
    }
}
