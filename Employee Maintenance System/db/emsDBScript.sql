User_Master TABLE:
 
create table User_Master(UserId VARCHAR2(6), UserName VARCHAR2(15), UserPassword VARCHAR2(50), UserType VARCHAR2(10), Emp_ID VARCHAR2(6) refrences Employee(Emp_ID));

Department TABLE:

create table Department(Dept_ID number, Dept_Name VARCHAR2(50));

Employee TABLE:

create table Employee(Emp_ID VARCHAR2(6) primary key, Emp_First_Name VARCHAR2(25), Emp_Last_Name VARCHAR2(25), Emp_Date_of_Birth DATE, Emp_Date_of_JoiningDATE, Emp_Dept_ID number, Emp_Grade VARCHAR2(2), Emp_Designation VARCHAR2(50), Emp_Basic number, Emp_Gender VARCHAR2(1), Emp_Marital_Status VARCHAR2(1), Emp_Home_Address VARCHAR2(100), Emp_Contact_Num VARCHAR2(15), Mgr_Id varchar2(6), foreign key(Mgr_Id) references Employee(Emp_ID),Emp_Leave_Bal Number(2));

Grade_Master TABLE:

create table Grade_Master(Grade_Code VARCHAR2(2), Description VARCHAR2(10), Min_Salary number, Max_Salary number);

Leave_History TABLE:

create table Leave_History(Leave_Id number, Emp_id VARCHAR2(6) references Employee(emp_id), date_applied date, noofdays_applied number, date_from date, date_to date, status varchar2(20) check (status in ('applied','approved','rejected')));


User_Master TABLE WITH ALL THE DATA(MAX IS THE ADMIN AND OTHERS ARE EMPLOYEES):


insert into User_Master
VALUES('1001','MAX','max123','ADMIN', '100001');

insert into User_Master
VALUES('1002','TED','ted123','EMPLOYEE', '100002');

insert into User_Master
VALUES('1003','TIM','tim123','EMPLOYEE', '100003');

insert into User_Master
VALUES('1004','MIKE','mike123','EMPLOYEE', '100004');

insert into User_Master
VALUES('1005','JACK','jack123','EMPLOYEE', '100005');

insert into User_Master
VALUES('1006','JOHN','john123','EMPLOYEE', '100006');

insert into User_Master
VALUES('1007','LUKE','luke123','EMPLOYEE', '100007');

insert into User_Master
VALUES('1008','JIM','jim123','EMPLOYEE', '100008');

insert into User_Master
VALUES('1009','LUCY','lucy123','EMPLOYEE', '100009');

insert into User_Master
VALUES('1010','JAKE','jake123','EMPLOYEE', '100010');
