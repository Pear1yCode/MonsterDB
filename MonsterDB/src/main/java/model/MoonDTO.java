package model;

public class MoonDTO {
    private String code;
    private String name;
    private String rank;


    public MoonDTO () {};

    public MoonDTO(String code, String name, String rank) {
        this.code = code;
        this.name = name;
        this.rank = rank;
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return " 🌎 행성 전체 정보 조회 " +
                "✔ 행성 코드번호 : " + code + "\n" +
                "⭕ 행성 이름 : " + name + "\n" +
                "🚫 위험성 : " + rank + "\n";
    }
}
