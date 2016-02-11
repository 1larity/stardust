select floor((1 + (rand() * (select count(0) from `firstnames`Where (sex="f" and race="fr"))))) AS `firstname_id` from firstnames Where (sex="f" and race="fr")


