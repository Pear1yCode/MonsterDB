package model;

public class MonsterDTO {

    private String code;
    private String name;
    private String organic;
    private String kill;
    private int power;
    private String speed;
    private String intelligence;
    private int spawnNumber;
    private String size;
    private String rank;
    private String spawnId;
    private String interiorId;

    public MonsterDTO() {};

    public MonsterDTO(String code, String name, String organic, String kill, int power, String speed, String intelligence, int spawnNumber, String size, String rank, String spawnId, String interiorId) {
        this.code = code;
        this.name = name;
        this.organic = organic;
        this.kill = kill;
        this.power = power;
        this.speed = speed;
        this.intelligence = intelligence;
        this.spawnNumber = spawnNumber;
        this.size = size;
        this.rank = rank;
        this.spawnId = spawnId;
        this.interiorId = interiorId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganic() {
        return organic;
    }

    public void setOrganic(String organic) {
        this.organic = organic;
    }

    public String getKill() {
        return kill;
    }

    public void setKill(String kill) {
        this.kill = kill;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(String intelligence) {
        this.intelligence = intelligence;
    }

    public int getSpawnNumber() {
        return spawnNumber;
    }

    public void setSpawnNumber(int spawnNumber) {
        this.spawnNumber = spawnNumber;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSpawnId() {
        return spawnId;
    }

    public void setSpawnId(String spawnId) {
        this.spawnId = spawnId;
    }

    public String getInteriorId() {
        return interiorId;
    }

    public void setInteriorId(String interiorId) {
        this.interiorId = interiorId;
    }

    @Override
    public String toString() {
        return "👾 괴물 전체 정보 조회   " +
                "✔ 괴물 코드번호 : " + code + "\n" +
                "⭕ 괴물 이름 : " + name + "\n" +
                "🚫 위험성 : " + rank + "\n" +
                "💔 생명체 유무 : " + organic + "\n" +
                "💫 살상 가능 : " + kill + "\n" +
                "🚪 문 개폐 지능 : " + intelligence + "\n" +
                "💥 공격력 : " + power+ "\n" +
                "💨 속도 : " + speed + "\n" +
                "💹 크기 : " + size + "\n" +
                "❓❗ 출현구역 : " + interiorId  + "\n" +
                "✅ 출현지 : " + spawnId  + "\n" +
                "🚸 출현 최대 수 : " + spawnNumber + "\n"
                ;
    }
}