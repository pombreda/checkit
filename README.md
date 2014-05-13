# Developers wanted!
If you want to help us with future development, feel free to contact us. Any help is welcome.

# CheckIT!
CheckIT! is project for domain and server monitoring. It is platform independent functional prototype, which offers basic monitoring (HTTP, FTP, IMAP, SMTP, MSSQL, MySQL and PostgreSQL) and reporting (Email). It also allows an addition of further monitoring and notification functions by plugins system.

## How it works?
CheckIT! consists from more parts. The main application is web server. It is possible to set there all tests, contacts, reports etc. Administrator is able to manage all plugins for reporting and checking, see user list and edit agents. Agent is web application, which just and only does checking and sends results back to server.

Each folder is separete project:
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
Just download and compile. For **CheckIt** and **CheckItAgent** is required PosgreSQL database. Scripts for creation are included in project folders.

It is necessary to add to database following:
 * user roles
```
1 ROLE_USER
2 ROLE_ADMIN
```
 * servers (just example)
```
127.0.0.1 http://localhost:8080/CheckIt/ 50
```
 * agents add via administration
