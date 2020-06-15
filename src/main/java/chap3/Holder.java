package chap3;

public class Holder {

    private int n;

    public Holder(int n) {
        this.n = n;
    }

    public void assertNormality() {
        if (n != n) {
            throw new AssertionError("This statement is false");
        }
    }
}
