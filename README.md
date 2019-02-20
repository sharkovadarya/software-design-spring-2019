## Shell interpreter architecture-shmarchitecture:

Data-flow diagram.
![Data-flow diagram](https://raw.githubusercontent.com/karvozavr/software-design-spring-2019/hw1-cli/ShellDataDlow.png)

System decomposition.
![System decomposition](https://raw.githubusercontent.com/karvozavr/software-design-spring-2019/hw1-cli/SystemArchitecture.png)

Executable unit is a Pipeline. 
Pipeline executes commands sequentially, redirecting output of one command to input of next command.
First command receives input from stdin and the last command writes output to stdout.
Every command in pipeline is being executed in it's own shell context.

---

### Ревью архитектуры

Разделение парсера, команд, контекста друг от друга даёт возможность быстро разобраться в архитектуре. Для того, чтобы добавить новую команду, не нужно понимать, как работает парсер или `CliShell`, достаточно посмотреть на реализацию другой команды и сделать по аналогии. Отдельные классы для потоков ввода, вывода и ошибок также удобны в использовании. Использование различных exit codes в сочетании с функцией `writeError` представляется более хорошим механизмом обработки ошибок, чем использованные у меня исключения (по крайней мере, в контексте данного конкретного приложения). Вывод результата сразу в командную строку с помощью функции `writeLine` сначала показался несколько неудобным (в частности, для тестирования), но поскольку результат сохраняется в буфере потока вывода, никаких проблем с тестированием не возникло. Хотелось бы похвалить тесты, в которых для каждой команды был сделан отдельный файл, а также отдельно тестируются команды, пайплайн и парсер. В целом архитектура кажется хорошей и удобной в использовании, каких-то негативных замечаний у меня нет.