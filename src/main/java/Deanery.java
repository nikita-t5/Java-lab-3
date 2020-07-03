import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class Deanery {
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Group> groups = new ArrayList<>();

    public Deanery(String applicantsList, String groupsList) {
        try {
            File fGroups = new File(groupsList);
            String content = FileUtils.readFileToString(fGroups, "utf-8");
            JSONArray itemsGroups = new JSONArray(content);
            for (int i = 0; i < itemsGroups.length(); i++) {
                JSONObject jGroup = (JSONObject) itemsGroups.get(i);
                String title = (String) jGroup.get("name of group");
                Group group = new Group(title);
                groups.add(group);
            }
            File fStudents = new File(applicantsList);
            JSONParser parserStudents = new JSONParser();
            FileReader frStudents = new FileReader(fStudents);
            Object objStudents = parserStudents.parse(frStudents);
            org.json.simple.JSONObject jObjStudents = (org.json.simple.JSONObject) objStudents;
            org.json.simple.JSONArray itemsStudents = (org.json.simple.JSONArray) jObjStudents.get("List of Applicants");
            int id = 1;
            for (Object i : itemsStudents) {
                org.json.simple.JSONObject jObjI = (org.json.simple.JSONObject) i;
                String nameOfStudent = (String) jObjI.get("name of candidate");
                Student student = new Student(id++, nameOfStudent);
                addMarksToStudent(student);
                int randGroup = (int) (Math.random() * (groups.size()));
                student.setGroup(groups.get(randGroup));
                groups.get(randGroup).addStudent(student);
                students.add(student);
            }
            for (Group i : groups) {
                chooseHead(i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoStudentException e) {
            e.printStackTrace();
        }
    }

    public void addMarksToStudent(Student student) {
        for (int i = 0; i < 10; i++) {
            int mark = (int) (2 + Math.random() * 4);
            student.addMark(mark);
        }
    }

    public void chooseHead(Group group) throws NoStudentException {
        while (true) {
            int randIndex = (int) (Math.random() * students.size());
            Student randStudent = students.get(randIndex);
            if (randStudent.getGroup().equals(group)) {
                group.addHead(randStudent);
                break;
            }
        }
    }

    public void chooseHead(String groupTitle) throws NoStudentException {
        while (true) {
            int randIndex = (int) (Math.random() * students.size());
            Student randStudent = students.get(randIndex);
            if (randStudent.getGroup().getTitle().equals(groupTitle)) {
                randStudent.getGroup().addHead(randStudent);
                break;
            }
        }
    }

    public Group findGroup(String title) throws NoGroupException {
        Group foundGroup = null;
        for (Group group : groups) {
            String groupTitle = group.getTitle();
            if (groupTitle.equals(title))
                foundGroup = group;
        }
        if (foundGroup != null)
            return foundGroup;
        else throw new NoGroupException("A group not found");
    }

    public void changeGroupForStudent(int id, String GroupTitleTo) {
        Student student;
        for (Group i : groups) {
            try {
                student = i.searchStudent(id);
                student.getGroup().eliminationStudent(id);
                Group newGroup = findGroup(GroupTitleTo);
                if ((student.getGroup().getHead().getId() == id) && (newGroup.getHead().getId() != id)) {
                    chooseHead(student.getGroup());
                }
                student.setGroup(newGroup);
                newGroup.addStudent(student);
                return;
            } catch (NoStudentException e) {
            } catch (NoGroupException e) {
            }
        }
    }

    public void changeGroupForStudent(String fio, String GroupTitleTo) {
        Student student;
        for (Group i : groups) {
            try {
                student = i.searchStudent(fio);
                student.getGroup().eliminationStudent(fio);
                Group newGroup = findGroup(GroupTitleTo);
                if ((student.getGroup().getHead().getFio().equals(fio)) && (!newGroup.getHead().getFio().equals(fio))) {
                    chooseHead(student.getGroup());
                }
                student.setGroup(newGroup);
                newGroup.addStudent(student);
                return;
            } catch (NoStudentException e) {
            } catch (NoGroupException e) {
            }
        }
    }

    public void eliminationStudentForMarks(double minMark) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).averageMark() < minMark) {
                students.get(i).getGroup().eliminationStudent(students.get(i).getId());
                students.remove(students.get(i));
                i--;
            }
        }
    }

    public void printInfo() {
        for (Group group : groups) {
            System.out.println("-----------------------------------------------------------------");
            System.out.println("info about group: " + group.getTitle());
            System.out.println("head: " + group.getHead().getFio());
            System.out.println("average mark: " + group.averageMark() + '\n');
            for (Student student : students) {
                if (group.equals(student.getGroup())) {
                    System.out.println("     fio: " + student.getFio());
                    System.out.println("     ID:" + student.getId());
                    System.out.println("     average mark of student: " + student.averageMark());
                    System.out.println("     marks: " + student.getMarks() + '\n');
                }
            }
        }

    }

    public void createJSONFile() {
        JSONObject createdList = new JSONObject();
        JSONArray list = new JSONArray();
        createdList.put("List of students", list);
        for (Group group : groups) {
            JSONObject groupJsonObject = new JSONObject();
            groupJsonObject.put("name of group", group.getTitle());
            JSONArray groupJsonArray = new JSONArray();
            for (Student student : students) {
                if (student.getGroup().equals(group)) {
                    JSONObject studentJsonObject = new JSONObject();
                    studentJsonObject.put("FIO", student.getFio());
                    studentJsonObject.put("ID", student.getId());
                    studentJsonObject.put("Average mark", student.averageMark());
                    groupJsonArray.put(studentJsonObject);
                }
            }
            groupJsonObject.put("students", groupJsonArray);
            list.put(groupJsonObject);
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/Output.json"))) {
            bw.write(createdList.toString(3));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}