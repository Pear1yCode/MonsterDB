package model;

import method.UI;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

public class MonsterDAO {
    public static final String BACK_BLACK = "\u001B[40m";
    public static final String BACK_GREEN = "\u001B[42m";
    public static final String green = "\u001B[32m";
    public static final String blue = "\u001B[34m";
    public static final String red = "\u001B[31m";
    public static final String white = "\u001B[37m";
    public static final String exit = "\u001B[0m";
    Scanner sc = new Scanner(System.in);

    // 프로터피 사용을 위한 객체 생성
    private Properties prop = new Properties();

    // xml 파일 사용하기 위해 불러옴
    public MonsterDAO() {
        // 왜 여기선 FileInputStream을 쓰는지 언제 언제 써지는지 지식이 부족함 - 복습 필요
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/mapper/monster-query.xml"));
        } catch (IOException e) {               //  FileInputStream 에 대한 예외
            throw new RuntimeException(e);      //  FileInputStream 예외에 대한 던짐
        }
    }

    public void allMonster(Connection con) {
        Statement stmt = null;
        ResultSet rset = null;

        MonsterDTO allMonsterRow = null;

        List<MonsterDTO> allMonsterList = null;

        String query = prop.getProperty("allMonster");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            allMonsterList = new ArrayList<>();

            while (rset.next()) {
                allMonsterRow = new MonsterDTO();

                allMonsterRow.setCode(rset.getString("MONSTER_CODE"));
                allMonsterRow.setName(rset.getString("MONSTER_NAME"));
                allMonsterRow.setOrganic(rset.getString("MONSTER_ORGANIC"));
                allMonsterRow.setKill(rset.getString("MONSTER_KILL"));
                allMonsterRow.setPower(rset.getInt("MONSTER_POWER"));
                allMonsterRow.setSpeed(rset.getString("MONSTER_SPEED"));
                allMonsterRow.setIntelligence(rset.getString("MONSTER_INTELLIGENCE"));
                allMonsterRow.setSpawnNumber(rset.getInt("SPAWN_NUMBER"));
                allMonsterRow.setSize(rset.getString("MONSTER_SIZE"));
                allMonsterRow.setRank(rset.getString("RISK_NAME"));
                allMonsterRow.setSpawnId(rset.getString("SPAWN_LOCATION"));
                allMonsterRow.setInteriorId(rset.getString("INTERIOR_NAME"));

                allMonsterList.add(allMonsterRow);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (MonsterDTO allMonster : allMonsterList) {
            System.out.println(" 괴물 정보 " + allMonster);
        }
    }

    public String monsterRank(Connection con) {
        Statement stmt = null;
        ResultSet rset = null;

        List<Map<String, String>> monsterList = null;

        String query = prop.getProperty("monsterRisk"); // 쿼리문에 있는 key의 내용

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            monsterList = new ArrayList<>();

            System.out.println(" ⚫ S = 매우 높음 || 🔴 A = 높음 || 🟤 B = 보통 || 🟡 C = 낮음 || 🔵 D = 매우낮음 || 🟢 F = 안전 ");
            while (rset.next()) {
                Map<String, String> monster = new HashMap<>();
                monster.put(rset.getString("EMOJI_RANK"), rset.getString("MONSTER_NAME") + "\n");
                monsterList.add(monster);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String clean = monsterList.toString().replace("{", "").replace("(", "")
                .replace("}","").replace(")", "").replace("[", "").replace("]", "")
                .replace(",", "").replace("=", " : ");
        return clean;
    }

    public String perMoon(Connection con) {
        Statement stmt = null;
        ResultSet rset = null;

        Map<String, List<String>> perMoonList = new HashMap<>();

        String query = prop.getProperty("perMoon");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            System.out.println("행성 별 괴물 분포");
            while (rset.next()) {
                String monsterName = rset.getString("MONSTER_NAME");
                String moonName = rset.getString("MOON_NAME");
                perMoonList.computeIfAbsent( "🌞 괴물 : " + monsterName , monsterNameArray -> new ArrayList<>()).add("\n" + "-" + moonName + "\n");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String clean = perMoonList.toString().replace("{", "").replace("(", "")
                .replace("}","").replace(")", "").replace("[", "").replace("]", "")
                .replace(",", "").replace("=", " : ");
        return clean;
    }

    public void searchMonsterRank(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        MonsterDTO monsterRow = null;
        List<MonsterDTO> monsterList = null;

        System.out.println("검색하실 괴물의 위험도를 입력해주세요. S, A, B, C, D, F ");
        String answer = sc.nextLine().toUpperCase();

        String query = prop.getProperty("searchRank");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, answer);
            rset = pstmt.executeQuery();

            monsterList = new ArrayList<>();

            while (rset.next()) {
                monsterRow = new MonsterDTO();

                monsterRow.setCode(rset.getString("MONSTER_CODE"));
                monsterRow.setName(rset.getString("MONSTER_NAME"));
                monsterRow.setOrganic(rset.getString("MONSTER_ORGANIC"));
                monsterRow.setKill(rset.getString("MONSTER_KILL"));
                monsterRow.setPower(rset.getInt("MONSTER_POWER"));
                monsterRow.setSpeed(rset.getString("MONSTER_SPEED"));
                monsterRow.setIntelligence(rset.getString("MONSTER_INTELLIGENCE"));
                monsterRow.setSpawnNumber(rset.getInt("SPAWN_NUMBER"));
                monsterRow.setSize(rset.getString("MONSTER_SIZE"));
                monsterRow.setRank(rset.getString("RISK_NAME"));
                monsterRow.setSpawnId(rset.getString("SPAWN_LOCATION"));
                monsterRow.setInteriorId(rset.getString("INTERIOR_NAME"));

                monsterList.add(monsterRow);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (MonsterDTO monsterRank : monsterList) {
            System.out.println(" 검색한 위험도의 괴물들 " + monsterRank);
        }
    }

    public void searchMonsterName(Connection con) {
        UI ui = new UI();
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        MonsterDTO searchMonster = null;

        System.out.println("조회할 괴물의 이름을 입력해주세요.");
        String answer = sc.nextLine().toUpperCase().replace(" ", "");
        switch (answer) {
            case "EXIT" : ui.forUI(); break;
        }
        String query = prop.getProperty("searchMonsterName");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, answer);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                searchMonster = new MonsterDTO();

                searchMonster.setCode(rset.getString("MONSTER_CODE"));
                searchMonster.setName(rset.getString("MONSTER_NAME"));
                searchMonster.setOrganic(rset.getString("MONSTER_ORGANIC"));
                searchMonster.setKill(rset.getString("MONSTER_KILL"));
                searchMonster.setPower(rset.getInt("MONSTER_POWER"));
                searchMonster.setSpeed(rset.getString("MONSTER_SPEED"));
                searchMonster.setIntelligence(rset.getString("MONSTER_INTELLIGENCE"));
                searchMonster.setSpawnNumber(rset.getInt("SPAWN_NUMBER"));
                searchMonster.setSpawnId(rset.getString("MONSTER_SIZE"));
                searchMonster.setRank(rset.getString("RISK_NAME"));
                searchMonster.setSize(rset.getString("MONSTER_SIZE"));
                searchMonster.setInteriorId(rset.getString("INTERIOR_NAME"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Statement stmt = null;

        Map<String, List<String>> perMoonList = new HashMap<>();

        String queryz = prop.getProperty("perMoon");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(queryz);

            while (rset.next()) {
                String monsterName = rset.getString("MONSTER_NAME");
                String moonName = rset.getString("MOON_NAME");
                if (answer.equals(monsterName.toUpperCase())){
                    System.out.println("괴물 서식지 행성");
                    perMoonList.computeIfAbsent( "🌞 괴물 : " + monsterName , monsterNameArray -> new ArrayList<>()).add("\n" + "-" + moonName + "\n");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String clean = perMoonList.toString().replace("{", "").replace("(", "")
                .replace("}","").replace(")", "").replace("[", "").replace("]", "")
                .replace(",", "").replace("=", " : ");

        while (true) {
            if (searchMonster != null) {
                System.out.println("검색한 괴물의 정보 : " + searchMonster + "\n" + clean);
                System.out.println("다른 괴물을 검색하시겠습니까?");
                System.out.println("1 : 예, 2 : 아니오");
                String search = sc.nextLine();
                if (search.equals("예") || search.equals("네") || search.equals("1")) {
                    searchMonsterName(con);
                    break;
                } else if (search.equals("아니오") || search.equals("아니요") || search.equals("2")) {
                    ui.forUI();
                    break;
                } else {
                    System.out.println(red + "올바르게 입력해주세요 !" + exit + BACK_BLACK);
                    searchMonsterName(con);
                    break;
                }
            } else {
                System.out.println(red + "검색결과가 없습니다. 올바르게 입력해주세요." + blue + "(exit = 뒤로가기)" + exit + BACK_BLACK);
                searchMonsterName(con);
                break;
            }
        }
    }

    // 괴물 DTO로 받은 것 추가하는 메소드
    public int insertMonster(Connection con, MonsterDTO monsDTO) {
        PreparedStatement pstmt = null;

        int result = 0;

        String query = prop.getProperty("insertMonster");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, monsDTO.getCode());
            pstmt.setString(2, monsDTO.getName());
            pstmt.setString(3, monsDTO.getOrganic());
            pstmt.setString(4, monsDTO.getKill());
            pstmt.setInt(5, monsDTO.getPower());
            pstmt.setString(6, monsDTO.getSpeed());
            pstmt.setString(7, monsDTO.getIntelligence());
            pstmt.setInt(8, monsDTO.getSpawnNumber());
            pstmt.setString(9, monsDTO.getSize());
            pstmt.setString(10, monsDTO.getRank());
            pstmt.setString(11, monsDTO.getSpawnId());
            pstmt.setString(12, monsDTO.getInteriorId());

            result = pstmt.executeUpdate(); //이거 빼먹으면 result가 가만히 있어서 값이 들어가지 않게 됨
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    // 위의 메소드를 마지막에 넣기 위해 만든 메소드
    public void insertUI() {
        Connection con = getConnection();
        System.out.println(BACK_BLACK + green + "새로운 괴물의 코드를 지정해주세요. 규칙 : 앞 영어 두글자 + 숫자");
        String code = sc.nextLine().toUpperCase();
        System.out.println("괴물의 이름을 입력해주세요.");
        String name = sc.nextLine().toUpperCase();

        String organic;
        while (true) {
            System.out.println("생명체인지 선택해주세요. (유기물 Y, 무기물 N)");
            organic = sc.nextLine().toUpperCase();
            if (!organic.equals("Y") && !organic.equals("N")) {
                System.out.println(red + "올바른 알파벳을 선택해주세요." + exit + BACK_BLACK);
            } else {
                break;
            }
        }
        String kill;
        while (true) {
            System.out.println("괴물을 죽일 수 있나요? (가능 Y, 불가능 N)");
            kill = sc.nextLine().toUpperCase();
            if (!kill.equals("Y") && !kill.equals("N")) {
                System.out.println(red + "올바른 알파벳을 선택해주세요." + exit + BACK_BLACK);
            } else {
                break;
            }
        }
        int power;
        while (true) {
            System.out.println("괴물의 공격력을 등록해주세요.");
            power = sc.nextInt();
            if (power < 0) {
                System.out.println(red + "0 이상의 수만 입력 가능합니다." + exit + BACK_BLACK);
            } else {
                break;
            }
        }
        String speed;
        while (true) {
            System.out.println("괴물의 속도 정보를 저장해주세요. (F = 빠름, M = 보통, S = 느림, N = 없음 중 택 1)");
            sc.nextLine();
            speed = sc.nextLine().toUpperCase();
            if (!speed.equals("F") && !speed.equals("M") && !speed.equals("S") && !speed.equals("N")) {
                System.out.println(red + "올바른 입력이 아닙니다. 4개중 한개만 가능합니다." + exit + BACK_BLACK);
            } else {
                if (speed.equals("F")) {
                    speed = "빠름";
                } else if (speed.equals("M")) {
                    speed = "보통";
                } else if (speed.equals("S")) {
                    speed = "느림";
                } else {
                    speed = "없음";
                }
                break;
            }
        }
        String intelligence;
        while (true) {
            System.out.println("괴물이 문을 열 수 있는지 등록해주세요. (가능 Y, 불가능 N)");
            intelligence = sc.nextLine().toUpperCase();
            if (!intelligence.equals("Y") && !intelligence.equals("N")) {
                System.out.println(red + "올바른 입력이 아닙니다." + exit + BACK_BLACK);
            } else {
                break;
            }
        }
        int spawnNumber;
        while (true) {
            System.out.println("괴물이 생성되는 최대 수를 적어주세요.");
            spawnNumber = sc.nextInt();
            if (spawnNumber <= 0) {
                System.out.println(red + "올바른 입력이 아닙니다. 1 이상의 수를 입력해주세요." + exit + BACK_BLACK);
            } else {
                break;
            }
        }
        String size;
        while (true) {
            System.out.println("괴물의 크기를 등록해주세요. (G : 매우큰, B : 큰, N : 보통, S : 작은, T : 매우 작은)");
            size = sc.nextLine().toUpperCase();
            if (!size.equals("G") && !size.equals("B") && !size.equals("N") && !size.equals("S") && !size.equals("T")) {
                System.out.println(red + "올바른 입력이 아닙니다. 올바른 알파벳을 입력해주세요." + exit + BACK_BLACK);
            } else {
                break;
            }
        }
        String rank;
        while (true) {
            System.out.println("위험도를 평가해주세요. (S : 매우위험, A : 위험, B : 보통, C : 약간 위험, D : 위험하지 않음, F : 안전 ");
            rank = sc.nextLine().toUpperCase();
            if (!rank.equals("S") && !rank.equals("A") && !rank.equals("B") && !rank.equals("C") && !rank.equals("D") && !rank.equals("F")) {
                System.out.println(red + "올바른 입력이 아닙니다. 올바른 알파벳을 입력해주세요." + exit + BACK_BLACK);
            } else {
                break;
            }
        }
        String spawnId;
        while (true) {
            System.out.println("괴물이 나타나는 내외부를 등록해주세요. (I : 내부, O : 외부, IO : 내외부)");
            spawnId = sc.nextLine().toUpperCase();
            if (!spawnId.equals("I") && !spawnId.equals("O") && !spawnId.equals("IO")) {
                System.out.println(red + "올바른 입력이 아닙니다. 올바른 알파벳을 입력해주세요." + exit + BACK_BLACK);
            } else {
                break;
            }
        }
        String interiorId;
        while (true) {
            System.out.println("괴물이 나타나는 내부유형을 등록해주세요. (F : 공장, H : 저택, M : 광산, FH : 공장과 저택, FM : 공장과 광산, HM : 저택과 광산, FHM : 모두 출현");
            interiorId = sc.nextLine().toUpperCase();
            if (!interiorId.equals("F") && !interiorId.equals("H") && !interiorId.equals("M") && !interiorId.equals("FH") && !interiorId.equals("FM") && !interiorId.equals("HM") && !interiorId.equals("FHM")) {
                System.out.println(red + "올바른 입력이 아닙니다. 올바른 알파벳을 입력해주세요." + exit + BACK_BLACK);
            } else {
                break;
            }
        }
        MonsterDTO monsDTO = new MonsterDTO(code, name, organic, kill, power, speed, intelligence, spawnNumber, size, rank, spawnId, interiorId);
        int result = insertMonster(con, monsDTO);
        if (result > 0) {
            System.out.println(green + "신규 괴물 등록 성공" + exit);
            System.out.println("새롭게 추가된 괴물");
            System.out.println(monsDTO);
        } else {
            System.out.println(red + "괴물 등록 실패" + exit);
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
        System.out.println("변경할 괴물의 코드를 입력하세요.");
        answer = sc.nextLine();

        return answer;
    }

    public void updateMonster(Connection con) {
        // 변경할 코드 선택
        System.out.println("변경할 괴물의 코드를 입력하세요.");
        String answer = sc.nextLine();
        // 코드 변경에 선택점을 주기위해 선택한 방법
        System.out.println("괴물의 변경할 정보의 번호를 선택해주세요.");
        System.out.println("1 : 코드 수정");
        System.out.println("2 : 이름 수정");
        System.out.println("3 : 생명체 유무 수정");
        System.out.println("4 : 살상 가능 유무 수정");
        System.out.println("5 : 괴물 공격력 수정");
        System.out.println("6 : 괴물 속도 정보 수정");
        System.out.println("7 : 괴물 개폐 가능 정보 수정");
        System.out.println("8 : 괴물 최대 생성 수 정보 수정");
        System.out.println("9 : 괴물 크기 정보 수정");
        System.out.println("10 : 괴물 위험도 정보 수정");
        System.out.println("11 : 괴물 생성 위치 정보 수정");
        System.out.println("12 : 괴물 생성 구역 정보 수정");
        int updateAnswer = sc.nextInt();

        switch (updateAnswer) {
            case 1:
                updateCode(con, answer);
                break;
            case 2:
                updateName(con, answer);
                break;
            case 3:
                updateOrganic(con, answer);
                break;
            case 4:
                updateKill(con, answer);
                break;
            case 5:
                updatePower(con, answer);
                break;
            case 6:
                updateSpeed(con, answer);
                break;
            case 7:
                updateIntelligence(con, answer);
                break;
            case 8:
                updateSpawnNumber(con, answer);
                break;
            case 9:
                updateSize(con, answer);
                break;
            case 10:
                updateRank(con, answer);
                break;
            case 11:
                updateSpawnId(con, answer);
                break;
            case 12:
                updateInteriorId(con, answer);
                break;
        }
    }

    public void updateCode(Connection con, String answer) {
        PreparedStatement pstmt = null;
        int result = 0;

        
        System.out.println("선택한 괴물의 변경할 코드를 입력해주세요.");
        sc.nextLine();
        String monsterCode = sc.nextLine().toUpperCase().replace(" ", "");

        MonsterDTO changeCode = new MonsterDTO();
        changeCode.setCode(monsterCode);


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
            System.out.println(green + monsterCode + " 변경 성공" + exit);
        } else {
            System.out.println(red + monsterCode + " 변경 실패" + exit);
        }
    }

    // try catch 나중에 이용해보자
    public void updateName(Connection con, String answer) {
        PreparedStatement pstmt = null;
        int result = 0;
        System.out.println("선택한 괴물의 변경할 이름을 입력해주세요.");
        String monsterName = sc.nextLine();

        MonsterDTO changeName = new MonsterDTO();
        changeName.setName(monsterName);

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
            System.out.println(green + monsterName + " 변경 성공" + exit);
        } else {
            System.out.println(red + monsterName + "변경 실패" + exit);
        }
    }

    public void updateOrganic(Connection con, String answer) {
        PreparedStatement pstmt = null;
        int result = 0;
        String monsterOrganic;
        System.out.println("선택한 괴물의 변경할 생명체 유무를 입력해주세요. (가능 : Y, 불가능 : N)"); sc.nextLine();
        while (true) {
            monsterOrganic = sc.nextLine().toUpperCase();
            if (!monsterOrganic.equals("Y") && !monsterOrganic.equals("N")) {
                System.out.println(red + "잘못된 입력입니다." + exit + BACK_BLACK);
            }else {break;} }
        MonsterDTO changeOrganic = new MonsterDTO();
        changeOrganic.setOrganic(monsterOrganic);

        String query = prop.getProperty("updateOrganic");
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, changeOrganic.getOrganic());
            pstmt.setString(2, answer);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (result > 0) {
            System.out.println(green + monsterOrganic + " 변경 성공" + exit);
        } else {
            System.out.println(red + monsterOrganic + "변경 실패" + exit);
        }
    }

    public void updateKill(Connection con, String answer) {
        PreparedStatement pstmt = null;
        int result = 0;
        String monsterKill;
        System.out.println("선택한 괴물의 변경할 살상 유무를 입력해주세요. (가능 : Y, 불가능 : N)"); sc.nextLine();
        while (true) {
            monsterKill = sc.nextLine().toUpperCase().replace(" ", "");
            switch (monsterKill) {
                case "가능":
                    monsterKill = "Y";
                    break;
                case "불가능":
                    monsterKill = "N";
                    break;
            }
            if (!monsterKill.equals("Y") && !monsterKill.equals("N")) {
                System.out.println(red + "잘못된 입력입니다." + exit + BACK_BLACK);
            } else {break;}
        }

            MonsterDTO changeKill = new MonsterDTO();
            changeKill.setKill(monsterKill);

            String query = prop.getProperty("updateKill");
            try {
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, changeKill.getKill());
                pstmt.setString(2, answer);
                result = pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (result > 0) {
                System.out.println(green + monsterKill + " 변경 성공" + exit);
            } else {
                System.out.println(red + monsterKill + "변경 실패" + exit);
            }

    }

    public void updatePower(Connection con, String answer) {
        PreparedStatement pstmt = null;
        int result = 0;
        int monsterPower;
        System.out.println("선택한 괴물의 변경할 공격력을 입력해주세요. (" + blue + "0 (공격없음) ~ " + red + "100 (즉사)" + exit + BACK_BLACK + green + ")"); sc.nextLine();
        while (true) {
            monsterPower = sc.nextInt();
            if (monsterPower < 0) {
                System.out.println(red + "올바른 입력이 아닙니다. 0 이상의 수를 입력해주세요." + exit + BACK_BLACK);
            } else {break;}
        }
        MonsterDTO changePower = new MonsterDTO();
        changePower.setPower(monsterPower);

        String query = prop.getProperty("updatePower");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, changePower.getPower());
            pstmt.setString(2, answer);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (result > 0) {
            System.out.println(green + monsterPower + " 변경 성공" + exit);
        } else {
            System.out.println(red + monsterPower + "변경 실패" + exit);
        }
    }

    public void updateSpeed(Connection con, String answer) {
        PreparedStatement pstmt = null;
        int result = 0;
        System.out.println("선택한 괴물의 변경할 속도를 입력해주세요. 1 : 빠름으로 변경, 2 : 보통으로 변경, 3 : 느림으로 변경, 4 : 이동 없음으로 변경"); sc.nextLine();
        String monsterSpeed;
        while (true) {
            monsterSpeed = sc.nextLine();
            if (monsterSpeed.equals("1") || monsterSpeed.contains("빠름")) {
                monsterSpeed = "빠름"; break;
            } else if (monsterSpeed.equals("2") || monsterSpeed.contains("보통")) {
                monsterSpeed = "보통"; break;
            } else if (monsterSpeed.equals("3") || monsterSpeed.contains("느림")) {
                monsterSpeed = "느림"; break;
            } else if (monsterSpeed.equals("4") || monsterSpeed.contains("없음")) {
                monsterSpeed = "없음"; break;
            } else {
                System.out.println(red + "올바른 입력이 아닙니다. 숫자나 단어를 입력해주세요." + exit + BACK_BLACK);
            }
        }
        MonsterDTO changeSpeed = new MonsterDTO();
        changeSpeed.setSpeed(monsterSpeed);

        String query = prop.getProperty("updateSpeed");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, changeSpeed.getSpeed());
            pstmt.setString(2, answer);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (result > 0) {
            System.out.println(green + monsterSpeed + " 변경 성공" + exit);
        } else {
            System.out.println(red + monsterSpeed + "변경 실패" + exit);
        }
    }

    public void updateIntelligence(Connection con, String answer) {
        PreparedStatement pstmt = null;
        int result = 0;
        System.out.println("선택한 괴물의 변경할 문 개폐가능 여부를 입력해주세요. (가능 : Y, 불가능 : N) "); sc.nextLine();
        String monsterIntelligence;
        while (true) {
            monsterIntelligence = sc.nextLine().toUpperCase().replace(" ", "");
            switch (monsterIntelligence) {
                case "가능":
                    monsterIntelligence = "Y";
                    break;
                case "불가능":
                    monsterIntelligence = "N";
                    break;
            }
            if (!monsterIntelligence.equals("Y") && !monsterIntelligence.equals("N")) {
                System.out.println(red + "잘못된 입력입니다." + exit + BACK_BLACK);
            } else {break;}
        }
        MonsterDTO changeIntelligence = new MonsterDTO();
        changeIntelligence.setIntelligence(monsterIntelligence);

        String query = prop.getProperty("updateIntelligence");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, changeIntelligence.getIntelligence());
            pstmt.setString(2, answer);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (result > 0) {
            System.out.println(green + monsterIntelligence + " 변경 성공" + exit);
        } else {
            System.out.println(red + monsterIntelligence + "변경 실패" + exit);
        }
    }

    public void updateSpawnNumber(Connection con, String answer) {
        PreparedStatement pstmt = null;
        int result = 0;
        System.out.println("선택한 괴물의 변경할 최대 생성 수를 입력해주세요."); sc.nextLine();
        int monsterSpawnNumber;
        while (true) {
            monsterSpawnNumber = sc.nextInt();
            if (monsterSpawnNumber < 0) {
                System.out.println(red + "잘못된 입력입니다." + exit + BACK_BLACK);
            } else {break;}
        }
        MonsterDTO changeSpawnNumber = new MonsterDTO();
        changeSpawnNumber.setSpawnNumber(monsterSpawnNumber);

        String query = prop.getProperty("updateSpawnNumber");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, changeSpawnNumber.getSpawnNumber());
            pstmt.setString(2, answer);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (result > 0) {
            System.out.println(green + monsterSpawnNumber + " 변경 성공" + exit);
        } else {
            System.out.println(red + monsterSpawnNumber + "변경 실패" + exit);
        }
    }

    public void updateSize(Connection con, String answer) {
        PreparedStatement pstmt = null;
        int result = 0;
        String monsterSize;
        System.out.println("선택한 괴물의 변경할 크기를 입력하세요. ( G : 거대, B : 큼, N : 보통, S : 작은, T : 매우 작은"); sc.nextLine();
        while (true) {
            monsterSize = sc.nextLine().toUpperCase().replace(" ", "");
            if (monsterSize.equals("거대") || monsterSize.equals("G")) {
                monsterSize = "G";
                break;
            } else if (monsterSize.equals("큼") || monsterSize.equals("B")) {
                monsterSize = "B";
                break;
            } else if (monsterSize.equals("보통") || monsterSize.equals("N")) {
                monsterSize = "N";
                break;
            } else if (monsterSize.equals("작은") || monsterSize.equals("S")) {
                monsterSize = "S";
                break;
            } else if (monsterSize.equals("매우작은") || monsterSize.equals("T")) {
                monsterSize = "T";
                break;
            } else {
                System.out.println(red + "올바른 입력이 아닙니다." + exit + BACK_BLACK);
            }
        }
//        switch (monsterSize) {
//            case "거대" :
//            case "G" :
//                monsterSize = "G"; break;
//            case "큼" :
//            case "B" :
//                monsterSize = "B"; break;
//            case "보통" :
//            case "N" :
//                monsterSize = "N"; break;
//            case "작은" :
//            case "S" :
//                monsterSize = "S"; break;
//            case "매우작은" :
//            case "T" :
//                monsterSize = "T"; break;
//            default:
//                System.out.println(red + "올바른 입력이 아닙니다." + exit + BACK_BLACK);
//        }
        MonsterDTO changeMonsterSize = new MonsterDTO();
        changeMonsterSize.setSize(monsterSize);
        String query = prop.getProperty("updateSize");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, changeMonsterSize.getSize());
            pstmt.setString(2, answer);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (result > 0) {
            System.out.println(green + monsterSize + " 변경 성공" + exit);
        } else {
            System.out.println(red + monsterSize + "변경 실패" + exit);
        }
    }

    public void updateRank(Connection con, String answer) {
        PreparedStatement pstmt = null;
        int result = 0;
        String monsterRank;
        System.out.println("선택한 괴물의 변경할 위험도를 입력하세요. ( S : 매우 높음, A : 높음, B : 중간, C : 낮음, D : 매우 낮음, F : 안전 )"); sc.nextLine();
        while (true) {
            monsterRank = sc.nextLine().toUpperCase().replace(" ", "");
            switch (monsterRank) {
                case "매우높음": case "S": monsterRank = "S"; break;
                case "높음": case "A": monsterRank = "A"; break;
                case "중간": case "B": monsterRank = "B"; break;
                case "낮음": case "C": monsterRank = "C"; break;
                case "매우낮음": case "D": monsterRank = "D"; break;
                case "안전": case "F": monsterRank = "F"; break;
                default: System.out.println(red + "잘못된 입력입니다." + exit + BACK_BLACK); continue;
            }
            break;
        }
//        if (monsterRank.equals("매우높음")||monsterRank.equals("S")) {
//            monsterRank = "S";
//            break;
//        } else if (monsterRank.equals("높음") || monsterRank.equals("A")) {
//            monsterRank = "A";
//            break;
//        } else if (monsterRank.equals("중간") || monsterRank.equals("B")) {
//            monsterRank = "B";
//            break;
//        } else if (monsterRank.equals("낮음") || monsterRank.equals("C")) {
//            monsterRank = "C";
//            break;
//        } else if (monsterRank.equals("매우낮음") || monsterRank.equals("D")) {
//            monsterRank = "D";
//            break;
//        } else if (monsterRank.equals("안전") || monsterRank.equals("F")) {
//            monsterRank = "F";
//            break;
//        } else {
//            System.out.println(red + "잘못된 입력입니다." + exit + BACK_BLACK);
//        }

        MonsterDTO changeRank = new MonsterDTO();
        changeRank.setRank(monsterRank);
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
            System.out.println(green + monsterRank + " 변경 성공" + exit);
        } else {
            System.out.println(red + monsterRank + "변경 실패" + exit);
        }
    }

    public void updateSpawnId(Connection con, String answer) {
        PreparedStatement pstmt = null;
        int result = 0;
        String monsterSpawnId;
        System.out.println("선택한 괴물의 변경할 생성 위치를 입력하세요. ( I : 내부, O : 외부, IO : 내외부 )"); sc.nextLine();
        while (true) {
            monsterSpawnId = sc.nextLine().toUpperCase().replace(" ", "");
            switch (monsterSpawnId) {
                case "내부": case "I":
                    monsterSpawnId = "I";
                    break;
                case "외부": case "O":
                    monsterSpawnId = "O";
                    break;
                case "내외부": case "내부,외부": case "내부외부": case "외부,내부": case "외부내부":
                case "IO": case "OI":
                    monsterSpawnId = "IO";
                    break;
                default:
                    System.out.println(red + "잘못된 입력입니다." + exit + BACK_BLACK);
                    continue;
            }
            break;
        }

        MonsterDTO changeSpawnId = new MonsterDTO();
        changeSpawnId.setSpawnId(monsterSpawnId);
        String query = prop.getProperty("updateSpawnId");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, changeSpawnId.getSpawnId());
            pstmt.setString(2, answer);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (result > 0) {
            System.out.println(green + monsterSpawnId + " 변경 성공" + exit);
        } else {
            System.out.println(red + monsterSpawnId + "변경 실패" + exit);
        }
    }

    public void updateInteriorId(Connection con, String answer) {
        PreparedStatement pstmt = null;
        int result = 0;
        System.out.println("선택한 괴물의 변경할 내부 유형을 입력하세요.");
        System.out.println("( F : 공장, H : 저택, M : 광산, FH : 공장 & 저택, FM : 공장 & 광산, HM : 저택 & 광산, FHM : 모든 맵)"); sc.nextLine();
        String monsterInteriorId;
        while (true) {
            monsterInteriorId = sc.nextLine().toUpperCase().replace(" ", "");
            switch (monsterInteriorId) {
                case "공장": case "F":
                    monsterInteriorId = "F";
                    break;
                case "저택": case "H":
                    monsterInteriorId = "H";
                    break;
                case "광산": case "M":
                    monsterInteriorId = "M";
                    break;
                case "공장저택": case "저택공장": case "공장,저택": case "저택,공장":
                case "HF": case "FH":
                    monsterInteriorId = "FH";
                    break;
                case "공장광산": case "광산공장": case "공장,광산": case "광산,공장":
                case "MF": case "FM":
                    monsterInteriorId = "FM";
                    break;
                case "저택광산": case "광산저택": case "저택,광산": case "광산,저택":
                case "MH": case "HM":
                    monsterInteriorId = "HM";
                    break;
                case "ALL": case "모든맵": case "공장저택광산": case "공장광산저택": case "저택공장광산":
                case "저택광산공장": case "광산공장저택": case "광산저택공장": case "공장,저택,광산": case "저택,공장,광산":
                case "광산,저택,공장": case "공장,광산,저택": case "저택,광산,공장": case "광산,공장,저택":
                case "FMH": case "HFM": case "HMF": case "MFH": case "MHF": case "FHM":
                    monsterInteriorId = "FHM";
                    break;
                default:
                    System.out.println(red + "잘못된 입력입니다." + exit + BACK_BLACK);
                    continue;
            }
            break;
        }
        MonsterDTO changeInteriorId = new MonsterDTO();
        changeInteriorId.setInteriorId(monsterInteriorId);
        String query = prop.getProperty("updateInteriorId");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, changeInteriorId.getInteriorId());
            pstmt.setString(2, answer);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (result > 0) {
            System.out.println(green + monsterInteriorId + " 변경 성공" + exit);
        } else {
            System.out.println(red + monsterInteriorId + "변경 실패" + exit);
        }
    }

    public void deleteMonster(Connection con) {
        // 제거용으로 만든거
        PreparedStatement pstmt = null;
        // 이름 가져오려고 만든것
//        PreparedStatement confirmpstmt = null;
        Statement stmt = null;

        ResultSet rset = null; // 마찬가지로 이름 가져오기위해 생성

        int result = 0;

        int confirmResult = 0;
        String query = prop.getProperty("deleteMonster");
        System.out.println("제거할 괴물의 코드를 입력해주세요.");
        String deleteAnswer = sc.nextLine().replace(" ","");

//        String confirmQuery = prop.getProperty("deleteConfirm");
        String confirmQuery = "SELECT * FROM MONSTER WHERE MONSTER_CODE = '" + deleteAnswer +"'";
        MonsterDTO confirmMonster = new MonsterDTO();

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(confirmQuery);

            if(rset.next()){
                confirmMonster = new MonsterDTO();
                confirmMonster.setName(rset.getString("MONSTER_NAME"));
            }
            // ResultSet에서 정보를 가져오기 위해 필수로 if (next()) 사용해야 하는것 같다.
            // 순서 중요
//            confirmpstmt = con.prepareStatement(confirmQuery);
//            confirmpstmt.setString(1, deleteAnswer);
//            rset = confirmpstmt.executeQuery(confirmQuery);

             // Resultset에도 쿼리문이 들어감 result가 아님
//            if (rset.next()) {
//                confirmMonster.setName(rset.getString("MONSTER_NAME"));
//            }
            // 아무리 해도 안돼서 변경 (질문해야할듯 ?)
            // 뭔가 잘못된듯 함 그리고 마지막 부분에 confirmMonster의 객체를 불러오면 전체
            // getString으로 초기화가 이름에만 돼 있어 null로 전부반환되므로 confirmMonster.getName() 사용

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, deleteAnswer);
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (result > 0) {
            System.out.println(green + confirmMonster.getName() +" 제거 성공" + exit);
        } else {
            System.out.println(red + "제거 실패" + exit);
        }
    }

}
