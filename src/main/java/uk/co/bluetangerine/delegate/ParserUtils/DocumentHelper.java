package uk.co.bluetangerine.delegate.ParserUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by tony on 15/11/2016.
 */
public class DocumentHelper {
    public Document getDocumentHelper(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}
