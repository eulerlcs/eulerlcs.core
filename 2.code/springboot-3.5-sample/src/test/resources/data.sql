truncate table "user";
INSERT INTO "user" (username, password, email, created_at) VALUES
  ('alice', 'alice123', 'alice@example.com', '2026-01-01 10:00:00'),
  ('bob', 'bob123', 'bob@example.com', '2026-01-02 10:00:00'),
  ('charlie', 'charlie123', 'charlie@example.com', '2026-01-03 10:00:00');
