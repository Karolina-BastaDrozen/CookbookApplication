package pl.javastart.ksiazka.przepis;

public enum DifficultyLevel {
    VERY_EASY("Bardzo łatwy"), EASY("Łatwy"), MEDIUM("Średni"), HARD("Trudny"), VERY_HARD("Bardzo trudny");

    private String name;

    DifficultyLevel(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Poziom trudności: " + name;
    }
}