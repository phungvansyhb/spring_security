alter table audit_log
alter column parameters type text using parameters::text;

alter table audit_log
alter column body type text using body::text;

alter table audit_log
alter column response type text using response::text;