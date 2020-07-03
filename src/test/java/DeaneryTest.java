import org.junit.Test;

import static org.junit.Assert.*;

public class DeaneryTest {
    Deanery testDeanery = new Deanery("src/main/resources/listOfApplicants.json", "src/main/resources/Groups.json");

    @Test
    public void addMarksToStudent() throws NoGroupException {
        Student student = testDeanery.findGroup("C++").getHead();
        float markBefore = student.averageMark();
        float markAfter;
        do {
            testDeanery.addMarksToStudent(student);
            markAfter = student.averageMark();
        } while (markBefore == markAfter);
        assertTrue(markBefore != markAfter);
    }

    @Test
    public void chooseHead() throws NoGroupException, NoStudentException {
        Student actualHead = testDeanery.findGroup("C++").getHead();
        Student expectedHead;
        do {
            testDeanery.chooseHead("C++");
            expectedHead = testDeanery.findGroup("C++").getHead();
        } while (actualHead.getFio().equals(expectedHead.getFio()));
        assertTrue(!expectedHead.equals(actualHead));
    }

    @Test
    public void findGroup() throws NoGroupException {
        String actual = testDeanery.findGroup("C++").getTitle();
        String expected = "C++";
        assertEquals(expected, actual);
    }

    @Test
    public void changeGroupForStudent() throws NoStudentException, NoGroupException {
        testDeanery.changeGroupForStudent("Беляков Гарри Евгеньевич", "C++");
        Student student = testDeanery.findGroup("C++").searchStudent("Беляков Гарри Евгеньевич");
        Group actual = student.getGroup();
        Group expected = testDeanery.findGroup("C++");
        assertEquals(expected, actual);
    }

    @Test
    public void eliminationStudentForMarks() throws NoGroupException {
        double minMark = 3.4;
        testDeanery.eliminationStudentForMarks(minMark);
        float averageMarkC = testDeanery.findGroup("C++").averageMark();
        float averageMarkJava = testDeanery.findGroup("Java").averageMark();
        float averageMarkPython = testDeanery.findGroup("Python").averageMark();
        float averageMarkAllGroups = (averageMarkC + averageMarkJava + averageMarkPython) / 3;
        assertTrue(minMark <= averageMarkAllGroups);
    }
}