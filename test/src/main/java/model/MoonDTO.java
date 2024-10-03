package model;

public class MoonDTO {
    private int code;
    private String name;
    private char rank;


    public MoonDTO () {};

    public MoonDTO(int code, String name, char rank) {
        this.code = code;
        this.name = name;
        this.rank = rank;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getRank() {
        return rank;
    }

    public void setRank(char rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "MoonDTO{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", rank=" + rank +
                '}';
    }
}
