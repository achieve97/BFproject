package BF;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBC {

	public static Connection DBConnect() {
		
		// DB에 접속에 필요한 정보를 저장하기 위한 Connection타입 객체 con선언
		Connection con = null;
		
		// String url = "jdbc:oracle:thin:@121.65.47.77:1522:XE";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "USER01";
		String password ="1111";
		
		try {
			// 오라클 데이터베이스 드라이버
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 오라클 데이터베이스 접속정보
			con = DriverManager.getConnection(url, user, password);
			
			System.out.println("DB접속 성공!");
		} catch (ClassNotFoundException e) {
			// ClassNotFoundException : 드라이버 오류시
			System.out.println("DB접속 실패 : 드라이버 오류");
			e.printStackTrace();
		} catch (SQLException e) {
			// SQLException : 접속정보 오류시
			System.out.println("DB접속 실패 : 접속정보 오류");
			e.printStackTrace();
		}
		
		// void타입이 아니기 때문에 Connection타입의 return 값 필요!!!!!
		return con;
	}
}
