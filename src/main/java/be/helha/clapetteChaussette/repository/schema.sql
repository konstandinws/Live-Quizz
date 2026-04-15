CREATE TABLE "teacher" (
  "uuid" UUID PRIMARY KEY,
  "name" TEXT NOT NULL,
  "password_hash" TEXT NOT NULL,
  "created_at" TIMESTAMP DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "teacher_questionnaire" (
  "teacher_uuid" UUID NOT NULL,
  "questionnaire_uuid" UUID NOT NULL
);

CREATE TABLE "questionnaire" (
  "quuid" UUID PRIMARY KEY,
  "teacher_uuid" UUID NOT NULL,
  "name" TEXT NOT NULL,
  "created_at" TIMESTAMP DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "question" (
  "quuid" UUID PRIMARY KEY,
  "questionnaire_uuid" UUID NOT NULL,
  "content" TEXT NOT NULL,
  "type" TEXT NOT NULL,
  "created_at" TIMESTAMP DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "reponse_question" (
  "question_uuid" UUID NOT NULL,
  "reponse" TEXT NOT NULL,
  "bonne_reponse" BOOLEAN NOT NULL
);

ALTER TABLE "teacher_questionnaire" ADD FOREIGN KEY ("teacher_uuid") REFERENCES "teacher" ("uuid");

ALTER TABLE "teacher_questionnaire" ADD FOREIGN KEY ("questionnaire_uuid") REFERENCES "questionnaire" ("quuid");

ALTER TABLE "questionnaire" ADD FOREIGN KEY ("teacher_uuid") REFERENCES "teacher" ("uuid");

ALTER TABLE "question" ADD FOREIGN KEY ("questionnaire_uuid") REFERENCES "questionnaire" ("quuid");

ALTER TABLE "reponse_question" ADD FOREIGN KEY ("question_uuid") REFERENCES "question" ("quuid");

