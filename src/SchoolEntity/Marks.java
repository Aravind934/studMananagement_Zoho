package SchoolEntity;

public class Marks {
    public int tamil;
    public int english;
    public int maths;
    public int science;
    public int socialScience;
    private int total;

    public Marks(int t, int e, int m, int s, int ss) {
        this.tamil = t;
        this.english = e;
        this.maths = m;
        this.science = s;
        this.socialScience = ss;
        this.setTotal(t + e + m + s + ss);
    }

    private void setTotal(int total) {
        this.total = total;
    }

    public String toString() {
        return " Tamil: " + this.tamil + " English: " + this.english + " Maths: " + this.maths + " Science: " + this.science + " Social science: " + this.socialScience;
    }
    public double getTotal() {
        return this.total;
    }
}
