package com.taiji.cdp.daily.common.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class DbUtil {

    private static final Logger log = LoggerFactory.getLogger(DbUtil.class);
    private static Connection conn = null;

    public static void openConnection() throws IOException, SQLException, ClassNotFoundException {
        Properties p = new Properties();
        p.load(DbUtil.class.getClassLoader().getResourceAsStream("config.properties"));
        String driverclass = p.getProperty("DRIVERCLASS");
        String url = p.getProperty("URL");
        String uid = p.getProperty("UID");
        String pwd = p.getProperty("PWD");
        //加载驱动
        Class.forName(driverclass);
        //创建链接
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(url, uid, pwd);
        }
    }

    public void closeConnection() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    public PreparedStatement buildPreparedStatement(String sql, boolean returnGeneratedKeys, Object... params) throws SQLException {
        try {
            openConnection();
        } catch (IOException e) {
            log.error("创建数据库链接异常！", e);
        } catch (ClassNotFoundException e) {
            log.error("class.forName异常！！", e);
        }
        PreparedStatement pstat =
                returnGeneratedKeys ? conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) : conn.prepareCall(sql);
        for (int i = 0; i < (params == null ? 0 : params.length); i++) {
            pstat.setObject(i + 1, params[i]);
        }
        return pstat;
    }

    /**
     * 执行查询
     *
     * @param sql
     * @param params
     * @return List中的每个元素表示一行数据，是一个Map
     * map中的key 是列名  value是这一列的值
     * select c_id id,c_name name from t_users
     * 如果有结果，那么 list中的一个map的就可能是这样：
     * key:id  value:1
     * key:name  value:xxx
     * @throws SQLException
     */
    public List<Map<String, Object>> executeQuery(String sql, Object... params) throws SQLException {
        PreparedStatement pstat = this.buildPreparedStatement(sql, false, params);
        ResultSet rs = pstat.executeQuery();
        //取出列的名字
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        while (rs.next()) {
            Map<String, Object> record = new HashMap<String, Object>();
            for (int i = 0; i < columnCount; i++) {
                record.put(rsmd.getColumnLabel(i + 1), rs.getObject(i + 1));
            }
            list.add(record);
        }
        return list;
    }

}
