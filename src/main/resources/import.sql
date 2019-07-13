INSERT INTO `authority`
VALUES (1, 'ROLE_ADMIN', '管理员'),
       (2, 'ROLE_SALES', '销售员'),
       (3, 'ROLE_USER', '顾客');



insert into scatalog(id, name)
values (1, '投资置业'),
       (2, '海外移民'),
       (3, '海外留学'),
       (4, '医疗健康'),
       (5, '教育培训');

insert into scatalog2(id, name, s_catalog_id)
values (1,'泰国房产',1),
       (2,'中国房产',1),
       (3,'美国房产',1);
