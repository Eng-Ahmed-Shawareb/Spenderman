
create table Cycle(
    ID int identity(1 , 1) primary key , 
    FK_UserID int not null , 
    budget_amount decimal(10 , 2) not null, 
    start_date date not null, 
    end_date date not null, 
    state varchar(20) , 
    foreign key(FK_UserID) references SystemUser(ID) on delete cascade ,
    check(end_date >= start_date) , 
    check(budget_amount >= 0.0)
)