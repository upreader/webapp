package com.upreader.security;

import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.security.Principal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.caucho.security.AbstractAuthenticator;
import com.caucho.security.Login;
import com.caucho.security.LoginException;
import com.caucho.security.NullAuthenticator;
import com.caucho.security.SingleSignon;
import com.caucho.server.session.SessionImpl;

public abstract class AbstractLogin implements Login {
	private static final Logger log = Logger.getLogger(AbstractLogin.class.getName());
	protected com.caucho.security.Authenticator _auth;
	protected SingleSignon _singleSignon;
	private Instance<com.caucho.security.Authenticator> _authInstance;
	private Instance<SingleSignon> _signonInstance;
	private boolean _isSessionSaveLogin = true;
	private boolean _isLogoutOnTimeout = true;

	public void setAuthenticator(com.caucho.security.Authenticator auth) {
		this._auth = auth;
	}

	public com.caucho.security.Authenticator getAuthenticator() {
		if (this._auth == null) {
			if (!this._authInstance.isUnsatisfied()) {
				this._auth = ((Authenticator) this._authInstance.get());
			}

			if (this._auth == null) {
				this._auth = new NullAuthenticator();
			}

			if (log.isLoggable(Level.FINE)) {
				log.fine(toString() + " using " + this._auth);
			}
		}
		return this._auth;
	}

	protected SingleSignon getSingleSignon() {
		if (this._singleSignon == null) {
			com.caucho.security.Authenticator auth = getAuthenticator();

			if ((this._auth instanceof AbstractAuthenticator)) {
				AbstractAuthenticator abstractAuth = (AbstractAuthenticator) auth;

				this._singleSignon = abstractAuth.getSingleSignon();
			}

		}

		return this._singleSignon;
	}

	public boolean isLogoutOnSessionTimeout() {
		return this._isLogoutOnTimeout;
	}

	public void setLogoutOnSessionTimeout(boolean logout) {
		this._isLogoutOnTimeout = logout;
	}

	public void setSessionSaveLogin(boolean isSave) {
		this._isSessionSaveLogin = isSave;
	}

	public boolean isSessionSaveLogin() {
		return this._isSessionSaveLogin;
	}

	@PostConstruct
	public void init() throws ServletException {
		if ((this._singleSignon == null) && (!this._signonInstance.isUnsatisfied()))
			this._singleSignon = ((SingleSignon) this._signonInstance.get());
	}

	public String getAuthType() {
		return "none";
	}

	public boolean isLoginUsedForRequest(HttpServletRequest request) {
		return true;
	}

	public Principal getUserPrincipal(HttpServletRequest request) {
		return getUserPrincipal(request, false);
	}

	private Principal getUserPrincipal(HttpServletRequest request, boolean isLogin) {
		Principal user = (Principal) request.getAttribute("caucho.user");

		if (user != null) {
			if (user != AbstractAuthenticator.NULL_USER) {
				return user;
			}
			if (!isLogin) {
				return null;
			}
		}
		Principal savedUser = findSavedUser(request);

		if ((savedUser != null) && (isSavedUserValid(request, savedUser))) {
			request.setAttribute("caucho.user", savedUser);
			return savedUser;
		}

		if (isLogin)
			user = getLoginPrincipalImpl(request);
		else {
			user = getUserPrincipalImpl(request);
		}
		if (user != null) {
			request.setAttribute("caucho.user", user);

			saveUser(request, user);
		} else if (savedUser != null) {
			request.setAttribute("caucho.user", AbstractAuthenticator.NULL_USER);

			saveUser(request, null);
		} else {
			request.setAttribute("caucho.user", AbstractAuthenticator.NULL_USER);
		}

		return user;
	}

	public Principal login(HttpServletRequest request, HttpServletResponse response, boolean isFail) {
		try {
			Principal savedUser = null;

			savedUser = findSavedUser(request);

			if ((savedUser != null) && (isSavedUserValid(request, savedUser))) {
				request.setAttribute("caucho.user", savedUser);

				return savedUser;
			}

			Principal user = login(request, response);

			if ((user != null) || (savedUser != null)) {
				saveUser(request, user);
			}

			if (user != null) {
				loginSuccessResponse(user, request, response);
				return user;
			}

			if (isFail) {
				log.fine(this + " sending login challenge");

				loginChallenge(request, response);
			}
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new LoginException(e);
		}

		return null;
	}

	protected Principal login(HttpServletRequest request, HttpServletResponse response) {
		return getUserPrincipal(request, true);
	}

