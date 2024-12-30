create table if not exists  role_permission
(
    id  bigint generated by default as identity
        constraint role_permission_pk
            primary key,
    role_id       bigint
        constraint role_permission_role_id_fk
            references role,
    permission_id bigint
        constraint role_permission_permission_id_fk
            references permission
);

