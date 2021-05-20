drop database if exists infyshop_orderdb;
create schema infyshop_orderdb;
use infyshop_orderdb;

create table ordertable
(
	order_id integer primary key AUTO_INCREMENT,
	buyer_id integer,
	amount FLOAT,
	date date,
	address varchar(50),
	status varchar(10)
);
a
create table products_ordered
(
	buyer_id integer,
	prod_id integer,
	seller_id integer not null,
	quantity integer not null,
	primary key(buyer_id,prod_id)
);