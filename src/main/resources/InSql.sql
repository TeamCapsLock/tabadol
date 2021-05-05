
delete from post;
delete from users;

insert into users(bio,email,firstname,image,lastname,number_of_followers,number_of_raters,password,phone,rating,skills,sum_of_total_rates,username)
values('admin','admin@admin.com','admin','admin.png','admin',0,0,'$2a$10$g7
JNa7S8uIFulotFGl/PE.si1We3WwwcPZQ43nuWBa7DnS3b2ld7m','0797000587',5,'admin',5,'admin');

insert into users(bio,email,firstname,image,lastname,number_of_followers,number_of_raters,password,phone,rating,skills,sum_of_total_rates,username)
values('admin2','admin2@admin.com','admin2','admin2.png','admin2',0,0,'$2a$10$g7
JNa7S8uIFulotFGl/PE.si1We3WwwcPZQ43nuWBa7DnS3b2ld7m','0797000587',5,'admin',5,'admin2');


insert into post (available,body,category,created_at,offer_type,type,weight,user_id) 
values (true,'1st post','music','5am','general','product',7,1);

insert into post (available,body,category,created_at,offer_type,type,weight,user_id) 
values (true,'2nd post','music','5am','general','product',6,1);

insert into post (available,body,category,created_at,offer_type,type,weight,user_id) 
values (true,'3rd post','music','5am','general','product',5,2);

insert into post (available,body,category,created_at,offer_type,type,weight,user_id) 
values (true,'4th post','music','5am','general','product',4,2);
