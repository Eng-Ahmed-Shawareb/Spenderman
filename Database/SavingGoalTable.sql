
create table SavingGoal(
    ID int identity(1 , 1) primary key , 
    FK_UserID int not null ,
    goal_name nvarchar(30) not null , 
    current_amount decimal(10 , 2) not null , 
    target_amount decimal(10 , 2) not null , 
    target_date date , 
    state varchar(20) , 
    foreign key(FK_UserID) references SystemUser(ID) on delete cascade , 
    check(current_amount >= 0.0) , 
    check(target_amount > 0)
)