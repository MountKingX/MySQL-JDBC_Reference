package com.kangmin.jdbc;

import com.kangmin.jdbc.model.Account;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public final class TableUtil {

    private static synchronized Connection getConnection(
        final List<Connection> connectionPool,
        final Connection defaultConnection
    ) {
        if (connectionPool.size() > 0) {
            return connectionPool.remove(connectionPool.size() - 1);
        }
        return defaultConnection;
    }

    private static synchronized void releaseConnection(
        final List<Connection> connectionPool,
        final Connection con
    ) {
        connectionPool.add(con);
    }

    private static boolean tableExists(
        final String tableName,
        final Connection con,
        final List<Connection> connectionPool
    ) throws Exception {
        try {
            final DatabaseMetaData metaData = con.getMetaData();
            final ResultSet rs = metaData.getTables(null, null, tableName, null);

            final boolean answer = rs.next();
            rs.close();
            if (connectionPool != null) {
                releaseConnection(connectionPool, con);
            }

            return answer;

        } catch (SQLException e) {
            try {
                con.close();
            } catch (SQLException e2) {
                /* ignore */
            }
            throw new SQLException(e);
        }
    }

    public static void createUserTable(
        final Connection con,
        final List<Connection> connectionPool
    ) {
        final String tableName = "accounts";
        try {
            final Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE TABLE " + tableName
                + " (id int not null auto_increment, "
                + "username VARCHAR(255), "
                + "password VARCHAR(255), "
                + "displayName VARCHAR(255), "
                + "PRIMARY KEY(id))");
            stmt.close();
            if (connectionPool != null) {
                releaseConnection(connectionPool, con);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Account read(
        final String tableName,
        final String username,
        final Connection con,
        final List<Connection> connectionPool
    ) throws SQLException {
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM "
                + tableName + " WHERE `username`=?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            final Account account;
            if (!rs.next()) {
                account = null;
            } else {
                account = new Account();
                account.setId(rs.getInt("id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                account.setDisplayName(rs.getString("displayName"));
            }
            rs.close();
            pstmt.close();
            if (connectionPool != null) {
                releaseConnection(connectionPool, con);
            }
            return account;
        } catch (final Exception e) {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (final SQLException e2) {
                /* ignore */
            }
            throw new SQLException(e);
        }
    }

    public static Account insert(
        final String tableName,
        final Account account,
        final Connection con,
        final List<Connection> connectionPool
    ) throws SQLException {
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO "
                + tableName + "(ID, USERNAME, PASSWORD, DISPLAYNAME) VALUES (?,?,?,?)");
            pstmt.setInt(1, account.getId());
            pstmt.setString(2, account.getUsername());
            pstmt.setString(3, account.getPassword());
            pstmt.setString(4, account.getDisplayName());

            int row = pstmt.executeUpdate();  // rows affected
            System.out.println(row); // 1

            pstmt.close();
            if (connectionPool != null) {
                releaseConnection(connectionPool, con);
            }
            return account;
        } catch (final Exception e) {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (final SQLException e2) {
                /* ignore */
            }
            throw new SQLException(e);
        }
    }
}
