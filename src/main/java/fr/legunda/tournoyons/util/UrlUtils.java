package fr.legunda.tournoyons.util;

import fr.legunda.tournoyons.server.core.MapParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UrlUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlUtils.class);

    public static URLConnection callUrl(String urlString) {
        LOGGER.info(String.format("Call url: [%s]", urlString));

        try {
            URL url = new URL(urlString);

            return url.openConnection();
        }
        catch (Exception e) {
            LOGGER.error(String.format("Unable to call url [%s], cause: [%s].", urlString, e.getMessage()));
        }

        return null;
    }

    public static String getContent(URLConnection urlConnection) {
        StringBuilder result = new StringBuilder();

        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {

            String inputLine;

            while ((inputLine = buffer.readLine()) != null) {
                result.append(inputLine);
            }
        }
        catch (Exception e) {
            LOGGER.error("Unable to call random service.");
        }

        return result.toString();
    }

    public static String makeCouple(MapParam.Parameter param, String value) {
        return String.format("%s=%s", param.getName(), value);
    }

}
