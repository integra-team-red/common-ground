CREATE TABLE system_feedback (
        "id" UUID not null,
        "email" VARCHAR(100) NOT NULL,
        "message" VARCHAR(100) NOT NULL,
        "created_at" timestamp
);
