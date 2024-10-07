package run;

import method.UI;
import model.MonsterDAO;
import model.MoonDAO;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static common.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {

    // 내가만든 getConnection 메소드를 불러 con이라는 변수에 연결상태
    Connection con = getConnection();

    // DAO 사용을 위해 객체 생성
    MonsterDAO monsDAO = new MonsterDAO();
    MoonDAO moonDAO = new MoonDAO();
    UI ui = new UI();

    ui.forUI();

    }

}
