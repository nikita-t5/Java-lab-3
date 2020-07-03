public class Main {
    public static void main(String[] args) throws NoStudentException {
        Deanery deanery = new Deanery("src/main/resources/listOfApplicants.json", "src/main/resources/Groups.json");
        deanery.printInfo();
        deanery.changeGroupForStudent(6, "C++");
        deanery.changeGroupForStudent("Бровина Элларда Ярославовна", "C++");
        deanery.eliminationStudentForMarks(3);
        deanery.chooseHead("Java");
        deanery.chooseHead("C++");
        deanery.chooseHead("Python");
        deanery.createJSONFile();
        System.out.println("После изменений:");
        deanery.printInfo();
    }
}
