
create table Wallet(
    ID int identity(1 , 1) primary key , 
    FK_UserID int not null, 
    wallet_name nvarchar(50) not null, 
    balance decimal(10 , 2) ,
    foreign key (FK_UserID) references SystemUser(ID) on delete cascade
)