package method;

import model.MonsterDAO;
import model.MoonDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

public class UI {

    Connection con = getConnection();
    ResultSet rset = null;
    Statement stmt = null;

    public static final String BACK_BLACK = "\u001B[40m";
    public static final String green = "\u001B[32m";
    public static final String blue = "\u001B[34m";
    public static final String red = "\u001B[31m";
    public static final String white = "\u001B[37m";
    public static final String aqua = "\u001B[36m"; // 아쿠아
    public static final String exit = "\u001B[0m";
    MonsterDAO monsDAO = new MonsterDAO();
    MoonDAO moonDAO = new MoonDAO();

    public void loading() {
        int loadingBar = 300;
        for(int i=0; i <= 15; i++) {
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
            System.out.println(BACK_BLACK + green +"     ■■■■             ■■■■          ■■■■■          ■■          ■■■       ■■■■■■■■■■■■     ■■■■■■■■■■■■■■■■    ■■■■■■■■■■■■■■    ■■■■■■■■■■■■■■■              ■■■■■■■■■■         ■■■■■■■■■■■■        ");
            System.out.println("     ■■■■■■          ■■■■■      ■■         ■■      ■■■■        ■■■    ■■■                 ■■■■■■■■■■■■■■■■    ■■■               ■■■           ■■■            ■■■       ■■■      ■■■        ■■■      ");
            System.out.println("     ■■■■■■■        ■■■■■■     ■■           ■■     ■■■■■       ■■■    ■■■                       ■■■■          ■■■■              ■■■           ■■■            ■■■         ■■■    ■■■        ■■■      ");
            System.out.println("     ■■■■  ■■■   ■■■  ■■■■    ■■             ■■    ■■■ ■■■■    ■■■       ■■■■■■■■■■             ■■■■          ■■■■■■■■■■■■■■    ■■■■■■■■■■■■■■■■             ■■■         ■■■    ■■■■■■■■■■■■        ");
            System.out.println("     ■■■■   ■■■ ■■■   ■■■■     ■■           ■■     ■■■    ■■■■ ■■■                 ■■■          ■■■■          ■■■■              ■■■         ■■■■             ■■■         ■■■    ■■■        ■■■      ");
            System.out.println("     ■■■■     ■■■     ■■■■      ■■         ■■      ■■■        ■■■■                 ■■■          ■■■■          ■■■               ■■■            ■■            ■■■       ■■■      ■■■        ■■■      ");
            System.out.println("     ■■■■      ■      ■■■■          ■■■■■          ■■■          ■■     ■■■■■■■■■■■■             ■■■■          ■■■■■■■■■■■■■■    ■■■             ■■           ■■■■■■■■■■         ■■■■■■■■■■■■        ");
            System.out.println("몬스터 데이터 베이스에 오신 것을 환영합니다.");
            System.out.println("0. 데이터 베이스 공지사항");
            System.out.println(red + "MONSTER" + exit + BACK_BLACK);
            System.out.println("1. 괴물 전체 정보 열람");
            System.out.println("2. 괴물 위험도 확인");
            System.out.println("3. 괴물 검색");
            System.out.println("4. 신규 괴물 추가");
            System.out.println("5. 괴물 정보 수정");
            System.out.println("6. 괴물 정보 제거");
            System.out.println(blue + "MOON" + exit + BACK_BLACK);
            System.out.println("7. 행성 전체 정보 열람");
            System.out.println("8. 행성 위험도 확인");
            System.out.println("9. 행성 검색");
            System.out.println("10. 신규 행성 추가");
            System.out.println("11. 행성 정보 수정");
            System.out.println("12. 행성 정보 제거");
            System.out.println("100. 데이터 베이스 시스템 종료");
            int answer = sc.nextInt();
            switch (answer) {
                case 0 : dbRule(); break;
                case 1 : monsDAO.allMonster(con); break;
                case 2 : System.out.println(monsDAO.monsterRank(con)); break;
                case 3 :
                    System.out.println("1. 이름 검색");
                    System.out.println("2. 위험도 검색");
                    System.out.println("3. 괴물 서식지 행성 전체 조회");
                    int searchMonster = sc.nextInt();
                    if (searchMonster == 1) {
                        monsDAO.searchMonsterName(con);
                    } else if (searchMonster == 2) {
                        monsDAO.searchMonsterRank(con);
                    } else if (searchMonster == 3) {
                        System.out.println(monsDAO.perMoon(con));
                    } else {
                        System.out.println(red + "올바르게 입력바랍니다. (1, 2, 3 = 선택1)" + exit + BACK_BLACK);
                    }
                    break;
                case 4 : monsDAO.insertUI(); break;
                case 5 : monsDAO.updateMonster(con); break;
                case 6 : monsDAO.deleteMonster(con); break;
                case 7 : moonDAO.allMoon(con); break;
                case 8 : System.out.println(moonDAO.moonRank(con)); break;
                case 9 :
                    System.out.println("1. 이름 검색");
                    System.out.println("2. 위험도 검색");
                    int searchMoon = sc.nextInt();
                    if (searchMoon == 1) {
                        moonDAO.searchMoonName(con);
                    } else if (searchMoon == 2) {
                        moonDAO.searchMoonRank(con);
                    } else {
                        System.out.println(red + "올바르게 입력바랍니다. (1, 2 = 선택1)" + exit + BACK_BLACK);
                    }
                    break;
                case 10 : moonDAO.insertUI(); break;
                case 11 : moonDAO.updateMoon(con); break;
                case 12 : moonDAO.deleteMoon(con); break;
                case 100 :
                    System.out.println(aqua + "TTTTTTTTTTTTTTTTT    HH          HH          AAAA          NNN          NN    KKK        KK           YYY          YYY           OOO           UU           UU");
                    System.out.println("TTTTTTTTTTTTTTTTT    HH          HH         AA  AA         NNNN         NN    KKK     KKK               YYY      YYY         OO       OO       UU           UU");
                    System.out.println("      TTTTT          HH          HH        AA    AA        NN  NN       NN    KKK   KKK                    YY   YY         OO           OO     UU           UU");
                    System.out.println("      TTTTT          HHHHHHHHHHHHHH       AAAAAAAAAA       NN    NN     NN    KKKKKK                         YYY          OO             OO    UU           UU");
                    System.out.println("      TTTTT          HH          HH      AAAAAAAAAAAA      NN      NN   NN    KKK   KKK                      YYY           OO           OO     UU           UU");
                    System.out.println("      TTTTT          HH          HH     AA          AA     NN         NNNN    KKK      KKK                   YYY             OO       OO        UU         UU ");
                    System.out.println("      TTTTT          HH          HH    AA            AA    NN          NNN    KKK         KK                 YYY                 OOO               UUUUUUU   ");

                    System.exit(0);
            }
        }
    }

    public void closeSet(ResultSet rset, Statement stmt, Connection con) {
        close(rset);
        close(stmt);
        close(con);
    }

    public void dbRule() {
        System.out.println();
        System.out.println(blue +"    MONSTER DB RULE (사용 유의사항 및 공지사항)");
        System.out.println(red +"    1. 거짓 작성으로 인한 오정보 공유는 근무자들의 죽음으로 이어져 처벌받을 수 있음");
        System.out.println();
        System.out.println("    2. 확실한 정보가 아니라면 데이터베이스에 입력하지말 것");
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
        System.out.println(green + "5. 해당 공지사항은 근무자들간에 공지사항이 있을 때 꼭 수정바랍니다.");
        System.out.println(exit);
    }

}
