package uk.co.bluetangerine.delegate;

import uk.co.bluetangerine.delegate.dto.ResultsDto;

import java.io.IOException;

/**
 * Created by tony on 15/11/2016.
 * Interface class to allow future extension
 * perhaps for a different target page or
 * from another source
 */
public interface ParseHTML {
    String parse (String url) throws IOException;
}
