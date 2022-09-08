# Wells
Rest web service Wells позволяет получить данные из файлов с массивом JSON или из БД Postgres с названиями и координатамами скважин,
департаментов и значений параметров скважин. 

## Endpoints 
- Ендпоинт "/parameters" выводит в лог и в файл список названий всех уникальных параметров скважин.
- Ендпоинт "/values" для скважин с id от и до указанных в параметрах запроса (или от 10 до 30 id по умолчанию) выводит в лог название скважины, 
а также название и минимальное, максимальное и среднее арифметическое значения каждого параметра.
- Ендпоинт "/departments" 3.	Выводит в лог название скважины и её принадлежность к месторождению.

## Запуск сервиса Wells
- Для обновления пути файлов и самих файлов во время работы приложения используется Spring Cloud.
- Для этого требуется запустить приложение well-server: https://github.com/Slipeer13/wells-server
- В файле application.properties приложения well-server указывается репозиторий откуда будут браться пути к файлам, в данном случае Git и указывается порт на котором
будет работать сервер.
- Для обновления конфигурации приложения- клиента Well необходимо выполнить POST(body = none) запрос на ендпоинт "actuator/refresh", 
после этого настройки приложения обновятся 
