# Wells
Rest web service Wells позволяет получить данные из файлов с массивом JSON или из БД Postgres с названиями и координатамами скважин,
департаментов и значений параметров скважин. 

## Endpoints 
- Ендпоинт "/parameters" выводит в лог и в файл список названий всех уникальных параметров скважин.
- Ендпоинт "/values" для скважин с id от и до указанных в параметрах запроса (или от 10 до 30 id по умолчанию) выводит в лог название скважины, 
а также название и минимальное, максимальное и среднее арифметическое значения каждого параметра.
- Ендпоинт "/departments" выводит в лог название скважины и её принадлежность к месторождению.

## Запуск сервиса Wells
- Для обновления пути файлов и самих файлов во время работы приложения используется Spring Cloud.
- Для этого требуется запустить приложение well-server: https://github.com/Slipeer13/wells-server
- В файле application.properties приложения well-server указывается репозиторий откуда будут браться пути к файлам, в данном случае Git и указывается порт на котором
будет работать сервер.
- Для обновления конфигурации приложения- клиента Wells необходимо выполнить POST(body = none) запрос на ендпоинт "actuator/refresh", 
после этого настройки приложения обновятся 
### Скрипты для PostgresSql
CREATE TABLE public.parameter
(
    well_id integer NOT NULL,
    parameter_name varchar(255) NOT NULL,
    value double precision NOT NULL,
	PRIMARY KEY(well_id, parameter_name, value)
);
CREATE TABLE public.well
(
    id integer NOT NULL,
    name varchar(255) NOT NULL,
    x double precision,
    y double precision,
	PRIMARY KEY(id)
);
CREATE TABLE public.department
(
    name varchar(255) NOT NULL,
    x double precision NOT NULL,
    y double precision NOT NULL,
    radius double precision NOT NULL,
	PRIMARY KEY(name)
);
