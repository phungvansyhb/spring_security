CREATE TABLE public.audit_log (
                               id bigint generated by default as identity
                                   primary key,
                               method varchar NULL,
                               ip varchar NULL,
                               time varchar NULL,
                               api_path varchar NULL,
                               parameters date NULL,
                               body varchar NULL,
                               response varchar NULL
);