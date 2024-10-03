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

    public List<Map<String, String>> monsterRank(Connection con) {
        Statement stmt = null;
        ResultSet rset = null;

        List<Map<String, String>> monsterList = null;

        String query = prop.getProperty("monsterRisk"); // 쿼리문에 있는 key의 내용

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            monsterList = new ArrayList<>();

            System.out.println(" S = 매우 높음 || A = 높음 || B = 보통 || C = 낮음 || D = 매우낮음 || F = 안전 " );
            while (rset.next()) {
                Map<String, String> monster = new HashMap<>();
                monster.put(rset.getString("EMOJI_RANK"), rset.getString("MONSTER_NAME"));
                monsterList.add(monster);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return monsterList;
    }

    public void serachMonsterRank(Connection con) {
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
        for(MonsterDTO monsterRank : monsterList){
            System.out.println(" 검색한 위험도의 괴물들 " + monsterRank);
        }

    }

    public void searchMonsterName(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        MonsterDTO searchMonster = null;

        System.out.println("조회할 괴물의 이름을 입력해주세요.");
        String answer = sc.nextLine().toUpperCase().replace(" ", "");
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
        while (true) {
            if (searchMonster != null) {
                System.out.println("검색한 괴물의 정보 : " + searchMonster);
                System.out.println("다른 괴물을 검색하시겠습니까?");
                System.out.println("1 : 예, 2 : 아니오");
                String search = sc.nextLine();
                if (search.equals("예") || search.equals("네") || search.equals("1")) {
                    searchMonsterName(con);
                } else if (search.equals("아니오") || search.equals("아니요") || search.equals("2")) {
                    UI ui = new UI();
                    ui.forUI();
                } else {
                    System.out.println("올바르게 입력해주세요 !");
                    searchMonsterName(con);
                }
            } else {
                System.out.println("검색결과가 없습니다. 올바르게 입력해주세요.");
                searchMonsterName(con);
                if (answer.equals("exit")) {
                    break;
                }
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
        Scanner sc = new Scanner(System.in);
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
                System.out.println("올바른 알파벳을 선택해주세요.");
            } else {
                break;
            }
        }
        String kill;
        while (true) {
            System.out.println("괴물을 죽일 수 있나요? (가능 Y, 불가능 N)");
            kill = sc.nextLine().toUpperCase();
            if (!kill.equals("Y") && !kill.equals("N")) {
                System.out.println("올바른 알파벳을 선택해주세요.");
            } else {
                break;
            }
        }
        int power;
        while (true) {
            System.out.println("괴물의 공격력을 등록해주세요.");
            power = sc.nextInt();
            if (power < 0) {
                System.out.println("0 이상의 수만 입력 가능합니다.");
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
                System.out.println("올바른 입력이 아닙니다. 4개중 한개만 가능합니다.");
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
                System.out.println("올바른 입력이 아닙니다.");
            } else {
                break;
            }
        }
        int spawnNumber;
        while (true) {
            System.out.println("괴물이 생성되는 최대 수를 적어주세요.");
            spawnNumber = sc.nextInt();
            if (spawnNumber <= 0) {
                System.out.println("올바른 입력이 아닙니다. 1 이상의 수를 입력해주세요.");
            } else {
                break;
            }
        }
        String size;
        while (true) {
            System.out.println("괴물의 크기를 등록해주세요. (G : 매우큰, B : 큰, N : 보통, S : 작은, T : 매우 작은)");
            sc.nextLine();
            size = sc.nextLine().toUpperCase();
            if(!size.equals("G") && !size.equals("B") && !size.equals("N") && !size.equals("S") && !size.equals("T")) {
                System.out.println("올바른 입력이 아닙니다. 올바른 알파벳을 입력해주세요.");
            } else {
                break;
            }
        }
        String rank;
        while (true) {
            System.out.println("위험도를 평가해주세요. (S : 매우위험, A : 위험, B : 보통, C : 약간 위험, D : 위험하지 않음, F : 안전 ");
            rank = sc.nextLine().toUpperCase();
            if(!rank.equals("S") && !rank.equals("A") && !rank.equals("B") && !rank.equals("C") && !rank.equals("D") && !rank.equals("F")) {
                System.out.println("올바른 입력이 아닙니다. 올바른 알파벳을 입력해주세요.");
            } else {
                break;
            }
        }
        String spawnId;
        while (true) {
            System.out.println("괴물이 나타나는 내외부를 등록해주세요. (I : 내부, O : 외부, IO : 내외부)");
            spawnId = sc.nextLine().toUpperCase();
            if(!spawnId.equals("I") && !spawnId.equals("O") && !spawnId.equals("IO")) {
                System.out.println("올바른 입력이 아닙니다. 올바른 알파벳을 입력해주세요.");
            } else {
                break;
            }
        }
        String interiorId;
        while (true) {
            System.out.println("괴물이 나타나는 내부유형을 등록해주세요. (F : 공장, H : 저택, M : 광산, FH : 공장과 저택, FM : 공장과 광산, HM : 저택과 광산, FHM : 모두 출현");
            interiorId = sc.nextLine().toUpperCase();
            if(!interiorId.equals("F") && !interiorId.equals("H") && !interiorId.equals("M") && !interiorId.equals("FH") && !interiorId.equals("FM") && !interiorId.equals("HM") && !interiorId.equals("FHM")) {
                System.out.println("올바른 입력이 아닙니다. 올바른 알파벳을 입력해주세요.");
            } else {
                break;
            }
        }
        MonsterDTO monsDTO = new MonsterDTO(code, name, organic, kill, power, speed, intelligence, spawnNumber, size, rank, spawnId, interiorId);
        int result = insertMonster(con, monsDTO);
        if(result > 0) {
            System.out.println("괴물 등록 성공 !");
        } else {
            System.out.println("괴물 등록 실패 !");
        }
    }

    public void updateMonster(Connection con) {
        // 변경할 코드 선택
        System.out.println("변경할 괴물의 코드를 입력하세요.");
        String answer = sc.nextLine();
        // 코드 변경에 선택점을 주기위해 선택한 방법
        System.out.println("괴물의 변경할 정보의 번호를 선택해주세요.)");
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
        System.out.println("12 : 괴물 생성 지역 정보 수정");
        int updateAnswer = sc.nextInt();

        switch (updateAnswer) {
            case 1 : updateCode(); break;
            case 2 : updateName(); break;
            case 3 : updateOrganic(); break;
            case 4 : updateKill(); break;
            case 5 : updatePower(); break;
            case 6 : updateSpeed(); break;
            case 7 : updateIntelligence(); break;
        }
    }

    public void updateCode() {
        System.out.println("선택한 괴물의 변경할 코드를 입력해주세요.");
        String monsterCode = sc.nextLine();
    }

    public void updateName() {
        System.out.println("선택한 괴물의 변경할 이름을 입력해주세요.");
        String monsterName = sc.nextLine();
    }

    public void updateOrganic() {
        System.out.println("선택한 괴물의 변경할 생명체 유무를 입력해주세요. (가능 : Y, 불가능 : N)");
        String monsterOrganic = sc.nextLine();
    }

    public void updateKill() {
        System.out.println("선택한 괴물의 변경할 살상 유무를 입력해주세요. (가능 : Y, 불가능 : N)");
        String monsterKill = sc.nextLine();
    }

    public void updatePower() {
        System.out.println("선택한 괴물의 변경할 공격력을 입력해주세요. (0 공격없음 ~ 100 (즉사)");
        int monsterPower = sc.nextInt();
    }

    public void updateSpeed() {
        System.out.println("선택한 괴물의 변경할 속도를 입력해주세요. ");
        String monsterSpeed = sc.nextLine();
    }

    public void updateIntelligence() {
        System.out.println("선택한 괴물의 변경할 문 개폐가능 여부를 입력해주세요. (가능 : Y, 불가능 : N) ");
        String monsterIntelligence = sc.nextLine();
    }

    public void updateSpawnNumber() {
        System.out.println("선택한 괴물의 변경할 최대 생성 수를 입력해주세요.");
        int monsterSpawnNumber = sc.nextInt();
    }

    public void updateSize() {
        System.out.println("선택한 괴물의 변경할 크기를 입력하세요.");
        String monsterSize = sc.nextLine();
    }

    public void updateRank() {
        System.out.println("선택한 괴물의 변경할 위험도를 입력하세요.");
        String monsterRank = sc.nextLine();
    }

    public void updateSpawnId() {
        System.out.println("선택한 괴물의 변경할 생성 위치를 입력하세요.");
        String monsterSpawnId = sc.nextLine();
    }

    public void updateInteriorId() {
        System.out.println("선택한 괴물의 변경할 내부 유형을 입력하세요.");
        String monsterInteriorId = sc.nextLine();
    }

}
