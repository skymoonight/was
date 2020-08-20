insert into lights (select id,dated,lat,lng,named,own from CSVREAD('C:\Users\206399\IdeaProjects\was\src\main\resources\lightwithU3.csv'));
