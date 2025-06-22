# JAVA_Lib6   学生成绩管理系统

## 项目概述

本系统是一个基于Java的学生成绩管理系统，采用面向对象设计，实现了学生信息的录入、显示、统计和文件存储功能。系统提供友好的图形界面，支持输入合法性检查，并能自动生成学号。
![PixPin_2025-06-22_22-24-50](https://github.com/user-attachments/assets/f575ac5d-021c-4e2c-b33b-a5fb0c808b81)
![PixPin_2025-06-22_22-25-10](https://github.com/user-attachments/assets/9febbb71-fd5a-4f54-8bc1-ef1a92ad5829)
![PixPin_2025-06-22_22-26-41](https://github.com/user-attachments/assets/c72f44a9-889f-4a75-bd0b-426fb5edcec7)
![PixPin_2025-06-22_22-26-55](https://github.com/user-attachments/assets/abd53d9f-91cd-4e60-b339-78ddaef6f270)
![PixPin_2025-06-22_22-27-38](https://github.com/user-attachments/assets/340ac329-8565-446b-a401-9e2924c9a06b)

## 功能特点

1. **学生信息管理**：支持添加、显示学生信息
2. **自动学号生成**：从"221000"开始自动分配唯一学号
3. **输入验证**：对出生日期和成绩进行合法性检查
4. **成绩统计**：计算平均分、最高分和最低分
5. **数据持久化**：将学生信息保存到文件并支持备份
6. **图形界面**：直观友好的用户界面，便于操作

## 技术实现

- **面向对象设计**：使用Student类封装学生信息
- **异常处理**：确保输入数据的有效性
- **文件操作**：实现数据的存储和备份
- **Swing界面**：提供可视化操作界面

## 系统结构

- **Student类**：存储学生信息，包括学号、姓名、出生日期和成绩
- **MyDate类**：封装日期信息，提供日期合法性检查
- **ScoreStatistics类**：计算成绩统计数据
- **FileHandler类**：处理文件操作，包括保存和备份
- **StudentManagementGUI类**：图形用户界面
- **StudentGradeManagement类**：主程序入口

## 使用说明

1. 运行程序后，输入学生数量并点击"添加学生"按钮
2. 在弹出的对话框中输入学生信息（姓名、出生日期、Java成绩）
3. 系统会自动验证输入的合法性，如有错误会提示重新输入
4. 输入完成后，点击"显示学生信息"查看所有学生的详细信息和成绩统计
5. 点击"保存到文件"将信息保存到StuInfo.txt并备份到Backup.txt

## 运行环境

- Java JDK 8或以上版本
- 支持Swing的图形界面环境

## 安装步骤

1. 确保已安装Java开发环境（JDK）
2. 下载项目源代码
3. 使用Java编译器编译所有.java文件
4. 运行StudentGradeManagement类

## 文件结构

- StuInfo.txt：保存学生信息和统计数据
- Backup.txt：自动生成的备份文件

## 注意事项

- 输入的出生日期必须为有效日期（如2000-01-31）
- 成绩必须在0-100之间
- 程序会自动覆盖已存在的StuInfo.txt和Backup.txt文件