	protected Principal findSavedUser(HttpServletRequest request) {
		SingleSignon singleSignon = getSingleSignon();

		SessionImpl session = (SessionImpl) request.getSession(false);
		String sessionId;
		if (session != null)
			sessionId = session.getId();
		else {
			sessionId = request.getRequestedSessionId();
		}
		if (sessionId == null) {
			return null;
		}
		if (singleSignon != null) {
			Principal user = singleSignon.get(sessionId);

			if ((user != null) && (log.isLoggable(Level.FINER))) {
				log.finer(this + " load user '" + user + "' from " + singleSignon);
			}
			return user;
		}
		if ((isSessionSaveLogin()) && (session != null)) {
			Principal user = (Principal) session.getAttribute("caucho.user");

			if ((user != null) && (log.isLoggable(Level.FINER))) {
				log.finer(this + " load user '" + user + "' from session");
			}
			return user;
		}

		return null;
	}

	protected void saveUser(HttpServletRequest request, Principal user) {
		SingleSignon singleSignon = getSingleSignon();
		SessionImpl session;
		if (isSessionSaveLogin())
			session = (SessionImpl) request.getSession(true);
		else
			session = (SessionImpl) request.getSession(false);
		String sessionId;
		if (session != null)
			sessionId = session.getId();
		else {
			sessionId = request.getRequestedSessionId();
		}
		if (sessionId != null) {			
			if (singleSignon != null) {
				singleSignon.put(sessionId, user);

				if (log.isLoggable(Level.FINER))
					log.finer(this + " save user '" + user + "' in single signon " + singleSignon);
			} else if (isSessionSaveLogin()) {
				session.setAttribute("caucho.user", user);
				
				if (log.isLoggable(Level.FINER))
					log.finer(this + " save user '" + user + "' in session " + session);
			}
		}
	}
	
	
	public boolean isPasswordBased() {
		return false;
	}

	protected Principal getUserPrincipalImpl(HttpServletRequest request) {
		return null;
	}

	protected boolean isSavedUserValid(HttpServletRequest request, Principal savedUser) {
		return true;
	}

	protected Principal getLoginPrincipalImpl(HttpServletRequest request) {
		return getUserPrincipalImpl(request);
	}

	protected void loginChallenge(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
	}

	protected void loginSuccessResponse(Principal user, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public boolean isUserInRole(Principal user, String role) {
		return getAuthenticator().isUserInRole(user, role);
	}

	public void logout(Principal user, HttpServletRequest request, HttpServletResponse response) {
		String sessionId = request.getRequestedSessionId();

		logoutImpl(user, request, response);

		HttpSession session = request.getSession(false);

		if (session != null) {
			session.removeAttribute("caucho.user");
		}
		request.removeAttribute("caucho.user");

		SingleSignon singleSignon = getSingleSignon();

		if (singleSignon != null)
			singleSignon.remove(sessionId);
	}

	public void sessionInvalidate(HttpSession session, boolean isTimeout) {
		if (session != null) {
			SingleSignon singleSignon = getSingleSignon();

			if ((singleSignon != null) && ((!isTimeout) || (isLogoutOnSessionTimeout()))) {
				singleSignon.remove(session.getId());
			}
		}
	}

	protected void logoutImpl(Principal user, HttpServletRequest request, HttpServletResponse response) {
	}

	public String toString() {
		return getClass().getSimpleName() + "[]";
	}
		
	static class LoginPrincipal implements Serializable {
		private Principal _user;

		LoginPrincipal(Principal user) {
			this._user = user;
		}

		public Principal getUser() {
			return this._user;
		}

		public String toString() {
			return getClass().getSimpleName() + "[" + this._user + "]";
		}
	}

	static class PrincipalEntry {
		private Principal _principal;
		private ArrayList<SoftReference<SessionImpl>> _sessions;

		PrincipalEntry(Principal principal) {
			this._principal = principal;
		}

		Principal getPrincipal() {
			return this._principal;
		}

		void addSession(SessionImpl session) {
			if (this._sessions == null) {
				this._sessions = new ArrayList();
			}
			this._sessions.add(new SoftReference(session));
		}

		boolean logout(HttpSession timeoutSession) {
			ArrayList sessions = this._sessions;

			if (sessions == null) {
				return true;
			}
			boolean isEmpty = true;
			for (int i = sessions.size() - 1; i >= 0; i--) {
				SoftReference ref = (SoftReference) sessions.get(i);
				SessionImpl session = (SessionImpl) ref.get();
				try {
					if (session == timeoutSession) {
						sessions.remove(i);
					} else if (session == null)
						sessions.remove(i);
					else
						isEmpty = false;
				} catch (Exception e) {
					AbstractLogin.log.log(Level.WARNING, e.toString(), e);
				}
			}

			return isEmpty;
		}

		void logout() {
			ArrayList sessions = this._sessions;
			this._sessions = null;

			for (int i = 0; (sessions != null) && (i < sessions.size()); i++) {
				SoftReference ref = (SoftReference) sessions.get(i);
				SessionImpl session = (SessionImpl) ref.get();
				try {
					if (session != null) {
						session.invalidateLogout();
					}
				} catch (Exception e) {
					AbstractLogin.log.log(Level.WARNING, e.toString(), e);
				}
			}
		}
	}
}