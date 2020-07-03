import org.junit.Test;

import static org.junit.Assert.*;

public class GroupTest {

    Group testGroup = new Group("testGroup");
    Student testStudent1 = new Student(1, "Лобанов Лазарь Альбертович");
    Student testStudent2 = new Student(2, "Селезнёв Эльдар Иосифович");
    Student testStudent3 = new Student(3, "Авдеева Георгина Владиславовна");

    @Test
    public void getHead() throws NoStudentException {
        testGroup.addStudent(testStudent1);
        testGroup.addStudent(testStudent2);
        testGroup.addStudent(testStudent3);
        testGroup.addHead(testStudent1);
        assertEquals(testStudent1, testGroup.getHead());
    }

    @Test
    public void getTitle() {
        String actual = "testGroup";
        String expected = testGroup.getTitle();
        assertEquals(expected, actual);
    }

    @Test
    public void addHead() throws NoStudentException {
        Student actual = testStudent1;
        testGroup.addStudent(testStudent1);
        testGroup.addHead(actual);
        Student expected = testGroup.getHead();
        assertEquals(expected, actual);
    }

    @Test
    public void searchStudent() throws NoStudentException {
        Student actual = testStudent3;
        testGroup.addStudent(testStudent1);
        testGroup.addStudent(testStudent2);
        testGroup.addStudent(testStudent3);
        Student expected = testGroup.searchStudent(testStudent3.getFio());
        assertEquals(expected, actual);
    }

    @Test
    public void testSearchStudent() throws NoStudentException {
        Student actual = testStudent1;
        testGroup.addStudent(testStudent1);
        testGroup.addStudent(testStudent2);
        testGroup.addStudent(testStudent3);
        Student expected = testGroup.searchStudent(testStudent1.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void averageMark() {
        testGroup.addStudent(testStudent1);
        testGroup.addStudent(testStudent2);
        testStudent1.addMark(5);
        testStudent1.addMark(5);
        testStudent1.addMark(5);
        testStudent2.addMark(3);
        testStudent2.addMark(3);
        testStudent2.addMark(3);
        float actual = testGroup.averageMark();
        float expected = 4;
        assertEquals(expected, actual, 0.00001);
    }
}