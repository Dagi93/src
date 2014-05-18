package boersenspiel;

public abstract class Asset {
    private String name;
    private long value;

    public String getName() {
        return this.name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public void addValue(long difference) {
        this.value += difference;
    }

    public void setName(String newName) {
        this.name = newName;
    }
}