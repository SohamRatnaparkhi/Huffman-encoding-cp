package cp.utils;

public class Data {
    Byte data;
    int frequency;

    public Data(Byte data, int frequency) {
        this.data = data;
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return data == null ? frequency + "" : (char)data.byteValue() + "";
    }
}
