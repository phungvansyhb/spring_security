create table if not exists  permission
(
    id                     bigint generated by default as identity
        constraint permission_pk
            primary key,
    permission_code        varchar not null,
    permission_description varchar
);