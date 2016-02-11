

select floor((1 + (rand() * 
(select count(0) from `surnames`Where (race="fr") )
))) AS `surname_id` 
from 
surnames
Where (race="fr")
