CREATE TABLE email_audit
(
    id uuid PRIMARY KEY,
    recipient_email varchar(300) NOT NULL,
    subject varchar(300) NOT NULL,
    sent_at timestamp NOT NULL,
    status varchar(40) NOT NULL,
    error_message varchar(400)
)


