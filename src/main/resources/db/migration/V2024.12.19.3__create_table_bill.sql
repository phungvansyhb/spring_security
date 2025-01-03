create table bill
(
    id             bigint generated by default as identity
        primary key,
    created_date   timestamp(6),
    deleted_date   timestamp(6),
    payment_method smallint
        constraint bill_payment_method_check
            check ((payment_method >= 0) AND (payment_method <= 2)),
    price          bigint,
    updated_date   timestamp(6),
    user_id        bigint
        constraint fkqhq5aolak9ku5x5mx11cpjad9
            references "user"
);

alter table bill
    owner to postgres;

