package com.upreader;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.upreader.util.Configurable;
import com.upreader.util.ConfigurationProperties;

/**
 * Upreadaer session manager
 * 
 * @author Flavius
 */
public class UpreaderSessionManager implements Configurable {
	public static final int DEFAULT_TIMEOUT = 3600;
	public static final String SESSION_HASH = "Upreader-Session-Hash";

	private Logger log = Logger.getLogger(UpreaderSessionManager.class);
	private int timeoutSeconds = DEFAULT_TIMEOUT;
	private boolean strictSessions = false;
	private long sessionAccumulator = 0L;

	public UpreaderSessionManager(UpreaderApplication application) {
		application.getConfigurator().addConfigurable(this);
	}

	/**
	 * Configures the object
	 * 
	 * @param props
	 *            - loaded configuration properties
	 */
	@Override
	public void configure(ConfigurationProperties props) {
		setTimeoutSeconds(props.getIntegerProperty("SessionTimeout", 3600));
		this.strictSessions = props.getYesNoProperty("StrictSessions", this.strictSessions);
		if (this.strictSessions) {
			this.log.debug("Scrict sessions enabled.");
		}
	}

	public void setTimeoutMinutes(int minutes) {
		this.timeoutSeconds = (minutes * 60);
	}

	public void setTimeoutSeconds(int seconds) {
		this.timeoutSeconds = seconds;
	}

	public int getTimeoutSeconds() {
		return this.timeoutSeconds;
	}

	public UpreaderSession getSession(HttpServletRequest request, boolean create) {
		UpreaderSession session = UpreaderSession.createSession(request.getSession(create));
		if (session == null) {
			return null;
		}

		boolean newSession = session.isNew();

		if (this.strictSessions) {
			if (newSession) {
				int requestHash = getRequestHash(request);
				session.setAttribute(SESSION_HASH, requestHash);
			} else {
				int requestHash = getRequestHash(request);
				int sessionHash = 0;
				try {
					sessionHash = Integer.parseInt((String) session.getAttribute(SESSION_HASH));
				} catch (Exception localException) {
				}

				if (requestHash != sessionHash) {
					this.log.debug("Session hash mismatch.  Invalidating session " + session.getId());
					session.invalidate();
					session = UpreaderSession.createSession(request.getSession(true));
					session.setAttribute(SESSION_HASH, requestHash);
					newSession = true;
				}
			}
		}

		if (newSession) {
			session.setMaxInactiveInterval(this.timeoutSeconds);
			session.setAttribute("Upreader-Session-ID", constructSessionID());
		}

		return session;
	}

	protected int getRequestHash(HttpServletRequest request) {
		String toHash = request.getHeader("User-Agent") + request.getHeader("Accept-Charset") + request.getHeader("Keep-Alive");
		return toHash.hashCode();
	}

	protected String constructSessionID() {
		StringBuilder sessionID = new StringBuilder(13);

		this.sessionAccumulator += 1L;
		String sessionSequenceNumber = Long.toString(this.sessionAccumulator, 36);
		for (int i = sessionSequenceNumber.length(); i < 5; i++) {
			sessionID.append('0');
		}
		sessionID.append(sessionSequenceNumber);

		String timeStamp = Long.toString(System.currentTimeMillis(), 36);
		for (int i = timeStamp.length(); i < 8; i++) {
			sessionID.append('0');
		}
		sessionID.append(timeStamp);

		return sessionID.toString();
	}
}
