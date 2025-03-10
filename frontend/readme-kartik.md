for front-end i have modified just

Login.js and Signup.js

in both the files we just change the target as http://<public-ip>:port/login or /signin that's it

Mostly the image build fails because of size it is taking much memory so just give 

t3.small or t3.medium and EBS volumes size of 10-12 gb better

and the mysql  database we are creating in the vm so we have to follow these steps

1. sudo apt install mysql-server #not any other db service
2. we have to create the user with localhost if we are running application on vm if we are using the container we have to give user host as PRIVATE IP of vm
3. like this for container if it is localhost
"DB_HOST = "172.17.0.1" #"localhost"
DB_USER = "kartikuser"
DB_PASSWORD = "password"
DB_NAME = "kartik_database"
4. we have to give the all permissions to user means we have to GRANT THE PERMISSIONS
5. we have to change the "bind address" in mysql config file
6. /etc/mysql/mysql.conf.d/mysqld.conf and change the bind address here like this
bind-address		= 0.0.0.0
mysqlx-bind-address	= 127.0.0.1
7. granting like this 
GRANT ALL PRIVILEGES ON kartik_database.* TO 'kartikuser'@'%' IDENTIFIED BY 'your_password';
FLUSH PRIVILEGES;
8. i have added "cryptography" in requiremets.txt it is necessary for container application connection with mysql database in local vm 

cryptography + requirements.txt + conatainer connection with mysql
