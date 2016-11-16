package uk.co.bluetangerine.delegate;

import uk.co.bluetangerine.delegate.Command;

import java.io.IOException;

/**
 * Created by tony on 16/11/2016.
 */
public class ParseHTMLFromUrlCommand implements Command {
    ParseHTMLFromUrl parseHTMLFromUrl;

    public ParseHTMLFromUrlCommand(ParseHTMLFromUrl parseHTMLFromUrl) {
        this.parseHTMLFromUrl = parseHTMLFromUrl;
    }

    public String parse(String url) throws IOException {
        return parseHTMLFromUrl.parse(url);
    }
}
