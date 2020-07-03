import java.util.ArrayList;

public class Group {
    private String title;
    private ArrayList<Student> students;
    private Student head;

    Group(String title) {
        this.title = title;
        this.students = new ArrayList<>();
        this.head = null;
    }

    public Student getHead() {
        return head;
    }

    public String getTitle() {
        return title;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void addHead(Student head) throws NoStudentException {
        if (students.contains(head))
            this.head = head;
        else
            throw new NoStudentException("A student not found");
    }

    public Student searchStudent(String fioSearchStudent) throws NoStudentException {
        for (Student i : students) {
            if (i.getFio().equals(fioSearchStudent))
                return i;
        }
        throw new NoStudentException("A student not found");
    }

    public Student searchStudent(int idSearchStudent) throws NoStudentException {
        for (Student i : students) {
            if (i.getId() == idSearchStudent)
                return i;
        }
        throw new NoStudentException("A student not found");
    }

    public float averageMark() {
        float total = 0;
        for (Student i : students) {
            total += i.averageMark();
        }
        return total / students.size();
    }

    public void eliminationStudent(String fioElStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getFio().equals(fioElStudent))
                students.remove(i);
        }
    }

    public void eliminationStudent(int idElStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == idElStudent)
                students.remove(i);
        }
    }
}
