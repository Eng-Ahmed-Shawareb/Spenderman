
create table UserTransaction(
    ID int identity(1 , 1) primary key , 
    FK_SavingGoalID int , 
    FK_WalletID int , 
    FK_CategoryID int , 
    amount decimal(10 , 2) not null , 
    type varchar(20) not null , 
    note nvarchar(150) , 
    transaction_date datetime not null , 
    foreign key(FK_SavingGoalID) references SavingGoal(ID) , 
    foreign key(FK_WalletID) references Wallet(ID) on delete cascade , 
    foreign key(FK_CategoryID) references Category(ID), 
    check(amount >= 0.0) , 
    check (
            (FK_WalletID is not null and FK_SavingGoalID is null) 
            or 
            (FK_WalletID is null and FK_SavingGoalID is not null)
          )
)