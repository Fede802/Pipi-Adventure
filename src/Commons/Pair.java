package commons;

public class Pair<k,v> {

    private k key;
    private v value;

    public Pair(k key, v value){
        this.key = key;
        this.value = value;
    }

    public k getKey() {
        return key;
    }

    public void updateKey(k key) {
        this.key = key;
    }

    public v getValue() {
        return value;
    }

    public void updateValue(v value) {
        this.value = value;
    }

}
