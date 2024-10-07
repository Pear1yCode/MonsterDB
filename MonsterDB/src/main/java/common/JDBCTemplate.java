package common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCTemplate {

    /* getConnection 메소드 생성
     * 생성 이유 : getConnection이라는 기존의 JDBC API에 있는 DriverManager.getConnection 메소드와 같은 이름으로
     * 더 실용성 있게 사용하고자 새롭게 메소드를 생성
     * 1. 추상화 : 클래스 내부에서 연결 생성 로직을 캡슐화하여 관리하고 코드의 복잡성을 줄인다. (연결 생성 시 수정이 필요하다면 그 때 메소드만 수정하면 되겠다.)
     * 2. 재사용성 : 해당 메소드를 사용해서 간편하게 데이터베이스에 연결이 필요할 때마다 사용할 수 있다.
     * 3. 코드 간결성 : 간결해지는 코드와 더불어 효율성 제고
     * 4. 예외 처리 : 예외 처리 로직까지 한데 묶여 효율적으로 처리 가능
     *       여기서 처리된 예외에 대한 내용
     *       1. FileReader에 대한 예외 : IOException e와 RuntimeException(e)
     *       2. forName에 대한 예외 : ClassNotFoundException e와 RuntimeException(e)
     *       3. getConnection에 대한 예외 : SQLException e와 RuntimeException(e)
     *   결론 : 같은 이름 사용 이유는 JDBC 기본 기능 확장 및 코드 간결성을 통한 유지보수성 증진
     */
    public static Connection getConnection() {

        Connection con = null; // 데이터가 제대로 연결됐는지 확인하기 위한 커넥션의 con이라는 변수에 null값 적용

        Properties prop = new Properties(); // 프로퍼티를 불러오기 위한 객체 생성

        try {
            // 여기선 왜 FileReader를 쓰는지 지식 부족 - 복습 필요
            prop.load(new FileReader("src/main/java/config/connection.properties")); // 프로퍼티 가져오기
            String driverz = prop.getProperty("driver"); // 프로퍼티 파일의 driver에 있는 내용을 driverz라는 변수에 담겠다. (구분 연습을 위해 driverz로 지칭)
            String urlz = prop.getProperty("url"); // 프로퍼티 파일의 url에 있는 내용을 urlz라는 변수에 담겠다. (마찬가지로 구분 연습을 위해 urlz로 지칭)

            Class.forName(driverz); // 아까 불러온 드라이버의 변수를 담은 드라이버를 불러오면서 JDBC 드라이버를 동적으로 로딩하는 필수 과정

            con = DriverManager.getConnection(urlz, prop); // DriverManager 메소드와 getConnection 메소드를 사용하여 각 정보를 데이터 베이스에서 불러옴.
            // urlz와 prop을 따로 부르는 이유를 잘 모르겠다. 둘이 같은 곳에 있다고 생각중

            // ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
//            con.setAutoCommit(false); // 이렇게 두면 일단 저장을 보류한다. 제거하거나 주석하면 데이터베이스에 저장 가능
            // ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★

        } catch (IOException e) {                       //  FileReader에 대한 예외 상황
            throw new RuntimeException(e);              //  FileReader의 예외 상황에 대한 던짐
        } catch (ClassNotFoundException e) {        //  forName에 대한 예외 상황
            throw new RuntimeException(e);            //  forName의 예외 상황에 대한 던짐
        } catch (SQLException e) {                 //  getConnection에 대한 예외 상황
            throw new RuntimeException(e);         //  getConnection의 예외 상황에 대한 던짐
        }

        return con;
    }

    // 데이터와 연결(Connection)에 대한 close 메소드 생성 (변수 : con)
    public static void close(Connection con) {
        try {
            if (con != null && !con.isClosed())
                ; // con이 null값이 아니라면 연결이 돼서 null이 사라지고 데이터가 들어와 있는 상태임을 시사, isClosed는 앞에 !를 붙여 닫혀있지 않다면 닫으라는 의미 부여
            con.close();
        } catch (SQLException e) {          //  close에 대한 예외 상황
            throw new RuntimeException(e);  //  close에 대한 예외 상황에 대한 던짐
        }
    }

    // Statement에 대한 close 메소드 생성 (변수 : stmt) = > Statement는 쿼리문을 저장하고 실행하는 기능을 담당
    public static void close(Statement stmt) {
        try {
            if (stmt != null && !stmt.isClosed()) ;
            stmt.close();
        } catch (SQLException e) {          // 동일하여 생략
            throw new RuntimeException(e);
        }
    }

    // ResultSet에 대한 close 메소드 생성 (변수 : rest) = > ResultSet은 데이터베이스의 SELECT의 결과 집합을 받아올 인터페이스 => 쿼리문을 실행한 값을 받아오는 역할
    public static void close(ResultSet rset) {
        try {
            if (rset != null && !rset.isClosed()) ;
            rset.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
}
