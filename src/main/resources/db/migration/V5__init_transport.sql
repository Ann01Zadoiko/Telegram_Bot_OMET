
insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values (1,'тролейбус','№2','Парк ім. Шевченка - вул. Новосельського',
  'https://www.eway.in.ua/en/cities/odesa/routes/22',
  '29-31','29-31',
  '06:48',
  E'21:23 - від Парка ім. Шевченка\n' ||
  E'20:53 - від вул. Новосельского',
  true
);


insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(2, 'тролейбус','№3','Застава 1 – Парк ім. Шевченка',
'https://www.eway.in.ua/en/cities/odesa/routes/23','10-11', '14-15',
E'06:33 - від Застави 1\n' ||
E'06:07 - Парка ім. Шевченка',
E'22:03 - від Застава 1\n' ||
E'21:27 - Парка ім. Шевченка',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(3, 'тролейбус','№7','вул. Архітекторська – вул. Новосельського',
'https://www.eway.in.ua/en/cities/odesa/routes/25','12-13', '16-17',
'05:47',
E'21:59 - від вул. Архітекторська\n' ||
E'21:50 - від вул. Новосельського',
true
);

insert into transports
 (id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(4, 'тролейбус','№8','Суперфосфатний завод – Залізничний вокзал',
'https://www.eway.in.ua/en/cities/odesa/routes/26','6', '6-7',
'05:37',
E'21:58 - від Суперфосфтного заводу\n' ||
E'21:26 - від Залізничого вокзала',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(5, 'тролейбус','№9','вул. Інглезі – вул. Рішельєвська',
'https://www.eway.in.ua/en/cities/odesa/routes/27','10-11', '16-17',
'05:26',
E'21:33 - від вул. Інглезі\n' ||
E'22:22 - від вул. Рішельєвська',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(6, 'тролейбус','№10','вул. Інглезі – ст. Приморська',
'https://www.eway.in.ua/en/cities/odesa/routes/29','10-11', '11-12',
'05:00',
E'21:02 - від вул. Інглезі\n' ||
E'22:02 - від вул. Приморська',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(7, 'тролейбус','№12','вул. Архітекторська – вул. Центральний Аеропорт',
'https://www.eway.in.ua/en/cities/odesa/routes/31','15-16', '19-20',
'05:50',
E'21:55 - від вул. Архітекторська\n' ||
E'22:41 - від вул. Центральний Аеропорт',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(8,'трамвай','№1','з-д “Центроліт” - Чорноморського козацтва',
'https://www.eway.in.ua/en/cities/odesa/routes/2','7', '7',
E'04:51 - від Чорноморського козацтва до з-да “Центроліт”\n' ||
E'04:37 - від Чорноморського козацтва до вул. 28 бригади\n' ||
'05:15 - від вул. 28 бригади',
E'20:50 - від Чорноморського козацтва\n' ||
E'21:53 - від з-да “Центроліт”',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(9,'трамвай','№3','11-а ст. Люстдорфської дороги - вул. Пастера',
'https://www.eway.in.ua/en/cities/odesa/routes/3','15-16', '17-18',
E'04:44 - від пл. Стаосінної до 11 ст. Люстдорфскої дорого\n' ||
E'05:30 - від 11 ст. Люстдорфскої дорого\n' ||
E'06:37 - від вул. Пастера',
E'21:29 - від пл. Старосінної на 11 ст Люстдорфської дороги\n' ||
E'22:59 - від пл. Старосінної до вул. Пастера\n' ||
E'22:15 - від 11 ст Люстдорфської дороги\n' ||
E'23:22 - від вул. Пастера до пл. Старосінної',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(10,'трамвай','№5','Аркадія – Автовокзал',
'https://www.eway.in.ua/en/cities/odesa/routes/4','10-11', '14-15',
E'06:30 - від Акрадії\n' ||
E'05:47 - від Автовокзала',
E'21:40 - від Аркадія\n' ||
E'22:22 - від Автовокзал',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(11,'трамвай','№7','Чорноморського козацтва – вул. 28 бригади',
'https://www.eway.in.ua/en/cities/odesa/routes/210','6-7', '6-7',
E'04:54 - від Чорноморського козацтва\n' ||
E'05:40 - від вул. 28 бригада',
E'21:08 - від Чорноморського козацтва\n' ||
E'21:57 - від вул. 28 бригада',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(12,'трамвай','№10','Вул. І. Рабіна – Тираспольська пл.',
'https://www.eway.in.ua/en/cities/odesa/routes/8','10-11', '13-14',
'06:06',
E'22:27 - від вул. І. Рабіна\n' ||
E'21:46 - від Тираспольської пл.',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(13,'трамвай','№11','Пл. Олексіївська – Залізничний вокзал',
'https://www.eway.in.ua/en/cities/odesa/routes/9','33', '33',
'06:12',
E'21:08 - від пл. Старосінної\n' ||
E'21:24 - від пл. Олексіївська',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(14,'трамвай','№12','Херсонський сквер – Слобідський ринок',
'https://www.eway.in.ua/en/cities/odesa/routes/10','18', '18',
'05:46',
E'21:44 - від Херсонського скверу\n' ||
E'21:25 - від Слобідського риноку',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(15,'трамвай','№13','Старосінна площа – ж/м Шкільний',
'https://www.eway.in.ua/en/cities/odesa/routes/11','35-36', '35-36',
'06:09',
E'20:29 - від пл. Старосінної\n' ||
E'21:04 - від Ж/М Шкільний',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(16,'трамвай','№15','Тираспольська площа – Слобідський ринок',
'https://www.eway.in.ua/en/cities/odesa/routes/12','12', '14-15',
'05:14',
E'23:00 - від Тираспольської пл.\n' ||
E'21:59 - від Слобідського риноку',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(17,'трамвай','№17','Куликове поле – 11 ст. Великого фонтану',
'https://www.eway.in.ua/en/cities/odesa/routes/13','15-16', '15-16',
'06:00',
E'21:38 - від Куликове поле\n' ||
E'22:09 - від 11 ст. Великого фонтану',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(18,'трамвай','№18','Куликове поле – Меморіал 411 батареї',
'https://www.eway.in.ua/en/cities/odesa/routes/14','27-28', '27-28',
'05:50',
E'21:12 - від Куликове поле\n' ||
E'22:06 - від Меморіал 411 батареї',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(19,'трамвай','№20','Херсонський сквер – Хаджибейський лиман',
'https://www.eway.in.ua/en/cities/odesa/routes/16','29', '29',
'05:39',
E'21:04 - від Херсонського скверу\n' ||
E'21:49 - від Хаджибейського лиману',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(20,'трамвай','№21','Пл. Тираспольська – Застава 2',
'https://www.eway.in.ua/en/cities/odesa/routes/17','24-25', '24-25',
'05:57',
E'21:56 - від пл. Тираспольська\n' ||
E'22:11 - від Застава 2',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(21,'трамвай','№27','Старосінна площа – Переправа',
'https://www.eway.in.ua/en/cities/odesa/routes/19','15-16', '19-20',
'04:37',
E'21:05 - від пл. Старосінної до Люстдорфу\n' ||
E'21:18 - від Переправа\n' ||
E'20:03 - від пл. Старосінної до Перерави\n' ||
E'22:04 - від Люстдорфу до пл. Старосінної',
true
);

insert into transports
(id, type, number_of_track, stops_start_end, link, interval_weekdays, interval_weekend, time_start, time_end, work)
values(22,'трамвай','№28','Парк Шевченка - вул. Пастера',
'https://www.eway.in.ua/en/cities/odesa/routes/1','12-13', '15-16',
E'06:02 - від Парка ім. Шевченка\n' ||
E'06:13 - від вул. Пастера',
E'21:37 - від Парка ім. Шевченка\n' ||
E'22:09 - від вул. Пастера',
true
);

