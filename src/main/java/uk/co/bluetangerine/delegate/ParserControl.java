package uk.co.bluetangerine.delegate;

import java.io.IOException;

/**
 * Created by tony on 16/11/2016.
 */
public class ParserControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public String ExecuteParser(String resourceLocation) throws IOException {
        return command.parse(resourceLocation);
    }
}
