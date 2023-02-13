drop database if exists alpaca;
create database alpaca;
use alpaca;

-- tables --
create table user (
	id int primary key auto_increment,
    username varchar(20),
    password varchar(200),
    role int, -- 1 = admin && 2 = user 
    is_logged_in boolean
)engine = InnoDB;

create table task (
	id int primary key auto_increment,
    name varchar(20),
    deadline date,
    priority_level int, -- 1 = low && 2 = medium && 3 = high
    description varchar(100),
    finished bool,
    parent_id int,
    user_id int,
    
    constraint task_parent_id foreign key (parent_id) references task(id),
    constraint task_user_id foreign key (user_id) references user(id)
)engine = InnoDB;

create table note(
	id int primary key auto_increment,
    name varchar(50),
    content varchar(500),
    task_id int,
    user_id int,
    
    constraint note_user_id foreign key (user_id) references user(id),
    constraint note_task_id foreign key (task_id) references task(id)
)engine = InnoDB;

-- indices --
create index task_parent_id
on task(parent_id)

