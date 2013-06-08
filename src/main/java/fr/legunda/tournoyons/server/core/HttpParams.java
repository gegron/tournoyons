package fr.legunda.tournoyons.server.core;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

import static fr.legunda.tournoyons.server.core.MapParam.Parameter;

public class HttpParams {

    private static Logger LOG = LoggerFactory.getLogger(HttpParams.class);

    /**
     * Lecture des paramètres qui sont placés dans une MapParam
     *
     * @param query
     * @return
     */
    public static MapParam getParameters(String query) {
        MapParam parameters = new MapParam();

        if (query != null) {
            Iterable<String> couples = Splitter.on("&").split(query);

            for (String param : couples) {
                if (!Strings.isNullOrEmpty(param)) {
                    Iterable<String> element = Splitter.on("=").split(param);
                    Iterator<String> iterator = element.iterator();

                    String paramName = iterator.next();
                    String paramValue = iterator.next();

                    try {
                        parameters.put(Parameter.getParamByName(paramName), paramValue);
                    } catch (IllegalArgumentException e) {
                        LOG.warn(String.format("Unable to retrieve parameter '%s'", paramName));
                    }
                }
            }
        }

        return parameters;
    }

}