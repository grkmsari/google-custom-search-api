import com.bdijital.GoogleSearch.GoogleSearch;
import com.google.api.services.customsearch.v1.model.Result;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class GoogleSearchTest {
    Dotenv dotenv = Dotenv.load();

    @Test
    void search() {
        GoogleSearch.setGoogleApiKey(dotenv.get("GOOGLE_API_KEY"));
        GoogleSearch.setSearchEngineId(dotenv.get("SEARCH_ENGINE_ID"));

        List<Result> results = new ArrayList<>();

        try {
            results = GoogleSearch.search(5, "kaufen");
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(Result result : results){
            System.out.println(result.getDisplayLink());
            System.out.println(result.getTitle());
            // all attributes:
            System.out.println(result.toString());
        }
    }
}