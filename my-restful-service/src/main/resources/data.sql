insert into users(id, join_Date, name, password, ssn) values(90001, now(), 'User1', 'test1', '701010-11111111')
insert into users(id, join_Date, name, password, ssn) values(90002, now(), 'User2', 'test2', '801010-11111111')
insert into users(id, join_Date, name, password, ssn) values(90003, now(), 'User3', 'test3', '901010-11111111')

insert into post(description, user_id) values('first', 90001)
insert into post(description, user_id) values('second', 90002)