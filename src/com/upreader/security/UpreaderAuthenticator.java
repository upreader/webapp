package com.upreader.security;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.caucho.security.*;
import com.caucho.server.security.CachingPrincipal;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 * Upreader username / password authenticator that supports cookie login also
 */
public class UpreaderAuthenticator extends AbstractAuthenticator implements CookieAuthenticator {
    private static final Logger log = Logger.getLogger(UpreaderAuthenticator.class.getName());

    private DataSource _dataSource;

    public UpreaderAuthenticator() {
        setPasswordDigestAlgorithm("MD5-plain");
    }

    @Override
    public boolean isCookieSupported(String defaultValue) {
        return true;
    }

    @Override
    public Principal authenticateByCookie(String cookieValue) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = this._dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT email FROM users WHERE cookie=?");
            stmt.setString(1, cookieValue);

            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            String user = rs.getString(1);
            if (user != null) {
                return new CachingPrincipal(user);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    @Override
    public boolean associateCookie(Principal user, String cookieValue) {
        if ((user == null) || (cookieValue == null)) {
            return true;
        }
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this._dataSource.getConnection();
            stmt = conn.prepareStatement("UPDATE users SET cookie=? WHERE email=?");
            stmt.setString(1, cookieValue);
            stmt.setString(2, user.getName());

            stmt.executeUpdate();
        } catch (Exception e) {
            log.log(Level.FINE, e.toString(), e);
        } finally {
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
        return true;
    }

    @Override
    public Principal authenticate(Principal principal, PasswordCredentials credentials, Object details) {
        String username = principal.getName();
        HttpServletRequest request = (HttpServletRequest) details;
        char[] password = credentials.getPassword();
        char[] digest = getPasswordDigest(principal.getName(), password);
        String digestPassword = new String(digest);

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = this._dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT password FROM users WHERE email=?");
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                log.info("no such user:" + username);
                return null;
            }

            String dbPassword = rs.getString(1);
            if ((dbPassword != null) && (dbPassword.equals(digestPassword))) {
                return new CachingPrincipal(username);
            }

            log.info("mismatched password:" + username);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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

    @Override
    protected PasswordUser getPasswordUser(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = this._dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT password FROM users WHERE email=?");
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            if (!rs.next()) {
                if (log.isLoggable(Level.FINE)) {
                    log.fine("no such user:" + username);
                }
                return null;
            }

            String dbPassword = rs.getString(1);

            return new PasswordUser(new BasicPrincipal(username), dbPassword.toCharArray(), false, false, new String[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    /**
     * Hack to use a table column for the list of roles (separated by ',')
     */
    @Override
    public boolean isUserInRole(Principal principal, String role) {
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
            stmt = conn.prepareStatement("SELECT roles FROM users WHERE email=?");
            stmt.setString(1, principal.getName());
            boolean inRole = false;
            rs = stmt.executeQuery();
            while (rs.next()) {
                String dbRoles = rs.getString(1);
                StringTokenizer tok = new StringTokenizer(dbRoles, ",");
                while (tok.hasMoreElements()) {
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

    public DataSource getDataSource() {
        return this._dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this._dataSource = dataSource;
    }
}
