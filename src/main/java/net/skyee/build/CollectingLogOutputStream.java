package net.skyee.build;

import org.apache.commons.exec.LogOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectingLogOutputStream extends LogOutputStream {
    Logger log = LoggerFactory.getLogger(CollectingLogOutputStream.class);

    @Override protected void processLine(String line, int level) {
        log.info(line);
    }
}
