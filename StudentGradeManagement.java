import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// 日期类
class MyDate {
    private int year;
    private int month;
    private int day;

    public MyDate(int year, int month, int day) {
        if (!isValidDate(year, month, day)) {
            throw new IllegalArgumentException("无效的日期");
        }
        this.year = year;
        this.month = month;
        this.day = day;
    }

    private boolean isValidDate(int year, int month, int day) {
        if (month < 1 || month > 12) return false;
        if (day < 1) return false;

        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        // 闰年2月有29天
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            daysInMonth[1] = 29;
        }
        return day <= daysInMonth[month - 1];
    }

    public String toString() {
        return year + "-" + month + "-" + day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}

class Student {
    private static int nextId = 221000;
    private String studentId;
    private String name;
    private MyDate birthDate;
    private int score;

    public Student(String name, MyDate birthDate, int score) {
        this.studentId = String.valueOf(nextId++);
        this.name = name;
        this.birthDate = birthDate;
        this.score = score;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(MyDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("成绩必须在0-100之间");
        }
        this.score = score;
    }

    public String toString() {
        return "学号: " + studentId +
                ", 姓名: " + name +
                ", 出生日期: " + birthDate +
                ", Java成绩: " + score;
    }
}

class ScoreStatistics {
    private double average;
    private int max;
    private int min;

    public ScoreStatistics(List<Student> students) {
        if (students.isEmpty()) {
            average = 0;
            max = 0;
            min = 0;
            return;
        }

        int sum = 0;
        max = students.get(0).getScore();
        min = students.get(0).getScore();

        for (Student student : students) {
            int score = student.getScore();
            sum += score;
            if (score > max) max = score;
            if (score < min) min = score;
        }

        average = (double) sum / students.size();
    }

    public double getAverage() {
        return average;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    @Override
    public String toString() {
        return "平均分: " + String.format("%.2f", average) +
                ", 最高分: " + max +
                ", 最低分: " + min;
    }
}

class FileHandler {
    public static void saveToFile(List<Student> students, ScoreStatistics stats, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("学生成绩信息:\n");
            writer.write("====================\n");
            for (Student student : students) {
                writer.write(student.toString() + "\n");
            }
            writer.write("\n成绩统计信息:\n");
            writer.write("====================\n");
            writer.write(stats.toString());
        } catch (IOException e) {
            System.err.println("保存文件时出错: " + e.getMessage());
        }
    }

    public static void copyFile(String source, String destination) {
        try (BufferedReader reader = new BufferedReader(new FileReader(source));
             BufferedWriter writer = new BufferedWriter(new FileWriter(destination))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("复制文件时出错: " + e.getMessage());
        }
    }
}

class StudentManagementGUI extends JFrame {
    private List<Student> students = new ArrayList<>();
    private JTextArea displayArea;
    private JTextField numField;
    private JPanel inputPanel;
    private JButton addButton;
    private JButton displayButton;
    private JButton saveButton;

    public StudentManagementGUI() {
        setTitle("学生成绩管理系统");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        inputPanel = new JPanel();
        JLabel numLabel = new JLabel("学生数量:");
        numField = new JTextField(10);
        addButton = new JButton("添加学生");
        addButton.addActionListener(new AddButtonListener());

        inputPanel.add(numLabel);
        inputPanel.add(numField);
        inputPanel.add(addButton);

        displayArea = new JTextArea(20, 40);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        JPanel buttonPanel = new JPanel();
        displayButton = new JButton("显示学生信息");
        displayButton.addActionListener(new DisplayButtonListener());
        saveButton = new JButton("保存到文件");
        saveButton.addActionListener(new SaveButtonListener());

        buttonPanel.add(displayButton);
        buttonPanel.add(saveButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int n = Integer.parseInt(numField.getText().trim());
                if (n <= 0) {
                    JOptionPane.showMessageDialog(null, "学生数量必须为正整数", "输入错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                students.clear();
                for (int i = 0; i < n; i++) {
                    addStudentDialog(i + 1);
                }

                JOptionPane.showMessageDialog(null, "学生信息录入完成", "成功", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "请输入有效的学生数量", "输入错误", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void addStudentDialog(int studentNum) {
            JTextField nameField = new JTextField();
            JTextField yearField = new JTextField();
            JTextField monthField = new JTextField();
            JTextField dayField = new JTextField();
            JTextField scoreField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(5, 2));
            panel.add(new JLabel("姓名:"));
            panel.add(nameField);
            panel.add(new JLabel("出生年份:"));
            panel.add(yearField);
            panel.add(new JLabel("出生月份:"));
            panel.add(monthField);
            panel.add(new JLabel("出生日期:"));
            panel.add(dayField);
            panel.add(new JLabel("Java成绩:"));
            panel.add(scoreField);

            int result = JOptionPane.showConfirmDialog(null, panel, "输入第" + studentNum + "个学生信息",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String name = nameField.getText().trim();
                    if (name.isEmpty()) {
                        throw new IllegalArgumentException("姓名不能为空");
                    }

                    int year = Integer.parseInt(yearField.getText().trim());
                    int month = Integer.parseInt(monthField.getText().trim());
                    int day = Integer.parseInt(dayField.getText().trim());
                    MyDate birthDate = new MyDate(year, month, day);

                    int score = Integer.parseInt(scoreField.getText().trim());
                    if (score < 0 || score > 100) {
                        throw new IllegalArgumentException("成绩必须在0-100之间");
                    }

                    students.add(new Student(name, birthDate, score));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "请输入有效的数字", "输入错误", JOptionPane.ERROR_MESSAGE);
                    addStudentDialog(studentNum); // 重新输入
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "输入错误", JOptionPane.ERROR_MESSAGE);
                    addStudentDialog(studentNum); // 重新输入
                }
            } else {
                // 用户取消，减少计数
                studentNum--;
            }
        }
    }

    private class DisplayButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            displayArea.setText("");
            if (students.isEmpty()) {
                displayArea.setText("没有学生信息");
                return;
            }

            displayArea.append("学生成绩信息:\n");
            displayArea.append("====================\n");
            for (Student student : students) {
                displayArea.append(student.toString() + "\n");
            }

            ScoreStatistics stats = new ScoreStatistics(students);
            displayArea.append("\n成绩统计信息:\n");
            displayArea.append("====================\n");
            displayArea.append(stats.toString());
        }
    }

    private class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (students.isEmpty()) {
                JOptionPane.showMessageDialog(null, "没有学生信息可保存", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ScoreStatistics stats = new ScoreStatistics(students);
            FileHandler.saveToFile(students, stats, "StuInfo.txt");
            FileHandler.copyFile("StuInfo.txt", "Backup.txt");

            JOptionPane.showMessageDialog(null, "学生信息已保存到StuInfo.txt，备份到Backup.txt", "成功", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

// 主类
public class StudentGradeManagement {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentManagementGUI gui = new StudentManagementGUI();
            gui.setVisible(true);
        });
    }
}    