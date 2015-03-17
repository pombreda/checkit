# Developers wanted!
If you want to help us with future development, feel free to contact us. Every help is welcome.

# CheckIT!
CheckIT! is a project for domain and server monitoring. It is an platform independent functional prototype, which offers basic monitoring (HTTP, FTP, IMAP, SMTP, MSSQL, MySQL and PostgreSQL) and reporting (Email). It also allows an addition of further monitoring and notification functions by plugins system.

## How it works?
CheckIT! consists from more parts. The main application is web server. It is possible to set there all tests, contacts, reports etc. Administrator is able to manage all plugins for reporting and checking, watch user list and edit agents. An agent is a web application, which just and only does checking and sends results back to the server.

Each folder is a separete project:
 * **CheckIt** - main application
 * **CheckItAgent** - agent for checking
 * **DatabasesCheckPlugin** - plugin for displaying results of MySQL, MSSQL and PostgreSQL checking
 * **DatabasesCheckPluginAgent** - plugin for checking MySQL, MSSQL and PostgreSQL
 * **EmailCheckPlugin** - plugin for displaying results of SMTP and IMAP checking
 * **EmailCheckPluginAgent** - plugin for checking SMTP and IMAP
 * **EmailReportPlugin** - plugin for reporting
 * **FTPCheckPlugin** - plugin for displaying results of FTP checking
 * **FTPCheckPluginAgent** - plugin for checking FTP
 * **HTTPCheckPlugin** - plugin for displaying results of HTTP checking
 * **HTTPCheckPluginAgent** - plugin for checking HTTP

## How to run it?
> Only very briefly, if you are really interested in, please contact us for more information.

Just download and compile. For **CheckIt** and **CheckItAgent** PosgreSQL database is required. Creating scripts are included in the project folders.

It is necessary to add to database following:
 * user roles
```
1 ROLE_USER
2 ROLE_ADMIN
```
 * servers (an example)
```
127.0.0.1 http://localhost:8080/CheckIt/ 50
```
 * agents will be added via administration

It is also necessary to add settings.properties files:
 * CheckIt
```
jdbc.driverClassName = org.postgresql.Driver
jdbc.url = URL
jdbc.username = USERNAME
jdbc.password = PASSWORD

mail.host = HOST
mail.port = PORT
mail.username = USERNAME
mail.password = PASSWORD
```
 * CheckItAgent
```
jdbc.driverClassName = org.postgresql.Driver
jdbc.url = URL
jdbc.username = USERNAME
jdbc.password = PASSWORD
```
 * EmailReportPlugin
```
mail.host = HOST
mail.port = PORT
mail.username = USERNAME
mail.password = PASS
```
