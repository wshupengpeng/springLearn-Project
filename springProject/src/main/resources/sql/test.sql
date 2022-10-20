create table if not exists t_user(
    id int(11) auto_increment primary key  ,
    user_name varchar(20) comment '用户姓名'
);

insert into t_user values(null,'张飞'),
(null,'刘备'),
(null,'关羽'),
(null,'鲁肃'),
(null,'吕布'),
(null,'曹操'),
(null,'张辽'),
(null,'李四'),
(null,'王五'),
(null,'赵二麻子'),
(null,'赵二麻子1'),
(null,'赵二麻子2');