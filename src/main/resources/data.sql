-- Insertar temas
INSERT INTO topics (id, topic) VALUES
  ('1e522b30-03d7-4c4b-bba7-5a9c65d7bf6a', 'Matemáticas'),
  ('2d80ab4d-b9d9-4971-947a-2e1a97c6357c', 'Ciencias'),
  ('1f4c7fb9-f43d-4e99-827f-7c9f8fcfb3e1', 'Historia');

-- Insertar preguntas
INSERT INTO questions (id, topic_id, question) VALUES
  ('5fc51e9b-f2d5-46d2-a5d3-f1e166a14e71', '1e522b30-03d7-4c4b-bba7-5a9c65d7bf6a', '¿Cuál es el resultado de 2 + 2?'),
  ('46f26fe1-54d0-4b7c-883a-3fe166f845ac', '2d80ab4d-b9d9-4971-947a-2e1a97c6357c', '¿Cuál es la fórmula química del agua?'),
  ('734b6150-c369-48e0-8466-7a70179f3a82', '1f4c7fb9-f43d-4e99-827f-7c9f8fcfb3e1', '¿En qué año comenzó la Segunda Guerra Mundial?');

-- Insertar respuestas
INSERT INTO answers (id, is_correct, question_id, answer) VALUES
  ('c0f9305d-5f82-4939-9148-7b8967b06e6d', true, '5fc51e9b-f2d5-46d2-a5d3-f1e166a14e71', '4'),
  ('9ff0a482-af4b-4b1f-b679-c1bce9123b16', false, '5fc51e9b-f2d5-46d2-a5d3-f1e166a14e71', '3'),
  ('59d16157-d413-4ef5-b9c5-88ed5566e78e', false, '5fc51e9b-f2d5-46d2-a5d3-f1e166a14e71', '5'),
  ('16b9d1dc-465a-4e09-81af-5eaf271cd5eb', false, '5fc51e9b-f2d5-46d2-a5d3-f1e166a14e71', '6'),
  ('c17295fb-38ec-43db-aec2-2a4db1f7cf42', true, '46f26fe1-54d0-4b7c-883a-3fe166f845ac', 'H2O'),
  ('f3e73268-646e-4dc5-88d2-63a188d62279', false, '46f26fe1-54d0-4b7c-883a-3fe166f845ac', 'O2'),
  ('4c71a0eb-019e-4907-81c3-f04c8ac04139', false, '46f26fe1-54d0-4b7c-883a-3fe166f845ac', 'CO2'),
  ('e1870d4b-66e4-4c99-8353-4eb4e56fbc2e', false, '46f26fe1-54d0-4b7c-883a-3fe166f845ac', 'H'),
  ('2e4a0949-7cc8-4411-b63f-2f5f3e18a90d', false, '734b6150-c369-48e0-8466-7a70179f3a82', '1940'),
  ('a45f8a7e-08b2-4d9d-bc21-5e4ebe224e51', true, '734b6150-c369-48e0-8466-7a70179f3a82', '1939'),
  ('df7e6e32-8a0b-4e42-8961-2d4396602b79', false, '734b6150-c369-48e0-8466-7a70179f3a82', '1938'),
  ('b6a7d314-67b0-4cb9-9249-7430e2cf6e15', false, '734b6150-c369-48e0-8466-7a70179f3a82', '1941');
