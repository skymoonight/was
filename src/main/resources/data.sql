insert into lights (select id,dated,lat,lng,named,own from CSVREAD('D:\Study\인프런\springboot\was2\src\main\resources\lightwithU2.csv'));

