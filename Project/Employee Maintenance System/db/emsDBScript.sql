User_Master TABLE:
 
CREATE TABLE User_Master(UserId VARCHAR2(6) PRIMARY KEY, UserName VARCHAR2(15), UserPassword VARCHAR2(50), 
UserType VARCHAR2(10), Emp_ID VARCHAR2(6) REFERENCES Employee(Emp_ID));

Department TABLE:

CREATE TABLE Department(Dept_ID NUMBER, Dept_Name VARCHAR2(50));
INSERT INTO Department VALUES(121, 'JEE');
INSERT INTO Department VALUES(122, 'BI');
INSERT INTO Department VALUES(123, 'SAP');
INSERT INTO Department VALUES(124, 'PEGA');
Employee TABLE:

CREATE TABLE Employee(Emp_ID VARCHAR2(6) PRIMARY KEY, Emp_First_Name VARCHAR2(25), 
Emp_Last_Name VARCHAR2(25), Emp_Date_of_Birth DATE, Emp_Date_of_Joining DATE, Emp_Dept_ID NUMBER, Emp_Grade VARCHAR2(2), 
Emp_Designation VARCHAR2(50), Emp_Basic NUMBER, Emp_Gender VARCHAR2(1), Emp_Marital_Status VARCHAR2(10), Emp_Home_Address VARCHAR2(100), 
Emp_Contact_Num VARCHAR2(15), Mgr_Id VARCHAR2(6), Emp_Leave_Bal NUMBER(2), FOREIGN KEY(Mgr_Id) REFERENCES Employee(Emp_ID));

Grade_Master TABLE:

CREATE TABLE Grade_Master(Grade_Code VARCHAR2(2), Description VARCHAR2(10), Min_Salary NUMBER, Max_Salary NUMBER);

Leave_History TABLE:

CREATE TABLE Leave_History(Leave_Id NUMBER, Emp_id VARCHAR2(6) REFERENCES Employee(emp_id), date_applied DATE, noofdays_applied NUMBER, 
date_from DATE, date_to DATE, status VARCHAR2(20) CHECK (status IN ('Applied','Approved','Rejected')));


User_Master TABLE WITH ALL THE DATA(MAX IS THE ADMIN AND OTHERS ARE EMPLOYEES):


INSERT INTO User_Master
VALUES('1001','SUKU','suku123','EMPLOYEE', '100001');

INSERT INTO User_Master
VALUES('1002','JAKA','jaka123','EMPLOYEE', '100002');

INSERT INTO User_Master
VALUES('1003','JOHO','joho123','EMPLOYEE', '100003');

INSERT INTO User_Master
VALUES('1004','RAHU','rahu123','ADMIN', '100004');

INSERT INTO User_Master
VALUES('1005','JACK','jack123','EMPLOYEE', '100005');

INSERT INTO User_Master
VALUES('1006','JOHN','john123','EMPLOYEE', '100006');

INSERT INTO User_Master
VALUES('1007','LUKE','luke123','EMPLOYEE', '100007');

INSERT INTO User_Master
VALUES('1008','JIM','jim123','EMPLOYEE', '100008');

INSERT INTO User_Master
VALUES('1009','LUCY','lucy123','EMPLOYEE', '100009');

INSERT INTO User_Master
VALUES('1010','JAKE','jake123','EMPLOYEE', '100010');

insert into leave_history values(seq.curr_val, '100004', to_date('2018-10-09', 'yyyy-MM-dd'), 2, TO_DATE('2018-10-15','yyyy-MM-dd'), TO_DATE('2018-10-16','yyyy-MM-dd'), 'Applied');

yyyy-MM-dd

INSERT INTO Employee VALUES ( '100004', 'Rahul', 'Kumar', TO_DATE('1995-08-25','yyyy-MM-dd'), TO_DATE('2018-08-01','yyyy-MM-dd'), 10 ,'M1','Manager', 500000, 'M', 'Married' , 'Whitefield','4885291467', '100004', 9 );

INSERT INTO Employee VALUES ( '100001', 'Suresh', 'Kumar', TO_DATE('1995-08-25','yyyy-MM-dd'), TO_DATE('2018-08-01','yyyy-MM-dd'), 10 ,'M1','Manager', 500000, 'M', 'Married' , 'Whitefield','4885291467', '100001', 9 );

INSERT INTO Employee VALUES ( '100002', 'Jaspreet', 'Kaur', TO_DATE('1996-10-17','yyyy-MM-dd'), TO_DATE('2018-07-01','yyyy-MM-dd'), 11 ,'M4','Senior Analyst', 400000, 'F', 'Single' , 'Bellandur','9453394101', '100001', 5);

INSERT INTO Employee VALUES ( '100003', 'Johnson', 'Holmes', TO_DATE('1996-01-25','yyyy-MM-dd'), TO_DATE('2018-09-01','yyyy-MM-dd'), 12 ,'M3','Associate Consultant', 450000, 'M', 'Divorced' , 'Electronic City ','9988174321', '100001',15);
