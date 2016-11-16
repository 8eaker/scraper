package uk.co.bluetangerine.delegate;

import java.io.IOException;

/**
 * Created by tony on 15/11/2016.
 * Main entry point for command line.
 * Keep the entry point as lightweight as possible
 *
 */
public class WebPageScraper {
    public static void main (String args[]) throws IOException {
      ParseHTML parser = new ParseHTMLFromUrl();
        System.out.println(parser.parse(args[0]));
    }
}
