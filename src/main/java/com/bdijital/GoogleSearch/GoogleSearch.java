package com.bdijital.GoogleSearch;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.v1.CustomSearchAPI;
import com.google.api.services.customsearch.v1.model.Result;
import com.google.api.services.customsearch.v1.model.Search;

import java.util.List;

public class GoogleSearch {
    public static void setGoogleApiKey(String googleApiKey) {
        GOOGLE_API_KEY = googleApiKey;
    }

    public static void setSearchEngineId(String searchEngineId) {
        SEARCH_ENGINE_ID = searchEngineId;
    }

    private static String GOOGLE_API_KEY;
    private static String SEARCH_ENGINE_ID;

    /**
     * @param httpTimeout HTTP connection timeout in seconds.
     * @param keyword Google Search query string.
     * @return List of search query results.
     */
    public static List<Result> search(int httpTimeout, String keyword) {
        CustomSearchAPI customsearch = null;

        try {
            HttpRequestInitializer httpRequestInitializer = new HttpRequestInitializer() {
                public void initialize(HttpRequest httpRequest) {
                    try {
                        // set connect and read timeouts
                        httpRequest.setConnectTimeout(httpTimeout * 1000);
                        httpRequest.setReadTimeout(0);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };
            customsearch = new CustomSearchAPI.Builder(new NetHttpTransport(), new JacksonFactory(), httpRequestInitializer).setApplicationName("BBdijital").build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Result> resultList = null;
        try {
            assert customsearch != null;
            CustomSearchAPI.Cse.List list = customsearch.cse().list();
            list.setQ(keyword);
            list.setKey(GOOGLE_API_KEY);
            list.setCx(SEARCH_ENGINE_ID);
            Search results = list.execute();
            resultList = results.getItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }
}