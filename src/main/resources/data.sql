INSERT INTO loan_product (id, name, rate, min_term, max_term, min_amount, max_amount, currency)
	VALUES 
		(1, 'Микрозайм под минимальный процент', 1.25, 3, 6, 2000, 10000, 'RUB'),
		(2, 'Кредит наличными на еду', 0.25, 12, 60, 50000, 10000000, 'RUB'),
		(3, 'Долларовый кредит', 0.37, 12, 36, 1000, 5000, 'USD'),
		(4, 'Ипотека на всю жизнь', 0.45, 60, 120, 5000000, 40000000, 'RUB')
	ON CONFLICT (id) DO NOTHING;