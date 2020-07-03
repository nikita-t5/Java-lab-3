import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class StudentTest {

    Student testStudent1 = new Student(1, "Лобанов Лазарь Альбертович");
    Student testStudent3 = new Student(3, "Авдеева Георгина Владиславовна");

    @Test
    public void getFio() {
        String actual = testStudent1.getFio();
        String expected = "Лобанов Лазарь Альбертович";
        assertEquals(expected, actual);
    }

    @Test
    public void getId() {
        int actual = testStudent3.getId();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    public void addMark() {
        ArrayList<Integer> actual = new ArrayList<>();
        actual.add(2);
        actual.add(3);
        actual.add(4);
        actual.add(5);
        testStudent1.addMark(2);
        testStudent1.addMark(3);
        testStudent1.addMark(4);
        testStudent1.addMark(5);
        ArrayList<Integer> expected = testStudent1.getMarks();
        assertEquals(expected, actual);
    }

    @Test
    public void averageMark() {
        testStudent1.addMark(2);
        testStudent1.addMark(3);
        testStudent1.addMark(4);
        testStudent1.addMark(5);
        float actual = (float) 3.5;
        float expected = testStudent1.averageMark();
        assertEquals(expected, actual, 0.000001);
    }

    @Test
    public void testEquals() {
        Student testStudent4 = new Student(1, "Лобанов Лазарь Альбертович");
        assertTrue(testStudent1.equals(testStudent4));
    }

    @Test
    public void testHashCode() {
        Student testStudent5 = new Student(2, "Лобанов Лазарь Альбертович");
        assertTrue(!testStudent1.equals(testStudent5));
    }
}