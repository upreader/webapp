package com.upreader.security;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.caucho.security.DatabaseAuthenticator;
import com.caucho.server.security.CachingPrincipal;

public class Authenticator extends DatabaseAuthenticator {
	private static final Logger log = Logger.getLogger(Authenticator.class.getName());
	
	/**
	 * Hack to use a table column for the list of roles (separated by ',')
	 */
	@Override
	public boolean isUserInRole(Principal principal, String role) {
		if (this.getRoleQuery() == null)
			return (principal != null) && ("user".equals(role));

		if ((principal == null) || (role == null)) {
			return false;
		}

		CachingPrincipal cachingPrincipal = null;
		if ((principal instanceof CachingPrincipal)) {
			cachingPrincipal = (CachingPrincipal) principal;
			Boolean isInRole = cachingPrincipal.isInRole(role);
			if (isInRole != null) {
				return isInRole.equals(Boolean.TRUE);
			}
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = this.getDataSource().getConnection();
			stmt = conn.prepareStatement(getRoleQuery());
			stmt.setString(1, principal.getName());
			boolean inRole = false;
			rs = stmt.executeQuery();
			while (rs.next()) {
				String dbRoles = rs.getString(1);
				StringTokenizer tok = new StringTokenizer(dbRoles, ",");
				while(tok.hasMoreElements()) {
					String _role = tok.nextToken();
					if (cachingPrincipal != null) {
						cachingPrincipal.addRole(_role);
					}
					if (role.equals(_role)) {
						inRole = true;
					}
				}
			}
			return inRole;
		} catch (Exception e) {
			log.log(Level.FINE, e.toString(), e);
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}
	}
}
