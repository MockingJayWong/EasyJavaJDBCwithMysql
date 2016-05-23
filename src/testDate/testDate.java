package testDate;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;
public class testDate {
	public static void main(String[] args) {
		Connection con;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://172.18.41.13:3306/cinema_datebase?useUnicode=true&characterEncoding=UTF-8";
        //MySQL配置时的用户名
        String user = "Marchse305";
        //MySQL配置时的密码
        String password = "se305";
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url,user,password);
            if(!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            
           
            insert(con);
            //delete(con);
            select(con);
            con.close();
        } catch(ClassNotFoundException e) {   
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();  
            }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
        	
            System.out.println("数据库数据成功获取！！");
        }
    }
	

	public static void delete(Connection con) throws SQLException {
		//删除id=？的数据
		String sql = "delete from movie where id = ?";
		PreparedStatement st = con.prepareStatement(sql);
		//这里的1代表第一个参数？
		int id = 0;
		st.setInt(1, id);
		st.executeUpdate();
	}

	public static void select(Connection con) throws SQLException {
		//查看movie列表所有数据
		String sql = "select * from movie";
		Statement st = con.createStatement();
		ResultSet rs  = st.executeQuery(sql);
		
		while (rs.next()) {
			System.out.println(rs.getInt("id") + " " + rs.getString("movieName")
			+ " "+ rs.getString(3) + " "+ rs.getString(4)+ " "+ rs.getFloat(5)
			+ " "+rs.getLong(6) + " "+rs.getLong(7));
		}
		rs.close();
	}
	

	public static void insert(Connection con) throws SQLException, UnsupportedEncodingException {
		//对于时间的UTC时间戳获取方法,lg1为2016.4.21 21：23：10的时间戳------------
		Calendar calendar = Calendar.getInstance();
		int year = 2016;
		int month = 4;
		int date = 21;
		int hourOfDay = 21;
		int minute = 23;
		int second = 10;
		calendar.set(year, month, date, hourOfDay, minute, second);
		Long lg1 = calendar.getTimeInMillis();
		calendar.set(year, month, date, hourOfDay, minute+20, second);
		Long lg2 = calendar.getTimeInMillis();
		//-------------------------分割线-----------------------------------
		//这里values的顺序按照sql列项的顺序来加入(第一项为id，已设置自增长，所以插入用
		//null)代替。
		String sql = "insert into movie values(null,?,'http://www.baidu.com',?,7,?,?)";
		PreparedStatement st = con.prepareStatement(sql);
		//对于中文字最好参数注入，不然在mysql显示？？？.

		st.setString(1, "泰囧");
		st.setString(2, "俩逗之旅");
		st.setLong(3, lg1);
		st.setLong(4,lg2);
		st.executeUpdate();
	}
	
	/*
	public static void select_2(Connection con) throws SQLException {
		String sql = "select * from orders where time = ?";
		PreparedStatement st = con.prepareStatement(sql);
		Calendar calendar = Calendar.getInstance();
		int year = 2016;
		int month = 4;
		int date = 21;
		int hourOfDay = 21;
		int minute = 32;
		int second = 10;
		calendar.set(year, month, date, hourOfDay, minute, second);
		Long lg1 = calendar.getTimeInMillis();
		minute = 50;
		calendar.set(year, month, date, hourOfDay, minute, second);
		Long lg2 = calendar.getTimeInMillis();
		st.setLong(1, 1463838190321L);
		//st.setLong(2, lg2);
		ResultSet rs  = st.executeQuery();
		
		while (rs.next()) {
			System.out.println(String.format("%tF %<tT", rs.getLong("time")));
		}
		rs.close();
	}*/

}
