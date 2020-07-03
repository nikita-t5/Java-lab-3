import java.util.ArrayList;

public class Student {
    private int id;
    private String fio;
    private Group group;
    private ArrayList<Integer> marks = new ArrayList<>();

    Student(int id, String fio) {
        this.id = id;
        this.fio = fio;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getFio() {
        return fio;
    }

    public int getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    public ArrayList<Integer> getMarks() {
        return marks;
    }

    public void addMark(int mark) {
        this.marks.add(mark);
    }

    public float averageMark() {
        float total = 0;
        for (Integer i : marks) {
            total += i;
        }
        return total / marks.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Student stu = (Student) obj;
        return id == stu.id && (fio.equals(stu.fio) || fio.equals(stu.getFio()));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = (result * 37) + id;
        result = (result * 37) + fio.hashCode();
        return result;
    }
}
