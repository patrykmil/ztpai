-- Avatar inserts
INSERT INTO public.avatar (avatar_id, avatar_path)
VALUES (1, 'basic_green.svg');
INSERT INTO public.avatar (avatar_id, avatar_path)
VALUES (2, 'basic_orange.svg');
INSERT INTO public.avatar (avatar_id, avatar_path)
VALUES (3, 'basic_purple.svg');
INSERT INTO public.avatar (avatar_id, avatar_path)
VALUES (4, 'glasses_green.svg');
INSERT INTO public.avatar (avatar_id, avatar_path)
VALUES (5, 'glasses_orange.svg');
INSERT INTO public.avatar (avatar_id, avatar_path)
VALUES (6, 'glasses_purple.svg');
INSERT INTO public.avatar (avatar_id, avatar_path)
VALUES (7, 'hair_green.svg');
INSERT INTO public.avatar (avatar_id, avatar_path)
VALUES (8, 'hair_orange.svg');
INSERT INTO public.avatar (avatar_id, avatar_path)
VALUES (9, 'hair_purple.svg');

-- Type inserts
INSERT INTO public.type (type_id, type_name)
VALUES (1, 'button');
INSERT INTO public.type (type_id, type_name)
VALUES (2, 'input');
INSERT INTO public.type (type_id, type_name)
VALUES (3, 'checkbox');
INSERT INTO public.type (type_id, type_name)
VALUES (4, 'radio button');

-- Color inserts
INSERT INTO public.color (color_id, hex)
VALUES (1, '00F0FF');
INSERT INTO public.color (color_id, hex)
VALUES (2, '13A823');
INSERT INTO public.color (color_id, hex)
VALUES (3, 'FFFFFF');
INSERT INTO public.color (color_id, hex)
VALUES (4, 'FF5733');
INSERT INTO public.color (color_id, hex)
VALUES (5, 'C70039');
INSERT INTO public.color (color_id, hex)
VALUES (6, '900C3F');
INSERT INTO public.color (color_id, hex)
VALUES (7, '581845');
INSERT INTO public.color (color_id, hex)
VALUES (8, 'DAF7A6');
INSERT INTO public.color (color_id, hex)
VALUES (9, '9f29e5');
INSERT INTO public.color (color_id, hex)
VALUES (10, 'd43ad4');
INSERT INTO public.color (color_id, hex)
VALUES (11, '6a5ed3');
INSERT INTO public.color (color_id, hex)
VALUES (12, '5ed398');
INSERT INTO public.color (color_id, hex)
VALUES (13, '3D85C6');
INSERT INTO public.color (color_id, hex)
VALUES (14, 'F1C232');
INSERT INTO public.color (color_id, hex)
VALUES (15, '38761D');
INSERT INTO public.color (color_id, hex)
VALUES (16, 'CC0000');
INSERT INTO public.color (color_id, hex)
VALUES (17, '674EA7');
INSERT INTO public.color (color_id, hex)
VALUES (18, '073763');

-- Tag inserts
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (1, 'simple', 1);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (2, 'fancy', 2);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (3, 'colorless', 3);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (4, 'responsive', 4);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (5, 'animated', 5);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (6, 'modest', 6);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (7, 'modern', 8);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (8, 'classic', 8);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (9, 'dark', 7);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (10, 'light', 2);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (11, 'flat', 3);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (12, 'gradient', 4);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (13, 'shadow', 5);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (14, 'bordered', 6);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (15, 'rounded', 13);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (16, 'outlined', 8);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (17, 'transparent', 1);
INSERT INTO public.tag (tag_id, tag_name, color_id)
VALUES (18, 'solid', 2);