package model;

import method.UI;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import static common.JDBCTemplate.getConnection;

public class MoonDAO {
    public static final String BACK_BLACK = "\u001B[40m";
    public static final String BACK_GREEN = "\u001B[42m";
    public static final String green = "\u001B[32m";
    public static final String blue = "\u001B[34m";
    public static final String red = "\u001B[31m";
    public static final String white = "\u001B[37m";
    public static final String exit = "\u001B[0m";
    Scanner sc = new Scanner(System.in);

    private Properties prop = new Properties();

    public MoonDAO() {
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/mapper/moon-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void allMoon(Connection con) {
        Statement stmt = null;
        ResultSet rset = null;
        MoonDTO allMoonRow = null;
        List<MoonDTO> allMoonList = null;

        String query = prop.getProperty("allMoon");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            allMoonList = new ArrayList<>();

            while (rset.next()) {
                allMoonRow = new MoonDTO();
                allMoonRow.setCode(rset.getString("MOON_CODE"));
                allMoonRow.setName(rset.getString("MOON_NAME"));
                allMoonRow.setRank(rset.getString("RISK_RANK"));

                allMoonList.add(allMoonRow);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (MoonDTO allMoon : allMoonList) {
            System.out.println(" 행성 정보 " + allMoon);
        }
    }

    public String moonRank(Connection con) {
        Statement stmt = null;
        ResultSet rset = null;

        List<Map<String, String>> moonList = null;

        String query = prop.getProperty("moonRisk"); // 쿼리문에 있는 key의 내용

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            moonList = new ArrayList<>();

            System.out.println("  S = 매우 높음 || A = 높음 || B = 보통 || C = 낮음 || D = 매우낮음 || F = 안전 ");
            while (rset.next()) {
                Map<String, String> moon = new HashMap<>();
                moon.put(rset.getString("EMOJI_RANK"), rset.getString("MOON_NAME") + "\n");
                moonList.add(moon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String clean = moonList.toString().replace("{", "").replace("(", "")
                .replace("}","").replace(")", "").replace("[", "").replace("]", "")
                .replace(",", "").replace("=", " : ");
        return clean;
    }

    public void searchMoonRank(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        MoonDTO moonRow = null;
        List<MoonDTO> moonList = null;

        System.out.println("검색하실 행성의 위험도를 입력해주세요. S, A, B, C, D, F ");
        String answer = sc.nextLine().toUpperCase();

        String query = prop.getProperty("searchRank");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, answer);
            rset = pstmt.executeQuery();

            moonList = new ArrayList<>();

            while (rset.next()) {
                moonRow = new MoonDTO();

                moonRow.setCode(rset.getString("MOON_CODE"));
                moonRow.setName(rset.getString("MOON_NAME"));
                moonRow.setRank(rset.getString("RISK_NAME"));


                moonList.add(moonRow);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (MoonDTO moonRank : moonList) {
            System.out.println(" 검색한 위험도의 행성들 " + moonRank);
        }
    }

    public void searchMoonName(Connection con) {
        UI ui = new UI();
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        MoonDTO searchMoon = null;

        System.out.println("조회할 행성의 이름을 입력해주세요.");
        String answer = sc.nextLine().toUpperCase().replace(" ", "");
        switch (answer) {
            case "EXIT" : ui.forUI(); break;
        }
        String query = prop.getProperty("searchMoonName");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, answer);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                searchMoon = new MoonDTO();

                searchMoon.setCode(rset.getString("MOON_CODE"));
                searchMoon.setName(rset.getString("MOON_NAME"));
                searchMoon.setRank(rset.getString("RISK_NAME"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            if (searchMoon != null) {
                System.out.println("검색한 행성의 정보 : " + searchMoon);
                System.out.println("다른 행성을 검색하시겠습니까?");
                System.out.println("1 : 예, 2 : 아니오");
                String search = sc.nextLine();
                if (search.equals("예") || search.equals("네") || search.equals("1")) {
                    searchMoonName(con);
                    break;
                } else if (search.equals("아니오") || search.equals("아니요") || search.equals("2")) {
                    ui.forUI();
                    break;
                } else {
                    System.out.println(red + "올바르게 입력해주세요 !" + exit + BACK_BLACK);
                    searchMoonName(con);
                    break;
                }
            } else {
                System.out.println(red + "검색결과가 없습니다. 올바르게 입력해주세요."  + blue + "(exit = 뒤로가기)" + exit + BACK_BLACK);
                searchMoonName(con);
                break;
            }
        }
    }

    public int insertMoon(Connection con, MoonDTO moonDTO) {
        PreparedStatement pstmt = null;

        int result = 0;

        String query = prop.getProperty("insertMoon");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, moonDTO.getCode());
            pstmt.setString(2, moonDTO.getName());
            pstmt.setString(3, moonDTO.getRank());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void insertUI() {
        Connection con = getConnection();

        System.out.println(BACK_BLACK + green + "새로운 행성의 코드를 지정해주세요. 규칙 : 앞 영어 한글자 + 숫자 3자리");
        String code = sc.nextLine().toUpperCase();

        System.out.println("행성의 이름을 입력해주세요.");
        String name = sc.nextLine().toUpperCase();

        String rank;
        while (true) {
            System.out.println("행성의 위험도를 평가해주세요. (S : 매우위험, A : 위험, B : 보통, C : 약간 위험, D : 위험하지 않음, F : 안전 ");
            rank = sc.nextLine().toUpperCase();
            if (!rank.equals("S") && !rank.equals("A") && !rank.equals("B") && !rank.equals("C") && !rank.equals("D") && !rank.equals("F")) {
                System.out.println(red + "올바른 입력이 아닙니다. 올바른 알파벳을 입력해주세요." + exit + BACK_BLACK);
            } else {
                break;
            }
        }
        MoonDTO moonDTO = new MoonDTO(code, name, rank);
        int result = insertMoon(con, moonDTO);
        if (result > 0) {
            System.out.println(green + "신규 괴물 등록 성공" + exit);
            System.out.println("새롭게 추가된 행성");
            System.out.println(moonDTO);
        } else {
            System.out.println(red + "행성 등록 실패" + exit);
            System.out.println("재등록 하시겠습니까?");
            System.out.println("1 : 예, 2 : 아니오");
            String reAnswer = sc.nextLine();
            if (reAnswer.equals("1") || reAnswer.equals("예") || reAnswer.equals("네")) {
                insertUI();
            } else if (reAnswer.equals("2") || reAnswer.equals("아니오") || reAnswer.equals("아니요")) {
                UI ui = new UI();
                ui.forUI();
            } else {
                System.out.println(red + "올바르게 입력해주세요." + exit);
            }
        }
    }

    public String answer(String answer) {
        System.out.println("변경할 행성의 코드를 입력하세요.");
        answer = sc.nextLine();

        return answer;
    }

    public void updateMoon(Connection con) {
        // 변경할 코드 선택
        System.out.println("변경할 행성의 코드를 입력하세요.");
        String answer = sc.nextLine();
        // 코드 변경에 선택점을 주기위해 선택한 방법
        System.out.println("행성의 변경할 정보의 번호를 선택해주세요.");
        System.out.println("1 : 코드 수정");
        System.out.println("2 : 이름 수정");
        System.out.println("3 : 행성 위험도 정보 수정");
        int updateAnswer = sc.nextInt();

        switch (updateAnswer) {
            case 1:
                updateCode(con, answer);
                break;
            case 2:
                updateName(con, answer);
                break;
            case 3:
                updateRank(con, answer);
                break;
        }
    }

    public void updateCode(Connection con, String answer) {
        PreparedStatement pstmt = null;
        int result = 0;


        System.out.println("선택한 행성의 변경할 코드를 입력해주세요.");
        sc.nextLine();
        String moonCode = sc.nextLine().toUpperCase().replace(" ", "");

        MoonDTO changeCode = new MoonDTO();
        changeCode.setCode(moonCode);


        String query = prop.getProperty("updateCode");
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, changeCode.getCode());
            pstmt.setString(2, answer);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (result > 0) {
            System.out.println(green + moonCode + " 변경 성공" + exit);
        } else {
            System.out.println(red + moonCode + " 변경 실패" + exit);
        }
    }

    public void updateName(Connection con, String answer) {
        PreparedStatement pstmt = null;
        int result = 0;
        System.out.println("선택한 행성의 변경할 이름을 입력해주세요.");
        sc.nextLine();
        String moonName = sc.nextLine();

        MoonDTO changeName = new MoonDTO();
        changeName.setName(moonName);

        String query = prop.getProperty("updateName");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, changeName.getName());
            pstmt.setString(2, answer);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (result > 0) {
            System.out.println(green + moonName + " 변경 성공" + exit);
        } else {
            System.out.println(red + moonName + "변경 실패" + exit);
        }
    }

    public void updateRank(Connection con, String answer) {
        PreparedStatement pstmt = null;
        int result = 0;
        String moonRank;
        System.out.println("선택한 행성의 변경할 위험도를 입력하세요. ( S : 매우 높음, A : 높음, B : 중간, C : 낮음, D : 매우 낮음, F : 안전 )"); sc.nextLine();
        while (true) {
            moonRank = sc.nextLine().toUpperCase().replace(" ", "");
            switch (moonRank) {
                case "매우높음": case "S": moonRank = "S"; break;
                case "높음": case "A": moonRank = "A"; break;
                case "중간": case "B": moonRank = "B"; break;
                case "낮음": case "C": moonRank = "C"; break;
                case "매우낮음": case "D": moonRank = "D"; break;
                case "안전": case "F": moonRank = "F"; break;
                default: System.out.println(red + "잘못된 입력입니다." + exit + BACK_BLACK); continue;
            }
            break;
        }
        MoonDTO changeRank = new MoonDTO();
        changeRank.setRank(moonRank);
        String query = prop.getProperty("updateRank");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, changeRank.getRank());
            pstmt.setString(2, answer);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (result > 0) {
            System.out.println(green + moonRank + " 변경 성공" + exit);
        } else {
            System.out.println(red + moonRank + "변경 실패" + exit);
        }
    }

    public void deleteMoon(Connection con) {
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rset = null;

        int result = 0;

        int confirmResult = 0;
        String query = prop.getProperty("deleteMoon");
        System.out.println("제거할 행성의 코드를 입력해주세요.");
        String deleteAnswer = sc.nextLine().replace(" ","");

//        String confirmQuery = prop.getProperty("deleteConfirm");
        String confirmQuery = "SELECT * FROM MOON WHERE MOON_CODE = '" + deleteAnswer +"'";
        MoonDTO confirmMoon = new MoonDTO();

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(confirmQuery);

            if(rset.next()){
                confirmMoon = new MoonDTO();
                confirmMoon.setName(rset.getString("MOON_NAME"));
            }
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, deleteAnswer);
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (result > 0) {
            System.out.println(green + confirmMoon.getName() +" 제거 성공" + exit);
        } else {
            System.out.println(red + "제거 실패" + exit);
        }
    }

}
