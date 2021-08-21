package util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

	private static final Logger anonymousLogger = Logger.getAnonymousLogger();
	private static Logger       logger;

	public static Log run() {
		return new Log();
	}

	public static Logger getAnonLog() {
		return anonymousLogger;
	}

	public static Log logAnon() {
		logger = Logger.getAnonymousLogger();
		return run();
	}

	public void logSevere(final String message) {
		logger.log(Level.SEVERE, message);
	}

	public void logInfo(final String message) {
		logger.log(Level.INFO, message);
	}
}
