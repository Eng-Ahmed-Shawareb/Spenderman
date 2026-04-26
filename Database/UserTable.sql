
create table SystemUser(
    ID int identity(1 , 1) primary key , 
    username varchar(30) unique not null , 
    user_password varchar(100) not null ,
    first_name nvarchar(50) , 
    last_name nvarchar(50) , 
    created_date datetime default getdate() 
)