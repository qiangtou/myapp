package cn.jiuling.distributedmanagement.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jiuling.distributedmanagement.dao.UserDao;
import cn.jiuling.distributedmanagement.model.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	public User findByUserName(String userName) {
		User user = null;
		List<User> userList = getHibernateTemplate().find("select new User(u.id,u.name,u.md5pwd) from User u where u.name=?", userName);
		if (userList.size() > 0) {
			user = userList.get(0);
		}
		return user;
	}

	public int onlineUser() {
		int onlineNum = 0;
		Statement stmt = null;
		String sql = "select count(*) from tbl_user where logined=1";
		String driver = "com.mysql.jdbc.Driver";

		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://192.168.1.61:3306/astvs1v20_db_master";

		// MySQL配置时的用户名
		String user = "root";

		// Java连接MySQL配置时的密码
		String password = "Ast4HS";
		try {
	        Class.forName(driver);
	        Connection conn = DriverManager.getConnection(url, user, password);
	        stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        while (rs.next()) {
	        	onlineNum = rs.getInt(1); 
            }
	        stmt.close();
	        conn.close();
        } catch (Exception ex) {
        	System.err.println("sqlexception :"+ex.getMessage());
        }
        return onlineNum;
	}
}
