select count(*) as repeats from charmission where (charuid = '101' and missionuid='1')

select 
   missiondefs.uid,
   missiondefs.title,
   missiondefs.text,
   missiondefs.type,
   missiondefs.conditions,
   missiondefs.rewards,
   missiondefs.giver,
   missiondefs.handin,
   missiondefs.prereq,
   missiondefs.completetext
from
    charmission,
    missiondefs
where
    (missiondefs.uid not in(select missionuid from charmission)) and charmission.charuid = 101 and  ( missiondefs.prereq = -1)
union
select 
      missiondefs.uid,
   missiondefs.title,
   missiondefs.text,
   missiondefs.type,
   missiondefs.conditions,
   missiondefs.rewards,
   missiondefs.giver,
   missiondefs.handin,
   missiondefs.prereq,
   missiondefs.completetext
from
    charmission,
    missiondefs
where
    (missiondefs.uid not in(select missionuid from charmission)) and charmission.charuid = 101 and charmission.inprogress=2 and  (missiondefs.prereq = charmission.missionuid)