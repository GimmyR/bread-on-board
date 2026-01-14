INSERT INTO Account(id, username, mail_address, password, admin) 
values
	(ACCOUNT_SEQ.NEXTVAL, 'adawong', 'adawong@example.com', '$2a$12$UukSkD0F9zIsA8tRRiaHk.0Gh9pAlYIrR9YvwleLcvkth311y.qCa', 0);

	
INSERT INTO Recipe(id, account_id, title, image, ingredients)
values
	(RECIPE_SEQ.NEXTVAL, 
	SELECT id FROM Account WHERE username = 'adawong', 
	'Tartiflette', '37.jpg', 
	'1 kg (2.2 lb) potatoes, peeled and sliced, 200g (7 oz) bacon lardons, 1 large onion, thinly sliced, 250g (9 oz) Reblochon cheese, halved horizontally, 200ml (3/4 cup) heavy cream, 2 tablespoons butter, salt and pepper to taste');
	
	
INSERT INTO Recipe_Step(id, recipe_id, step_order, text)
VALUES (
	RECIPE_STEP_SEQ.NEXTVAL,
	SELECT id FROM Recipe WHERE title = 'Tartiflette',
	1,
	'Preheat the oven to 200°C (400°F).'
);
INSERT INTO Recipe_Step(id, recipe_id, step_order, text)
VALUES (
	RECIPE_STEP_SEQ.NEXTVAL,
	SELECT id FROM Recipe WHERE title = 'Tartiflette',
	2,
	'Boil the sliced potatoes in salted water for 10 minutes, then drain and set aside.'
);