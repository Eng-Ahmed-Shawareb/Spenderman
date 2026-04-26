
create table Category(
    ID int identity(1 , 1) primary key , 
    FK_UserID int not null , 
    category_name nvarchar(30) not null , 
    type varchar(20) not null , 
    color varchar(20) , 
    foreign key(FK_UserID) references SystemUser(ID) on delete cascade
)