package method;

import model.MonsterDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

public class UI {

    Connection con = getConnection();

    public static final String BACK_BLACK = "\u001B[40m";
    public static final String BACK_GREEN = "\u001B[42m";
    public static final String green = "\u001B[32m";
    public static final String blue = "\u001B[34m";
    public static final String red = "\u001B[31m";
    public static final String white = "\u001B[37m";
    public static final String exit = "\u001B[0m";
    MonsterDAO monsDAO = new MonsterDAO();

    public void loading() {
        int loadingBar = 500;
        for(int i=0; i <= 10; i++) {
            try {
                Thread.sleep(loadingBar);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.print("🚀");
        }
        System.out.println("로딩 완료");
    }

    public void forUI() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            loading();
            System.out.println(BACK_BLACK + green +"■■■■             ■■■■          ■■■■■          ■■          ■■■       ■■■■■■■■■■■■     ■■■■■■■■■■■■■■■■    ■■■■■■■■■■■■■■    ■■■■■■■■■■■■■■■              ■■■■■■■■■■         ■■■■■■■■■■■■        ");
            System.out.println("■■■■■■          ■■■■■      ■■         ■■      ■■■■        ■■■    ■■■                 ■■■■■■■■■■■■■■■■    ■■■               ■■■           ■■■            ■■■       ■■■      ■■■        ■■■      ");
            System.out.println("■■■■■■■        ■■■■■■     ■■           ■■     ■■■■■       ■■■    ■■■                       ■■■■          ■■■■              ■■■           ■■■            ■■■         ■■■    ■■■        ■■■      ");
            System.out.println("■■■■  ■■■   ■■■  ■■■■    ■■             ■■    ■■■ ■■■■    ■■■       ■■■■■■■■■■             ■■■■          ■■■■■■■■■■■■■■    ■■■■■■■■■■■■■■■■             ■■■         ■■■    ■■■■■■■■■■■■        ");
            System.out.println("■■■■   ■■■ ■■■   ■■■■     ■■           ■■     ■■■    ■■■■ ■■■                 ■■■          ■■■■          ■■■■              ■■■         ■■■■             ■■■         ■■■    ■■■        ■■■      ");
            System.out.println("■■■■     ■■■     ■■■■      ■■         ■■      ■■■        ■■■■                 ■■■          ■■■■          ■■■               ■■■            ■■            ■■■       ■■■      ■■■        ■■■      ");
            System.out.println("■■■■      ■      ■■■■          ■■■■■          ■■■          ■■     ■■■■■■■■■■■■             ■■■■          ■■■■■■■■■■■■■■    ■■■             ■■           ■■■■■■■■■■         ■■■■■■■■■■■■        ");
            System.out.println("몬스터 데이터 베이스에 오신 것을 환영합니다.");
            System.out.println("0. 데이터 베이스 공지사항");
            System.out.println("1. 괴물 전체 정보 열람");
            System.out.println("2. 괴물 위험도 확인");
            System.out.println("3. 괴물 검색");
            System.out.println("4. 신규 괴물 추가");
            System.out.println("5. 괴물 정보 수정");
            System.out.println("6. 행성 전체 정보 열람");
            System.out.println("7. 행성 위험도 확인");
            System.out.println("8. 행성 코드로 검색");
            System.out.println("9. 신규 행성 추가");
            System.out.println("10. 행성 정보 수정");
            System.out.println("100. 데이터 베이스 시스템 종료");
            int answer = sc.nextInt();
            ResultSet rset = null;
            Statement stmt = null;
            switch (answer) {
                case 0 : dbRule(); break;
                case 1 : monsDAO.allMonster(con); break;
                case 2 : System.out.println(monsDAO.monsterRank(con)); break;
                case 3 :
                    System.out.println("1. 이름 검색");
                    System.out.println("2. 위험도 검색");
                    int searchAnswer = sc.nextInt();
                    if (searchAnswer == 1) {
                        monsDAO.searchMonsterName(con);
                    } else if (searchAnswer == 2) {
                        monsDAO.serachMonsterRank(con);
                    } else {
                        System.out.println("올바르게 입력바랍니다. 1과 2중에 하나만 가능");
                    }
                    break;
                case 4 : monsDAO.insertUI(); break;
                case 5 : ;
                case 6 : ;
                case 7 : ;
                case 8 : ;
                case 9 : ;
                case 10 : ;
                case 11 : ;
                case 12 : ;
                case 100 : System.exit(0);
            }
        }
    }

    public void closeSet(Connection con, ResultSet rset, Statement stmt) {
        close(rset);
        close(stmt);
        close(con);
    }

    public void dbRule() {
        System.out.println();
        System.out.println(blue +"    MONSTER DB RULE (사용 유의사항 및 공지사항)");
        System.out.println(red +"    1. 거짓 작성으로 인한 근무자들의 피해로 처벌을 받을 수 있음");
        System.out.println();
        System.out.println("    2. 확실한 정보가 아니라면 데이터베이스에 입력하지말 것");
        System.out.println("       잘못된 정보로 누군가 죽을 수 있음");
        System.out.println();
        System.out.println("    3. 확신에 근접한 정보를 얻었을 때 데이터베이스에 괴물을 추가할 것");
        System.out.println();
        System.out.println("    4. 데이터베이스 추가 및 업데이트에 대한 내용");
        System.out.println("    -괴물 추가 시 코드 명은 괴물의 영어 이름 앞글자 OO + 숫자 01로 시작할 것");
        System.out.println("     코드 중복 시 추가되지 않으니 괴물 코드 확인 후 추가바람");
        System.out.println();
        System.out.println("    -행성 추가 시 코드 명은 행성의 영어 이름 앞글자 O + 숫자 001로 시작");
        System.out.println("     동 생략");
        System.out.println();
        System.out.println(exit);
    }

}
